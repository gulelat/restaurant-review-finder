package com.mis.Assignment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class RestaurantReviewFinderActivity extends Activity implements OnClickListener{
    /** Called when the activity is first created. */
	public String[] ref; 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Spinner spinner = (Spinner) findViewById(R.id.cuisine_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.cuisine_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        
        Button getReviews_button=(Button)findViewById(R.id.getReviews_button);
        getReviews_button.setOnClickListener(this);
    }
    
    public void onClick(View view) {
    	if(view.getId() == R.id.getReviews_button){
    		getReview();
    	}    	
    }
    public void getReview() {
    	EditText location_text=(EditText)findViewById(R.id.location_text);
		String location=location_text.getText().toString();
		location=location.replaceAll("\\s","+");
		String urltext = "https://maps.googleapis.com/maps/api/geocode/json?address="+location+"&sensor=true";
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
    		JSONArray results=json1.getJSONArray("results");
    		String lat=((JSONObject) results.get(0)).getJSONObject("geometry").getJSONObject("location").getString("lat");
    		String lng=((JSONObject) results.get(0)).getJSONObject("geometry").getJSONObject("location").getString("lng");
    		
    		String[] restaurants=getRestaurantList(lat,lng);
    		Intent intent = new Intent(RestaurantReviewFinderActivity.this, RestaurantListActivity.class);
    		intent.putExtra("restaurants",restaurants);
    		intent.putExtra("ref",ref);
    		startActivity(intent);
		} catch (MalformedURLException e) {
			
			Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
			e.printStackTrace();
		} catch (IOException e) {
			
			Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
			e.printStackTrace();
		} catch (JSONException e) {
			
			Toast.makeText(this, "Please Enter correct location", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
    }
    public String[] getRestaurantList(String lat,String lng) {
    	String placesurl="https://maps.googleapis.com/maps/api/place/search/json?location="+lat+","+lng+"&radius=2000&types=restaurant&sensor=false&key=AIzaSyAerEOIs_mbCMsz_t3HotGEfTD2ncr6IG8";
    	String[] restaurants = null;
    	try {
			URL url = new URL(placesurl);
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
    		String inputLine,json="";
    		while ((inputLine = in.readLine()) != null) {
    			json=json+inputLine;
    		}		
    		in.close();
    		JSONObject json1=new JSONObject(json);
    		JSONArray results=json1.getJSONArray("results");
    		restaurants=new String[results.length()];
    		ref=new String[results.length()];
    		for(int i=0;i<results.length();i++) {
    			String name=((JSONObject) results.get(i)).getString("name");
	    		String vicinity=((JSONObject) results.get(i)).getString("vicinity");
	    		String reference=((JSONObject) results.get(i)).getString("reference");
	    		restaurants[i]=name+"\n"+vicinity;
	    		ref[i]=reference;
    		}
    		
		} catch (MalformedURLException e) {
			
			Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
			e.printStackTrace();
		} catch (IOException e) {
			
			Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
			e.printStackTrace();
		} catch (JSONException e) {
			
			Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
		return restaurants;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
    	getReview();
        return true;
    }
}
        