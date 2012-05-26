/*
 * Copyright 2012 Athens Team
 * 
 * This file to you under the Apache License, version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a
 * copy of the License at:
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.eincs.athens.android;

import java.util.ArrayList;


import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.eincs.athens.android.R;
import com.eincs.athens.android.utils.DefaultAsyncTask;
import com.eincs.athens.android.utils.JSONResponseHandler;
import com.eincs.athens.android.utils.ProgressDialogs;
import com.eincs.athens.android.utils.StringUtils;

/**
 * @author roth2520@gmail.com
 */
public class OlympusJoinActivity extends Activity implements
		View.OnClickListener {

	private OlympusPreference mPref;
	private EditText mEditJoinEmail;
	private EditText mEditJoinNickname;
	private Button mBtnSubmit;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_join);

		mPref = OlympusPreference.create(this);

		mEditJoinEmail = (EditText) findViewById(R.id.join_mail);
		mEditJoinNickname = (EditText) findViewById(R.id.join_nickname);

		mBtnSubmit = (Button) findViewById(R.id.btn_submit_json);
		mBtnSubmit.setOnClickListener(this);

		checkAndMove();
	}

	private void checkAndMove() {
		/* if already signin */
		if (!StringUtils.isEmptyOrNull(mPref.getAccessToken())) {
			Intent intent = new Intent(this, OlympusFeedActivity.class);
			startActivity(intent);
			finish();
		}
	}

	public void onClick(View v) {

		final String email = mEditJoinEmail.getText().toString();
		final String nickname = mEditJoinNickname.getText().toString();
		final String tag = android.os.Build.MODEL;

		final HttpClient httpClient = new DefaultHttpClient();
		new DefaultAsyncTask<JSONObject>(ProgressDialogs.createDialog(this)) {

			@Override
			protected JSONObject doInBackground(Object... params) {
				Uri uri = Uri.parse(OlympusConst.SERVER_HOST
						+ OlympusConst.PATH_GET_ACCEESS_TOKEN);
				HttpPost httpPost = new HttpPost(uri.toString());

				ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("email", email));
				nameValuePairs
						.add(new BasicNameValuePair("nickname", nickname));
				nameValuePairs.add(new BasicNameValuePair("tag", tag));
				try {
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs,
							"UTF-8"));
					JSONObject result = httpClient.execute(httpPost,
							new JSONResponseHandler());
					return result;

				} catch (Exception e) {
					Log.e("Olympus", e.getMessage(), e);
					return null;
				}
			}

			protected void onPostExecute(JSONObject result) {
				super.onPostExecute(result);

				if (result == null || result.has("error")) {
					Toast.makeText(OlympusJoinActivity.this, "error occered",
							Toast.LENGTH_SHORT).show();
				} else {
					try {
						String accessToken = result.getString("result");
						mPref.setAccessToken(accessToken);
						checkAndMove();
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			};
		}.execute();
	}
}