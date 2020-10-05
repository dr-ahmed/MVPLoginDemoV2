package com.loginapp.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.loginapp.R;
import com.loginapp.presenter.LoginController;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText loginEdt, passwordEdt;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        loginEdt = findViewById(R.id.loginEdt);
        passwordEdt = findViewById(R.id.passwordEdt);
        Button connectBtn = findViewById(R.id.connectBtn);
        connectBtn.setOnClickListener(this);
    }

    public EditText getLoginEdt() {
        return loginEdt;
    }

    public EditText getPasswordEdt() {
        return passwordEdt;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.connectBtn) {
            LoginController loginController = new LoginController(this);
            loginController.onClickConnectButtonController();
        }
    }

    public void showProgressDialog() {
        progressDialog = ProgressDialog.show(this, "", "Vérification des informations en cours ...",
                false, false);
    }

    public void dismissProgressDialog() {
        progressDialog.dismiss();
    }

    public void showMessage(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setNegativeButton("Ok", null)
                .show();
    }
}
