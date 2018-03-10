package com.greenwich.utils;

import android.content.*;
import android.content.SharedPreferences.Editor;

import com.greenwich.menwatch.LoginActivity;
import com.greenwich.menwatch.MainActivity;

import java.util.HashMap;

/**
 * Created by nguye on 3/10/2018.
 */

public class CustomerSession {
    SharedPreferences sharedPreferences;
    Editor editor;
    Context _context;

    // Sharedpref file name
    private static final String PREFER_NAME = "MenwatchPref";

    // All Shared Preferences Keys
    private static final String IS_USER_LOGIN = "IsCustomerLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "CustomerName";

    // Email address (make variable public to access from outside)
    public static final String KEY_ID = "CustomerId";
    public static final String KEY_EMAIL = "email";
    public CustomerSession(Context context) {
        this._context = context;
        sharedPreferences = _context.getSharedPreferences(PREFER_NAME, 0);
        editor = sharedPreferences.edit();
    }

    public void createCustomerLoginSession(String id, String email, String fullname) {
        editor.putBoolean(IS_USER_LOGIN, true);
        editor.putString(KEY_ID, id);
        editor.putString(KEY_EMAIL, id);
        editor.putString(KEY_NAME, fullname);
        editor.commit();
    }

    public boolean checkCustomerLogin() {
        if (!this.isCustomerLoggedIn()) {
            Intent intent = new Intent(_context, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(intent);
            return true;
        }
        return false;

    }

    /**
     * Get stored session data
     */
    public HashMap<String, String> getCustomerDetails() {

        //Use hashmap to store user credentials
        HashMap<String, String> customer = new HashMap<String, String>();

        // customer name
        customer.put(KEY_NAME, sharedPreferences.getString(KEY_NAME, null));

        // user email id
        customer.put(KEY_ID, sharedPreferences.getString(KEY_ID, null));

        // return user
        return customer;
    }

    /**
     * Clear session details
     */
    public void logoutCustomer() {

        // Clearing all user data from Shared Preferences
        editor.clear();
        editor.commit();
        // After logout redirect user to Login Activity
        Intent i = new Intent(_context.getApplicationContext(), MainActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // Staring Login Activity
        _context.getApplicationContext().startActivity(i);
    }


    // Check for login
    public boolean isCustomerLoggedIn() {
        return sharedPreferences.getBoolean(IS_USER_LOGIN, false);
    }

}
