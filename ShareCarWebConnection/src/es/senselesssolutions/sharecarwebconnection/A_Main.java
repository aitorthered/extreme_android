package es.senselesssolutions.sharecarwebconnection;

import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class A_Main extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ((Button)findViewById(R.id.button1)).setOnClickListener(mClick);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    protected Dialog onCreateDialog(int id) {
        Dialog dialog;
        switch(id) {
        case 0:
        	dialog = new ProgressDialog(A_Main.this);
        	((ProgressDialog) dialog).setProgressStyle(ProgressDialog.STYLE_SPINNER);
        	((ProgressDialog) dialog).setMessage("Conectando con el chanchiserver");
        	((ProgressDialog) dialog).setCancelable(false);
            break;
        default:
            dialog = null;
        }
        return dialog;
    }
    
    private OnClickListener mClick = new OnClickListener(){
		@SuppressWarnings("deprecation")
		public void onClick(View mView) {
			showDialog(0);
			ConnectAsyncTask cat = new ConnectAsyncTask();
//			cat.addPerson("Gorrin", "Eres un gran", "6999", "muchospenes@paraelgorrino.muchos", mHandler);
//			cat.addCar("Benasque", "Madrid", "2013/07/15", "10:00", "5", "8b208d2591a98b3077ef6a6d0a9941ca9784879b5783ee08479fc294ec7efd4b936553e6", mHandler);
			cat.queryCars("Benasque", "Madrid", mHandler);
		}
    };
    
    private Handler mHandler = new Handler() {
        @SuppressWarnings("deprecation")
		public void handleMessage(Message msg) {
        	JSONObject result = (JSONObject)msg.obj;
        	int success = result.optInt("success",0);
        	if(result != null){
        		((TextView)findViewById(R.id.textView1)).setText(result.toString());
        	}
        	else{
        		((TextView)findViewById(R.id.textView1)).setText("result == null");
        	}
        	// 0 mal
        	// 1 bien
        	// 2 entrada duplicada (al insertar persona con el mismo mail)
        	//TODO Actualizar UI
        	dismissDialog(0);
        	
        	//PARA EL GORRINO, comentado
//        	try {
//				Thread.sleep(3000);
//			} catch (InterruptedException e) {
//				Log.e("TAG", "[KAKA en sleep]"+e.getLocalizedMessage());
//			}
//        	showDialog(0);
//			ConnectAsyncTask cat = new ConnectAsyncTask();
//			cat.addPerson("Gorrin", "Eres un gran", "6999", "penes@paraelgorrino.muchos", mHandler);
        }
    };
}
