package com.example.sample;

import java.text.SimpleDateFormat;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.example.AppConstants.AppConstants;



import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class LoginActivity extends Activity implements OnClickListener {

	private EditText userId;
	private EditText password;
	private Spinner cityAccountCode;
	private String cityCodeJson;
	private Button loginButton;
	private PackageInfo info;
	private String installDate;
	private final SimpleDateFormat formatter = new SimpleDateFormat(
			"yyyy-MM-dd");
	private final long ONE_DAY = 24 * 60 * 60 * 1000;
	private final long EXPIRY1 = 30;
	private String USERID;
	private String PASSWORD;
	private String ACCOUNT_ID;
	private String IMEI;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);

		TelephonyManager mngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		IMEI = mngr.getDeviceId();
		Log.e("IMEI*****", IMEI);

		// PackageManager pm = this.getPackageManager();
		// try {
		// info = pm.getPackageInfo("com.example.bluetoothprinter", 0);
		// SimpleDateFormat dateFormat = new
		// SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// installDate = dateFormat.format( new Date( info.firstInstallTime ));
		// } catch (NameNotFoundException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		//
		// Log.d("installed time--------------->>>>>>",installDate);
		// if(installDate == null) {
		// Date now = new Date();
		// String dateString = formatter.format(now);
		// }
		// else{
		// // This is not the 1st run, check install date
		// Date before = null;
		// try {
		// before = (Date)formatter.parse(installDate);
		// } catch (ParseException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// Date now = new Date();
		// long diff = now.getTime() - before.getTime();
		// long days = diff / ONE_DAY;
		// if(days > EXPIRY1) { // More than 15 days?
		// // Expired !!!
		// Log.d("Expired Êtime--------------->>>>>>","");
		// showTrialPeriodEndedAlert();
		// }
		// else{
		userId = (EditText) findViewById(R.id.UserIdJson);
		password = (EditText) findViewById(R.id.PasswordJson);

		String user_name = PreferenceManager.getDefaultSharedPreferences(this)
				.getString("UserName", "");
		String pass_word = PreferenceManager.getDefaultSharedPreferences(this)
				.getString("PassWord", "");

		loginButton = (Button) findViewById(R.id.login);
		loginButton.setOnClickListener(this);

		cityAccountCode = (Spinner) findViewById(R.id.AccCodeJson);

		String[] cityAccountcodeArr = new String[] { "Select Account Id","734" };
		ArrayAdapter<String> cityCode = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item,
				cityAccountcodeArr);
		this.cityAccountCode.setAdapter(cityCode);
		this.cityAccountCode
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						cityCodeJson = LoginActivity.this.cityAccountCode
								.getSelectedItem().toString();
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}

				});

	}

	// }

	// }
	

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		// super.onBackPressed();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (((Button) v).getId() == loginButton.getId()) {
			PASSWORD = password.getText().toString();
			USERID = userId.getText().toString();

			if ((!PASSWORD.equals("") && !USERID.equals("") && !cityCodeJson
					.equals("Select Account Id"))) {
				AsyncTaskRunner runner = new AsyncTaskRunner();
				runner.execute(cityCodeJson);
			} else {
				Toast.makeText(getApplicationContext(),
						"Please enter User Id, Password AND Account Id!",
						Toast.LENGTH_LONG).show();
			}
		}
	}

	class AsyncTaskRunner extends AsyncTask<String, String, String> {

		private ProgressDialog pDialog;
		private String NAMESPACE;
		private String METHOD_NAME;
		private String URL;

		private SoapObject result;
		private String pkk;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			// super.onPreExecute();
			pDialog = new ProgressDialog(LoginActivity.this);
			pDialog.setMessage("Please Wait.....");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub

			ACCOUNT_ID = params[0];
			Log.d("asssysnc", ACCOUNT_ID);

			String SOAP_ACTION1 = "http://tempuri.org/logindetails";

			NAMESPACE = "http://tempuri.org/";

			METHOD_NAME = "logindetails";

			

			USERID = userId.getText().toString();
			PASSWORD = password.getText().toString();

			// Initialize soap request + add parameters
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
			Log.d("IMEI****", IMEI);
			// Use this to add parameters accountid
			request.addProperty("accountid", ACCOUNT_ID);
			request.addProperty("MobileNo", "");
			request.addProperty("imei", IMEI);
			request.addProperty("userid", USERID);
			request.addProperty("password", PASSWORD);

			// Declare the version of the SOAP request
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER10);

			envelope.setOutputSoapObject(request);

			envelope.dotNet = true;

			envelope.implicitTypes = true;

			HttpTransportSE androidHttpTransport = new HttpTransportSE(AppConstants.LOGIN_URL);
			try {

				// this is the actual part that will call the webservice
				androidHttpTransport.call(SOAP_ACTION1, envelope);

				SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
				// Get the SoapResult from the envelope body.
				result = (SoapObject) envelope.bodyIn;

				pkk = response.toString();
				Log.d("Responxe-----", response.toString());
				if (result != null) {
					// Get the first property and change the label text
					Log.d("result he------", result.getProperty(0).toString());

				} else {
					Log.d("result nai he------", "result nai he");
				}

			} catch (Exception e) {

				e.printStackTrace();

			}
			return result.toString();

		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (pDialog != null) {
				pDialog.dismiss();
				pDialog = null;
			}
			Log.d("rposttttt***", result.toString());
			String[] separated = result.split("\\=");

			// Log.d("resulttttttt***",separated.toString());
			Log.d("result1111***", separated[0]);
			// Log.d("result2222***",separated[1]);
			String[] separated1 = separated[1].split(",");
			Log.d("resulttttttt***", separated1.toString());
			Log.d("result1111***", separated1[0]);
			Log.d("result2222***", separated1[1]);
			Log.d("result3333***", separated1[2]);
			Log.d("result4444111***", separated1[3]);
			Log.d("result4444***", separated1[4]);
			Log.d("result5555***", separated1[5]);
			// Log.d("result6666***",separated1[6]);
			String[] separateddd = separated1[6].split("\\;");
			Log.d("result6666***", separateddd[0]);

			String[] separateddd1 = separated1[7].split("\\;");
			Log.d("result7777***", separateddd1[0]);
			// Log.d("result6666***",separateddd[1]);
			// if(result.toString().equals("logindetailsResponse{logindetailsResult=0; }")){
			// Toast.makeText( getApplicationContext(),
			// "Invalid User Id, Password OR Account Id!",
			// Toast.LENGTH_LONG).show();
			//
			// }
			//
			if (separated1[4].equals("1")) {
				Log.d("alooo", "yes");
				Toast.makeText(getApplicationContext(), "Login Successful!",
						Toast.LENGTH_SHORT).show();

				PreferenceManager
						.getDefaultSharedPreferences(LoginActivity.this).edit()
						.putString("AccountId", ACCOUNT_ID).commit();
				PreferenceManager
						.getDefaultSharedPreferences(LoginActivity.this).edit()
						.putString("UserName", USERID).commit();
				PreferenceManager
						.getDefaultSharedPreferences(LoginActivity.this).edit()
						.putString("PassWord", PASSWORD).commit();
				PreferenceManager
						.getDefaultSharedPreferences(LoginActivity.this).edit()
						.putString("DayEnd", separated1[0]).commit();
				PreferenceManager
						.getDefaultSharedPreferences(LoginActivity.this).edit()
						.putString("ReviseBill", separated1[1]).commit();
				PreferenceManager
						.getDefaultSharedPreferences(LoginActivity.this).edit()
						.putString("DuplicateBill", separated1[2]).commit();
				PreferenceManager
						.getDefaultSharedPreferences(LoginActivity.this).edit()
						.putString("PaymentReciept", separated1[3]).commit();
				PreferenceManager
						.getDefaultSharedPreferences(LoginActivity.this).edit()
						.putString("month1", separated1[5]).commit();
				PreferenceManager
						.getDefaultSharedPreferences(LoginActivity.this).edit()
						.putString("month2", separateddd[0]).commit();
				PreferenceManager
						.getDefaultSharedPreferences(LoginActivity.this).edit()
						.putString("BillDelivery", separateddd1[0]).commit();
				Intent k = new Intent(LoginActivity.this,
						DashboardActivity.class);

				startActivity(k);
				finish();
			} else if (separated1[4].equals("0")) {
				Toast.makeText(getApplicationContext(),
						"Invalid User Id, Password OR Account Id!",
						Toast.LENGTH_LONG).show();
			} else {
				Log.d("alooo", "noooo");
			}

		}
	}
}

