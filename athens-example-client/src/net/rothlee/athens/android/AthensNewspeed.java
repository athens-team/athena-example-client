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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import net.rothlee.athens.android.data.Post;

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