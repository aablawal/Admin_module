package com.unionbankng.future.authorizationserver.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.unionbankng.future.authorizationserver.entities.Kyc;
import com.unionbankng.future.authorizationserver.entities.KycAddressVerification;
import com.unionbankng.future.authorizationserver.entities.User;
import com.unionbankng.future.authorizationserver.enums.AddressStatus;
import com.unionbankng.future.authorizationserver.enums.IdType;
import com.unionbankng.future.authorizationserver.pojos.*;
import com.unionbankng.future.authorizationserver.repositories.KycAddressRepository;
import com.unionbankng.future.authorizationserver.repositories.KycRepository;
import com.unionbankng.future.authorizationserver.repositories.UserRepository;
import com.unionbankng.future.authorizationserver.retrofitservices.KYCServiceInterface;
import com.unionbankng.future.authorizationserver.utils.App;
import com.unionbankng.future.authorizationserver.utils.JWTUserDetailsExtractor;
import com.unionbankng.future.authorizationserver.utils.SMSSender;
import com.unionbankng.future.authorizationserver.utils.UnsafeOkHttpClient;
import com.unionbankng.future.futureutilityservice.grpcserver.BlobType;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class KYCService {

    private final SMSSender smsSender;
    private final EmailService emailService;
    private final KycRepository kycRepository;
    private final MessageSource messageSource;
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;
    private final KycAddressRepository kycAddressRepository;
    private final App app;
    OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();
    Logger logger = LoggerFactory.getLogger(KYCService.class);
    @Value("${kyc.service.base.url}")
    private String baseURL;
    @Value("#{${kyc.service.credentials}}")
    private Map<String, String> credentials;
//    @Value("${kula.kyc.address.webhook}")
    private String kulaAddressWebhook = "https://testapi.kula.work/authserv/api/v1/webhooks/kyc/verifyme";
    private KYCServiceInterface kycServiceInterface;
    private String accessTokenForKycService;
    private Date tokenExpiryTime;

    @PostConstruct
    public void init() {
        Retrofit retrofit = new Retrofit.Builder().client(okHttpClient).addConverterFactory(GsonConverterFactory.create(new GsonBuilder().serializeNulls().create()))
                .baseUrl(baseURL)
                .build();
        kycServiceInterface = retrofit.create(KYCServiceInterface.class);
    }


    private String getAccessTokenForKycServiceService() throws IOException {
        Calendar calendar = Calendar.getInstance();
        Response<KYCTokenResponse> response = getAccessToken();

        if (!response.isSuccessful()) {
            app.print("KYC Service Token request failed");
            return null;
        }

        app.print("KYC Service Token request successful");
        KYCTokenResponse tokenResponse = response.body();
        accessTokenForKycService = tokenResponse.getAccess_token();
        calendar.add(Calendar.SECOND, Integer.parseInt(tokenResponse.getExpires_in()));
        tokenExpiryTime = calendar.getTime();
        return accessTokenForKycService;
    }


    public String getAccessTokenForWalletServiceCache() throws Exception {
        if (accessTokenForKycService == null) {
            app.print("KYC Service Token is null");
            return getAccessTokenForKycServiceService();
        }
        if (new Date().compareTo(tokenExpiryTime) > 0) {
            app.print("KYC Service Token is expired");
            return getAccessTokenForKycServiceService();
        }
        logger.info("retrieving token from cache");
        return accessTokenForKycService;
    }


    public Response<KYCTokenResponse> getAccessToken() throws IOException {
        String basicCred = credentials.get("username") + ":" + credentials.get("password");
        final String basic = "Basic " + Base64.getEncoder().encodeToString(basicCred.getBytes());
        return kycServiceInterface.getAccessToken(basic, credentials.get("username"),
                credentials.get("password"), credentials.get("grantType")).execute();
    }


    public Response<NinFullDetailResponse> getNinFullDetail(NinFullDetailRequest request) throws Exception {
        String token = "Bearer " + getAccessTokenForWalletServiceCache();
        return kycServiceInterface.getNinFullDetail(token, request).execute();
    }

    public Response<KycApiResponse<PassportFaceMatchResponse>> getPassportFaceMatch(PassportFaceMatchRequest request) throws Exception {
        String token = "Bearer " + getAccessTokenForWalletServiceCache();
        app.print(new Gson().toJson(request));
        return kycServiceInterface.getPassportFaceMatch(token, request).execute();
    }

    public Response<KycApiResponse<VerifiedIdResponse>> getIdFaceMatch(IdRequest request) throws Exception {
        String token = "Bearer " + getAccessTokenForWalletServiceCache();
        app.print(new Gson().toJson(request));
        return kycServiceInterface.getIdFaceMatch(token, request).execute();
    }

    public Response<KycApiResponse<VotersCardFaceMatchResponse>> getVotersCardFaceMatch(VotersCardFaceMatchRequest request) throws Exception {
        String token = "Bearer " + getAccessTokenForWalletServiceCache();
        app.print("request: " + request);
        app.print(new Gson().toJson(request));
        return kycServiceInterface.getVotersCardFaceMatch(token, request).execute();
    }

    public Response<KycApiResponse<DriversLicenceFaceMatchResponse>> getDriverLicenseFaceMatch(IdentityBiometricsRequest request) throws Exception {
        String token = "Bearer " + getAccessTokenForWalletServiceCache();
        app.print("request: " + request);
        app.print(new Gson().toJson(request));
        return kycServiceInterface.getDriverLicenseFaceMatch(token, request).execute();
    }

    public Response<DriversLicenceFaceMatchResponse> getNinFaceMatch(AddressVerificationRequest request) throws Exception {
        String token = "Bearer " + getAccessTokenForWalletServiceCache();
        return kycServiceInterface.getNinFaceMatch(token, request).execute();
    }

    public Response<KycApiResponse<VerifyMeResponse>> getIdentityBiometrics(IdentityBiometricsRequest request) throws Exception {
        String token = "Bearer " + getAccessTokenForWalletServiceCache();
        app.print("request: " + request);
        app.print(new Gson().toJson(request));
        return kycServiceInterface.getIdentityBiometrics(token, request).execute();
    }

    public APIResponse<String> VerifyInternationalPassport(VerifyKycRequest verifyKycRequest, MultipartFile selfieImage, MultipartFile idImage, User user) throws Exception {

        Random rand = new Random();
        int maxNumber = 10003430;
        int randomNumber = rand.nextInt(maxNumber) + 1;

        if (selfieImage == null) {

            return new APIResponse<>("User Selfie Image Required", false, null);
        } else if (idImage == null) {
            return new APIResponse<>("User Id Image Required", false, null);

        } else {
            String selfieImageBase64 = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(selfieImage.getBytes());
            String idImageBase64 = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(idImage.getBytes());
            Date dateOfBirth = user.getDateOfBirth();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dateOfBirthString = formatter.format(dateOfBirth);

            PassportFaceMatchRequest passportFaceMatchRequest2 = new PassportFaceMatchRequest();
            passportFaceMatchRequest2.setDob(dateOfBirthString);
            passportFaceMatchRequest2.setEmail(user.getEmail());
            passportFaceMatchRequest2.setFirstName(user.getFirstName());
            passportFaceMatchRequest2.setGender(user.getGender());
            passportFaceMatchRequest2.setPhone(app.toPhoneNumber(user.getPhoneNumber())); //Todo: Verify Phone Number required. Check for the phone number in the BVN verification.
            passportFaceMatchRequest2.setLastName(user.getLastName());
            passportFaceMatchRequest2.setSelfie(selfieImageBase64);
            passportFaceMatchRequest2.setSearchParameter(verifyKycRequest.getIdNumber());
            passportFaceMatchRequest2.setSelfieToDatabaseMatch("true");
            passportFaceMatchRequest2.setTransactionReference(String.valueOf(randomNumber));
            passportFaceMatchRequest2.setVerificationType("PASSPORT-FACE-MATCH-VERIFICATION");
            passportFaceMatchRequest2.setCountry("NG");

            app.print("Verify International Passport with PassportFaceMatch");
            app.print("Verify International Passport with PassportFaceMatch request body" + passportFaceMatchRequest2);

            Response<KycApiResponse<PassportFaceMatchResponse>> response = getPassportFaceMatch(passportFaceMatchRequest2);

            if (response.isSuccessful()) {
//                if (response.body().getDescription().equals("The ID provided is invalid")) {
                if (response.body() == null || !response.body().isSuccess()) {
                    sendKycEmailUser(user, "kyc.picture.verification.email.body", "KYC Upgrade Status");
                    return new APIResponse<>(messageSource.getMessage("kyc.picture.verification.failed.response", null, LocaleContextHolder.getLocale()),
                            false, null);
                } else {
                    app.print("Uploading user selfie....");
                    String selfieFileName = "selfie-" + randomNumber + "-" + user.getUuid() + ".jpg";
                    String savedSelfieUrl = fileStorageService.storeFile(selfieImage, selfieFileName, BlobType.IMAGE);
                    app.print("Uploaded... " + savedSelfieUrl);

                    app.print("Uploading user id....");
                    String idFileName = "id-" + randomNumber + "-" + user.getUuid() + ".jpg";
                    String savedIdImageUrl = fileStorageService.storeFile(idImage, idFileName, BlobType.IMAGE);
                    app.print("Uploaded... " + savedIdImageUrl);

                    //Step one & two verification
                    if (response.body().isSuccess() && response.body().getData().getVerificationStatus().equals("VERIFIED")) {
                        // Step three verification name matches
//                        if (response.body().getData().getResponse().getFirst_name().equalsIgnoreCase(passportFaceMatchRequest2.getFirstName()) && response.body().getData().getResponse().getLast_name().equalsIgnoreCase(passportFaceMatchRequest2.getLastName())) { //TODO : Confirm match before sending to production
                        if (true) { // TODO: To be removed before pushing to production
                            //Step four id card expiry validation

                            Kyc kyc = new Kyc();
                            kyc.setVerificationStatus(true);
                            kyc.setFirstName(user.getFirstName());
                            kyc.setLastName(user.getLastName());
                            kyc.setIdExpiry(response.body().getData().getResponse().getExpiry_date());
                            kyc.setDob(String.valueOf(verifyKycRequest.getDob()));
                            kyc.setIdType(IdType.INTERNATIONAL_PASSPORT.toString());
                            kyc.setUserId(user.getUuid());
                            kyc.setIdNUmber(verifyKycRequest.getIdNumber());
                            kyc.setScore(response.body().getData().getFaceMatch().getScore());
                            kyc.setVerdict(response.body().getData().getFaceMatch().getVerdict());
                            kyc.setSelfieImage(savedSelfieUrl);
                            kyc.setIdImage(savedIdImageUrl);
                            kycRepository.save(kyc);
                            // Upgrade User KYC level to tear 2
                            user.setKycLevel(2);
                            userRepository.save(user);

                            // Send Email User
                            sendKycEmailUser(user, "kyc.verification.email.level.one.upgraded", "kyc.wallet.upgrade.email.subject");
                            // Send SMS User
                            sendSMS(verifyKycRequest.getPhoneNumber(), "kyc.verification.sms.level.one.upgraded");

                            return new APIResponse<>(messageSource.getMessage("000", null, LocaleContextHolder.getLocale()),
                                    true, null);
                        } else {
                            return new APIResponse<>(messageSource.getMessage("101", null, LocaleContextHolder.getLocale()),
                                    false, "Unable to extract response data");
                        }
                    } else {
                        Kyc kyc = new Kyc();
                        kyc.setVerificationStatus(false);
                        kyc.setIdType(IdType.INTERNATIONAL_PASSPORT.toString());
                        kyc.setUserId(user.getUuid());
                        kyc.setIdNUmber(verifyKycRequest.getIdNumber());
                        kyc.setScore(response.body().getData().getFaceMatch().getScore());
                        kyc.setVerdict(response.body().getData().getFaceMatch().getVerdict());
                        kyc.setSelfieImage(savedSelfieUrl);
                        kyc.setIdImage(savedIdImageUrl);
                        kycRepository.save(kyc);
                        // Upgrade User KYC level to tear 2
                        userRepository.save(user);


                        // Send Email to User
                        sendKycEmailUser(user, "kyc.verification.email.not.verified", "kyc.wallet.upgrade.email.subject");
                        // Send sms to user
                        sendSMS(verifyKycRequest.getPhoneNumber(), "kyc.verification.sms.not.verified");

                        return new APIResponse<>(messageSource.getMessage("101", null, LocaleContextHolder.getLocale()),
                                true, "Verification Sent for manual approval");
                    }
                }
            } else {

                sendKycEmailUser(user, "kyc.id.verification.failed.body", "KYC Upgrade Status");

                return new APIResponse<>(messageSource.getMessage("kyc.id.verification.failed.response", null, LocaleContextHolder.getLocale()),
                        false, "The detail you provide is invalid");
            }
        }
    }

    public APIResponse<String> VerifyVotersCard(VerifyKycRequest verifyKycRequest, MultipartFile selfieImage, MultipartFile idImage, User user) throws Exception {

        if (selfieImage == null) {
            return new APIResponse<>("User Selfie Image Required", false, null);
        } else if (idImage == null) {
            return new APIResponse<>("User Id Image Required", false, null);

        } else {
            String selfieImageBase64 = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(selfieImage.getBytes());
            String idImageBase64 = Base64.getEncoder().encodeToString(selfieImage.getBytes());

            Random rand = new Random();
            int maxNumber = 10003430;
            int randomNumber = rand.nextInt(maxNumber) + 1;


            VotersCardFaceMatchRequest votersCardFaceMatchRequest = new VotersCardFaceMatchRequest();
            votersCardFaceMatchRequest.setCountry("NG");
            votersCardFaceMatchRequest.setSelfie(selfieImageBase64);
            votersCardFaceMatchRequest.setSearchParameter(verifyKycRequest.getIdNumber());
            votersCardFaceMatchRequest.setSelfieToDatabaseMatch(true);
            votersCardFaceMatchRequest.setVerificationType("VIN-FACE-MATCH-VERIFICATION");

            app.print("Voters Card Face Match Request: " + votersCardFaceMatchRequest);
            Response<KycApiResponse<VotersCardFaceMatchResponse>> response = getVotersCardFaceMatch(votersCardFaceMatchRequest);
            app.print("Voters Card Face Match Response: " + response);
            if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                if (response.body().getData().getDescription().equals("The ID provided is invalid")) {
                    return new APIResponse<>(messageSource.getMessage("101", null, LocaleContextHolder.getLocale()),
                            false, "The ID provided is invalid");
                } else {
                    Kyc kyc = new Kyc();

                    app.print("Uploading user selfie....");
                    String selfieFileName = "selfie-" + randomNumber + "-" + user.getUuid() + ".jpg";
                    String savedSelfieUrl = fileStorageService.storeFile(selfieImage, selfieFileName, BlobType.IMAGE);
                    app.print("Uploaded... " + savedSelfieUrl);

                    app.print("Uploading user id....");
                    String idFileName = "id-" + randomNumber + "-" + user.getUuid() + ".jpg";
                    String savedIdImageUrl = fileStorageService.storeFile(idImage, idFileName, BlobType.IMAGE);
                    app.print("Uploaded... " + savedSelfieUrl);


//                    if (response.body().getData().getResponse().getFirst_name().equals(user.getFirstName()) && response.body().getData().getResponse().getLast_name().equals(user.getLastName())) {
                    if (true) {

                        kyc.setVerificationStatus(true);
                        kyc.setFirstName(user.getFirstName());
                        kyc.setLastName(user.getLastName());
                        kyc.setIdExpiry("null");
                        kyc.setDob(String.valueOf(verifyKycRequest.getDob()));
                        kyc.setIdType(IdType.VOTERS_CARD.toString());
                        kyc.setUserId(user.getUuid());
                        kyc.setIdNUmber(verifyKycRequest.getIdNumber());
                        kyc.setScore(response.body().getData().getFaceMatch().getScore());
                        kyc.setVerdict(response.body().getData().getFaceMatch().getVerdict());
                        kyc.setSelfieImage(savedSelfieUrl);
                        kyc.setIdImage(savedIdImageUrl);
                        kycRepository.save(kyc);
                        // Upgrade User KYC level to tear 2
                        user.setKycLevel(2);
                        userRepository.save(user);
                        // Send Email User
                        sendKycEmailUser(user, "kyc.verification.email.level.one.upgraded", "kyc.wallet.upgrade.email.subject");
                        // Send SMS User
                        sendSMS(verifyKycRequest.getPhoneNumber(), "kyc.verification.sms.level.one.upgraded");
                        return new APIResponse<>(messageSource.getMessage("000", null, LocaleContextHolder.getLocale()),
                                true, "Verified successfully");
                    } else {
                        kyc.setVerificationStatus(false);
                        kyc.setFirstName(user.getFirstName());
                        kyc.setLastName(user.getLastName());
                        kyc.setIdExpiry("null");
                        kyc.setDob(verifyKycRequest.getDob());
                        kyc.setIdType(IdType.VOTERS_CARD.toString());
                        kyc.setUserId(user.getUuid());
                        kyc.setIdNUmber(verifyKycRequest.getIdNumber());
                        kyc.setScore(response.body().getData().getFaceMatch().getScore());
                        kyc.setVerdict(response.body().getData().getFaceMatch().getVerdict());
                        kyc.setSelfieImage(savedSelfieUrl);
                        kyc.setIdImage(savedIdImageUrl);
                        kycRepository.save(kyc);
                        // Upgrade User KYC level to tear 2 TODO confirm from rabiu if this is really an upgrade to tier 3
                        userRepository.save(user);
                        // Send Email to User
                        sendKycEmailUser(user, "kyc.verification.email.not.verified", "kyc.wallet.upgrade.email.subject");
                        // Send sms to user
                        sendSMS(verifyKycRequest.getPhoneNumber(), "kyc.verification.sms.not.verified");
                        return new APIResponse<>(messageSource.getMessage("000", null, LocaleContextHolder.getLocale()),
                                true, "Verification Sent for manual approval");
                    }
                }
            } else {
                return new APIResponse<>(messageSource.getMessage("101", null, LocaleContextHolder.getLocale()),
                        false, "The detail you provide is invalid");
            }
        }

    }

    public APIResponse<String> VerifyDriverLicence(VerifyKycRequest verifyKycRequest, MultipartFile selfieImage, MultipartFile idImage, User user) throws Exception {

        if (selfieImage == null) {
            return new APIResponse<>("User Selfie Image Required", false, null);
        } else if (idImage == null) {
            return new APIResponse<>("User Id Image Required", false, null);

        } else {
            String selfieImageBase64 = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(selfieImage.getBytes());
            String idImageBase64 = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(selfieImage.getBytes());

            Random rand = new Random();
            int maxNumber = 10003430;
            int randomNumber = rand.nextInt(maxNumber) + 1;

            Date dateOfBirth = user.getDateOfBirth();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dateOfBirthString = formatter.format(dateOfBirth);


            IdentityBiometricsRequest identityBiometricsRequest = new IdentityBiometricsRequest();
            identityBiometricsRequest.setIdType("frsc");
            identityBiometricsRequest.setPhoto(selfieImageBase64);
            identityBiometricsRequest.setIdNumber(verifyKycRequest.getIdNumber());
            identityBiometricsRequest.setPhotoUrl(idImageBase64);

            app.print(identityBiometricsRequest);
            Response<KycApiResponse<DriversLicenceFaceMatchResponse>> response = getDriverLicenseFaceMatch(identityBiometricsRequest);
            app.print("DriversLicenceFaceMatchResponse VERIFICATION RESPONSE");
            app.print(response.body());
            if (response.body().isSuccess()) {
                Kyc kyc = new Kyc();

                app.print("Uploading user selfie....");
                String selfieFileName = "selfie-" + randomNumber + "-" + user.getUuid() + ".jpg";
                String savedSelfieUrl = fileStorageService.storeFile(selfieImage, selfieFileName, BlobType.IMAGE);
                app.print("Uploaded... " + savedSelfieUrl);

                app.print("Uploading user id....");
                String idFileName = "id-" + randomNumber + "-" + user.getUuid() + ".jpg";
                String savedIdImageUrl = fileStorageService.storeFile(idImage, idFileName, BlobType.IMAGE);
                app.print("Uploaded... " + savedSelfieUrl);

                if (response.body() != null && response.body().isSuccess()) {

                    kyc.setVerificationStatus(true);
                    kyc.setFirstName(user.getFirstName());
                    kyc.setLastName(user.getLastName());
                    kyc.setIdExpiry("null");
                    kyc.setDob(verifyKycRequest.getDob());
                    kyc.setIdType(IdType.DRIVERS_LICENSE.toString());
                    kyc.setUserId(user.getUuid());
                    kyc.setIdNUmber(verifyKycRequest.getIdNumber());
//                    kyc.setScore(response.body().getData().getFaceMatchScore());
//                    kyc.setVerdict(response.body().getData().getFaceMatchStatus());
                    kyc.setSelfieImage(savedSelfieUrl);
                    kyc.setIdImage(savedIdImageUrl);
                    kycRepository.save(kyc);
                    // Upgrade User KYC level to tear 2
                    user.setKycLevel(2);
                    userRepository.save(user);

                    // Send Email User
                    sendKycEmailUser(user, "kyc.verification.email.level.one.upgraded", "kyc.wallet.upgrade.email.subject");
                    // Send SMS User
                    sendSMS(user.getPhoneNumber(), "kyc.verification.sms.level.one.upgraded");
                    return new APIResponse<>(messageSource.getMessage("000", null, LocaleContextHolder.getLocale()),
                            true, "Verified successfully");
                } else {
                    kyc.setVerificationStatus(false);
                    kyc.setFirstName(user.getFirstName());
                    kyc.setLastName(user.getLastName());
                    kyc.setIdExpiry("null");
                    kyc.setDob(verifyKycRequest.getDob());
                    kyc.setIdType(IdType.DRIVERS_LICENSE.toString());
                    kyc.setUserId(user.getUuid());
                    kyc.setIdNUmber(verifyKycRequest.getIdNumber());
//                    kyc.setScore(response.body().getData().getFaceMatchScore());
//                    kyc.setVerdict(response.body().getData().getFaceMatchStatus());
                    kyc.setSelfieImage(savedSelfieUrl);
                    kyc.setIdImage(savedIdImageUrl);
                    kycRepository.save(kyc);
//                    user.setKycLevel(3); //TODO confirm from Rabiu
                    userRepository.save(user);
                    // Send Email to User
                    sendKycEmailUser(user, "kyc.verification.email.not.verified", "kyc.wallet.upgrade.email.subject");
                    // Send push sms to user
                    sendSMS(verifyKycRequest.getPhoneNumber(), "kyc.verification.sms.not.verified");

                    return new APIResponse<>(messageSource.getMessage("101", null, LocaleContextHolder.getLocale()),
                            true, "Verification Sent for manual approval");
                }
            } else {
                return new APIResponse<>(messageSource.getMessage("101", null, LocaleContextHolder.getLocale()),
                        false, "Could not verify id at the moment, Please try again later");
            }
        }

    }

    public APIResponse<String> VerifyNIN(VerifyKycRequest verifyKycRequest, MultipartFile selfieImage, MultipartFile idImage, User user) throws Exception {

        if (selfieImage == null) {
            return new APIResponse<>("User Selfie Image Required", false, null);
        } else if (idImage == null) {
            return new APIResponse<>("User Id Image Required", false, null);

        } else {
            String selfieImageBase64 = Base64.getEncoder().encodeToString(selfieImage.getBytes());
            String idImageBase64 = Base64.getEncoder().encodeToString(selfieImage.getBytes());

            Random rand = new Random();
            int maxNumber = 10003430;
            int randomNumber = rand.nextInt(maxNumber) + 1;

            IdentityBiometricsRequest identityBiometricsRequest = new IdentityBiometricsRequest();
            identityBiometricsRequest.setIdType("nin");
            identityBiometricsRequest.setPhoto(selfieImageBase64);
            identityBiometricsRequest.setPhotoUrl(idImageBase64);
            identityBiometricsRequest.setIdNumber(verifyKycRequest.getIdNumber());
            Response<KycApiResponse<VerifyMeResponse>> response = this.getIdentityBiometrics(identityBiometricsRequest);
            if (!response.isSuccessful())
                return new APIResponse<>(messageSource.getMessage("101", null, LocaleContextHolder.getLocale()),
                        false, "Details Provided is invalid");

            Kyc kyc = new Kyc();
            app.print("Uploading user selfie....");
            String selfieFileName = "selfie-" + randomNumber + "-" + user.getUuid() + ".jpg";
            String savedSelfieUrl = fileStorageService.storeFile(selfieImage, selfieFileName, BlobType.IMAGE);
            app.print("Uploaded... " + savedSelfieUrl);

            app.print("Uploading user id....");
            String idFileName = "id-" + randomNumber + "-" + user.getUuid() + ".jpg";
            String savedIdImageUrl = fileStorageService.storeFile(idImage, idFileName, BlobType.IMAGE);
            app.print("Uploaded... " + savedSelfieUrl);

            if (response.body() != null && response.body().isSuccess()) {

                kyc.setVerificationStatus(true);
                kyc.setFirstName(user.getFirstName());
                kyc.setLastName(user.getLastName());
                kyc.setIdExpiry("null");
                kyc.setDob(verifyKycRequest.getDob());
                kyc.setIdType(IdType.NATIONAL_IDENTITY.toString());
                kyc.setUserId(user.getUuid());
                kyc.setIdNUmber(verifyKycRequest.getIdNumber());
//                kyc.setScore(response.body().getData().getData().getPhotoMatching().getMatchScore());
//                kyc.setVerdict(response.body().getData().getData().getPhotoMatching().getMatch());
                kyc.setSelfieImage(savedSelfieUrl);
                kyc.setIdImage(savedIdImageUrl);
                kycRepository.save(kyc);

                // Upgrade User KYC level to tear 2
                user.setKycLevel(2);
                userRepository.save(user);

                // Send Email User
                sendKycEmailUser(user, "kyc.verification.email.level.one.upgraded", "kyc.wallet.upgrade.email.subject");
                // Send SMS User
                sendSMS(verifyKycRequest.getPhoneNumber(), "kyc.verification.email.level.one.upgraded");
                return new APIResponse<>(messageSource.getMessage("000", null, LocaleContextHolder.getLocale()),
                        true, "Verified successfully");
            } else {
                kyc.setVerificationStatus(false);
                kyc.setIdType(IdType.NATIONAL_IDENTITY.toString());
                kyc.setUserId(user.getUuid());
                kyc.setIdNUmber(verifyKycRequest.getIdNumber());
                kyc.setScore(response.body().getData().getData().getPhotoMatching().getMatchScore());
                kyc.setVerdict(response.body().getData().getData().getPhotoMatching().getMatch());
                kyc.setSelfieImage(savedSelfieUrl);
                kyc.setIdImage(savedIdImageUrl);
                kycRepository.save(kyc);
                // Upgrade User KYC level to tear 2 TODO confirm from rabiu
                // Send Email to User
                sendKycEmailUser(user, "kyc.verification.email.not.verified", "kyc.wallet.upgrade.email.subject");
                // Send push sms to user
                sendSMS(verifyKycRequest.getPhoneNumber(), "kyc.verification.email.not.verified");

                return new APIResponse<>(messageSource.getMessage("101", null, LocaleContextHolder.getLocale()),
                        true, "Verification Sent for manual approval");
            }
        }

    }

    public APIResponse<String> kycVerifyId(VerifyKycRequest verifyKycRequest, MultipartFile selfieImage, MultipartFile idImage, User user) throws Exception {

        if (selfieImage == null)
            return new APIResponse<>("User Selfie Image Required", false, null);

        if (idImage == null)
            return new APIResponse<>("User Id Image Required", false, null);

        Random rand = new Random();
        int maxNumber = 10003430;
        int randomNumber = rand.nextInt(maxNumber) + 1;

        String selfieImageBase64 = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(selfieImage.getBytes());
        Date dateOfBirth = user.getDateOfBirth();
        app.print("dateOfBirth: >>"+ dateOfBirth);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateOfBirthString = formatter.format(dateOfBirth);

        IdRequest idRequest = new IdRequest();
        idRequest.setDob(dateOfBirthString);
        idRequest.setEmail(user.getEmail());
        idRequest.setFirstName(user.getFirstName());
        idRequest.setGender(user.getGender());
        idRequest.setPhone(app.toPhoneNumber(user.getPhoneNumber()));
        idRequest.setLastName(user.getLastName());
        idRequest.setPhoto(selfieImageBase64);
        idRequest.setIdType(verifyKycRequest.getIdType());
        idRequest.setIdNumber(verifyKycRequest.getIdNumber());
        idRequest.setUserId(user.getUuid());

        app.print("Verify Id for KYC");
        app.print("Verify Id (" + verifyKycRequest.getIdType().toString() + ") request body" + idRequest);

        Response<KycApiResponse<VerifiedIdResponse>> response = getIdFaceMatch(idRequest);

        logger.info("THE ID VERIFICATION RESPONSE {}", new Gson().toJson(response.body()));

        if (!response.isSuccessful()) {
            sendKycEmailUser(user, "kyc.id.verification.failed.body", "KYC Upgrade Status");

            return new APIResponse<>(messageSource.getMessage("kyc.id.verification.failed.response", null, LocaleContextHolder.getLocale()),
                    false, "The detail you provide is invalid");
        }

        if (!response.body().isSuccess()) {
            sendKycEmailUser(user, "kyc.picture.verification.email.body", "KYC Upgrade Status");
            return new APIResponse<>(messageSource.getMessage("kyc.picture.verification.failed.response", null, LocaleContextHolder.getLocale()),
                    false, null);
        }

        VerifiedIdResponse responseData = response.body().getData();

        app.print("Uploading user id....");
        String idFileName = "id-" + randomNumber + "-" + user.getUuid() + ".jpg";
        String savedIdImageUrl = fileStorageService.storeFile(idImage, idFileName, BlobType.IMAGE);
        System.out.println("Saved image: >>>" + savedIdImageUrl);
        app.print("Uploaded... " + savedIdImageUrl);

        //todo: uncomment before moving to production
//        if (responseData.getFirstName().equalsIgnoreCase(idRequest.getFirstName()) && responseData.getLastName().equalsIgnoreCase(idRequest.getLastName())) {
        if(true){
            Kyc kyc = new Kyc();
            kyc.setVerificationStatus(true);
            kyc.setFirstName(user.getFirstName());
            kyc.setLastName(user.getLastName());
            kyc.setDob(String.valueOf(verifyKycRequest.getDob()));
            kyc.setIdType(idRequest.getIdType().toString());
            kyc.setUserId(user.getUuid());
            kyc.setIdNUmber(verifyKycRequest.getIdNumber());
            kyc.setIdImage(savedIdImageUrl);
            kyc.setIdType(idRequest.getIdType().toString());
            kyc.setSelfieImage(responseData.getPhotoUrl());
            kycRepository.save(kyc);
            user.setKycLevel(2);
            userRepository.save(user);

            // Send Email User
            sendKycEmailUser(user, "kyc.verification.email.level.one.upgraded", "kyc.wallet.upgrade.email.subject");
            // Send SMS User
            sendSMS(verifyKycRequest.getPhoneNumber(), "kyc.verification.sms.level.one.upgraded");

            return new APIResponse<>(messageSource.getMessage("000", null, LocaleContextHolder.getLocale()),
                    true, null);
        } else {
            Kyc kyc = new Kyc();
            kyc.setVerificationStatus(false);
            kyc.setIdType(idRequest.getIdType().toString());
            kyc.setFirstName(user.getFirstName());
            kyc.setLastName(user.getLastName());
            kyc.setDob(String.valueOf(verifyKycRequest.getDob()));
            kyc.setUserId(user.getUuid());
            kyc.setIdNUmber(verifyKycRequest.getIdNumber());
            kyc.setIdImage(savedIdImageUrl);
            kyc.setIdType(idRequest.getIdType().toString());
            kyc.setSelfieImage(responseData.getPhotoUrl());
            kycRepository.save(kyc);

            // Send Email to User
            sendKycEmailUser(user, "kyc.verification.email.not.verified", "kyc.wallet.upgrade.email.subject");
            // Send sms to user
            sendSMS(verifyKycRequest.getPhoneNumber(), "kyc.verification.sms.not.verified");

            return new APIResponse<>(messageSource.getMessage("101", null, LocaleContextHolder.getLocale()),
                    true, "Verification Sent for manual approval");
        }

    }

    public Response<AddressVerifyResponse<AddressVerificationDto>> getAddressVerification(VerifyAddressRequest request) throws Exception {
        String token = "Bearer " + getAccessTokenForWalletServiceCache();
        return kycServiceInterface.getAddressVerification(token, request).execute();
    }

    public void sendKycEmailUser(User user, String msg, String subject) {
        String[] params = new String[]{user.getFirstName(), "KYC - VERIFICATION"};
        String body = messageSource.getMessage(msg, params, LocaleContextHolder.getLocale());
        EmailMessage message = new EmailMessage();
        message.setBody(body);
        message.setRecipient(user.getEmail());
        message.setSubject(subject);
        emailService.sendEmail(message);

    }

    public void sendSMS(String recipient, String msg) {
        String[] params = new String[]{"Kula", "KYC - VERIFICATION"};
        String message = messageSource.getMessage(msg, params, LocaleContextHolder.getLocale());
        SMS sms = new SMS();
        sms.setMessage(message);
        sms.setRecipient(recipient);
        smsSender.sendSMS(sms);
    }

    public APIResponse<String> VerifyAddress(AddressVerificationRequest addressVerificationRequest, OAuth2Authentication authentication) {
        JwtUserDetail authorizedUser = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(authentication);
        User user = userRepository.findByUuid(authorizedUser.getUserUUID()).orElse(null);

        assert user != null;
        if (user.getKycLevel() == 3)
            return new APIResponse<>(messageSource.getMessage("101", null, LocaleContextHolder.getLocale()),
                    false, "User Already verified");

        String idType = "KYC";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateOfBirth = sdf.format(user.getDateOfBirth());

        // format the phone number
        String userPhone = user.getPhoneNumber();
        userPhone = app.toPhoneNumber(userPhone);

        Applicant applicant = new Applicant();
        applicant.setIdType(idType);

        VerifyAddressRequest verifyAddressRequest = new VerifyAddressRequest();
        verifyAddressRequest.setCallbackUrl(kulaAddressWebhook);
        verifyAddressRequest.setDob(dateOfBirth);

        verifyAddressRequest.setUserId(user.getUuid());
        verifyAddressRequest.setIdNumber(userPhone);
        verifyAddressRequest.setCountry("NG");
        verifyAddressRequest.setFirstName(user.getFirstName());
        verifyAddressRequest.setLandmark(addressVerificationRequest.getLandmark());
        verifyAddressRequest.setLastName(user.getLastName());
        verifyAddressRequest.setPhone(userPhone);
        verifyAddressRequest.setCity(addressVerificationRequest.getLga());
        verifyAddressRequest.setStreet(addressVerificationRequest.getStreet());
        verifyAddressRequest.setState(addressVerificationRequest.getState());
        verifyAddressRequest.setEmail(user.getEmail());
        verifyAddressRequest.setLga(addressVerificationRequest.getLga());
        verifyAddressRequest.setImage(addressVerificationRequest.getImage());
        verifyAddressRequest.setClientRef("KULA_"+ app.makeUIID());

        KycAddressVerification kycAddressVerification = new KycAddressVerification();
        kycAddressVerification.setUserId(user.getUuid());
        kycAddressVerification.setBirthdate(dateOfBirth);
        kycAddressVerification.setFirstname(user.getFirstName());
        kycAddressVerification.setLastname(user.getLastName());
        kycAddressVerification.setGender(user.getGender());
        kycAddressVerification.setPhone(userPhone);
        kycAddressVerification.setIdNumber(userPhone);
        kycAddressVerification.setPhoto(null);
        kycAddressVerification.setReference(verifyAddressRequest.getClientRef());
        kycAddressVerification.setCountry(verifyAddressRequest.getCountry());
        kycAddressVerification.setCity(verifyAddressRequest.getCity());
        kycAddressVerification.setLga(verifyAddressRequest.getLga());
        kycAddressVerification.setStreet(verifyAddressRequest.getStreet());
        kycAddressVerification.setState(verifyAddressRequest.getState());
        kycAddressVerification.setStatus("PENDING");
        kycAddressVerification.setSubStatus("PENDING");

        kycAddressRepository.save(kycAddressVerification);

        Response<AddressVerifyResponse<AddressVerificationDto>> response = null;
        try {

            //TEST DATA
//            verifyAddressRequest.setLandmark("Beside GTbank");
//            verifyAddressRequest.setStreet("270 Murtala Muhammed Way, Alagomeji. Yaba");
//            verifyAddressRequest.setLga("surulere");
//            verifyAddressRequest.setState("lagos");
//            verifyAddressRequest.getApplicant().setIdNumber("08121234567");
//            verifyAddressRequest.getApplicant().setFirstname("john");
//            verifyAddressRequest.getApplicant().setLastname("doe");
//            verifyAddressRequest.getApplicant().setPhone("08121234567");
//            verifyAddressRequest.getApplicant().setDob("04-04-1944");
//            verifyAddressRequest.getApplicant().setIdType("KYC");

            app.print("addressVerificationRequestVerifyMe request body: " + verifyAddressRequest);
            response = getAddressVerification(verifyAddressRequest);
            app.print("addressVerificationRequestVerifyMe response body: " + response.body().toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (!response.isSuccessful() || !response.body().isSuccess())
            return new APIResponse<>(messageSource.getMessage("101", null, LocaleContextHolder.getLocale()),
                    false, "Request Failed");


        return new APIResponse<>(messageSource.getMessage("000", null, LocaleContextHolder.getLocale()),
                true, "Address verification Request Submitted.");
    }

    public APIResponse<String> processWebhookRequest(@RequestBody AddressVerificationDto addressVerificationWebhookRequest) {
        if (addressVerificationWebhookRequest != null) {
            KycAddressVerification kycAddressVerification = kycAddressRepository.findByReference(addressVerificationWebhookRequest.getClientRef()).orElseThrow(null);

            User user = userRepository.findByUuid(kycAddressVerification.getUserId()).orElse(null);
            if (user != null) {
                if (addressVerificationWebhookRequest.getStatus().equals(AddressStatus.VERIFIED)) {
                    kycAddressVerification.setStatus(addressVerificationWebhookRequest.getStatus().toString());
                    kycAddressVerification.setSubStatus(addressVerificationWebhookRequest.getStatus().toString());
                    kycAddressRepository.save(kycAddressVerification);
                    user.setKycLevel(3);
                    userRepository.save(user);

                    // Send Email User
                    sendKycEmailUser(user, "kyc.verification.email.level.two.upgraded", "kyc.wallet.upgrade.email.subject");
                    return new APIResponse<>(messageSource.getMessage("000", null, LocaleContextHolder.getLocale()),
                            true, "Address verification Completed");} else {
                    // Update Kyc detail address
                    kycAddressVerification.setStatus(addressVerificationWebhookRequest.getStatus().toString());
                    kycAddressVerification.setSubStatus(addressVerificationWebhookRequest.getStatus().toString());
                    kycAddressRepository.save(kycAddressVerification);
                    sendKycEmailUser(user, "kyc.verification.email.level.two.failed.upgrade", "kyc.wallet.upgrade.email.subject");
                    // Send SMS User
                    sendSMS(kycAddressVerification.getPhone(), "kyc.verification.sms.level.two.failed.upgrade");
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

    public APIResponse<String> modifyId(User user) {
        user = userRepository.save(user);
        return new APIResponse<>(messageSource.getMessage("000", null, LocaleContextHolder.getLocale()),
                true, "User's KYC level is now : "+ user.getKycLevel());
    }
}
