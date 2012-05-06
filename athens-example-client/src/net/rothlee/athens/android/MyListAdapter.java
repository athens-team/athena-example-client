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
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyListAdapter extends BaseAdapter{

	Context maincon;
	LayoutInflater Inflater;
	ArrayList<Post> itemArray;
	int layout;
	
	public MyListAdapter(Context context, int alayout, ArrayList<Post> iitemArray){
		maincon = context;
		Inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		itemArray = iitemArray;
		layout = alayout;
	}
	
	public Post getItem(int position){
		return itemArray.get(position);
	}	
	
	public int getCount() {
		// TODO Auto-generated method stub
		return itemArray.size();
	}

	
	public Object getItemUser(int position) {
		// TODO Auto-generated method stub
		return itemArray.get(position).getUser();
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView == null){
			convertView = Inflater.inflate(layout, parent,false);
		}
		
		RelativeLayout rel_back = (RelativeLayout)convertView.findViewById(R.id.rel_back);
		rel_back.setBackgroundColor(Color.WHITE);

		TextView txt1 =(TextView)convertView.findViewById(R.id.text1);
		txt1.setText(itemArray.get(position).getUser().getNickname());
		txt1.setBackgroundColor(Color.WHITE);
		TextView txt2 =(TextView)convertView.findViewById(R.id.text2);
		txt2.setText(itemArray.get(position).getContent()); 
		txt2.setBackgroundColor(Color.WHITE);
		
		return convertView;
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
    	} catch(Exception e) {}
    	return jsonHtml.toString();
    }
}