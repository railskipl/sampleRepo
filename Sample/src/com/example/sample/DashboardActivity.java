package com.example.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DashboardActivity extends Activity
{

	ImageView img;
	TextView t1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dashboard);
		img = (ImageView) findViewById(R.id.imageView1);
		t1=(TextView)findViewById(R.id.textView1);

	   }

	   @Override
	   public boolean onCreateOptionsMenu(Menu menu) {
	      // Inflate the menu; this adds items to the action bar if it is present.
//	      getMenuInflater().inflate(R.menu.main, menu);
	      return true;
	   }
	   public void open(View view){
	      Intent shareIntent = new Intent();
	      shareIntent.setAction(Intent.ACTION_SEND);
	      shareIntent.setType("text/plain");
	      shareIntent.putExtra(Intent.EXTRA_TEXT, "Hello, from tutorialspoint");
	      startActivity(Intent.createChooser(shareIntent, "Share your thoughts"));

	   }
	
	
}
