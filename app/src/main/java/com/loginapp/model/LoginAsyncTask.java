package com.loginapp.model;

import android.os.AsyncTask;
import android.util.Log;

import com.loginapp.presenter.LoginController;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.*;

import static com.loginapp.model.Util.*;

public class LoginAsyncTask extends AsyncTask<String, Integer, String> {

    private static final String TAG = "LoginAsyncTask";
    private boolean userIsConfirmed = false, exceptionOccurred = false;
    private LoginController loginControllerListener;
    private User user;

    public LoginAsyncTask(LoginController loginControllerListener) {
        this.loginControllerListener = loginControllerListener;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            URL insertURL = new URL(SCRIPT_PATH + LOGIN_SCRIPT_NAME);
            HttpURLConnection connection = (HttpURLConnection) insertURL.openConnection();
            connection.setRequestMethod(POST_METHOD);
            connection.setDoOutput(true);
            //connection.setDoInput(true);

            connection.connect();

            OutputStream outputStream = connection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, ENCODING));

            String post_data = URLEncoder.encode(USER_LOGIN_TAG, ENCODING) + "=" + URLEncoder.encode(params[0], ENCODING)
                    + "&" + URLEncoder.encode(USER_PASSWORD_TAG, ENCODING) + "=" + URLEncoder.encode(params[1], ENCODING);
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, CHAR_SET_NAME));
            StringBuilder result = new StringBuilder();
            String line = "";

            while ((line = bufferedReader.readLine()) != null)
                result.append(line);
            bufferedReader.close();
            inputStream.close();
            connection.disconnect();

            if (!result.toString().equals(EMPTY_JSON_DOCUMENT)) {
                JSONObject response = new JSONObject(result.toString());
                if (!response.isNull(JSON_HEADER_TAG)) {
                    userIsConfirmed = true;
                    JSONObject userData = response.getJSONObject(JSON_HEADER_TAG);
                    String login = userData.getString(USER_LOGIN_TAG);
                    String password = userData.getString(USER_PASSWORD_TAG);
                    user = new User(login, password);
                } else {
                    Log.e(TAG, result.toString());
                    return result.toString();
                }
            }
            return "";
        } catch (Exception e) {
            exceptionOccurred = true;
            Log.e(TAG, Log.getStackTraceString(e));
            return "Exception name : " + e.getClass().getName() + "\nException message : " + e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        loginControllerListener.onLoginResponse(result, user, exceptionOccurred, userIsConfirmed);
    }
}
