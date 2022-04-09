package com.example.khughes.machewidget;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface CommandService {
    @Headers({"Content-Type: application/json",
            "Accept-Encoding: gzip, deflate, br",
            "User-Agent: fordpass-cn/232 CFNetwork/1325.0.1 Darwin/21.1.0"})
    @PUT("vehicles/{APIVERSION}/{VIN}/{COMPONENT}/{OPERATION}")
    Call<CommandStatus> putCommand(@Header("auth-token") String token,
                                   @Header("Application-Id") String APID,
                                   @Path("APIVERSION") String version,
                                   @Path("VIN") String VIN,
                                   @Path("COMPONENT") String component,
                                   @Path("OPERATION") String operation
    );

    @Headers({"Accept: application/json",
            "Accept-Encoding: gzip, deflate, br",
            "User-Agent: fordpass-cn/232 CFNetwork/1325.0.1 Darwin/21.1.0",
            "Referer: https://ford.com", "Origin: https://ford.com'"})
    @DELETE("vehicles/{APIVERSION}/{VIN}/{COMPONENT}/{OPERATION}")
    Call<CommandStatus> deleteCommand(@Header("auth-token") String token,
                                      @Header("Application-Id") String APID,
                                      @Path("APIVERSION") String version,
                                      @Path("VIN") String VIN,
                                      @Path("COMPONENT") String component,
                                      @Path("OPERATION") String operation
    );

    @Headers({"Accept: application/json",
            "Accept-Encoding: gzip",
            "Connection: Keep-Alive",
            "User-Agent: fordpass-cn/232 CFNetwork/1325.0.1 Darwin/21.1.0",
            "Referer: https://ford.com",
            "Origin: https://ford.com'"})
    @GET("vehicles/v3/{VIN}/{COMPONENT}/{OPERATION}/{CODE}")
    Call<CommandStatus> getCommandResponse(@Header("auth-token") String token,
                                      @Header("Application-Id") String APID,
                                      @Path("VIN") String VIN,
                                      @Path("COMPONENT") String component,
                                      @Path("OPERATION") String operation,
                                      @Path("CODE") String code
    );

}
