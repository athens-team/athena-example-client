/*
* Copyright 2012 Athens Team
 *
 * This file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.eincs.athens.android;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import com.eincs.athens.android.R;

/**
 * @author roth2520@gmail.com
 */
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
