package org.ftui.mobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import es.dmoral.toasty.Toasty;
import org.ftui.mobile.fragment.Home;
import org.ftui.mobile.model.User;
import org.ftui.mobile.model.surveyor.Details;
import org.ftui.mobile.model.surveyor.Surveyor;
import org.ftui.mobile.utils.ApiCall;
import org.ftui.mobile.utils.ApiService;
import org.ftui.mobile.service.FirebaseInstance;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import org.ftui.mobile.utils.PDialog;
import org.ftui.mobile.utils.SPService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private TextView forgotPasswordLinkBtn, registerLinkBtn;
    private EditText email;
    private TextInputEditText password;
    private TextInputLayout password_input_layout;
    private Button goLoginButton;
    private ApiService service;
    private PDialog pDialog;
    private SPService sharedPreferenceService;
    private Gson jsonUtil = new Gson();

    public static String USER_SHARED_PREFERENCE = "USER_SHARED_PREFERENCE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferenceService = new SPService(this);

        if(sharedPreferenceService.isUserSpExist()) finish();

        pDialog = new PDialog(this);
        pDialog.buildDialog();

        registerLinkBtn = findViewById(R.id.goRegisterBtn);
        password = findViewById(R.id.pwd_signin);
        email = findViewById(R.id.email);
        password_input_layout = findViewById(R.id.pwd_signin_layout);
        registerLinkBtn = findViewById(R.id.goRegisterBtn);
        goLoginButton = findViewById(R.id.goLoginButton);

        goLoginButton.setOnClickListener(this::onClick);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

    }

    @Override
    protected void onResume(){
        super.onResume();

        if(sharedPreferenceService.isUserSpExist()) finish();
    }


    public void onClick(View v){
        switch (v.getId()){
            case R.id.goRegisterBtn:
                Intent intent = new Intent(LoginActivity.this, VerifyStudentForRegister.class);
                startActivity(intent);
                break;
            case R.id.forgotPasswordButton:
                Toasty.info(this, "Incoming Features").show();
                break;
            case R.id.goLoginButton:
                pDialog.showDialog();
                goLogin();
                break;
        }
    }

    private void goLogin(){
        if(password.getText().toString().trim().length() < 1 || email.getText().toString().trim().length() < 1){
            password_input_layout.setError(this.getText(R.string.email_password_cant_be_empty));
            pDialog.dismissDialog();
            return;
        }

        service = ApiCall.getClient().create(ApiService.class);
        Call<JsonObject> call = service.login(email.getText().toString().trim(), password.getText().toString());
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.errorBody() != null){
                    password_input_layout.setError("Email atau password salah");
                    pDialog.dismissDialog();
                    return;
                }
                JsonObject parsed_res = response.body();
                JsonObject succes_obj = parsed_res.get("success").getAsJsonObject();
                String token = succes_obj.get("token").getAsString();

                String fbtoken = getSharedPreferences("FIREBASE_TOKEN_SP", MODE_PRIVATE).getString("fb", null);
                User userStoreCache = new User(email.getText().toString().trim(), token, fbtoken);


                //this below code is actually wtf
                Gson gson = new Gson();
                String spSurveyor = getSharedPreferences(Home.SURVEYOR_SHARED_PREFERENCES, MODE_PRIVATE).getString("surveyor", null);

                if(spSurveyor != null && !spSurveyor.equals("[]")) {
                    Type listType = new TypeToken<List<Surveyor>>() {}.getType();
                    List<Surveyor> surveyors = gson.fromJson(spSurveyor, listType);

                    for (Surveyor surveyor : surveyors) {
                        Details det = surveyor.getDetails();
                        String name = det.getName();
                        FirebaseMessaging.getInstance().subscribeToTopic(name);
                    }
                }

                    HashMap<String,String> headerMap = new HashMap<>();
                    headerMap.put("accept", "application/json");
                    headerMap.put("Authorization", "Bearer " + token);
                    Call<JsonObject> called = service.updatetoken(headerMap, fbtoken);
                    called.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.errorBody() == null){
                            Toasty.info(getApplicationContext(), "initialized").show();
                        }else{
                            Toasty.info(getApplicationContext(), "notinitialized").show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toasty.info(getApplicationContext(), "Failed to Register").show();
                        t.printStackTrace();
                    }
                });

                sharedPreferenceService.setUserToSp(jsonUtil.toJson(userStoreCache));

                pDialog.dismissDialog();

                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);

                finish();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toasty.error(LoginActivity.this, "Gagal untuk login, silahkan cek koneksi internet Anda").show();
                t.printStackTrace();
            }
        });
    }
}
