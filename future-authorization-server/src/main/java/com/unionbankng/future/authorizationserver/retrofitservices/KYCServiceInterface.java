package com.unionbankng.future.authorizationserver.retrofitservices;

import com.unionbankng.future.authorizationserver.pojos.*;
import retrofit2.Call;
import retrofit2.http.*;

public interface KYCServiceInterface {
    String OAUTH_URL = "kycserv/oauth/token";
    String NIN_FULL_DETAIL = "kycserv/api/v1/kyc/ninfull";
    String VOTERS_CARD_FACEMATCH = "kycserv/api/v1/kyc/verify_id_with_seamfix";
    String DRIVERS_LICENSE_FACEMATCH = "kycserv/api/v1/kyc/driversLicenseFacematch";
    String ID_VERIFICATION = "kycserv/api/v1/kyc/id_verification";
    String NIN_BOOLEAN = "kycserv/api/v1/kyc/ninfullboolenMatch";
    String PASSPORT_FACEMATCH = "kycserv/api/v1/kyc/passportFacematch";
    String ID_FACEMATCH = "kycserv/api/v1/kyc/id";
    String NIN_FACEMATCH = "kycserv/api/v1/kyc/ninFacematch";
    String IDENTITY_BIOMETRICS = "kycserv/api/v1/kyc/identityBiometrics";
    String ADDRESS_VERIFICATION = "kycserv/api/v1/kyc/address";

    @FormUrlEncoded
    @POST(OAUTH_URL)
    Call<KYCTokenResponse> getAccessToken(@Header("Authorization") String authorization, @Field("username") String username, @Field("password") String password,
                                          @Field("grant_type") String grantType);

    @POST(NIN_FULL_DETAIL)
    Call<NinFullDetailResponse> getNinFullDetail(@Header("Authorization") String authorization, @Body NinFullDetailRequest ninFullDetailRequest);


    @POST(PASSPORT_FACEMATCH)
    Call<KycApiResponse<PassportFaceMatchResponse>> getPassportFaceMatch(@Header("Authorization") String authorization, @Body PassportFaceMatchRequest passportFaceMatchRequest);

    @POST(ID_FACEMATCH)
    Call<KycApiResponse<VerifiedIdResponse>> getIdFaceMatch(@Header("Authorization") String authorization, @Body IdRequest request);

    @POST(VOTERS_CARD_FACEMATCH)
    Call<KycApiResponse<VotersCardFaceMatchResponse>> getVotersCardFaceMatch(@Header("Authorization") String authorization, @Body VotersCardFaceMatchRequest votersCardFaceMatchRequest);

    @POST(IDENTITY_BIOMETRICS)
    Call<KycApiResponse<DriversLicenceFaceMatchResponse>> getDriverLicenseFaceMatch(@Header("Authorization") String authorization, @Body IdentityBiometricsRequest identityBiometricsRequest);

    @POST(NIN_FACEMATCH)
    Call<DriversLicenceFaceMatchResponse> getNinFaceMatch(@Header("Authorization") String authorization, @Body AddressVerificationRequest addressVerificationRequest);

    @POST(IDENTITY_BIOMETRICS)
    Call<KycApiResponse<VerifyMeResponse>> getIdentityBiometrics(@Header("Authorization") String authorization, @Body IdentityBiometricsRequest identityBiometricsRequest);

    @POST(ADDRESS_VERIFICATION)
    Call<AddressVerifyResponse<AddressVerificationDto>> getAddressVerification(@Header("Authorization") String authorization, @Body VerifyAddressRequest addressVerificationRequestVerifyme);

}
