package com.mis.Assignment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class RestaurantDetails extends Activity {
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen3);
        Bundle extras = getIntent().getExtras();
		if (extras == null) {
			return;
		}
		String ref = extras.getString("ref");
		String urltext = "https://maps.googleapis.com/maps/api/place/details/json?reference="+ref+"&sensor=true&key=AIzaSyAerEOIs_mbCMsz_t3HotGEfTD2ncr6IG8";
		URL url;
		try {
			url = new URL(urltext);
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			String inputLine,json="";
			while ((inputLine = in.readLine()) != null) {
				json=json+inputLine;
			}		
			in.close();
			JSONObject json1=new JSONObject(json);
			JSONObject results=json1.getJSONObject("result");
			String rating=results.getString("rating");
			String phno=results.getString("formatted_phone_number");
			String address=results.getString("formatted_address");
			String name=results.getString("name");
			TextView name1=(TextView)findViewById(R.id.textView1);
			name1.setText(name+"\n"+results.getString("vicinity"));
			TextView rating1=(TextView)findViewById(R.id.textView3);
			rating1.setText(rating);
			TextView phno1=(TextView)findViewById(R.id.textView7);
			phno1.setText(phno);
			TextView address1=(TextView)findViewById(R.id.textView5);
			address1.setText(address);
		} catch (MalformedURLException e) {
			Toast.makeText(this, (e.toString()).split(":")[1], Toast.LENGTH_LONG).show();
			e.printStackTrace();
		} catch (IOException e) {
			Toast.makeText(this, (e.toString()).split(":")[1], Toast.LENGTH_LONG).show();
			e.printStackTrace();
		} catch (JSONException e) {
			Toast.makeText(this, (e.toString()).split(":")[1], Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
		
	}
}
