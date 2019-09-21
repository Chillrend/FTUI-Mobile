package org.ftui.mobile.utils;

import com.google.gson.JsonObject;
import org.ftui.mobile.model.keluhan.Keluhan;
import org.ftui.mobile.model.keluhan.Ticket;
import retrofit2.Call;
import retrofit2.http.*;

public interface ApiService {

    @FormUrlEncoded
    @POST("/api/pregister/")
    Call<JsonObject> register(@Field("name") String name, @Field("email") String email, @Field("password") String password,
                              @Field("c_password") String confirm_password, @Field("orgcode") String org_code, @Field("faculty") String faculty,
                              @Field("studyprog") String study_prog, @Field("identitas") String identitas, @Field("noidentitas") String no_identitas);
    @FormUrlEncoded
    @POST("/api/plogin/")
    Call<JsonObject> login(@Field("email") String username, @Field("password") String password);

    @POST("/api/amialive")
    Call<JsonObject> amialive(@Header("Accept") String accept, @Header("Authorization") String bearer);

    @GET
    Call<Keluhan> getAllKeluhan(@Url String url, @Header("Accept") String accept, @Header("Authorization") String bearer);
}
