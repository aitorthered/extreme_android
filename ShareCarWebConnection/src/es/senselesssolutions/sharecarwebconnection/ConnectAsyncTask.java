package es.senselesssolutions.sharecarwebconnection;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.KeyStore;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;


import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class ConnectAsyncTask {
	private String SERVER = "http://www.senselesssolutions.com/XA/sharecar/";
//	private String TAG = "";
	private Handler mHandler;
	
	
	public void addPerson(String name, String surname, String phone, String mail, Handler callback){
		String URL = SERVER+"insert/person/";
		mHandler = callback;
		JSONObject person = new JSONObject();
		try {
			person.put("URL", URL);
			person.put("name", name);
			person.put("surname", surname);
			person.put("phone", phone);
			person.put("mail", mail);
		} catch (JSONException e) {
			Log.e(TAG, "[ConnectAsyncTask.addPerson] error añadir JSONObject person: "+e.getLocalizedMessage());
		}
		
		new MyAsyncTask().execute(person);
	}
	
	public void addCar(String origin, String destination, String day, String time, String seats, String owner, Handler callback){
		String URL = SERVER+"insert/car/";
		mHandler = callback;
		JSONObject person = new JSONObject();
		try {
			person.put("URL", URL);
			person.put("origin", origin);
			person.put("destination", destination);
			person.put("day", day);
			person.put("time", time);
			person.put("seats", seats);
			person.put("owner", owner);
		} catch (JSONException e) {
			Log.e(TAG, "[ConnectAsyncTask.addCar] error añadir JSONObject person: "+e.getLocalizedMessage());
		}
		
		new MyAsyncTask().execute(person);
	}
	
	public void queryCars(String origin, String destination, Handler callback){
		queryCars(origin, destination, "", callback);
	}

	public void queryCars(String origin, String destination, String day, Handler callback){
		String URL = SERVER+"get/cars/";
		mHandler = callback;
		JSONObject person = new JSONObject();
		try {
			person.put("URL", URL);
			person.put("origin", origin);
			person.put("destination", destination);
			person.put("day", day);
		} catch (JSONException e) {
			Log.e(TAG, "[ConnectAsyncTask.addPerson] error añadir JSONObject person: "+e.getLocalizedMessage());
		}
		
		new MyAsyncTask().execute(person);
	}
	
	
	
	
	private class MyAsyncTask extends AsyncTask<JSONObject, Void, JSONObject> {

		@Override
		protected JSONObject doInBackground(JSONObject... arg0) {
			JSONObject toSend = arg0[0];
			String URL = toSend.optString("URL", "");
			toSend.remove("URL");
			JSONObject returnedJSON = SendHttpPostWithSession(URL, toSend);
			return returnedJSON;
		}
		
		protected void onPostExecute(JSONObject result) {
			Message msg = Message.obtain();
			msg.obj = result;
			mHandler.dispatchMessage(msg);
		}
	}

	private final static int MAX_TIMEOUT_MILISECONDS = 60000; 

	private static final String TAG = "ConnectAsyncTask";
	
	private DefaultHttpClient getNewHttpClient() {
		try {
			HttpParams params = new BasicHttpParams();
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
			HttpConnectionParams.setConnectionTimeout(params, MAX_TIMEOUT_MILISECONDS);
		    HttpConnectionParams.setSoTimeout(params, MAX_TIMEOUT_MILISECONDS);
			return new DefaultHttpClient(params);
		} catch (Exception e) {
			return new DefaultHttpClient();
		}
	}
	
	private JSONObject SendHttpPostWithSession(String URL, JSONObject jsonObjSend) {
		JSONObject jsonObjRecv = null;
		try {
//			DefaultHttpClient httpclient = new DefaultHttpClient();
			DefaultHttpClient httpclient = getNewHttpClient();
			HttpPost httpPostRequest = new HttpPost(URL);//+";jsessionid="+idSession);
//			httpPostRequest.setHeader("Content-type", "application/json");
//			se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json") );
			httpPostRequest.setHeader("Cache-Control", "no-store, no-cache");

//			HttpPost httpPostRequest = new HttpPost(URL);
//			postParameters.add(new BasicNameValuePair("jsessionid", idSession));
			HttpResponse response;

//			ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
//			postParameters.add(new BasicNameValuePair("username", username));
//			postParameters.add(new BasicNameValuePair("password", password));

			
			String responseStr = null;
			try {
//				UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(postParameters);
//				httpPostRequest.setEntity(formEntity);

//				StringEntity se = new StringEntity( jsonObjSend.toString(), HTTP.UTF_8);  
//				se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json") );
//				httpPostRequest.setEntity(se);
//				httpPostRequest.setHeader("json", jsonObjSend.toString());

//				httpPostRequest.setEntity(new ByteArrayEntity(jsonObjSend.toString().getBytes(
//		                "UTF8")));
//				httpPostRequest.setHeader("jsonData", jsonObjSend.toString());
//				httpPostRequest.setHeader("Content-type", "application/json");


				ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
				postParameters.add(new BasicNameValuePair("json", jsonObjSend.toString()));
				UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(postParameters, "UTF-8");
				httpPostRequest.setEntity(formEntity);
//				httpPostRequest.getParams().setParameter("jsonData",jsonObjSend.toString()); 

				
//				JSONObject jsonData = new JSONObject();
//				jsonData.put("jsonData", jsonObjSend);
//				httpPostRequest.setEntity(new ByteArrayEntity(jsonData.toString().getBytes(
//		                "UTF8")));
//				httpPostRequest.setHeader("jsonData", jsonData.toString());
//				httpPostRequest.setHeader("Content-type", "application/json");

				
//				StringEntity se;				
//				se = new StringEntity(jsonObjSend.toString(), HTTP.ISO_8859_1);
//				se.setContentType("application/json");
//				httpPostRequest.setEntity(se);
//				httpPostRequest.setHeader("Accept", "application/json");
//				httpPostRequest.setHeader("Content-type", "application/json");

//				ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
//				nameValuePairs.add(new BasicNameValuePair("jsonData", jsonObjSend.toString()));
//				UrlEncodedFormEntity se = new UrlEncodedFormEntity(nameValuePairs);
//				se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json") );
//				httpPostRequest.setEntity(se);
////				httpPostRequest.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//				httpPostRequest.setHeader("Accept", "application/json");
//				httpPostRequest.setHeader("Content-type", "application/json");
//				httpPostRequest.setHeader("jsonData", jsonObjSend.toString());


				
				response = httpclient.execute(httpPostRequest);
//				httpPostRequest.

				BufferedReader in = null;
				in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

				StringBuffer sb = new StringBuffer("");
				String line = "";
				String NL = System.getProperty("line.separator");
				while ((line = in.readLine()) != null) {
					sb.append(line + NL);
				}
				in.close();

				responseStr = sb.toString();
				jsonObjRecv = new JSONObject(responseStr);
				// Raw DEBUG output of our received JSON object:
				Log.i(TAG,jsonObjRecv.toString());

			} catch (Exception e) {
				Log.e(TAG,"[HttpClient.SendHttpPostWithSession(String, String, JSONObject)] "+URL+" "+e.toString());
				jsonObjRecv = new JSONObject();
//				JSONObject RETURN = new JSONObject();
//				jsonObjRecv.put("RETURN", RETURN);
				jsonObjRecv.put("success", 100);
				if(e.toString().contains("IllegalStateException")){
					jsonObjRecv.put("timeout", true);
				}
				else if(responseStr.contains("renoveSession")){ //TODO Cuidado con que response sea null -> NullPointerException
					jsonObjRecv.put("renoveSession", true);
				}
				Log.e(TAG,"[HttpClient.SendHttpPostWithSession(String, String, JSONObject)] "+URL+" responseStr="+responseStr);
			}

		}
		catch (Exception e) {
			// More about HTTP exception handling in another tutorial.
			// For now we just print the stack trace.
			jsonObjRecv = new JSONObject();
			try {
//				JSONObject RETURN = new JSONObject();
//				jsonObjRecv.put("RETURN", RETURN);
				jsonObjRecv.put("success", 101);
				jsonObjRecv.put("renoveSession", true);
			} catch (JSONException e1) {
				Log.e(TAG,"[HttpClient.SendHttpPostWithSession(String, String, JSONObject)] Error al poner renoveSession"+e1.toString());
			}
			Log.e(TAG,"[HttpClient.SendHttpPostWithSession(String, String, JSONObject)] Catch del primer try "+e.toString());
		}
		return jsonObjRecv;
	}

}
