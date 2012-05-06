package net.rothlee.athens.android;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class joinActivity extends Activity implements View.OnClickListener {

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
			Intent i = new Intent(this, Athens_banner.class);
			startActivity(i);
     		finish();
		}
	}

	public void onClick(View v) {
		if (v.getId() == R.id.submitMember) {
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
			 
			     BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			     String line;
			     while ((line = rd.readLine()) != null) {
			    	 //유효한 값인지 검사할 경우 여기서 그 결과를 확인하는 처리를 해줌.
				}
			}catch(Exception e) {}
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

	String DownloadHtml(String addr) {
		StringBuilder jsonHtml = new StringBuilder();
		try {
			URL url = new URL(addr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			if (conn != null) {
				conn.setConnectTimeout(10000);
				conn.setUseCaches(false);

				if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
					BufferedReader br = new BufferedReader(
							new InputStreamReader(conn.getInputStream(), "EUC-KR"));
					for (;;) {
						String line = br.readLine();
						if (line == null)
							break;
						jsonHtml.append(line + "\n");
					}
					br.close();
				}
				conn.disconnect();
			}
		} catch (Exception e) {
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
		}
		return jsonHtml.toString();
	}
}