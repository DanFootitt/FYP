package com.example.menuexample;

import android.content.Context;
import android.content.Intent;

public class CommonUtilities {

    static final String SERVER_URL = "http://192.168.0.14/register.php"; 
 
    static final String SENDER_ID = "611462714435"; 
 
    static final String TAG = "Unilink Bus Application";
 
    static final String DISPLAY_MESSAGE_ACTION =
            "com.androidhive.pushnotifications.DISPLAY_MESSAGE";
 
    static final String EXTRA_MESSAGE = "message";
 

    static void displayMessage(Context context, String message) {
        Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
        intent.putExtra(EXTRA_MESSAGE, message);
        context.sendBroadcast(intent);
    }
}
