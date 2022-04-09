package com.example.khughes.machewidget;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface OTAStatusService {
    @Headers({"Accept: application/json",
            "Accept-Encoding: gzip, deflate, br",
            "User-Agent: fordpass-cn/232 CFNetwork/1325.0.1 Darwin/21.1.0",
            "Referer: https://ford.com", "Origin: https://ford.com'"})
    @GET("status")
    Call<OTAStatus> getOTAStatus(@Header("auth-token") String token,
                                 @Header("Accept-Language") String language,
                                 @Header("Application-Id") String APID,
                                 @Query("country") String country,
                                 @Query("vin") String VIN);

    ////    https://www.digitalservices.ford.com/owner/api/v2/ota/status?country=USA&vin='+VIN
}
