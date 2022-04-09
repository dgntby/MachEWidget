package com.example.khughes.machewidget;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface StatusService {
    @Headers({"Content-Type: application/json",
            "Accept-Encoding: gzip, deflate, br",
            "Accept: */*",
            "Accept-Language': en-us",
            "User-Agent: fordpass-cn/232 CFNetwork/1325.0.1 Darwin/21.1.0"})
/*            "User-Agent: FordPass/5 CFNetwork/1327.0.4 Chrome/96.0.4664.110"})*/
    // @GET("vehicles/v4/{VIN}/status")
    @GET("vehicles/{VIN}/status")
    Call<CarStatusCN> getStatus(@Header("auth-token") String token,
                              @Header("Accept-Language") String language,
                              @Header("Application-Id") String APID,
                              @Path("VIN") String VIN);
}
