package com.nowonetofifty;

import java.io.UnsupportedEncodingException;
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

import com.nowonetofifty.R;

import net.daum.adam.publisher.AdView;
import net.daum.adam.publisher.AdView.AnimationType;
import net.daum.adam.publisher.AdView.OnAdClickedListener;
import net.daum.adam.publisher.AdView.OnAdClosedListener;
import net.daum.adam.publisher.AdView.OnAdFailedListener;
import net.daum.adam.publisher.AdView.OnAdLoadedListener;
import net.daum.adam.publisher.AdView.OnAdWillLoadListener;
import net.daum.adam.publisher.impl.AdError;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

public class Logo extends Activity {
	private AdView adView = null;
	ImageView imageView;
	private BitmapDrawable[] frame;
	private AnimationDrawable animation = null;
	private final String SERVER_ADDRESS	= "http://goldsmart.cafe24.com/apps/app_ad_manager.php";
	private final String APP_NAME 			= "nowonetofifty";
    /** Called when the activity is first created. */
	
	public Handler mHandler = new Handler(){
		public void handleMessage(Message msg){
			Intent mainview = new Intent(getApplicationContext(), GameActivity.class);
			mainview.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(mainview);
			
			finish();
		}
	};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logo);
        
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        
		imageView = (ImageView) findViewById(R.id.main_iv_animation);
		
		startAnimaion();
        
		mHandler.sendEmptyMessageDelayed(0, 1100);
        }
    
   
    
    private void startAnimaion()
	{
		Resources res;
		res = getResources();
		int page = 0;
		String image_name = null;
		
		
		page = 12;
		image_name = "icon_";
		
		frame = new BitmapDrawable[page];
		
		for(int i=0, j=1;i < page; i++,j++)
		{
			String temp1 = Integer.toString(i);
			
			int lid = this.getResources().getIdentifier(image_name+j, "drawable", this.getPackageName());
			frame[i] = (BitmapDrawable) res.getDrawable(lid);
			String temp2 = Integer.toString(i);
		}
		
		animation = new AnimationDrawable();
		animation.setOneShot(false);
		
		for(int i=0;i < page; i++)
		{
			animation.addFrame(frame[i], 100);
		}
    
		imageView.setBackgroundDrawable(animation);
		animation.start();
	}
    
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event)
    {
    	return false;
    }

}