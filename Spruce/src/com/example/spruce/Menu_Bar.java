package com.example.spruce;

import java.io.ByteArrayOutputStream;
import java.io.File;

import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class Menu_Bar extends Activity{
	
	private static final String TAG = "Menu_Bar";
	public ImageAdapter imageadapter = new ImageAdapter(this);
	ParseObject mainItem; //main item that is displayed on spruce activity class

	
	//setting up the action bar 
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.spruce, menu);
			return true;
		}
		
		public boolean onOptionsItemSelected(MenuItem item) {
			try{
				//handle each item on the action bar
				switch (item.getItemId()) {
					case R.id.Spruce:
						Intent spruce = new Intent(Menu_Bar.this, Spruce.class);
						startActivity(spruce);
						break;
					
						//linking each activity page through intents
					case R.id.Profile:
						Intent profile = new Intent(Menu_Bar.this, Profile.class);
						startActivity(profile);
						break;
						
					case R.id.My_Store:
						Intent myStore = new Intent(Menu_Bar.this, My_Store.class);
						startActivity(myStore);
						break;
						
					case R.id.Settings:
						Intent settings = new Intent(Menu_Bar.this, Settings.class);
						startActivity(settings);
						break;
						
					case R.id.Buy_Now:
						Uri uri = Uri.parse(mainItem.getString("url"));
						Intent toItemPage = new Intent(Intent.ACTION_VIEW, uri);
						startActivity(toItemPage);
						break;
						
					case R.id.Register:
						Intent register = new Intent(Menu_Bar.this, Register.class);
						startActivity(register);
						break;
						
					case R.id.Copy_Link:
						ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE); 
						ClipData clip = ClipData.newPlainText("URL", mainItem.getString("url"));
						clipboard.setPrimaryClip(clip);
						Toast.makeText(Menu_Bar.this, "Copied to Clipboard", Toast.LENGTH_SHORT).show();

					case R.id.Save_To_Profile:
						
						
					case R.id.Message:
						//byte[] data = convert();
						//ParseFile file = new ParseFile("ic_launcher.png", data);
						//file.saveInBackground();
						break;
	
						
				}//end switch
			}catch (ActivityNotFoundException e) {
				e.printStackTrace();
				Log.e(TAG, "failed to load activity");
				throw e;
			}
			
			return true;
		}//end onOptionsItemSelected
		
	//returns true if no currentUser
	public boolean checkLogin(){
		
		ParseUser currentUser = ParseUser.getCurrentUser();
		if (currentUser == null) {
			return true;
		}
		else return false;
	}
	
	//switches to the Spruce activity
	public void toSpruce() {
		Intent spruce = new Intent(Menu_Bar.this, Spruce.class);
		startActivity(spruce);
	}
	
	//checks to see if there is a current user, if there is no current user the activity is changed to the login screen
	public void toLogin() {
		if (checkLogin() == true) {
			Intent toLogin = new Intent(Menu_Bar.this, Register.class);
			startActivity(toLogin);
		}
	}
	
	//method to switch to the profile activity
	public void toProfile() {
		try{
			Intent toProfile = new Intent(Menu_Bar.this, Profile.class);
			startActivity(toProfile);
		} catch(ActivityNotFoundException e) {
			e.printStackTrace();
			Log.e(TAG, "failed to load activity");
			throw e;
		}
	}
	
	public void toProduct_Information() {
		try{
			Intent toProductInformation = new Intent(Menu_Bar.this, Product_Information.class);
			startActivity(toProductInformation);
		} catch(ActivityNotFoundException e) {
			e.printStackTrace();
			Log.e(TAG, "failed to load activity");
			throw e;
		}
	}
}//end Menu_Bar
