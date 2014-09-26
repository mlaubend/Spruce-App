package com.example.spruce;

import com.example.spruce.R.id;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class My_Store extends Menu_Bar implements OnClickListener{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_store);
		
		//setting up buttons and onClickListeners
		Button Everything, Casual, Dressy, Gadgets, Accessories, Active, Home;
		
		View[] Buttons = new View[] {
				Everything = (Button) findViewById(R.id.Everything),
				Casual = (Button) findViewById(R.id.Casual),
				Dressy = (Button) findViewById(R.id.Dressy),
				Gadgets = (Button) findViewById(R.id.Gadgets),
				Accessories = (Button) findViewById(R.id.Accessories), 
				Active = (Button) findViewById(R.id.Active),
				Home = (Button) findViewById(R.id.Home)
		};
		
		for (int i = 0; i < Buttons.length-1; i++){
			Buttons[i].setOnClickListener(this);
		}
	}//end onCreate

	@Override
	public void onClick(View v) {
		/** TODO Auto-generated method stub
		 * connect each button with the appropriate database from parse
		 ***/
		switch(v.getId()) {
		
		case R.id.Everything:
			Intent toProductScreen = new Intent(My_Store.this, Product_Screen.class);
			startActivity(toProductScreen);
			break;
			
		case R.id.Casual:
			break;
			
		case R.id.Dressy:
			break;
			
		case R.id.Gadgets:
			break;
			
		case R.id.Accessories:
			break;
			
		case R.id.Active:
			break;
			
		case R.id.Home:
			break;
		}
	}
}//end My_Store
