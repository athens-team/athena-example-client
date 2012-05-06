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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import net.rothlee.athens.android.data.Post;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PostAdapter extends BaseAdapter{

	private Context mContext;
	private LayoutInflater mInflater;
	private ArrayList<Post> mItemArray;
	private int mItemResId;
	
	public PostAdapter(Context context, int itemResId, ArrayList<Post> itemArray){
		mContext = context;
		mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mItemArray = itemArray;
		mItemResId = itemResId;
	}
	
	public Post getItem(int position){
		return mItemArray.get(position);
	}	
	
	public int getCount() {
		return mItemArray.size();
	}
	
	public Object getItemUser(int position) {
		return mItemArray.get(position).getUser();
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(convertView == null){
			convertView = mInflater.inflate(mItemResId, parent,false);
			convertView.setTag(new Holder(convertView));
		}
		
		final Post post = getItem(position);
		final Holder holder = (Holder) convertView.getTag();

		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(post.getCreatedTime());
		SimpleDateFormat format = new SimpleDateFormat();
		
		holder.mProfile.setVisibility(View.GONE);
		holder.mNickname.setText(post.getUser().getNickname());
		holder.mContent.setText(post.getContent());
		holder.mDate.setText(format.format(new Date(post.getCreatedTime())));

		return convertView;
	}
	
	public static class Holder {
		public final ImageView mProfile;
		public final TextView mNickname;
		public final TextView mContent;
		public final TextView mDate;
		
		public Holder(View view) {
			mProfile = (ImageView) view.findViewById(R.id.profile);
			mNickname = (TextView) view.findViewById(R.id.nickname);
			mContent = (TextView) view.findViewById(R.id.content);
			mDate = (TextView) view.findViewById(R.id.date);
		}
		
	}
	
}