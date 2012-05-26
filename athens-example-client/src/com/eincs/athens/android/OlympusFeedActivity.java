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
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;


import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.eincs.athens.android.R;
import com.eincs.athens.android.data.Post;
import com.eincs.athens.android.utils.DefaultAsyncTask;
import com.eincs.athens.android.utils.JSONResponseHandler;
import com.eincs.athens.android.utils.ProgressDialogs;
import com.eincs.athens.android.utils.StringUtils;

public class OlympusFeedActivity extends ListActivity implements
		View.OnClickListener {

	private Context mContext;
	private TreeMap<Integer, Post> mData;
	private ArrayList<Post> mDataArray;
	private TextView mBtnWrite;
	private PostAdapter mAdapter;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feed);

		
		mContext = this;
		mBtnWrite = (TextView) findViewById(R.id.btn_write);
		mData = new TreeMap<Integer, Post>(new Comparator<Integer>() {

			@Override
			public int compare(Integer object1, Integer object2) {
				return object2 - object1;
			}
		});
		mDataArray = new ArrayList<Post>();
		mAdapter = new PostAdapter(this, R.layout.item_post, mDataArray);
		setListAdapter(mAdapter);

		mBtnWrite.setOnClickListener(this);
		getTimeline(null, null);
	}

	@Override
	protected void onResume() {
		super.onResume();
		getTimeline(null, null);
	}
	
	private void getTimeline(final String after, final String before) {

		final HttpClient httpClient = new DefaultHttpClient();
		new DefaultAsyncTask<JSONObject>(ProgressDialogs.createDialog(this)) {

			@Override
			protected JSONObject doInBackground(Object... params) {
				Uri uri = Uri.parse(OlympusConst.SERVER_HOST
						+ OlympusConst.PATH_TIMELINE);
				Uri.Builder builder = uri.buildUpon();

				if (!StringUtils.isEmptyOrNull(after)) {
					builder.appendQueryParameter("after", after);
				}

				if (!StringUtils.isEmptyOrNull(after)) {
					builder.appendQueryParameter("before", before);
				}

				HttpGet httpGet = new HttpGet(uri.toString());

				try {
					JSONObject result = httpClient.execute(httpGet,
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
					try {
						JSONArray jsonList = result.getJSONArray("result");
						List<Post> postList = Post.createList(jsonList);
						
						mData.clear();
						for(Post post : postList) {
							mData.put(post.getId(), post);
						}
						
						mDataArray.clear();
						for(Integer integer :mData.keySet()) {
							mDataArray.add(mData.get(integer));
						}
						mAdapter.notifyDataSetChanged();
						
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			};
		}.execute();
	}

	public void onClick(View v) {
		if (v.getId() == R.id.btn_write) {
			Intent intent = new Intent(this, OlympusWriteActivity.class);
			startActivity(intent);
		}
	}
}