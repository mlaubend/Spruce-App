package com.example.spruce;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class Product_Information extends Menu_Bar implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // lock screen rotation
		setContentView(R.layout.product_information);
		
		Button buynow, learnmore;
		//ImageView image = (ImageView) findViewById(R.id.image);
		//image.setOnClickListener(this);
		
		buynow = (Button) findViewById(R.id.buynow);
		buynow.setOnClickListener(this);
		
		learnmore = (Button) findViewById(R.id.learnmore);
		learnmore.setOnClickListener(this);

	}//end onCreate

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch(v.getId()) {
		
		case R.id.buynow:
			Uri uribuynow = Uri.parse("http://www.google.com");
			Intent toItemPagebuynow = new Intent(Intent.ACTION_VIEW, uribuynow);
			startActivity(toItemPagebuynow);
			
		case R.id.learnmore:
			Uri urilearnmore = Uri.parse("http://www.google.com");
			Intent toItemPagelearnmore = new Intent(Intent.ACTION_VIEW, urilearnmore);
			startActivity(toItemPagelearnmore);

		}//end switch
		
	}//end onClick
}//end Product_Information
