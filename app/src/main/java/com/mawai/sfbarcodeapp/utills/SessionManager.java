package com.mawai.sfbarcodeapp.utills;


import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;

    private static final String IS_LOGIN = "isLoggedIn";
    // OAUTH KEY (make variable public to access from outside)
    public static final String KEY_OAUTH = "OAUTH_KEY";
    // TOKEN TYPE (make variable public to access from outside)
    public static final String TOKEN_TYPE = "token_type";

    private static final String PREF_NAME = "appPref";

    public static final String USER_CODE = "user_code";
    public static final String IP = "ip";
    public static final String CHKBOX = "chkbox";

    public static final String PASSWORD = "password";
    public static final String USER_NAME = "user_name";
    public static final String UNIT_CODE = "unit_code";
    public static final String UNIT_NAME = "unit_name";
    public static final String ID = "id";
    public static final String MACHINE_CODE = "machine_code";
    public static final String SCAN_LOCATION = "scan_location";

    public static final String PO_NUMBER = "po_number";
    public static final String INVOICE_NUMBER = "invoice_number";
    public static final String SESSION_ID = "session_id";
    public static final String SESSION_IP = "session_ip";

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void clearDataPref() {
        pref = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.apply();
    }

    /**
     * Create login session
     **/
    public void createLoginSession(String oauth_key, String token_type) {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing oauth in pref
        editor.putString(KEY_OAUTH, oauth_key);

        // Storing token type in pref
        editor.putString(TOKEN_TYPE, token_type);

        // commit changes
        editor.commit();
    }


    public void save(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public void save(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void save(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public String getString(String key) {
        return pref.getString(key, "");
    }
    public int getInt(String key) {
        return pref.getInt(key, 0);
    }

    public Boolean getBoolean(String key) {
        return pref.getBoolean(key, false);
    }

}
