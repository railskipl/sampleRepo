 package com.example.sample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.example.sample.Main.DetectAppTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;

public class Processess extends Activity
{

	ListView lv;
	TextView total;
	TextView tv;
	CheckBox cBox;
	ArrayList<String> apps;
	ArrayList<String> packName;
	private boolean[] thumbnailsselection;  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.process);
		
		lv = (ListView)findViewById(R.id.listPro);
		total = (TextView)findViewById(R.id.processCount);
		apps = new ArrayList<String>();
		packName = new ArrayList<String>();
		
		DetectAppTask appTask = new DetectAppTask();
		appTask.execute();
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				// TODO Auto-generated method stub
				ActivityManager am = (ActivityManager) getSystemService(Activity.ACTIVITY_SERVICE);
			    am.killBackgroundProcesses(packName.get(pos));
			    }
		});
	}
	
	public class DetectAppTask extends AsyncTask<Void, Void, String>
	{
		
		public String activityOnTop;
		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
			PackageManager packageManager = getPackageManager();
			Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
			mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
			
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
			List<RunningTaskInfo> RunningTask = mActivityManager.getRunningTasks(5);
			
			for(int i=RunningTask.size()-1; i>=0; i--){

	            String packageName = RunningTask.get(i).topActivity.getPackageName();

	            Drawable d=null;
	            String appName="";

	            try {
	                d=packageManager.getApplicationIcon(packageName);

	                appName=(String)packageManager.getApplicationLabel(packageManager.getApplicationInfo(packageName,PackageManager.GET_META_DATA));

	            } catch (NameNotFoundException e) {
	                e.printStackTrace();
	            }

	                    packName.add(packageName);  //arraylist of package name 
	                apps.add(appName);          // arraylist of app name
	                            
	        }
			
//			ActivityManager.RunningTaskInfo ar = RunningTask.get(0);
//			activityOnTop=ar.topActivity.getClassName();
//			apps.get(1);
			
			return null;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

				lv.setAdapter(new bsAdapter());
//				total.setText(lv.getCount());
//				Intent lockIntent = new Intent(getApplicationContext(), LockScreen.class);
//				lockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				getApplicationContext().startActivity(lockIntent);
			
			
		}

	}
	
	// adapter class
//		@SuppressLint("ViewHolder")
		@SuppressLint("ViewTag")
		public class bsAdapter extends BaseAdapter {

			ViewHolder viewHolder;
			int data[];
			int selected_position = -1;
			ArrayList<Boolean> positionArray;
			
			private PackageManager pm = getApplicationContext().getPackageManager();;
			
			public int getCount() {
				// TODO Auto-generated method stub
				return apps.size();
			}

			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return apps.get(position);
			}

			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return apps.size();
			}

			@Override
			public View getView(final int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub  
	                ViewHolder holder;  
	                thumbnailsselection = new boolean[apps.size()];
	                if (convertView == null) {  
	                     holder = new ViewHolder();  
	                     convertView = LayoutInflater.from(getApplicationContext()).inflate(  
	                               R.layout.list_item, null);  
	                     holder.textview = (TextView) convertView  
	                               .findViewById(R.id.label);  
	                     holder.checkbox = (CheckBox) convertView  
	                               .findViewById(R.id.dtcCounterCheckbox);  
	                     convertView.setTag(holder);  
	                } else {  
	                     holder = (ViewHolder) convertView.getTag();  
	                }  
	                holder.checkbox.setId(position);  
	                holder.textview.setId(position);  
	                holder.textview.setText(apps.get(position));  
	                holder.checkbox.setChecked(thumbnailsselection[position]);  
	                holder.id = position;
	                holder.checkbox.setOnClickListener(new OnClickListener() {  
	                     public void onClick(View v) {  
	                          // TODO Auto-generated method stub  
	                          CheckBox cb = (CheckBox) v;  
	                       
	                          int id = cb.getId();  
	                          if (thumbnailsselection[id]) {  
	                               cb.setChecked(false);  
	                               thumbnailsselection[id] = false;  
	                          } else {  
	                               cb.setChecked(true);  
	                               thumbnailsselection[id] = true;  
	                          }  
	                     }  
	                });  
	                holder.textview.setOnClickListener(new OnClickListener() {  
	                     public void onClick(View v) {  
	                          // TODO Auto-generated method stub  
	                          int id = v.getId();  
	                     }  
	                });  
	                  
	                return convertView;  
	           }  
			}

		

		class ViewHolder {  
	           TextView textview;  
	           CheckBox checkbox;  
	           int id;  
	      }  
//		static class ViewHolder {
//			private CheckBox checkBox;  
//			 private TextView textView;  
//			  
//			 public ViewHolder() {  
//			 }  
//			  
//			 public ViewHolder(TextView textView, CheckBox checkBox) {  
//			  this.checkBox = checkBox;  
//			  this.textView = textView;  
//			 }  
//			  
//			 public CheckBox getCheckBox() {  
//			  return checkBox;  
//			 }  
//			  
//			 public void setCheckBox(CheckBox checkBox) {  
//			  this.checkBox = checkBox;  
//			 }  
//			  
//			 public TextView getTextView() {  
//			  return textView;  
//			 }  
//			  
//			 public void setTextView(TextView textView) {  
//			  this.textView = textView;  
//			 }  
//			}  
//		
//	    public class Planet {  
//	        private String name = "";  
//	        private boolean checked = false;  
//	         
//	        public Planet() {  
//	        }  
//	         
//	        public Planet(String name) {  
//	         this.name = name;  
//	        }  
//	         
//	        public Planet(String name, boolean checked) {  
//	         this.name = name;  
//	         this.checked = checked;  
//	        }  
//	         
//	        public String getName() {  
//	         return name;  
//	        }  
//	         
//	        public void setName(String name) {  
//	         this.name = name;  
//	        }  
//	         
//	        public boolean isChecked() {  
//	         return checked;  
//	        }  
//	         
//	        public void setChecked(boolean checked) {  
//	         this.checked = checked;  
//	        }  
//	         
//	        public String toString() {  
//	         return name;  
//	        }  
//	         
//	        public void toggleChecked() {  
//	         checked = !checked;  
//	        }  
//	       }  

//
//		private class CheckBoxstate {
//		    private int pos;
//		    private boolean state;
//
//		    public CheckBoxstate() {
//		        // TODO Auto-generated constructor stub
//		    }
//		    public CheckBoxstate(int pos, boolean state) {
//		        // TODO Auto-generated constructor stub
//		        this.pos = pos;
//		        this.state = state;
//		    }
//
//		    public void setPosition(int pos) {
//		        this.pos = pos;
//		    }
//		    public int getPosition() {
//		        return this.pos;
//		    }
//
//		    public void setState(boolean state) {
//		        this.state = state;
//		    }
//		    public boolean isChecked() {
//		        return this.state;
//		    }
//		}
}
