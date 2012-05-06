package net.rothlee.athens.android;

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

public class MyListAdapter extends BaseAdapter{

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