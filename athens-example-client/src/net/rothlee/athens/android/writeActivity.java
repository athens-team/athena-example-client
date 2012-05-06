package net.rothlee.athens.android;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class writeActivity extends Activity implements View.OnClickListener{

	String userID, myImage;
	ImageView myID;
	EditText writeContent;
	Button writeBtn[] = new Button[2];
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.write); 
       
        writeBtn[0] = (Button)findViewById(R.id.writeOK);
        writeBtn[1] = (Button)findViewById(R.id.writeCancel);
        writeContent = (EditText)findViewById(R.id.myPost);
        
        writeBtn[0].setOnClickListener(this);
        writeBtn[1].setOnClickListener(this);
	}
	
	public void onClick(View v) {
		if(v.getId() == R.id.writeOK){
			Log.d("what", writeContent.getText().toString());
			String replyWriteUrl = DownloadHtml("http://10.0.2.2/m_post_insert.php?userID=" + userID + "&content=" + java.net.URLEncoder.encode(writeContent.getText().toString()));
			
			if(replyWriteUrl.toString().contains("1")){
				Toast.makeText(writeActivity.this, "포스트가 등록되었습니다.", Toast.LENGTH_SHORT).show();			
			} else {
				Toast.makeText(writeActivity.this, "포스트 등록시에 에러가 발생하였습니다.", Toast.LENGTH_SHORT).show();			
			}
			
			finish();
		} else if(v.getId() == R.id.writeCancel) {
			finish();
		}
	}
	
	String DownloadHtml(String addr){
    	StringBuilder jsonHtml = new StringBuilder();
    	try{
    		URL url = new URL(addr);
    		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
    		if(conn != null){
    			conn.setConnectTimeout(10000);
    			conn.setUseCaches(false);
    			
    			if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
    				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "EUC-KR"));
    				for(;;){
    					String line = br.readLine();
    					if(line == null) break;
    					jsonHtml.append(line + "\n");
    				}
    			br.close();
    			}
    		conn.disconnect();
    		}
    	} catch(Exception e) {
    		Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
    	}
    	return jsonHtml.toString();
    }
}


