package net.rothlee.athens.android;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

public class AthensNewspeed extends Activity{
	
	private ListView lv_SA;
	private ArrayList<Post> list_post;
	private MyListAdapter adapter_post ;
	String userID;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.newspeed);
        
		list_post = new ArrayList<Post>();
		adapter_post = new MyListAdapter(this, R.layout.list_write,list_post);
		
        // lv_SA.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        String url = DownloadHtml("http://10.0.2.2/finalNFB/m_post_list.php?userID=" + userID);	
        try{
        	JSONArray ja = new JSONArray(url);
        	for(int i = 0 ; i < ja.length() ; i++){        		
        		JSONObject order = ja.getJSONObject(i);
        		Post wi = Post.create(order.getInt("name"), order.getString("content"));

        		list_post.add(wi);
        		lv_SA = (ListView)findViewById(R.id.comment_list);
        		lv_SA.setAdapter(adapter_post);
        		adapter_post.notifyDataSetChanged();
        	}
        
        	lv_SA.setClickable(true);
        } catch(Exception e) { }
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