package com.example.spruce;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Create_Account extends Menu_Bar implements OnClickListener{

	
	EditText username, email, password, confirmPass;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // lock screen rotation
		setContentView(R.layout.create_account);
		
		Parse.initialize(this, "4cFpeXihN2NA4CnkawX04iixC4VkQjiv6sGRxoSV", "HXhQgGZ6liU5jypojuTPUZlBs01SaUo3qGg1o2J9");
		
		Button createAccount = (Button) findViewById(R.id.create_account1);
		createAccount.setOnClickListener((android.view.View.OnClickListener) this);
		
		
		username = (EditText) findViewById(R.id.username1);
		email = (EditText) findViewById(R.id.email);
		password = (EditText) findViewById(R.id.password1);
		confirmPass = (EditText) findViewById(R.id.pasword2);
		
		
	}//end onCreate
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		
		case R.id.create_account1:
			if (password.getText().toString().equals(confirmPass.getText().toString())) {
				
				ParseUser createAccount = new ParseUser();
				
				createAccount.setUsername(username.getText().toString());
				createAccount.setEmail(email.getText().toString());
				createAccount.setPassword(password.getText().toString());
				createAccount.signUpInBackground(new SignUpCallback() {
					public void done(ParseException e) {
						if (e == null) {}
						else {
							e.printStackTrace();
						}
					}
				});
				
				Toast.makeText(Create_Account.this, "Account Created!", Toast.LENGTH_SHORT).show();
				toSpruce();
				break;
			}//end if
			
			else {
				Toast.makeText(Create_Account.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
				break;
			}
			
		}//end switch
	}//end onClick

}//end Create_Account
