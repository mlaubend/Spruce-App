package com.example.spruce;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

public class Profile extends Menu_Bar implements OnClickListener{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);
		
		GridView gridview = (GridView) findViewById(R.id.gridview);
		gridview.setAdapter(new ImageAdapter(this));
		
		gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View v, int position, long id) { 
				toProduct_Information();
	            Toast.makeText(Profile.this, "" + position, Toast.LENGTH_SHORT).show(); //delete
			}//end onItemClick
			
		});//end setOnItemClickListener
		
		
	}//end onCreate
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}//end Profile
