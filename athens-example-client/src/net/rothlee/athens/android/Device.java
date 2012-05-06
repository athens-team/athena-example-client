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
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author roth2520@gmail.com
 */
public class Device {

	private Integer id;
	
	private String uuid;
	
	private String model;
	
	private Long createdTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Long getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Long createdTime) {
		this.createdTime = createdTime;
	}
	
	public JSONObject toJSON() throws JSONException {
		JSONObject result = new JSONObject();
		result.put("id", getId());
		result.put("uuid", getUuid());
		result.put("model", getModel());
		result.put("created_time", getCreatedTime());
		return result;
	}
}
