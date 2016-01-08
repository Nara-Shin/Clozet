package com.nowonetofifty;

import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.Vector;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import net.daum.adam.publisher.AdView;
import net.daum.adam.publisher.AdView.AnimationType;
import net.daum.adam.publisher.AdView.OnAdClickedListener;
import net.daum.adam.publisher.AdView.OnAdClosedListener;
import net.daum.adam.publisher.AdView.OnAdFailedListener;
import net.daum.adam.publisher.AdView.OnAdLoadedListener;
import net.daum.adam.publisher.AdView.OnAdWillLoadListener;
import net.daum.adam.publisher.impl.AdError;

import com.nowonetofifty.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

public class Web_Board extends Activity implements View.OnClickListener{
	private WebView wv;
	private String url=null;
	private Button mClose = null;
	private AdView adView = null;
	private final String SERVER_ADDRESS	= "http://goldsmart.cafe24.com/apps/app_ad_manager.php";
	private final String APP_NAME 			= "nowonetofifty";
	
	 public class MyChromeClient extends WebChromeClient{
			@Override
		    public void onProgressChanged(WebView view, int newProgress) {
				Web_Board.this.setProgress(newProgress * 100);
		    }
	    }
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // ������¹� ǥ��
        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        
        setContentView(R.layout.board);
       
        if( !isNetworkConnected(this) )
    	{
        	Toast.makeText(getApplicationContext(), "��Ʈ��ũ ������� Ȯ�ο�!", Toast.LENGTH_LONG).show();            
    		finish();        
    	}
        
        SharedPreferences prefs =getSharedPreferences("nowddingddong", MODE_PRIVATE);
        String my_id = prefs.getString("my_id", "");
        String login_yn = prefs.getString("login_yn", "");
        
        /*
        if(login_yn.toString().compareTo("Y") != 0)
        {
        	Toast.makeText(getApplicationContext(), "�������� �α��� �� �̿��ϼ���.", Toast.LENGTH_LONG).show();            
    		finish();
        }
        */
        Button localButton = (Button)findViewById(R.id.Notice_Popup_Close);
        this.mClose = localButton;
        	
		url = "http://goldsmart.cafe24.com/app/nowonetofifty_rank_list.php"; // �������
		
        // ȭ�� �Ȳ����� �ϱ�
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        
        // ������
        initAdam();
        
        wv=(WebView)findViewById(R.id.web);
        wv.setBackgroundColor(0);          //�����ϰ� �ϱ�
        wv.setBackgroundResource(R.drawable.bg);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.setVerticalScrollbarOverlay(true);   //��ũ�� ���� ���信 ��������       
        wv.setWebViewClient(new WebViewClient());
        
        // �ڹٰ��â ó�� �� ���ε�
        wv.setWebViewClient(new WebViewClient(){
     	   
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
            	view.loadUrl(url);
            	return true;
            }
        
     	   public void onReceivedError(WebView view, int errorCode,
 				String description, String failingUrl) {
     		   	Toast.makeText(getApplicationContext(), "��Ʈ��ũ ������� Ȯ�ο�!", Toast.LENGTH_LONG).show();            
     		   	finish();        
     	   }
        });
        
        // �ڹٰ��â ó�� �� ���ε�
        final Context myApp = this;
        wv.setWebChromeClient(new MyChromeClient() { //���� Ŭ���̾�Ʈ(�ּ�â ���ֱ� ����)
        	
        @Override
        public boolean onJsAlert(WebView view, String url, String
        message, final android.webkit.JsResult result)
        {
        	new AlertDialog.Builder(myApp).setTitle("�˸�â").setIcon(R.drawable.icon).setMessage(message).setPositiveButton(android.R.string.ok, new OnClickListener()
        	{
        		public void onClick(DialogInterface dialog, int which)
        		{
        			result.confirm();
        			finish();
        		}
        		}
        	).setCancelable(false).create().show();
        	return true;
         };
       });
        
        this.mClose.setOnClickListener(this);
        wv.loadUrl(url); //�ּ��Է�
    }
    
    // BACK��ưŬ���� ���� �������� ����
    public boolean onKeyDown(int keyCode, KeyEvent event){
    	if((keyCode==KeyEvent.KEYCODE_BACK)&&wv.canGoBack()){
    		wv.goBack();
    		return true;
    	}
    	
    	return super.onKeyDown(keyCode, event);
    }
    
    public boolean isNetworkConnected(Context context){    
		boolean isConnected = false;    
		
		ConnectivityManager manager =         
			(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);    
		NetworkInfo mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);    
		NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);    
		
		if (mobile.isConnected() || wifi.isConnected()){        
			isConnected = true;    
			}else{        
				isConnected = false;    
			}    
		return isConnected;
	}
    
    public void onClick(View paramView)
    {
      Button localButton = this.mClose;
      if (paramView != localButton)
        return;
      finish();
    }
    
