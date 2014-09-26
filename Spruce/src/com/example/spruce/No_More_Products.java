package com.example.spruce;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class No_More_Products extends Menu_Bar implements OnClickListener{

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // lock screen rotation
		setContentView(R.layout.no_more_products);
		
		ImageView image = (ImageView) findViewById(R.id.no_more_products);
		image.setOnClickListener(this);
		image.setScaleType(ScaleType.FIT_XY);
	}
	
	@Override
	public void onClick(View v) {
		toProfile();
	}
	
}
