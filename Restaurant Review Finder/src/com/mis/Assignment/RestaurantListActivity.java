package com.mis.Assignment;



import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class RestaurantListActivity extends ListActivity {
	String[] ref;
	public boolean onCreateOptionsMenu(Menu menu) {
	        MenuInflater inflater = getMenuInflater();
	        inflater.inflate(R.menu.menu2, menu);
	        return true;
	}
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
		if (extras == null) {
			return;
		}
		String[] restaurants = extras.getStringArray("restaurants");
		ref = extras.getStringArray("ref");
		ArrayList<String> resList = new ArrayList<String>();

        for (int i = 0; i < restaurants.length; i++) {
            resList.add(restaurants[i]);
        }

		ResAdapter adapter=new ResAdapter(this,R.layout.list,resList);
		setListAdapter(adapter);
		ListView lv = getListView();
		lv.setTextFilterEnabled(true);

		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				Intent intent = new Intent(RestaurantListActivity.this, RestaurantDetails.class);
				intent.putExtra("ref",ref[position]);
				startActivity(intent);
		    }
		});		
	}
	public boolean onOptionsItemSelected(MenuItem item) {
        
		Intent intent = new Intent(RestaurantListActivity.this, RestaurantReviewFinderActivity.class);
		startActivity(intent);
        return true;
    }
	public class ResAdapter extends ArrayAdapter<String> {
        private ArrayList<String> items;
        private ResViewHolder resHolder;

        private class ResViewHolder {
            TextView name;
            TextView address; 
        }

        public ResAdapter(Context context, int tvResId, ArrayList<String> items) {
            super(context, tvResId, items);
            this.items = items;
        }

        @Override
        public View getView(int pos, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.list, null);
                resHolder = new ResViewHolder();
                resHolder.name = (TextView)v.findViewById(R.id.name);
                resHolder.address = (TextView)v.findViewById(R.id.address);
                v.setTag(resHolder);
            } else resHolder = (ResViewHolder)v.getTag(); 

            String res = items.get(pos);

            if (res != null) {
            	String[] restaurant=res.split("\n");
                resHolder.name.setText(restaurant[0]);
                resHolder.address.setText(restaurant[1]);
            }

            return v;
        }
    }

}
