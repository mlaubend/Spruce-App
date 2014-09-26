package com.example.spruce;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class Product_Screen extends Menu_Bar implements OnClickListener{
	
	private static final String TAG = "Product_Screen";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // lock screen rotation
		setContentView(R.layout.product_screen);
	
		ImageView imageview = new ImageView(this);
		imageview = (ImageView) findViewById(R.id.image);
		imageview.setOnClickListener(this);
		
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		try{
			Intent toProductInformation = new Intent(Product_Screen.this, Product_Information.class);
			startActivity(toProductInformation);
		} catch (ActivityNotFoundException e) {
			e.printStackTrace();
			Log.e(TAG, "failed to load activity");
			throw e;
		}
	}


}//end Product_Screen
