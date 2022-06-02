package com.unionbankng.future.authorizationserver.controllers;

import com.unionbankng.future.authorizationserver.entities.Kyc;
import com.unionbankng.future.authorizationserver.entities.KycAddressVerification;
import com.unionbankng.future.authorizationserver.entities.User;
import com.unionbankng.future.authorizationserver.pojos.*;
import com.unionbankng.future.authorizationserver.repositories.KycAddressRepository;
import com.unionbankng.future.authorizationserver.repositories.KycRepository;
import com.unionbankng.future.authorizationserver.repositories.UserRepository;
import com.unionbankng.future.authorizationserver.services.KYCService;
import com.unionbankng.future.authorizationserver.services.WalletService;
import com.unionbankng.future.authorizationserver.utils.App;
import com.unionbankng.future.authorizationserver.utils.JWTUserDetailsExtractor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import retrofit2.Response;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(path = "api")
@RequiredArgsConstructor
public class KYCController {
    private final MessageSource messageSource;
    private final KYCService kycService;
    private final WalletService walletService;
    private final KycRepository kycRepository;
    private final KycAddressRepository kycAddressRepository;
    private final UserRepository userRepository;
    private final App app;


    @PostMapping("/v1/kyc/initiation")
    public APIResponse<?> initiateKYC(OAuth2Authentication authentication, @RequestParam String bvn, @RequestParam String dob) {

        app.print("initiateKYC");
        JwtUserDetail authorizedUser = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(authentication);
        User user = userRepository.findByUuid(authorizedUser.getUserUUID()).orElse(null);

        if (user == null)
            return new APIResponse("User not found", false, null);

        if (bvn == null || !app.validBvn(bvn))
            return new APIResponse("Provide user verified BVN Number", false, null);

        return walletService.createWallet(user, bvn, dob);

    }

    @PostMapping("/v1/kyc/id_verification")
    public APIResponse<String> verifyId(
            @Valid @RequestParam(value = "data") String bioData,
            @RequestParam(value = "selfieImage") MultipartFile selfieImage,
            @RequestParam(value = "idImage") MultipartFile idImage, OAuth2Authentication authentication) throws Exception {

        VerifyKycRequest verifyKycRequest = app.getMapper().readValue(bioData, VerifyKycRequest.class);
        JwtUserDetail authorizedUser = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(authentication);
        User user = userRepository.findByUuid(authorizedUser.getUserUUID()).orElse(null);

        if (user == null)
            return new APIResponse<>("Authentication Failed", false, null);

        if (user.getKycLevel() >= 2)
            return new APIResponse<>(messageSource.getMessage("101", null, LocaleContextHolder.getLocale()), false, null);


        if (user.getGender() == null && verifyKycRequest.getGender() != null && !verifyKycRequest.getGender().isEmpty())
            user.setGender(verifyKycRequest.getGender());

        if (user.getGender() != null && user.getGender().startsWith("M") || user.getGender().startsWith("m")) {
            user.setGender("Male");
        } else if (user.getGender() != null && user.getGender().startsWith("F") || user.getGender().startsWith("f")) {
            user.setGender("Female");
        }

        userRepository.save(user);

        switch (verifyKycRequest.getIdType()) {
            case "INTERNATIONAL-PASSPORT":
                return kycService.VerifyInternationalPassport(verifyKycRequest, selfieImage, idImage, user);
            case "VOTERS-CARD":
                return kycService.VerifyVotersCard(verifyKycRequest, selfieImage, idImage, user);
            case "DRIVERS-LICENSE":
                return kycService.VerifyDriverLicence(verifyKycRequest, selfieImage, idImage, user);
            case "NATIONAL-IDENTITY":
                return kycService.VerifyNIN(verifyKycRequest, selfieImage, idImage, user);
            default:
                return new APIResponse<>(messageSource.getMessage("101", null, LocaleContextHolder.getLocale()),
                        false, "The detail you provide is invalid");
        }

    }


