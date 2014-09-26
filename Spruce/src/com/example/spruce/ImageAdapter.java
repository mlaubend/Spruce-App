package com.example.spruce;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ClassLoader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.content.Context;
import android.content.res.Resources;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;

public class ImageAdapter extends BaseAdapter {

	ParseObject[] itemArray;
	//String[] itemURLArray;
	ArrayList<Bitmap> bitmapArray = new ArrayList<Bitmap>();
	
	
    Bitmap map;

    
	private Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
		return mThumbIds.length;
	}
    
    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(convertDpToPixels(150, mContext), convertDpToPixels(150, mContext)));
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER); 
            imageView.setBackgroundResource(R.drawable.profile_border);
            imageView.setPadding(10, 10, 10, 10);
        } else {
            imageView = (ImageView) convertView;
        }

        querySaves();
        //convertToBitmap();
        imageView.setImageResource(mThumbIds[position]);
        //imageView.setImageBitmap(bitmapArray[position]);
        return imageView;
    }//end getView
    
    
    public void querySaves() {
    	ParseQuery<ParseObject> savesQuery = ParseQuery.getQuery("Save");
    	savesQuery.whereEqualTo("user", ParseUser.getCurrentUser().getObjectId());
    	ParseQuery<ParseObject> itemQuery = ParseQuery.getQuery("Items");
    	itemQuery.whereMatchesKeyInQuery("ObjectId", "item", savesQuery);
    	itemQuery.findInBackground(new FindCallback<ParseObject>() {
    	    public void done(List<ParseObject> savesList, ParseException e) {
    	        if (e == null) {
    	        	itemArray = savesList.toArray(new ParseObject[savesList.size()]);
    	        } else {

    	        }
    	    }
    	});
    		/*
    	    for (int i = 0; i < itemArray.length; i++) {
    	    	Arrays.fill(itemURLArray[i], itemArray[i].getString("imageUrl"));
    	    }*/
    	    
    	    //for each object, convert to bitmap and store in an array
    	    //call this array at imageView.setImageResource
    
    }
    
    public void convertToBitmap() {
    	GetXMLTask task = new GetXMLTask();
    	
    	for (int i = 0; i < itemArray.length; i++) {
    		task.execute(itemArray[i].getString("imageUrl"));
    		//Arrays.fill(bitmapArray, map);
    		//bitmapArray[i] = map;
    	}
    	
    }
    
    // references to our images   
    private Integer[] mThumbIds = {
            R.drawable.arborsweater, R.drawable.abacussplit,
            R.drawable.vans, R.drawable.costco,
            R.drawable.fashion, R.drawable.black_dress_shoes_men,
            R.drawable.ic_launcher,
    };
    
    public static int convertDpToPixels(float dp, Context context){    	
		Resources resources = context.getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp, 
                resources.getDisplayMetrics()
        );
    }
    
	private class GetXMLTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls) {
            for (String url : urls) {
                map = downloadImage(url);
            }
            return map;
        }
 
        // Sets the Bitmap returned by doInBackground
        @Override
        protected void onPostExecute(Bitmap result) {
            //mainImage.setImageBitmap(result);
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
    
}

