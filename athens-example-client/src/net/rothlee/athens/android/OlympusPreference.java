package net.rothlee.athens.android;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class OlympusPreference {

	private static final String KEY_ACCESS_TOKEN = "net.rothlee.athens.android.ACCESS_TOKEN";

	public static OlympusPreference create(Context context) {
		return new OlympusPreference(context);
	}

	private SharedPreferences mSharedPref;

	public OlympusPreference(Context context) {
		mSharedPref = PreferenceManager.getDefaultSharedPreferences(context);
	}

	public String getAccessToken() {
		return mSharedPref.getString(KEY_ACCESS_TOKEN, null);
	}

	public void setAccessToken(String accessToken) {
		Editor edit = mSharedPref.edit();
		edit.putString(KEY_ACCESS_TOKEN, accessToken);
		edit.commit();
	}

}
