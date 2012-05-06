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
package net.rothlee.athens.android.data;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author roth2520@gmail.com
 */
public class Post{

	public static Post create(Integer postId) {
		Post result = new Post();
		result.setId(postId);
		return result;
	}
	
	public static Post create(Integer userId, String content) {
		Post result = new Post();
		result.setUserId(userId);
		result.setContent(content);
		return result;
	}
	
	public static Post createFromJson(JSONObject jo) throws JSONException
	{
		Post result = new Post();
		result.setId(jo.getInt("id"));
		result.setId(jo.getInt("userId"));
		result.setContent(jo.getString("content"));
		result.setCreatedTime(jo.getLong("created_time"));
		return result;
	}
	
	private Integer id;
	
	private Integer userId;
	
	private User user;
	
	private String content;
	
	private Long createdTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public User getUser() {
		if(user==null) {
			user = User.create("null", "null", 0);
		}
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Long createdTime) {
		this.createdTime = createdTime;
	}

}
