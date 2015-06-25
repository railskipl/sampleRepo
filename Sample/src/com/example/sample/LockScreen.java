package com.example.sample;

import com.example.sample.Main.DetectAppTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LockScreen extends Activity
{
	EditText editPass;
	Button okBtn;
	TextView setPass,enterPass;
	String password,pass;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		
		editPass = (EditText)findViewById(R.id.editPass);
		okBtn = (Button)findViewById(R.id.okBtn);
		setPass = (TextView)findViewById(R.id.setPasstxt);
		enterPass = (TextView)findViewById(R.id.enterPasstxt);
		
		password = PreferenceManager.getDefaultSharedPreferences(this)
				.getString("PassWord", "");
		
		setPass.setVisibility(View.GONE);
		enterPass.setVisibility(View.VISIBLE);
		
		okBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pass = editPass.getText().toString();
				if(!password.equalsIgnoreCase(""))
				{
					if(pass.equalsIgnoreCase(password))
					{
						
						finish();
						
						Intent startHomescreen=new Intent(Intent.ACTION_MAIN);
						startHomescreen.addCategory(Intent.CATEGORY_HOME);
						startHomescreen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
						startActivity(startHomescreen);
					}
					else
					{
						Toast.makeText(getApplicationContext(), "Incorrect Password!", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
		
		
		
	}

	
	
}
