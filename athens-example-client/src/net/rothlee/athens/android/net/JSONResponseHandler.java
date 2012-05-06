package net.rothlee.athens.android.net;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class JSONResponseHandler implements ResponseHandler<JSONObject> {

	@Override
	public JSONObject handleResponse(HttpResponse response)
			throws ClientProtocolException, IOException {
		StringBuffer sb = new StringBuffer();
		sb.append(EntityUtils.getContentCharSet(response.getEntity()));

		try {
			return new JSONObject(sb.toString());
		} catch (Exception e) {
			return null;
		}
	}

}
