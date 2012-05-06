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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AthensPageView extends Activity implements View.OnClickListener{

	int number;
	String mID;
	String userID;
	
	TextView txtt1,txtt2,txtt3;
	ImageView pvimg;
	ListView lv_PV;
	
	Button replyButton;
	Button deleteContext;
	EditText replyContext;
	
	private ArrayList<AthensWritingItem> list_PV;
	private MyListAdapter adapter_PV;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.postview);
		
		userID = getIntent().getStringExtra("userID");
		number = getIntent().getIntExtra("postNumber", 0);
	    
		pvimg = (ImageView)findViewById(R.id.pv_image);
		txtt1 = (TextView)findViewById(R.id.pvtext1);
	    txtt2 = (TextView)findViewById(R.id.pvtext2);
	    txtt3 = (TextView)findViewById(R.id.pvtext3);
	    deleteContext = (Button)findViewById(R.id.delcomment);
	    deleteContext.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 삭제버튼
				String tempUrl = DownloadHtml("http://10.0.2.2/finalNFB/m_post_delete.php?userID=" + userID + "&number=" + number);
				
				if(tempUrl.toString().contains("0")){
					Toast.makeText(AthensPageView.this, "본인의 게시물이 아닙니다.", Toast.LENGTH_SHORT).show();			
				} else if(tempUrl.toString().contains("1")){
					Toast.makeText(AthensPageView.this, "포스트가 삭제되었습니다.", Toast.LENGTH_SHORT).show();			
				} else {
					Toast.makeText(AthensPageView.this, "삭제과정에서 에러가 발생했습니다.", Toast.LENGTH_SHORT).show();			
				}
				
				Intent i = new Intent(AthensPageView.this, AthensNewspeed.class);

	        	i.putExtra("userID", userID);
	    		startActivity(i);
				finish();
			}
	    });
	    
//	    txtt1.setOnClickListener(new Button.OnClickListener(){
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent i = new Intent(pageViewActivity.this, profileActivity.class);
//				
//	    		i.putExtra("mID", mID);
//	        	i.putExtra("userID", userID);
//	    		startActivity(i);
//	    		finish();
//			}
//	    });
	    
	    
		list_PV = new ArrayList<AthensWritingItem>();
		adapter_PV = new MyListAdapter(this, R.layout.list_write, list_PV);
		
		/* Jason을 이용한 본문 및 덧글 출력부분 */
		String url = DownloadHtml("http://10.0.2.2/finalNFB/m_post_view.php?number=" + number);	
		try{
        	JSONArray ja = new JSONArray(url);
        	for(int i = 0 ; i < ja.length() ; i++){
        		int printable; 
        		
        		JSONObject order = ja.getJSONObject(i);
        		printable = order.length() - 6;
         		
        		/* 본문 출력 */

    			byte[] decodedString = Base64.decode(order.getString("image"), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                pvimg.setImageBitmap(decodedByte);
                
    			txtt1.setBackgroundColor(Color.WHITE);
    			txtt2.setBackgroundColor(Color.WHITE);
    			txtt3.setBackgroundColor(Color.WHITE);
        		txtt1.setText(order.getString("name"));
        		txtt2.setText(order.getString("content"));
        		txtt3.setText(order.getString("date"));
        		
        		mID = order.getString("myID");

        		/* 덧글 출력 */
        		int j = 0;
        		while(printable > 0){    			
        			AthensWritingItem wi;
        			wi = new AthensWritingItem( order.getString("seminame"+j), order.getString("semicontent"+j), "", order.getString("semiID"+j), order.getInt("seminumber"+j));
        			
        			list_PV.add(wi);
        			lv_PV = (ListView)findViewById(R.id.post_list);
        			lv_PV.setAdapter(adapter_PV);
        			
        			printable = printable - 5;
        			j++;
        		}
        		adapter_PV.notifyDataSetChanged();
        	}
        	
        } catch(Exception e) { }
		
		/* 덧글을 입력하는 경우 */
		replyContext = (EditText)findViewById(R.id.replyInsertContext);
		replyButton = (Button)findViewById(R.id.replyInsertBtn);
		replyButton.setOnClickListener(this);
	}

	/** 덧글 출력을 위한 List Adapter 선언 부분 */
	class MyListAdapter extends BaseAdapter{

		Context maincon;
		LayoutInflater Inflater;
		ArrayList<AthensWritingItem> itemArray;
		int layout;
		
		public MyListAdapter(Context context, int alayout, ArrayList<AthensWritingItem> list_PV){
			maincon = context;
			Inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			itemArray = list_PV;
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

		/* Set List item Format */
		
		public View getView(final int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if(convertView == null){
				convertView = Inflater.inflate(layout, parent,false);
			}
			RelativeLayout rel_back = (RelativeLayout)convertView.findViewById(R.id.rel_back);
			rel_back.setBackgroundColor(Color.rgb(245, 211, 214));
			
			TextView txt1 =(TextView)convertView.findViewById(R.id.text1);
			txt1.setText(itemArray.get(position).name);
			TextView txt2 =(TextView)convertView.findViewById(R.id.text2);
			txt2.setText(itemArray.get(position).content);
			TextView txt3 =(TextView)convertView.findViewById(R.id.text3);
			txt3.setText(itemArray.get(position).date);
			Button btn = (Button)convertView.findViewById(R.id.delete);
			btn.setVisibility(View.VISIBLE);
			
			/* Click Event Handling */
			btn.setOnClickListener(new Button.OnClickListener(){
				/* 삭제버튼을 눌렀을 경우 */
				public void onClick(View v){
					String tempUrl = DownloadHtml("http://10.0.2.2/finalNFB/m_reply_delete.php?userID=" + userID + "&number=" + list_PV.get(position).number);
					
					if(tempUrl.toString().contains("0")){
						Toast.makeText(maincon, "본인의 게시물이 아닙니다.", Toast.LENGTH_SHORT).show();			
					} else if(tempUrl.toString().contains("1")){
						Toast.makeText(maincon, "덧글이 삭제되었습니다.", Toast.LENGTH_SHORT).show();			
					} else {
						Toast.makeText(maincon, "삭제과정에서 에러가 발생했습니다.", Toast.LENGTH_SHORT).show();			
					}
				}
			});
//			txt1.setOnClickListener(new Button.OnClickListener(){
//				/* 덧글 작성자를 눌렀을 경우 */
//				public void onClick(View v){
//					Intent i = new Intent(pageViewActivity.this, profileActivity.class);
//					
//		    		i.putExtra("mID", list_PV.get(position).ID);
//		        	i.putExtra("userID", userID);
//		    		startActivity(i);
//		    		finish();
//				}
//			});
//			
			return convertView;
		}
	}	

	public void onClick(View v){
		if(v.getId() == R.id.replyInsertBtn){			
			String replyWriteUrl = DownloadHtml("http://10.0.2.2/finalNFB/m_reply_insert.php?userID=" + userID + "&number=" + number + "&content=" + java.net.URLEncoder.encode(replyContext.getText().toString()));
			
			if(replyWriteUrl.toString().contains("1")){
				Toast.makeText(AthensPageView.this, "덧글이 등록되었습니다.", Toast.LENGTH_SHORT).show();
				finish();
			} else {
				Toast.makeText(AthensPageView.this, "덧글 등록시에 에러가 발생하였습니다.", Toast.LENGTH_SHORT).show();			
			}
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
