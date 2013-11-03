package com.example.util;

import android.content.Context;
import android.widget.Toast;

/**
 * A wrapper around the Android Toast
 * 
 * @author Blundell (http://blog.blundell-apps.com/)
 * 
 */
public class Toaster {

    private final Context context;

    public Toaster(Context context) {
        this.context = context;
    }

    public void popBurntToast(String msg) {
        makeToast(msg, Toast.LENGTH_LONG).show();
    }

    public void popToast(String msg) {
        makeToast(msg, Toast.LENGTH_SHORT).show();
    }

    private Toast makeToast(String msg, int length) {
        return Toast.makeText(context, msg, length);
    }
}
