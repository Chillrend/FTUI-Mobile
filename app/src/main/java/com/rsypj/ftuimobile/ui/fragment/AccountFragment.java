package com.rsypj.ftuimobile.ui.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.rsypj.ftuimobile.R;
import com.rsypj.ftuimobile.base.SmartSipBaseFragment;
import com.rsypj.ftuimobile.ui.fragment.controller.AccountController;

/**
 * Created by dhadotid on 08/04/2018.
 */

public class AccountFragment extends SmartSipBaseFragment {

//    TextView tvAccount;
    EditText etNim;
    EditText etName;
    EditText etEmail;
    EditText etRoles;
    EditText etPassword;
    Button btnUpdate;
    AccountController controller;

    @Override
    public int getLayoutID() {
        return R.layout.fragment_account;
    }

    @Override
    public void initItem(View v) {
        etNim  = v.findViewById(R.id.login_etNIM);
        etName = v.findViewById(R.id.login_etNama);
        etEmail = v.findViewById(R.id.login_etEmail);
        etRoles = v.findViewById(R.id.login_etRole);
        etPassword = v.findViewById(R.id.login_etPassword);
        btnUpdate = v.findViewById(R.id.edit_profil_button);

        controller = new AccountController(this);
    }

    public Button getBtnUpdate() {
        return btnUpdate;
    }

    public EditText getEtPassword() {
        return etPassword;
    }

    public EditText getEtNim() {
        return etNim;
    }

    public EditText getEtName() {
        return etName;
    }

    public EditText getEtEmail() {
        return etEmail;
    }

    public EditText getEtRoles() {
        return etRoles;
    }
}
