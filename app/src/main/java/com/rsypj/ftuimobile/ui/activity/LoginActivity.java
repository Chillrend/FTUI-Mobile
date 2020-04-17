package com.rsypj.ftuimobile.ui.activity;

import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.rsypj.ftuimobile.R;
import com.rsypj.ftuimobile.base.SmartSipBaseActivity;
import com.rsypj.ftuimobile.ui.activity.controller.LoginController;

/**
 * Created by dhadotid on 09/04/2018.
 */

public class LoginActivity extends SmartSipBaseActivity {

    EditText etNIMorNIP;
    EditText etPassword;
    TextView tvRegister;
    Button btnLogin;
    LoginController controller;

    @Override
    public int getLayoutID() {
        return R.layout.activity_login;
    }

    @Override
    public void initItem() {
        etNIMorNIP = findViewById(R.id.login_activity_username_edittext);
        etPassword = findViewById(R.id.login_activity_password_edittext);
        btnLogin = findViewById(R.id.login_activity_signin_button);
        tvRegister = findViewById(R.id.registtv);
        etPassword.setTransformationMethod(new PasswordTransformationMethod());

        controller = new LoginController(this);
    }

    public EditText getEtNIMorNIP() {
        return etNIMorNIP;
    }

    public EditText getEtPassword() {
        return etPassword;
    }

    public TextView getTvRegister() {
        return tvRegister;
    }

    public Button getBtnLogin() {
        return btnLogin;
    }
}
