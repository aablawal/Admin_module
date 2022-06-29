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

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
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
    public APIResponse<Map<String, String>> initiateKYC(OAuth2Authentication authentication, @RequestParam String bvn, @RequestParam(required = false) String dob) {

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
        return kycService.VerifyAddress(addressVerificationRequestVerifyme, authentication);
    }

    @PostMapping("/v1/kyc/verifyme/webhook")
    public APIResponse<String> verifyId(@RequestBody AddressVerificationWebhookRequest addressVerificationWebhookRequest) {
        if (addressVerificationWebhookRequest != null) {
            Long id = Long.valueOf(addressVerificationWebhookRequest.getData().getId());
            KycAddressVerification kycAddressVerification = kycAddressRepository.findByResID(id).orElseThrow(null);
            User user = userRepository.findByUuid(kycAddressVerification.getUserId()).orElse(null);
            if (user != null) {

                if (addressVerificationWebhookRequest.getData().getStatus().getStatus().contains("VERIFIED")) {
//                  Update Kyc detail address
                    kycAddressVerification.setStatus(addressVerificationWebhookRequest.getData().getStatus().getStatus());
                    kycAddressVerification.setSubStatus(addressVerificationWebhookRequest.getData().getStatus().getStatus());
                    kycAddressRepository.save(kycAddressVerification);
//                  Update user kyc level
                    user.setKycLevel(3);
                    userRepository.save(user);

                    // Send Email User
                    kycService.sendKycEmailUser(user, "kyc.verification.email.level.two.upgraded", "KYC Upgrade Status");
                    return new APIResponse<>(messageSource.getMessage("000", null, LocaleContextHolder.getLocale()),
                            true, "Address verification Completed");
                } else {
                    // Update Kyc detail address
                    kycAddressVerification.setStatus(addressVerificationWebhookRequest.getData().getStatus().getStatus());
                    kycAddressVerification.setSubStatus(addressVerificationWebhookRequest.getData().getStatus().getStatus());
                    kycAddressRepository.save(kycAddressVerification);
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
