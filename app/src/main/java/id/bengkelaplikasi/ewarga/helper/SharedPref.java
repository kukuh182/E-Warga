package id.bengkelaplikasi.ewarga.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import id.bengkelaplikasi.ewarga.main.App;

/**
 * Created by Kukuh182 on 08-Sep-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public class SharedPref {

    private static final String TAG = "SharedPref";

    private static SharedPreferences getPref() {
        Context context = App.getContext();
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void saveString(String key, String value) {
        Log.d(TAG, "saveString: " + value);
        getPref().edit()
                .putString(key, value)
                .apply();
    }

    public static String getString(String key) {
        Log.d(TAG, "getString: " + getPref().getString(key, null));
        return getPref().getString(key, null);
    }


    public static void deleteString(String key) {
        getPref().edit()
                .remove(key)
                .apply();
    }

    public static void saveInt(String key, int value) {
        Log.d(TAG, "saveInt: " + value);
        getPref().edit()
                .putInt(key, value)
                .apply();
    }

    public static int getInt(String key) {
        return getPref().getInt(key, 0);
    }

    public static void deleteInt(String key) {
        getPref().edit()
                .remove(key)
                .apply();
    }

    public static void saveBol(String key, boolean value) {
        getPref().edit()
                .putBoolean(key, value)
                .apply();
    }

    public static boolean getBol(String key) {
        return getPref().getBoolean(key, false);
    }

    public static void deleteBol(String key) {
        getPref().edit()
                .remove(key)
                .apply();
    }
}
