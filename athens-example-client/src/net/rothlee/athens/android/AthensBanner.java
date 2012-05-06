package net.rothlee.athens.android;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AthensBanner extends Activity {
	
	ListView lv_SA;
	private ArrayList<AthensWritingItem> list_NS;
	private MyListAdapter adapter_NS ;
	String userID, findMember;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.mypost);

		findMember = getIntent().getStringExtra("userID");
        userID = getIntent().getStringExtra("userID");
        
		list_NS = new ArrayList<AthensWritingItem>();
		//adapter_NS = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, list_NS);
		adapter_NS = new MyListAdapter(this, R.layout.list_write,list_NS);
		
        // lv_SA.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        String url = DownloadHtml("http://10.0.2.2/finalNFB/m_post_find.php?userID=" + userID + "&findMember=" + findMember);
        try{
        	JSONArray ja = new JSONArray(url);
        	for(int i = 0 ; i < ja.length() ; i++){        		
        		JSONObject order = ja.getJSONObject(i);

        		AthensWritingItem wi;								
        		wi = new AthensWritingItem(order.getString("image"), order.getString("name"), order.getString("content"),order.getString("date"), order.getInt("number"));

        		list_NS.add(wi);
        		lv_SA = (ListView)findViewById(R.id.mypost_list);
        		lv_SA.setAdapter(adapter_NS);
        		adapter_NS.notifyDataSetChanged();
        	}
        
        	lv_SA.setClickable(true);
            lv_SA.setOnItemClickListener(new AdapterView.OnItemClickListener() {  
          	  public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
          		   	
          		int orderNo;
          	    AthensWritingItem w = adapter_NS.getMyItem(position);
          		
          		orderNo = w.number;
          	    
          	    Intent i = new Intent(AthensBanner.this, AthensPageView.class);
          	    i.putExtra("userID", userID);
              	i.putExtra("postNumber",orderNo);
          		startActivity(i);
          		finish();
          	  } 
          });

        	
        } catch(Exception e) { }
	}

	class MyListAdapter extends BaseAdapter{

		Context maincon;
		LayoutInflater Inflater;
		ArrayList<AthensWritingItem> itemArray;
		int layout;
		
		public MyListAdapter(Context context, int alayout, ArrayList<AthensWritingItem> iitemArray){
			maincon = context;
			Inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			itemArray = iitemArray;
			layout = alayout;
		}
		
		public AthensWritingItem getMyItem(int position){
			return itemArray.get(position);
		}
		

		public int getCount() {
			// TODO Auto-generated method stub
			return itemArray.size();
		}


		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return itemArray.get(position).name;
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
			
			ImageView img = (ImageView)convertView.findViewById(R.id.write_image);
			byte[] decodedString = Base64.decode(itemArray.get(position).imageID, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            img.setImageBitmap(decodedByte);
            
			TextView txt1 =(TextView)convertView.findViewById(R.id.text1);
			txt1.setText(itemArray.get(position).name);
			txt1.setBackgroundColor(Color.WHITE);
			TextView txt2 =(TextView)convertView.findViewById(R.id.text2);
			txt2.setText(itemArray.get(position).content);
			txt2.setBackgroundColor(Color.WHITE);
			TextView txt3 =(TextView)convertView.findViewById(R.id.text3);
			txt3.setText(itemArray.get(position).date);
			txt3.setBackgroundColor(Color.WHITE);
			Button btn = (Button)convertView.findViewById(R.id.delete);
			btn.setVisibility(View.GONE);
			
			return convertView;
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
    	//	Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
    	}
    	return jsonHtml.toString();
    }
}
