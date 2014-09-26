package com.example.spruce;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends Menu_Bar implements OnClickListener{

	private static final String TAG = "Register";
	
	EditText username, email, password;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // lock screen rotation
		setContentView(R.layout.register);
		
		Parse.initialize(this, "4cFpeXihN2NA4CnkawX04iixC4VkQjiv6sGRxoSV", "HXhQgGZ6liU5jypojuTPUZlBs01SaUo3qGg1o2J9");
		
		Button createAccount, login;
		
		
		View[] Views = new View[] {
			createAccount = (Button) findViewById(R.id.create_account),
			login = (Button) findViewById(R.id.login),
			username = (EditText) findViewById(R.id.username),
			password = (EditText) findViewById(R.id.pasword)		
		};
		
		
		
		createAccount.setOnClickListener(this);
		login.setOnClickListener(this);
	}
		
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		
		case R.id.login:
			ParseUser.logInInBackground(username.getText().toString(), password.getText().toString(), new LogInCallback() {
				public void done(ParseUser user, ParseException e) {
					if (user != null) { }
					else{
						e.printStackTrace();
					}
				}
			});
			
			Toast.makeText(Register.this, "Login Successful!", Toast.LENGTH_SHORT).show();
			toProfile();
			break;		
			
		case R.id.create_account:
			Intent toCreateAccount = new Intent (Register.this, Create_Account.class);
			startActivity(toCreateAccount);
			break;
			
		}//end switch		
	}//end onClick
	


}//end Register
