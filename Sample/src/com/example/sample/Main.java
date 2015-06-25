package com.example.sample;



import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends Activity
{
	EditText editPass;
	Button okBtn;
	TextView setPass,enterPass;
	String password;
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
		
		if(!password.equalsIgnoreCase(""))
		{
			setPass.setVisibility(View.GONE);
			enterPass.setVisibility(View.VISIBLE);
			
		}
		else
		{
			setPass.setVisibility(View.VISIBLE);
			enterPass.setVisibility(View.GONE);
		}
		
		
		okBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				password = PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
						.getString("PassWord", "");
				if(!password.equalsIgnoreCase(""))
				{
					if(editPass.getText().toString().equalsIgnoreCase(password))
					{
						
						Intent i= new Intent(Main.this, Processess.class);
						startActivity(i);
//						DetectAppTask appTask = new DetectAppTask();
//						appTask.execute();
					}
				}
				else
				{
					PreferenceManager
					.getDefaultSharedPreferences(Main.this).edit()
					.putString("PassWord", editPass.getText().toString()).commit();
					Toast.makeText(getApplicationContext(), "Password Set!", Toast.LENGTH_SHORT).show();
					setPass.setVisibility(View.GONE);
					enterPass.setVisibility(View.VISIBLE);
					editPass.setText("");
				}
			}
		});
	}

	public class DetectAppTask extends AsyncTask<Void, Void, String>
	{
		ArrayList<String> apps;
		public String activityOnTop;
		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
			PackageManager packageManager = getPackageManager();
			Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
			mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
			apps = new ArrayList<String>();
			List<ResolveInfo> appList = packageManager.queryIntentActivities(mainIntent, 0);
			Collections.sort(appList, new ResolveInfo.DisplayNameComparator(packageManager));
			List<PackageInfo> packs = packageManager.getInstalledPackages(0);
			for(int i=0; i < packs.size(); i++) {
			    PackageInfo p = packs.get(i);
			    ApplicationInfo a = p.applicationInfo;
			    // skip system apps if they shall not be included
			    if((a.flags & ApplicationInfo.FLAG_SYSTEM) == 1) {
			        continue;
			    }
			    apps.add(p.packageName);
			}
			
			ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
			List<ActivityManager.RunningTaskInfo> RunningTask = mActivityManager.getRunningTasks(1);
			ActivityManager.RunningTaskInfo ar = RunningTask.get(0);
			activityOnTop=ar.topActivity.getClassName();
			apps.get(1);
			
			return activityOnTop;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			if(!result.equalsIgnoreCase(""))
			{
				Log.e("Current Processsss*****>>>>>", result);
				
				Intent lockIntent = new Intent(getApplicationContext(), LockScreen.class);
				lockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				getApplicationContext().startActivity(lockIntent);
			}
			
		}
		
		
		
	}
	
	
}