    @PostMapping("/v1/kyc/address_verification")
    public APIResponse<String> verifyAddress(@RequestBody AddressVerificationRequestVerifyme addressVerificationRequestVerifyme, OAuth2Authentication authentication) throws Exception {
//        String[] params = new String[]{userid, "User"};
        JwtUserDetail authorizedUser = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(authentication);
        User user = userRepository.findByUuid(authorizedUser.getUserUUID()).orElse(null);

        assert user != null;
        if (user.getKycLevel() == 3 || user.getKycLevel() == 5) //Todo: Ask Rabiu what KYC level 5 is
            return new APIResponse<>(messageSource.getMessage("101", null, LocaleContextHolder.getLocale()),
                    false, "User Already verified");


        String idType = "KYC";
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String currentData = sdf.format(user.getDateOfBirth());

        // format the phone number
        String phoneNumber = user.getPhoneNumber();
        String userPhone = app.toPhoneNumber(phoneNumber);

        Applicant applicant = new Applicant();
        applicant.setDob(currentData);
        applicant.setLastname(user.getLastName());
        applicant.setIdNumber(userPhone);
        applicant.setFirstname(user.getFirstName());
        applicant.setIdType(idType); //Todo: Confirm what is the id type of "KYC"
        applicant.setPhone(userPhone);

        AddressVerificationRequestVerifyme addressVerificationRequestVerifyme1 = new AddressVerificationRequestVerifyme();
        addressVerificationRequestVerifyme1.setApplicant(applicant);
        addressVerificationRequestVerifyme1.setLga(addressVerificationRequestVerifyme.getLga());
        addressVerificationRequestVerifyme1.setState(addressVerificationRequestVerifyme.getState());
        addressVerificationRequestVerifyme1.setStreet(addressVerificationRequestVerifyme.getStreet());
        addressVerificationRequestVerifyme1.setLandmark(addressVerificationRequestVerifyme.getLandmark());

        Response<VerifyMeAddressVerificationResponse> response = kycService.getAddressVerification(addressVerificationRequestVerifyme1);
        if (!response.isSuccessful())
            return new APIResponse<>(messageSource.getMessage("101", null, LocaleContextHolder.getLocale()),
                    false, "Details Provided is invalid");

        KycAddressVerification kycAddressVerification = new KycAddressVerification();
        kycAddressVerification.setUserId(user.getUuid());
        kycAddressVerification.setBirthdate(response.body().getData().getApplicant().getBirthdate());
        kycAddressVerification.setFirstname(response.body().getData().getApplicant().getFirstname());
        kycAddressVerification.setLastname(response.body().getData().getApplicant().getLastname());
        kycAddressVerification.setGender(response.body().getData().getApplicant().getGender());
        kycAddressVerification.setMiddlename(response.body().getData().getApplicant().getMiddlename());
        kycAddressVerification.setPhone(response.body().getData().getApplicant().getPhone());
        kycAddressVerification.setIdType(response.body().getData().getApplicant().getIdType());
        kycAddressVerification.setIdNumber(response.body().getData().getApplicant().getIdNumber());
        kycAddressVerification.setPhoto(response.body().getData().getApplicant().getPhoto());

        kycAddressVerification.setResID(Long.valueOf(response.body().getData().getId()));
        kycAddressVerification.setCountry(response.body().getData().getCountry());
        kycAddressVerification.setCity(response.body().getData().getCity());
        kycAddressVerification.setLga(response.body().getData().getLga());
        kycAddressVerification.setStreet(response.body().getData().getStreet());
        kycAddressVerification.setLattitude(response.body().getData().getLattitude());
        kycAddressVerification.setLongitude(response.body().getData().getLongitude());
        kycAddressVerification.setState(response.body().getData().getState());
        kycAddressVerification.setReference(response.body().getData().getReference());
        kycAddressVerification.setStatus(response.body().getData().getStatus().getStatus());
        kycAddressVerification.setSubStatus(response.body().getData().getStatus().getSubStatus());

        kycAddressRepository.save(kycAddressVerification);
        return new APIResponse<>(messageSource.getMessage("000", null, LocaleContextHolder.getLocale()),
                true, "Address verification Request Submitted.");
    }

