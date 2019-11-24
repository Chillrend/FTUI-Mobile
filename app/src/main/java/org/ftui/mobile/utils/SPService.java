package org.ftui.mobile.utils;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import io.realm.Realm;
import org.ftui.mobile.model.CompleteUser;
import org.ftui.mobile.model.User;
import org.ftui.mobile.model.surveyor.Surveyor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

//TODO: Fragment at EKeluhan, Login, Main, Register Activity Done

public class SPService {
    public static final String COMPLETE_USER_SP = "COMPLETE_USER_SHARED_PREFERENCES";
    public static final String USER_SP = "USER_SHARED_PREFERENCE";
    public static final String FB_TOKEN_SP = "FIREBASE_TOKEN_SP";
    public static final String SURVEYOR_SP = "SURVEYOR_SHARED_PREFERENCES";
    public static final String REALM_INIT = "REALM_INIT_SHARED_PREFERENCES";

    public static final String COMPLETE_USER_STRING = "complete_user";
    public static final String USER_STRING = "user";
    public static final String SURVEYOR_STRING = "surveyor";
    public static final String FB_TOKEN_STRING = "fbtoken";
    public static final String FB_TOKEN_IS_UPLOADED_STRING = "fbtoken_is_uploaded";
    public static final String FB_TOKEN_IS_DELETED_STRING = "fbtoken_is_deleted";
    public static final String FB_TOKEN_TEMPORARY_USER_TOKEN = "fbtoken_temp_user_token";
    public static final String IS_REALM_INIT_SP = "is_realm_init";

    private Context ctx;
    private SharedPreferences completeUser, user, fbToken, surveyor, realm;
    private Gson gson = new Gson();

    public SPService(Context ctx){
        this.ctx = ctx;
        completeUser = ctx.getSharedPreferences(COMPLETE_USER_SP, Context.MODE_PRIVATE);
        user = ctx.getSharedPreferences(USER_SP, Context.MODE_PRIVATE);
        fbToken = ctx.getSharedPreferences(FB_TOKEN_SP, Context.MODE_PRIVATE);
        surveyor = ctx.getSharedPreferences(SURVEYOR_SP, Context.MODE_PRIVATE);
        realm = ctx.getSharedPreferences(REALM_INIT, Context.MODE_PRIVATE);
    }

    public Boolean isUserSpExist(){
        return user.contains(USER_STRING);
    }

    public Boolean isCompleteSpExist(){
        return completeUser.contains(COMPLETE_USER_STRING);
    }

    public Boolean isFbTokenExist(){
        return fbToken.contains(FB_TOKEN_STRING);
    }

    public Boolean isSurveyor(){
        return surveyor.contains(SURVEYOR_STRING);
    }

    public Boolean isFbTokenUploaded() {
        return fbToken.getBoolean(FB_TOKEN_IS_UPLOADED_STRING, false);
    }

    public Boolean isRealmInitialized(){
        return realm.getBoolean(IS_REALM_INIT_SP, false);
    }

    public User getUserFromSp(){
        String json = user.getString("user", null);
        return gson.fromJson(json, User.class);
    }

    public CompleteUser getCompleteUserFromSp(){
        String json = completeUser.getString(COMPLETE_USER_STRING, null);
        return gson.fromJson(json, CompleteUser.class);
    }

    public List<Surveyor> getSurveyorListFromSp(){
        String json = surveyor.getString(SURVEYOR_STRING, null);
        Type listType = new TypeToken<List<Surveyor>>() {}.getType();

        return gson.fromJson(json, listType);
    }

    public String getFbTokenFromSp(){
        return  fbToken.getString(FB_TOKEN_STRING, null);
    }

    public static String getUserToken(Context ctx){
        if(ctx.getSharedPreferences(USER_SP, Context.MODE_PRIVATE).contains("user")){
            String json = ctx.getSharedPreferences(USER_SP, Context.MODE_PRIVATE).getString(USER_STRING, null);
            Gson jsonUtil = new Gson();
            User user = jsonUtil.fromJson(json, User.class);

            return user.getToken();
        }else{
            return null;
        }
    }

    public void setUserToSp(String obj){
        user.edit().putString(USER_STRING, obj).apply();
    }

    public void setCompleteUserToSp(String obj){
        completeUser.edit().putString(COMPLETE_USER_STRING, obj).apply();
    }

    public void setFbTokenToSp(String obj){
        fbToken.edit().putString(FB_TOKEN_STRING, obj).apply();
    }

    public void setFbTokenUploaded(@NonNull Boolean obj){
        fbToken.edit().putBoolean(FB_TOKEN_IS_UPLOADED_STRING, obj).apply();
    }

    public void setSurveyorToSp(String obj){
        surveyor.edit().putString(SURVEYOR_STRING, obj).apply();
    }

    public void setRealmIsInit(Boolean obj){
        realm.edit().putBoolean(IS_REALM_INIT_SP, obj).apply();
    }

    public void deleteAllSp(){
        fbToken.edit().putString(FB_TOKEN_TEMPORARY_USER_TOKEN, getUserToken(ctx)).apply();

        user.edit().clear().apply();
        completeUser.edit().clear().apply();

        deleteFbTokenFromServer();

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        realm.deleteAll();

        realm.commitTransaction();

        if(isSurveyor()){
            surveyor.edit().clear().apply();
        }
    }

    public boolean isFbTokenDeletedFromServer(){
        return fbToken.getBoolean(FB_TOKEN_IS_DELETED_STRING, false);
    }

    public void deleteFbTokenFromServer(){
        String token = fbToken.getString(FB_TOKEN_TEMPORARY_USER_TOKEN, null);
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("Authorization", "Bearer " + token);

        ApiService service = ApiCall.getClient().create(ApiService.class);
        Call<JsonObject> call = service.resetToken(headers);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(!response.isSuccessful()){
                    return;
                }

                fbToken.edit().putBoolean(FB_TOKEN_IS_UPLOADED_STRING, false).putBoolean(FB_TOKEN_IS_DELETED_STRING, true).apply();
                fbToken.edit().remove(FB_TOKEN_TEMPORARY_USER_TOKEN).apply();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }
}
