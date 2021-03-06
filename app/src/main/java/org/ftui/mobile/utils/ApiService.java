package org.ftui.mobile.utils;

import com.google.gson.JsonObject;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import org.ftui.mobile.model.keluhan.Keluhan;
import org.ftui.mobile.model.singlekeluhan.SingleKeluhan;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;
import java.util.Map;

public interface ApiService {

    @FormUrlEncoded
    @POST("/api/pregister/")
    Call<JsonObject> register(@Field("name") String name, @Field("email") String email, @Field("password") String password,
                              @Field("c_password") String confirm_password, @Field("orgcode") String org_code,
                              @Field("faculty") String faculty, @Field("studyprog") String study_prog,
                              @Field("identitas") String identitas, @Field("noidentitas") String no_identitas);

    @FormUrlEncoded
    @POST("/api/updateToken/")
    Call<JsonObject> updatetoken(@HeaderMap Map<String,String> headers,@Field("token") String token);

    @FormUrlEncoded
    @POST("/api/plogin/")
    Call<JsonObject> login(@Field("email") String username, @Field("password") String password);


    @GET("/api/myaccount")
    Call<JsonObject> amialive(@Header("Accept") String accept, @Header("Authorization") String bearer);

    @GET
    Call<Keluhan> getAllKeluhan(@Url String url, @Header("Accept") String accept, @Header("Authorization") String bearer);

    @GET
    Call<Keluhan> getKeluhanById(@Url String url, @HeaderMap Map<String, String> headerMap);

    @POST("/api/keluhan/")
    @Multipart
    Call<JsonObject> submitKeluhan(@HeaderMap Map<String,String> headers, @Part("subject") RequestBody title,
                                   @Part("content") RequestBody content,@Part("location") RequestBody location,
                                   @Part("category_id") RequestBody category_id, @Part List<MultipartBody.Part> images);
    @GET
    Call<JsonObject> procKeluhan(@HeaderMap Map<String,String> headers, @Url String url);

    @POST("/api/comments/")
    @FormUrlEncoded
    Call<JsonObject> submitComment(@HeaderMap Map<String,String> headers, @Field("content") String content, @Field("ticket_id") String ticket_id);

    @DELETE("/api/keluhan/{id}")
    Call<JsonObject> deleteKeluhan(@Path("id") int id ,@HeaderMap  Map<String,String> headers);

    @POST("/api/resetToken")
    Call<JsonObject> resetToken(@HeaderMap Map<String, String> headers);
}
