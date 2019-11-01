package org.ftui.mobile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import es.dmoral.toasty.Toasty;
import org.ftui.mobile.model.User;
import org.ftui.mobile.utils.ApiCall;
import org.ftui.mobile.utils.ApiService;
import org.ftui.mobile.utils.PDialog;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    TextView helloMessage;
    private Button register_btn;
    private TextInputEditText password, c_password;
    private ApiService service;
    private String name, username;
    private PDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        pDialog = new PDialog(this);
        pDialog.buildDialog();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        Intent received = getIntent();

        JSONObject receivedHtmlResponse;
        name = null;
        username = null;
        try {
            receivedHtmlResponse = new JSONObject(received.getStringExtra("response"));
            name = receivedHtmlResponse.getString("name");
            username = receivedHtmlResponse.getString("username");
        }catch (JSONException e){
            e.printStackTrace();
        }

        helloMessage = findViewById(R.id.success_verif_text);
        String combined_name = getResources().getString(R.string.hello_text, name);
        helloMessage.setText(combined_name);

        register_btn = findViewById(R.id.register_btn);
        password = findViewById(R.id.addPassword);
        c_password = findViewById(R.id.verify_password);

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                service = ApiCall.getClient().create(ApiService.class);

                //check for blank password
                if(password.getText().toString().trim().length() < 8){
                    new AlertDialog.Builder(RegisterActivity.this)
                            .setTitle(R.string.dialog_title_not_strong_password)
                            .setMessage(R.string.dialog_message_not_strong_password)
                            .setPositiveButton(android.R.string.yes, null)
                            .setIcon(R.drawable.ic_info_black_24dp)
                            .show();
                    return;
                }else if(!password.getText().toString().equals(c_password.getText().toString())){
                    new AlertDialog.Builder(RegisterActivity.this)
                            .setTitle(R.string.dialog_title_not_matching_password)
                            .setMessage(R.string.dialog_message_not_matching_password)
                            .setPositiveButton(android.R.string.yes, null)
                            .setIcon(R.drawable.ic_info_black_24dp)
                            .show();
                    return;
                }
                pDialog.showDialog();
                //Gave null to other optional fields for now, change later.
                //Also given no_identitas asal asalan
                Call<JsonObject> call = service.register(name, username, password.getText().toString(), c_password.getText().toString(), null, null, null, null, "1231234123");
                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.errorBody() == null){
                            Toasty.info(getApplicationContext(), "Succesfully registered").show();
                            JsonObject parsed_res = response.body();
                            JsonObject succes_obj = parsed_res.get("success").getAsJsonObject();
                            String token = succes_obj.get("token").getAsString();

                            SharedPreferences.Editor editor = getSharedPreferences(LoginActivity.USER_SHARED_PREFERENCE, MODE_PRIVATE).edit();

                            User userStoreCache = new User(username, token, "unknownfbid");

                            Gson jsonUtil = new Gson();
                            String stringifiedUserCache = jsonUtil.toJson(userStoreCache);

                            editor.putString("user", stringifiedUserCache);
                            editor.apply();

                            pDialog.dismissDialog();

                            finish();
                        }else{
                            Toasty.info(getApplicationContext(), "Failed to Register").show();
                            pDialog.dismissDialog();
                            Log.d("OnSuccess :", response.errorBody().toString());
                        }

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toasty.info(getApplicationContext(), "Failed to Register").show();
                        pDialog.dismissDialog();
                        t.printStackTrace();
                    }
                });
            }
        });
    }
}
