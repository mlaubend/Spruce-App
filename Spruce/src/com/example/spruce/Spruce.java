/***
 * Created by Mark Laubender July, 2014.
 * Copyright (c) mlaubend 2014. All rights reserved.
 * Redistribution at sole consent of Creator.
 ***/

package com.example.spruce;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.IntentSender;
import android.content.IntentSender.SendIntentException;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class Spruce extends Menu_Bar implements OnClickListener {
	
	private static final String TAG = "com.example.spruce";
	
	ImageView mainImage;
	TextView title, company, price, saves;
	
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // lock screen rotation
		setContentView(R.layout.activity_spruce);
		
		//initializing backend database (parse.com)
		Parse.initialize(this, "4cFpeXihN2NA4CnkawX04iixC4VkQjiv6sGRxoSV", "HXhQgGZ6liU5jypojuTPUZlBs01SaUo3qGg1o2J9");
		
		//setting up Buttons and onClickListeners
		ImageButton upvote, save, downvote;
		
		
		//array of views
		View[] views = new View[] {
			mainImage = (ImageView) findViewById(R.id.main_image),
			upvote = (ImageButton) findViewById(R.id.upvote),
			save = (ImageButton) findViewById(R.id.save),
			downvote = (ImageButton) findViewById(R.id.downvote),
			title = (TextView) findViewById(R.id.title), 
			company = (TextView) findViewById(R.id.category),
			price = (TextView) findViewById(R.id.price),
			saves = (TextView) findViewById(R.id.saves),
		};
	
		//onClickListeners for the buttons
		upvote.setOnClickListener(this);
		save.setOnClickListener(this);
		downvote.setOnClickListener(this);

		//set starting item to a correct item
		changeItem();
		

	}
	
	
	@Override
	public void onClick(View v) {
		
		TextView text = (TextView) findViewById(R.id.title);
		
		switch (v.getId()) {
		
		case R.id.upvote:
			toLogin();
			parseVote(text, true);
			break;
			
		case R.id.downvote:
			toLogin();
			parseVote(text, false);
			break;
			
		case R.id.save:
			toLogin();
			saveImage(mainImage, imageadapter);
			break;
			
		}//end switch
	}//end onClick
	
	
	
	//switch image and replace bottom text when a vote button is clicked
	public void parseVote(TextView text, boolean ID) {
	
		//setting up the parse class	
		try {
			ParseObject vote = new ParseObject("Vote");
			vote.put("item", mainItem.getObjectId().toString());
			
			if (ID == true)
				vote.put("like", 1);
			else vote.put("like", 0);
			
			vote.put("user", ParseUser.getCurrentUser().getObjectId().toString()); //save user objectID to the voteID
			try{
				vote.saveInBackground();
			} catch(Exception a) {
				a.printStackTrace();
				Log.e(TAG, "unable to connect to internet");
			}
			
			//finally {vote.saveEventually();} //save locally and upload when there is an Internet connection
		} catch (Exception ex) {
			ex.printStackTrace();
			Log.e(TAG, "failed to connect to parse.com"); 
		}
		
		changeItem();
		//incrementSkipAmount(); //put under changeItem so will not increment unless item has been changed
		
		//mainImage.setImageResource(R.drawable.black_dress_shoes_men);
		//text.setText("shoes\n $21.99 0 saves"); //John replace with a call to your database
	}
	
	
	
	//this is a tricky one
	public void changeItem(){
		if (checkLogin() == true) {
			toLogin();
		} else {
		ParseQuery<ParseObject> voteQuery = ParseQuery.getQuery("Vote");
		voteQuery.whereEqualTo("user", ParseUser.getCurrentUser().getObjectId().toString());
		ParseQuery<ParseObject> itemQuery = ParseQuery.getQuery("Items"); 
		itemQuery.whereDoesNotMatchKeyInQuery("objectId", "item", voteQuery);
		itemQuery.orderByAscending("votes");
		itemQuery.getFirstInBackground(new GetCallback<ParseObject>() {
		public void done(ParseObject object, ParseException e) {
			if (e == null) {
				mainItem = object;

				GetXMLTask task = new GetXMLTask();
				task.execute(mainItem.getString("imageUrl")); //changes to the correct image displayed

				title.setText(object.getString("name"));
				company.setText(object.getString("company") + " - ");
				price.setText(object.getString("priceString") + " - ");
				saves.setText(Integer.toString(object.getInt("saves")) + " Saves");				
			} else {
				Intent toNoMoreProducts = new Intent(Spruce.this, No_More_Products.class);
				startActivity(toNoMoreProducts);
				e.printStackTrace();
			}
		}
		});
		}
	}//end changeItem
	
	
		//create a new ImageView in profile and show confirmation text
	public void saveImage(ImageView mainImage, ImageAdapter imageadapter) {

		ParseObject saved = new ParseObject("Save");
		saved.put("item", mainItem.getObjectId());
		saved.put("user", ParseUser.getCurrentUser().getObjectId());
		saved.saveInBackground();
				
		//pop-up message 
		Toast imageSaved = Toast.makeText(this, "Saved to your Profile!", Toast.LENGTH_SHORT); 	//text box will show up in the middle of the screen 
		imageSaved.setGravity(Gravity.CENTER, 0, 0);											//indicating the picture was saved to your profile
		imageSaved.show();	
	}	
	
	private class GetXMLTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls) {
            Bitmap map = null;
            for (String url : urls) {
                map = downloadImage(url);
            }
            return map;
        }
 
        // Sets the Bitmap returned by doInBackground
        @Override
        protected void onPostExecute(Bitmap result) {
            mainImage.setImageBitmap(result);
        }
 
        // Creates Bitmap from InputStream and returns it
        private Bitmap downloadImage(String url) {
            Bitmap bitmap = null;
            InputStream stream = null;
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inSampleSize = 1;
 
            try {
                stream = getHttpConnection(url);
                bitmap = BitmapFactory.
                        decodeStream(stream, null, bmOptions);
                stream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return bitmap;
        }
 
        // Makes HttpURLConnection and returns InputStream
        private InputStream getHttpConnection(String urlString)
                throws IOException {
            InputStream stream = null;
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();
 
            try {
                HttpURLConnection httpConnection = (HttpURLConnection) connection;
                httpConnection.setRequestMethod("GET");
                httpConnection.connect();
 
                if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    stream = httpConnection.getInputStream();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return stream;
        }
    }
		
}//end Spruce





