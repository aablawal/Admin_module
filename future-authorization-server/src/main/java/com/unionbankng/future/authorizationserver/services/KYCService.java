package com.unionbankng.future.authorizationserver.services;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.unionbankng.future.authorizationserver.entities.KycAddressVerification;
import com.unionbankng.future.authorizationserver.entities.User;
import com.unionbankng.future.authorizationserver.pojos.*;
import com.unionbankng.future.authorizationserver.repositories.KycAddressRepository;
import com.unionbankng.future.authorizationserver.repositories.UserRepository;
import com.unionbankng.future.authorizationserver.retrofitservices.KYCServiceInterface;
import com.unionbankng.future.authorizationserver.entities.Kyc;
import com.unionbankng.future.authorizationserver.repositories.KycRepository;
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

    @Value("${kyc.service.base.url}")
    private String baseURL;
    @Value("#{${kyc.service.credentials}}")
    private Map<String, String> credentials;

    private KYCServiceInterface kycServiceInterface;
    private String accessTokenForKycService;
    private Date tokenExpiryTime;
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

        if (!response.isSuccessful()){
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
        if (new Date().compareTo(tokenExpiryTime) > 0){
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

    public Response<KycApiResponse<VotersCardFaceMatchResponse>> getVotersCardFaceMatch(VotersCardFaceMatchRequest request) throws Exception {
        String token = "Bearer " + getAccessTokenForWalletServiceCache();
        app.print("request: " + request);
        app.print(new Gson().toJson(request));
        return kycServiceInterface.getVotersCardFaceMatch(token, request).execute();
    }

    public Response<KycApiResponse<DriversLicenceFaceMatchResponse>> getDriverLicenseFaceMatch(DriversLicenceFaceMatchRequest request) throws Exception {
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

            app.print("Verify International Passport with PassportFaceMatch");

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
                            kyc.setIdType(verifyKycRequest.getIdType());
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
                        kyc.setIdType(verifyKycRequest.getIdType());
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
                        kyc.setIdType(verifyKycRequest.getIdType());
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
                        kyc.setIdType(verifyKycRequest.getIdType());
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

            DriversLicenceFaceMatchRequest driversLicenceFaceMatchRequest = new DriversLicenceFaceMatchRequest();
            driversLicenceFaceMatchRequest.setDob(dateOfBirthString);
            driversLicenceFaceMatchRequest.setFirstname(user.getFirstName());
            driversLicenceFaceMatchRequest.setIdBase64String(idImageBase64);
            driversLicenceFaceMatchRequest.setSurname(user.getLastName());
            driversLicenceFaceMatchRequest.setIdNo(verifyKycRequest.getIdNumber());
            driversLicenceFaceMatchRequest.setPassportBase64String(selfieImageBase64);

            app.print(driversLicenceFaceMatchRequest);
            Response<KycApiResponse<DriversLicenceFaceMatchResponse>> response = getDriverLicenseFaceMatch(driversLicenceFaceMatchRequest);
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
                    kyc.setIdType(verifyKycRequest.getIdType());
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
                    kyc.setIdType(verifyKycRequest.getIdType());
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

            app.print("Uploading user selfie....");
            String selfieFileName = "selfie-" + randomNumber + "-" + user.getUuid() + ".jpg";
            String savedSelfieUrl = fileStorageService.storeFile(selfieImage, selfieFileName, BlobType.IMAGE);
            app.print("Uploaded... " + savedSelfieUrl);

            app.print("Uploading user id....");
            String idFileName = "id-" + randomNumber + "-" + user.getUuid() + ".jpg";
            String savedIdImageUrl = fileStorageService.storeFile(idImage, idFileName, BlobType.IMAGE);
            app.print("Uploaded... " + savedSelfieUrl);

            IdentityBiometricsRequest identityBiometricsRequest = new IdentityBiometricsRequest();
            identityBiometricsRequest.setIdType("nin");
            identityBiometricsRequest.setPhoto(savedSelfieUrl);
            identityBiometricsRequest.setPhotoUrl(savedIdImageUrl);
            identityBiometricsRequest.setIdNumber(verifyKycRequest.getIdNumber());
            Response<KycApiResponse<VerifyMeResponse>> response = this.getIdentityBiometrics(identityBiometricsRequest);
            if (!response.isSuccessful())
                return new APIResponse<>(messageSource.getMessage("101", null, LocaleContextHolder.getLocale()),
                        false, "Details Provided is invalid");

            Kyc kyc = new Kyc();




            if (response.body() != null && response.body().getData().getStatus().contains("success")) {

                kyc.setVerificationStatus(true);
                kyc.setIdType(verifyKycRequest.getIdType());
                kyc.setUserId(user.getUuid());
                kyc.setIdNUmber(verifyKycRequest.getIdNumber());
                kyc.setScore(response.body().getData().getData().getPhotoMatching().getMatchScore());
                kyc.setVerdict(response.body().getData().getData().getPhotoMatching().getMatch());
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
                kyc.setIdType(verifyKycRequest.getIdType());
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

    public Response<VerifyMeAddressVerificationResponse> getAddressVerification(AddressVerificationRequestVerifyme request) throws Exception {
        String token = "Bearer " + getAccessTokenForWalletServiceCache();
        Response<VerifyMeAddressVerificationResponse> response = kycServiceInterface.getAddressVerification(token, request).execute();
        return response;
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

    public APIResponse<String> VerifyAddress(AddressVerificationRequestVerifyme addressVerificationRequestVerifyme, OAuth2Authentication authentication) {
        JwtUserDetail authorizedUser = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(authentication);
        User user = userRepository.findByUuid(authorizedUser.getUserUUID()).orElse(null);

        assert user != null;
        if (user.getKycLevel() == 3)
            return new APIResponse<>(messageSource.getMessage("101", null, LocaleContextHolder.getLocale()),
                    false, "User Already verified");


        String idType = "KYC";
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String currentData = sdf.format(user.getDateOfBirth());

        // format the phone number
        String userPhone = user.getPhoneNumber();
        userPhone = app.toPhoneNumber(userPhone).replaceFirst("234", "0");


        Applicant applicant = new Applicant();
        applicant.setDob(currentData);
        applicant.setLastname(user.getLastName());
        applicant.setIdNumber(userPhone);
        applicant.setFirstname(user.getFirstName());
        applicant.setIdType(idType);
        applicant.setPhone(userPhone);

        AddressVerificationRequestVerifyme addressVerificationRequestVerifyme1 = new AddressVerificationRequestVerifyme();
        addressVerificationRequestVerifyme1.setApplicant(applicant);
        addressVerificationRequestVerifyme1.setLga(addressVerificationRequestVerifyme.getLga());
        addressVerificationRequestVerifyme1.setState(addressVerificationRequestVerifyme.getState());
        addressVerificationRequestVerifyme1.setStreet(addressVerificationRequestVerifyme.getStreet());
        addressVerificationRequestVerifyme1.setLandmark(addressVerificationRequestVerifyme.getLandmark());

        Response<VerifyMeAddressVerificationResponse> response = null;
        try {

            //TEST DATA
            addressVerificationRequestVerifyme1.setLandmark("Beside GTbank");
            addressVerificationRequestVerifyme1.setStreet("270 Murtala Muhammed Way, Alagomeji. Yaba");
            addressVerificationRequestVerifyme1.setLga("surulere");
            addressVerificationRequestVerifyme1.setState("lagos");
            addressVerificationRequestVerifyme1.getApplicant().setIdNumber("08121234567");
            addressVerificationRequestVerifyme1.getApplicant().setFirstname("john");
            addressVerificationRequestVerifyme1.getApplicant().setLastname("doe");
            addressVerificationRequestVerifyme1.getApplicant().setPhone("08121234567");
            addressVerificationRequestVerifyme1.getApplicant().setDob("04-04-1944");
            addressVerificationRequestVerifyme1.getApplicant().setIdType("KYC");

            response = getAddressVerification(addressVerificationRequestVerifyme1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (!response.isSuccessful())
            return new APIResponse<>(messageSource.getMessage("101", null, LocaleContextHolder.getLocale()),
                    false, "Request Failed");

        KycAddressVerification kycAddressVerification = new KycAddressVerification();
        kycAddressVerification.setUserId(user.getUuid());
        kycAddressVerification.setBirthdate(response.body() != null ? response.body().getData().getApplicant().getBirthdate() : null);
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

    public APIResponse<String> processWebhookRequest(@RequestBody AddressVerificationWebhookRequest addressVerificationWebhookRequest) {
        if (addressVerificationWebhookRequest != null) {
            Long id = Long.valueOf(addressVerificationWebhookRequest.getData().getId());
            KycAddressVerification kycAddressVerification = kycAddressRepository.findByResID(id).orElseThrow(null);
            User user = userRepository.findByUuid(kycAddressVerification.getUserId()).orElse(null);
            if (user != null) {
                if (addressVerificationWebhookRequest.getData().getStatus().getStatus().contains("VERIFIED")) {
                    kycAddressVerification.setStatus(addressVerificationWebhookRequest.getData().getStatus().getStatus());
                    kycAddressVerification.setSubStatus(addressVerificationWebhookRequest.getData().getStatus().getStatus());
                    kycAddressRepository.save(kycAddressVerification);
                    user.setKycLevel(3);
                    userRepository.save(user);

                    // Send Email User
                    sendKycEmailUser(user, "kyc.verification.email.level.two.upgraded", "kyc.wallet.upgrade.email.subject");
                    return new APIResponse<>(messageSource.getMessage("000", null, LocaleContextHolder.getLocale()),
                            true, "Address verification Completed");
                } else {
                    // Update Kyc detail address
                    kycAddressVerification.setStatus(addressVerificationWebhookRequest.getData().getStatus().getStatus());
                    kycAddressVerification.setSubStatus(addressVerificationWebhookRequest.getData().getStatus().getStatus());
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
}
