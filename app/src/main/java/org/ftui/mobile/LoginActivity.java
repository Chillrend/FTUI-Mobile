package org.ftui.mobile;

import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import es.dmoral.toasty.Toasty;

public class LoginActivity extends AppCompatActivity {

    private TextView forgotPasswordLinkBtn, registerLinkBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        registerLinkBtn = findViewById(R.id.goRegisterBtn);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
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
        }
    }
}
