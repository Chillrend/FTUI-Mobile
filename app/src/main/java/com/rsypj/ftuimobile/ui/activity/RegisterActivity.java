package com.rsypj.ftuimobile.ui.activity;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.rsypj.ftuimobile.R;
import com.rsypj.ftuimobile.base.SmartSipBaseActivity;
import com.rsypj.ftuimobile.ui.activity.controller.RegisterController;

/**
 * Created by dhadotid on 09/04/2018.
 */

public class RegisterActivity extends SmartSipBaseActivity {

    EditText etNim;
    EditText etName;
    EditText etPassword;
    //EditText etRoles;
    Spinner spinnerRoles;
    EditText etEmail;
    Button btnRegister;
    RegisterController controller;

    @Override
    public int getLayoutID() {
        return R.layout.activity_register;
    }

    @Override
    public void initItem() {
        etNim = findViewById(R.id.login_etNIM);
        etName = findViewById(R.id.login_etNama);
        etPassword = findViewById(R.id.login_etPassword);
        //etRoles = findViewById(R.id.login_etRole);
        etEmail = findViewById(R.id.login_etEmail);
        spinnerRoles = findViewById(R.id.register_spinner_status);
        btnRegister = findViewById(R.id.edit_profil_button);

        controller = new RegisterController(this);
    }

    public Spinner getSpinnerRoles() {
        return spinnerRoles;
    }

    public EditText getEtNim() {
        return etNim;
    }

    public EditText getEtName() {
        return etName;
    }

    public EditText getEtPassword() {
        return etPassword;
    }


    public EditText getEtEmail() {
        return etEmail;
    }

    public Button getBtnRegister() {
        return btnRegister;
    }
}
