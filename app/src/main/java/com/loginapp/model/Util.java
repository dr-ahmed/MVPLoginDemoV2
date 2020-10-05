package com.loginapp.model;

import android.util.Log;

import java.util.concurrent.TimeUnit;

public class Util {

    private static final String TAG = "Util";

    public static String POST_METHOD = "POST",
            SCRIPT_PATH = "http://192.168.1.104/scripts/",
            LOGIN_SCRIPT_NAME = "get_user.php",
            ENCODING = "UTF-8",
            USER_LOGIN_TAG = "login",
            USER_PASSWORD_TAG = "password",
            CHAR_SET_NAME = "iso-8859-1",
            EMPTY_JSON_DOCUMENT = "[]",
            JSON_HEADER_TAG = "User";

    public static boolean isInternetAvailable() {
        String netAddress;
        try {
            netAddress = new InternetCheckingTask().execute("www.google.com").get(1000, TimeUnit.MILLISECONDS);
            return !netAddress.equals("");
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
            return false;
        }
    }
}