    @PostMapping("/v1/kyc/verifyme/webhook")
    public APIResponse<String> verifyId(@RequestBody VerifymeWebhook verifymeWebhook) {
        if (verifymeWebhook != null) {
            Long id = Long.valueOf(verifymeWebhook.getData().getId());
            KycAddressVerification kycAddressVerification = kycAddressRepository.findByResID(id).orElseThrow(null);
            User user = userRepository.findByUuid(kycAddressVerification.getUserId()).orElse(null);
            if (user != null) {

                if (verifymeWebhook.getData().getStatus().getStatus().contains("VERIFIED") && verifymeWebhook.getData().getStatus().getStatus().contains("VERIFIED")) {
//            Update Kyc detail address
                    kycAddressVerification.setStatus(verifymeWebhook.getData().getStatus().getStatus());
                    kycAddressVerification.setSubStatus(verifymeWebhook.getData().getStatus().getStatus());
                    kycAddressRepository.save(kycAddressVerification);
//            Update user kyc level
                    user.setKycLevel(3);
                    userRepository.save(user);

                    // Send Email User
                    kycService.sendKycEmailUser(user, "kyc.verification.email.level.two.upgraded", "KYC Upgrade Status");
                    return new APIResponse<>(messageSource.getMessage("000", null, LocaleContextHolder.getLocale()),
                            true, "Address verification Completed");
                } else {
                    // Update Kyc detail address
                    kycAddressVerification.setStatus(verifymeWebhook.getData().getStatus().getStatus());
                    kycAddressVerification.setSubStatus(verifymeWebhook.getData().getStatus().getStatus());
                    kycAddressRepository.save(kycAddressVerification);
                    user.setKycLevel(3);
                    userRepository.save(user);
                    // Send Email User
                    kycService.sendKycEmailUser(user, "kyc.verification.email.level.two.upgraded", "KYC Upgrade Status");
                    // Send SMS User
                    kycService.sendSMS(kycAddressVerification.getPhone(), "kyc.verification.sms.level.two.upgraded");
                    return new APIResponse<>(messageSource.getMessage("101", null, LocaleContextHolder.getLocale()),
                            false, "Error validating address, Please try again later");

                }

            } else {
                return new APIResponse<>("Account doesn't exist",
                        false, "User not found");
            }
        } else {
            return new APIResponse<>("Webhook Data not Found",
                    false, "Address verification failed");
        }
    }

    @GetMapping("/v1/kyc/id_verified_users")
    public List<Kyc> verifiedUsers() {
        return kycRepository.findAll();
    }

    @GetMapping("/v1/kyc/address_verified_users")
    public List<KycAddressVerification> addressVerifiedUsers() {
        return kycAddressRepository.findAll();
    }

    @GetMapping("/v1/kyc/user/{userId}")
    public Optional<Kyc> getUser(@PathVariable String userId) {
        return kycRepository.findByUserId(userId);
    }

    @PostMapping("/v1/kyc/user/verify")
    public APIResponse<String> verifyKYC(@RequestBody AdminKycRequest adminKycRequest) throws Exception {
        String userid = adminKycRequest.getUserid();
        User user = userRepository.findByUuid(userid).orElse(null);
        if (user != null) {
            // approved or reject user verification

            if (adminKycRequest.getStatus().equals("APPROVE")) {
                //Approved
                if (adminKycRequest.getVerificationType().equals("ID")) {
                    // KYC Verification request
                    user.setKycLevel(3);
                    userRepository.save(user);
                    // Upgrade kyc status
                    Kyc kyc_detail = new Kyc();
                    kyc_detail.setVerificationStatus(true);
                    kycRepository.save(kyc_detail);

                    // Send Email to User
                    kycService.sendKycEmailUser(user, "kyc.verification.email.level.one.upgraded", "KYC Upgrade Status");

                    return new APIResponse<>(messageSource.getMessage("000", null, LocaleContextHolder.getLocale()),
                            true, "KYC Verification approved");
                } else {
                    // Upgrade kyc status
                    Kyc kyc_detail = new Kyc();
                    kyc_detail.setVerificationStatus(false);
                    kycRepository.save(kyc_detail);

                    // Send Email to User
                    kycService.sendKycEmailUser(user, "kyc.verification.email.level.two.upgraded", "KYC Upgrade Status");
                    return new APIResponse<>(messageSource.getMessage("000", null, LocaleContextHolder.getLocale()),
                            true, "Address Verification approved");
                }
            } else {
                //Reject
                if (adminKycRequest.getVerificationType().equals("ADDRESS")) {
                    // Upgrade kyc status
                    Kyc kyc_detail = new Kyc();
                    kyc_detail.setVerificationStatus(true);
                    kycRepository.save(kyc_detail);

                    // Send Email to User
                    kycService.sendKycEmailUser(user, "kyc.verification.email.admin.not.verified", "KYC Upgrade Status");

                    return new APIResponse<>(messageSource.getMessage("000", null, LocaleContextHolder.getLocale()),
                            true, "KYC Verification rejected!");
                } else {
                    // Upgrade kyc status 
                    Kyc kyc_detail = new Kyc();
                    kyc_detail.setVerificationStatus(false);
                    kycRepository.save(kyc_detail);
                    // Send Email to User
                    kycService.sendKycEmailUser(user, "kyc.verification.email.admin.not.verified", "KYC Upgrade Status");

                    return new APIResponse<>(messageSource.getMessage("000", null, LocaleContextHolder.getLocale()),
                            true, "Address Verification rejected");
                }
            }
        } else {
            return new APIResponse<>(messageSource.getMessage("000", null, LocaleContextHolder.getLocale()),
                    true, "Address Verification rejected");
        }
    }
}