private void initAdam() {
    	
		// Ad@m sdk �ʱ�ȭ ����
		adView = (AdView) findViewById(R.id.adview);
		adView.setRequestInterval(5);
		
		// ���� Ŭ���� ������ ������
		adView.setOnAdClickedListener(new OnAdClickedListener() {
			@Override
			public void OnAdClicked() {
				
			}
		});

		// ���� �����ޱ� �������� ��쿡 ������ ������
		adView.setOnAdFailedListener(new OnAdFailedListener() {
			@Override
			public void OnAdFailed(AdError arg0, String arg1) {
				Log.i("####MAIN:", "���� ȣ�����");
			}
		});

		// ���� ���������� �����޾��� ��쿡 ������ ������
		adView.setOnAdLoadedListener(new OnAdLoadedListener() {

			@Override
			public void OnAdLoaded() {
				Log.i("####MAIN:", "���� ȣ�⼺��");
			}
		});

		// ���� �ҷ��ö� ������ ������
		adView.setOnAdWillLoadListener(new OnAdWillLoadListener() {

			@Override
			public void OnAdWillLoad(String arg1) {
				Log.i("####MAIN:", "���� �ҷ�����");
				
			}
		});

		// ���� �ݾ����� ������ ������
		adView.setOnAdClosedListener(new OnAdClosedListener() {

			@Override
			public void OnAdClosed() {
				
			}
		});
    	
		/* ���� Ű ��û */
    	try {
			String ad_key = (String)sendData(APP_NAME);
			adView.setClientId(ad_key);		
			//adView.setClientId("TestClientId");	//�����׽�Ʈ
			Log.i("�����׽�Ʈ", ad_key);
		} catch (Exception e) {
			adView.setClientId("5c10Z0iT14304f73e65");
    	}
    	
		adView.setRequestInterval(12);

		// Animation ȿ�� : �⺻ ���� AnimationType.NONE
		adView.setAnimationType(AnimationType.FLIP_HORIZONTAL);

		adView.setVisibility(View.VISIBLE);
	}
    
	@Override
	public void onDestroy() 
	{
		super.onDestroy();
		if (adView != null) 
		{
			adView.destroy();
			adView = null;
		}
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}
	
	private String sendData(String app_name) throws Exception 
    {
    	HttpPost request = makeHttpPost(app_name, SERVER_ADDRESS);
    	HttpClient client = new DefaultHttpClient();
    	ResponseHandler reshandler = new BasicResponseHandler();
    	String result = client.execute(request, reshandler);

    	return result;
    }
    
    private HttpPost makeHttpPost(String app_name, String url) throws Exception 
    {
		HttpPost request = new HttpPost(url);
		Vector<NameValuePair> nameValue = new Vector<NameValuePair>();
		nameValue.add(new BasicNameValuePair("app_name", app_name));
		request.setEntity( makeEntity(nameValue) );
		return request;
    }
    
    private HttpEntity makeEntity(Vector<NameValuePair> nameValue) throws Exception 
    {
		HttpEntity result = null;
		try {
		result = new UrlEncodedFormEntity(nameValue, "euc-kr");
		} catch (UnsupportedEncodingException e) {
		}
		return result;
    }
}