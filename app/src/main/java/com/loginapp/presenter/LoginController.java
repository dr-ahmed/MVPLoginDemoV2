package com.loginapp.presenter;

import android.widget.EditText;

import com.loginapp.model.LoginAsyncTask;
import com.loginapp.model.User;
import com.loginapp.model.Util;
import com.loginapp.view.LoginActivity;

public class LoginController {

    private LoginActivity view;

    public LoginController(LoginActivity view) {
        this.view = view;
    }

    private boolean isFieldEmpty(EditText editText) {
        if (editText.getText().toString().trim().length() == 0) {
            editText.requestFocus();
            editText.setError("Champ obligatoire !");
            return true;
        }
        return false;
    }

    public void onClickConnectButtonController() {
        if (isFieldEmpty(view.getLoginEdt()) || isFieldEmpty(view.getPasswordEdt()))
            return;

        if (!Util.isInternetAvailable()){
            view.showMessage("Pas de connexion internet", "Merci de vérifier votre connexion internet!");
            return;
        }

        view.showProgressDialog();
        verifyUser();
    }

    private void verifyUser() {
        String login = view.getLoginEdt().getText().toString(), password = view.getPasswordEdt().getText().toString();
        LoginAsyncTask loginAsyncTask = new LoginAsyncTask(this);
        loginAsyncTask.execute(login, password);
    }

    public void onLoginResponse(String result, User user, boolean exceptionOccurred, boolean userIsConfirmed) {
        view.dismissProgressDialog();

        if (exceptionOccurred)
            view.showMessage("Exception", "Désolé, une erreur s'est produite !\n" + "DETAILS :\n" + result);
        else {
            if (userIsConfirmed)
                view.showMessage("Bienvenue " + user.getUsername(), "Vous êtes désormais connecté.\n" +
                        "Votre mot de passe est : " + user.getPassword());
            else {
                if (result.isEmpty())
                    view.showMessage("Informations incorrectes", "Login ou mot de passe incorrect !");
                else
                    view.showMessage("Erreur ", "L'entête de JSON est invalide");
            }
        }
    }
}
