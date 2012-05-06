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
package net.rothlee.athens.android;

import java.util.ArrayList;

import net.rothlee.athens.android.net.JSONResponseHandler;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AthensJoin extends Activity implements View.OnClickListener {

	HttpClient httpclient = new DefaultHttpClient();
	private SharedPreferences pref = null;
	EditText p_TV[] = new EditText[2];
	Button p_BTN[] = new Button[2];

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.join);
		pref = getSharedPreferences("prefName",Activity.MODE_PRIVATE);

		p_TV[0] = (EditText) findViewById(R.id.joinMail);
		p_TV[1] = (EditText) findViewById(R.id.joinName);

		p_BTN[0] = (Button) findViewById(R.id.submitMember);
		p_BTN[1] = (Button) findViewById(R.id.requestMail);
		p_BTN[0].setOnClickListener(this);
		p_BTN[1].setOnClickListener(this);
		
		p_TV[0].setText(pref.getString("emailaddr", ""));
		p_TV[1].setText(pref.getString("nickname", ""));
	
		if(pref.getString("emailaddr", "") != ""){
			//이미 등록되었으므로 바로 게시판으로 넘어간다.
			Intent i = new Intent(this, AthensNewspeed.class);
			startActivity(i);
     		finish();
		}
	}

	public void onClick(View v) {
		if (v.getId() == R.id.submitMember) {
			//회원가입
			try {
				String url = "http://10.0.2.2/member_verify.php";
			    HttpPost httppost = new HttpPost(url);
				
				ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("emailaddr", p_TV[0].getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("nickname", p_TV[1].getText().toString()));
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			    String result = "";
			    Log.d("aa", "working proferly");
			    JSONObject res = httpclient.execute(httppost, new JSONResponseHandler());
		    
			}catch(Exception e) {e.printStackTrace();}
			finally{ httpclient.getConnectionManager().shutdown(); }
		}
		else if (v.getId() == R.id.requestMail){
			//메일 재전송 요청
			try {
			     
			}catch(Exception e) {}
		}
	}
}