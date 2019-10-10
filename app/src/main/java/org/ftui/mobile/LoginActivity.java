package org.ftui.mobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import es.dmoral.toasty.Toasty;
import org.ftui.mobile.fragment.Home;
import org.ftui.mobile.model.User;
import org.ftui.mobile.utils.ApiCall;
import org.ftui.mobile.utils.ApiService;
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

    public static String USER_SHARED_PREFERENCE = "USER_SHARED_PREFERENCE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(userPrefExist(this)) finish();

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

        if(userPrefExist(getApplicationContext())) finish();
    }

    public static boolean userPrefExist(Context ctx){
        SharedPreferences spref = ctx.getSharedPreferences(USER_SHARED_PREFERENCE, MODE_PRIVATE);

        return spref.contains("user");
    }

    public static boolean completeUserPrefExist(Context ctx){
        SharedPreferences spref = ctx.getSharedPreferences(Home.COMPLETE_USER_SHARED_PREFERENCES, MODE_PRIVATE);

        return spref.contains("complete_user");
    }

    public static boolean isSurveyor(Context ctx){
        SharedPreferences spref = ctx.getSharedPreferences(Home.SURVEYOR_SHARED_PREFERENCES, MODE_PRIVATE);

        return spref.contains("surveyor");
    }

    public static String getUserToken(Context ctx){
        if(ctx.getSharedPreferences(USER_SHARED_PREFERENCE, MODE_PRIVATE).contains("user")){
            String json = ctx.getSharedPreferences(USER_SHARED_PREFERENCE, MODE_PRIVATE).getString("user", null);
            Gson jsonUtil = new Gson();
            User user = jsonUtil.fromJson(json, User.class);


            return user.getToken();
        }else{
            return null;
        }
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
                goLogin();
                break;
        }
    }

    private void goLogin(){
        if(password.getText().toString().trim().length() < 1 || email.getText().toString().trim().length() < 1){
            password_input_layout.setError("Email dan Password tidak dapat kosong");
            return;
        }

        service = ApiCall.getClient().create(ApiService.class);
        Call<JsonObject> call = service.login(email.getText().toString().trim(), password.getText().toString());
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.errorBody() != null){
                    password_input_layout.setError("Email atau password salah");
                    Log.d("OnSuccess :", response.errorBody().toString());
                    return;
                }
                JsonObject parsed_res = response.body();
                JsonObject succes_obj = parsed_res.get("success").getAsJsonObject();
                String token = succes_obj.get("token").getAsString();

                SharedPreferences.Editor editor = getSharedPreferences(LoginActivity.USER_SHARED_PREFERENCE, MODE_PRIVATE).edit();

                User userStoreCache = new User(email.getText().toString().trim(), token);

                Gson jsonUtil = new Gson();
                String stringifiedUserCache = jsonUtil.toJson(userStoreCache);

                editor.putString("user", stringifiedUserCache);
                editor.apply();

                Log.d("OnSuccess :", parsed_res.toString());
                Log.d("OnSuccess, user token :", token);

                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);

                finish();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toasty.info(getApplicationContext(), "Something went wrong.. (err : onFailure Reached)").show();
                t.printStackTrace();
            }
        });
    }
}
