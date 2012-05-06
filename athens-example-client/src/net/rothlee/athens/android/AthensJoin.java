package net.rothlee.athens.android;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
		
		if(pref.getString("email", "") != ""){
			//이미 등록되었으므로 바로 게시판으로 넘어간다.
			Intent i = new Intent(this, AthensNewspeed.class);
			startActivity(i);
     		finish();
		}
	}

	public void onClick(View v) {
		if (v.getId() == R.id.submitMember) {
			try {			
				InputStream is = null;
				ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("mail", p_TV[0].getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("nick", p_TV[1].getText().toString()));

			     String result = "";
				 String url = "http://10.0.2.2/member_verify.php";
			     HttpPost httppost = new HttpPost(url);
			     UrlEncodedFormEntity entityRequest = new UrlEncodedFormEntity(nameValuePairs, "UTF-8");
			     httppost.setEntity(entityRequest);
				
			     HttpResponse response = httpclient.execute(httppost);
			     HttpEntity entityResponse = response.getEntity();
			     is = entityResponse.getContent();
			     
			     BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
			     StringBuilder sb = new StringBuilder();
			     String line = null;
			     while((line = reader.readLine()) != null)
			     {
			    	 if(line.contains(p_TV[0].getText().toString()))
			    		 sb.append(line).append("\n");
			     }
			     is.close();
			     result = sb.toString();
			     p_TV[1].setText(result);
			     
			}catch(Exception e) {e.printStackTrace();}
			finally{ httpclient.getConnectionManager().shutdown(); }
		}
		else if (v.getId() == R.id.requestMail){
			try {
			     //요청data 또는 요청XML
			     String data = URLEncoder.encode("email", "UTF-8") + "="+ URLEncoder.encode(p_TV[0].getText().toString(), "UTF-8")+"&";
			     data += URLEncoder.encode("nickname", "UTF-8") + "="+ URLEncoder.encode(p_TV[1].getText().toString(), "UTF-8");
			     URL url = new URL("http://10.0.2.2/member_verify.php");

			     HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			     conn.setDoOutput(true);
			     
			     OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			     wr.write(data);
			     wr.flush();
			}catch(Exception e) {}
		}
	}
}