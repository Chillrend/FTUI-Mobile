package org.ftui.mobile.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.ftui.mobile.model.CompleteUser;
import org.ftui.mobile.model.User;
import org.ftui.mobile.model.surveyor.Surveyor;

import java.lang.reflect.Type;
import java.util.List;

public class SPService {
    private static final String COMPLETE_USER_SP = "COMPLETE_USER_SHARED_PREFERENCES";
    private static final String USER_SP = "USER_SHARED_PREFERENCE";
    private static final String FB_TOKEN_SP = "FIREBASE_TOKEN_SP";
    private static final String SURVEYOR_SP = "SURVEYOR_SHARED_PREFERENCES";

    private static final String COMPLETE_USER_STRING = "complete_user";
    private static final String USER_STRING = "user";
    private static final String SURVEYOR_STRING = "surveyor";
    private static final String FB_TOKEN_STRING = "fbtoken";

    private Context ctx;
    private SharedPreferences completeUser, user, fbToken, surveyor;
    private SharedPreferences.Editor completeUserEditor, userEditor, fbTokenEditor;
    private Gson gson = new Gson();

    public SPService(Context ctx){
        this.ctx = ctx;
        completeUser = ctx.getSharedPreferences(COMPLETE_USER_SP, Context.MODE_PRIVATE);
        user = ctx.getSharedPreferences(USER_SP, Context.MODE_PRIVATE);
        fbToken = ctx.getSharedPreferences(FB_TOKEN_SP, Context.MODE_PRIVATE);
        surveyor = ctx.getSharedPreferences(SURVEYOR_SP, Context.MODE_PRIVATE);
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

    public void setSurveyorToSp(String obj){
        surveyor.edit().putString(SURVEYOR_STRING, obj).apply();
    }

    public void deleteAllSp(){
        user.edit().clear().apply();
        completeUser.edit().clear().apply();
        fbToken.edit().clear().apply();
        if(isSurveyor()){
            surveyor.edit().clear().apply();
        }
    }
}
