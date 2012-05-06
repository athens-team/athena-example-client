package net.rothlee.athens.android;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
		
		Button btn = (Button)convertView.findViewById(R.id.delete);
		btn.setVisibility(View.GONE);
		
		btn.setOnClickListener(new Button.OnClickListener(){
			/* 삭제버튼을 눌렀을 경우 */
			public void onClick(View v){
				String tempUrl = DownloadHtml("http://10.0.2.2/m_reply_delete.php?"); //?????
				
				if(tempUrl.toString().contains("0")){
					Toast.makeText(maincon, "본인의 게시물이 아닙니다.", Toast.LENGTH_SHORT).show();			
				} else if(tempUrl.toString().contains("1")){
					Toast.makeText(maincon, "삭제되었습니다.", Toast.LENGTH_SHORT).show();			
				} else {
					Toast.makeText(maincon, "삭제과정에서 에러가 발생했습니다.", Toast.LENGTH_SHORT).show();			
				}
			}
		});
		
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