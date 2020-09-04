package com.kevine.askbuddy.http;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//import com.google.android.gms.common.GooglePlayServicesUtil;

public class UserFunctions {
	private final JSONParser jsonParser;
	private static String URL = "http://www.nearfieldltd.com/rongai/index.php";

	public UserFunctions() {
		jsonParser = new JSONParser();
	}

	public JSONObject GenericFunction(String[] Tags, String[] Values) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		int i = 0;
		for (int j = 0; j < Tags.length; j++) {
			params.add(new BasicNameValuePair(Tags[i], Values[j]));
			i++;
		}
		JSONObject json = jsonParser.getJSONFromUrl(URL, params);
		return json;
	}
}