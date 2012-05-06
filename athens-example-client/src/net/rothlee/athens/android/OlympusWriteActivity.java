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
package net.rothlee.athens.android;

import java.util.ArrayList;

import net.rothlee.athens.android.utils.DefaultAsyncTask;
import net.rothlee.athens.android.utils.JSONResponseHandler;
import net.rothlee.athens.android.utils.ProgressDialogs;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class OlympusWriteActivity extends Activity implements
		View.OnClickListener {

	private Context mContext;
	private OlympusPreference mPref;
	private EditText mContent;
	private Button mBtnWriteSubmit;
	private Button mBtnWriteCancel;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_write);

		mContext = this;
		mPref = OlympusPreference.create(this);
		mContent = (EditText) findViewById(R.id.write_content);
		mBtnWriteSubmit = (Button) findViewById(R.id.btn_write_submit);
		mBtnWriteCancel = (Button) findViewById(R.id.btn_write_cancel);

		mBtnWriteSubmit.setOnClickListener(this);
		mBtnWriteCancel.setOnClickListener(this);
	}

	public void onClick(View v) {
		if (v.getId() == R.id.btn_write_submit) {

			final String content = mContent.getText().toString();
			final HttpClient httpClient = new DefaultHttpClient();
			new DefaultAsyncTask<JSONObject>(ProgressDialogs.createDialog(this)) {

				@Override
				protected JSONObject doInBackground(Object... params) {
					Uri uri = Uri.parse(OlympusConst.SERVER_HOST
							+ OlympusConst.PATH_WRITE);
					HttpPost httpPost = new HttpPost(uri.toString());
					httpPost.addHeader(OlympusConst.HEADER_ACCESS_TOKEN,
							mPref.getAccessToken());
					ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
					nameValuePairs.add(new BasicNameValuePair("content",
							content));

					try {
						httpPost.setEntity(new UrlEncodedFormEntity(
								nameValuePairs, "UTF-8"));
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

					if (result == null) {
						Toast.makeText(mContext, "error", Toast.LENGTH_SHORT)
								.show();
					} else if (result.has("error")) {
						try {
							Toast.makeText(mContext, result.getString("error"),
									Toast.LENGTH_SHORT).show();
						} catch (JSONException e) {
							e.printStackTrace();
						}
					} else {
						finish();
					}
				};
			}.execute();

		} else if (v.getId() == R.id.btn_write_cancel) {
			finish();
		}
	}

}
