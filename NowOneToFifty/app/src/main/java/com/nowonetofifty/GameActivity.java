package com.nowonetofifty;

import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.Vector;

import net.daum.adam.publisher.AdView;
import net.daum.adam.publisher.AdView.AnimationType;
import net.daum.adam.publisher.AdView.OnAdClickedListener;
import net.daum.adam.publisher.AdView.OnAdClosedListener;
import net.daum.adam.publisher.AdView.OnAdFailedListener;
import net.daum.adam.publisher.AdView.OnAdLoadedListener;
import net.daum.adam.publisher.AdView.OnAdWillLoadListener;
import net.daum.adam.publisher.impl.AdError;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.fsn.cauly.CaulyAdInfo;
import com.fsn.cauly.CaulyAdInfoBuilder;
import com.fsn.cauly.CaulyAdView;
import com.fsn.cauly.CaulyAdViewListener;
import com.fsn.cauly.CaulyInterstitialAd;
import com.fsn.cauly.CaulyInterstitialAdListener;
import com.nowonetofifty.TimeThread;
import com.nowonetofifty.SoundManager;
import com.nowonetofifty.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

public class GameActivity extends Activity implements OnClickListener,CaulyAdViewListener, CaulyInterstitialAdListener  {
	/** Called when the activity is first created. */
	
	Button bt[] = new Button[27];
	Random random1 = new Random();
	Random random2 = new Random();
	LinearLayout cntLayout, txtLayout, btnLayout;
	int mix = 0, count = 1, cnt = 1, hunt_sec = 1, send_count = 1;
	int[] b = new int[26];
	Animation ani;
	Handler mHandler = new Handler();
	TextView tv;
	Runnable r;
	private final String SERVER_ADDRESS	= "http://goldsmart.cafe24.com/apps/app_ad_manager.php";
	private final String URL_TIME_SAVE 	= "http://goldsmart.cafe24.com/app/nowonetofifty_rank_save.php";
	private final String APP_NAME 			= "nowonetofifty";
	private AdView adView = null;
	private SoundManager mSoundManager;
	private AudioManager mAudioManager;
	private boolean start_tp = false;
	private String message;
	// ���� ��û�� ���� App Code
	private static final String APP_CODE = "KLvJ9QYV";
	private CaulyAdView javaAdView;
	private CaulyInterstitialAd interstitialAd;
	ImageView cntImage;
	int mSec; //�и��� ���� 
	int i = 0;
    TimeThread thread; //������

    int sec,min,hour; //��,����,�ð� ǥ���ϱ� ����
    
    //������ �ڸ����� ��Ÿ���� ���� ����

    int mi1,s2,s1,m2;

    //���� �̹��� ���ҽ� id ���� ���� ������ �迭��ü �����ϱ�

    int[] img=new int[10];

    //������ ImageView ��ü 

    ImageView imgMi2,imgMi1,imgS2,imgS1,imgM2,imgM1; 
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		ani = AnimationUtils.loadAnimation(this, R.anim.alpha1);//���ϸ��̼� ����
		
        // ȭ�� �Ȳ����� �ϱ�
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		// ��������
        mAudioManager = (AudioManager)getSystemService(AUDIO_SERVICE);
        mSoundManager = new SoundManager();
        mSoundManager.initSounds(getBaseContext());
        mSoundManager.addSound(1, R.raw.click);
        mSoundManager.addSound(2, R.raw.error);
        
        // ������
        //initAdam();
        
        CaulyAdInfo adInfo = new CaulyAdInfoBuilder(APP_CODE).
				effect("RightSlide").
				build();
        
		// CaulyAdInfo�� �̿�, CaulyAdView ����.
		javaAdView = new CaulyAdView(this);
		javaAdView.setAdInfo(adInfo);
		javaAdView.setAdViewListener(this);

		RelativeLayout rootView = (RelativeLayout) findViewById(R.id.java_root_view);
		
		// ȭ�� �ϴܿ� ��� ����
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		rootView.addView(javaAdView, params);	
        
        //��ư ����
		bt[0] = (Button) findViewById(R.id.Button01);
		bt[1] = (Button) findViewById(R.id.Button02);
		bt[2] = (Button) findViewById(R.id.Button03);
		bt[3] = (Button) findViewById(R.id.Button04);
		bt[4] = (Button) findViewById(R.id.Button05);
		bt[5] = (Button) findViewById(R.id.Button06);
		bt[6] = (Button) findViewById(R.id.Button07);
		bt[7] = (Button) findViewById(R.id.Button08);
		bt[8] = (Button) findViewById(R.id.Button09);
		bt[9] = (Button) findViewById(R.id.Button10);
		bt[10] = (Button) findViewById(R.id.Button11);
		bt[11] = (Button) findViewById(R.id.Button12);
		bt[12] = (Button) findViewById(R.id.Button13);
		bt[13] = (Button) findViewById(R.id.Button14);
		bt[14] = (Button) findViewById(R.id.Button15);
		bt[15] = (Button) findViewById(R.id.Button16);
		bt[16] = (Button) findViewById(R.id.Button17);
		bt[17] = (Button) findViewById(R.id.Button18);
		bt[18] = (Button) findViewById(R.id.Button19);
		bt[19] = (Button) findViewById(R.id.Button20);
		bt[20] = (Button) findViewById(R.id.Button21);
		bt[21] = (Button) findViewById(R.id.Button22);
		bt[22] = (Button) findViewById(R.id.Button23);
		bt[23] = (Button) findViewById(R.id.Button24);
		bt[24] = (Button) findViewById(R.id.Button25);
		bt[25] = (Button) findViewById(R.id.Button26);
		bt[25].setOnClickListener(this);
		bt[26] = (Button) findViewById(R.id.Button27);
		bt[26].setOnClickListener(this);
		
		//�̹��� ���ҽ��� �迭�� �����ϱ�

        for(int i=0; i < 10 ; i++){

         img[i]=R.drawable.f00+i;

        }

        //�� �ڸ����� �����ϴ� ImageView ��ü�� ������ ������.

        imgMi1=(ImageView)findViewById(R.id.mi1);
        imgS2=(ImageView)findViewById(R.id.s2);
        imgS1=(ImageView)findViewById(R.id.s1);
        imgM2=(ImageView)findViewById(R.id.m2);
        
        //������ ��ü ����
		thread =new TimeThread(handler);
		
		//��Ʈ ��ȯ
		Typeface face = Typeface
				.createFromAsset(getAssets(), "fonts/font1.ttf");
		bt[25].setTypeface(face);
		bt[26].setTypeface(face);
		//��ư �ٹ̱�
		for (int i = 0; i < 25; i++) {
			bt[i].setOnClickListener(this);
			bt[i].setTextSize(40);
			bt[i].setTextColor(Color.WHITE);
			bt[i].setTypeface(face);
			bt[i].setEnabled(false);
			bt[i].setText("��");
		}
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
	
	private String sendTimeData(String input1, String input2, String input3) throws Exception 
    {
    	HttpPost request = makeTimeHttpPost(input1, input2, input3, URL_TIME_SAVE);
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
    
    private HttpPost makeTimeHttpPost(String input1, String input2, String input3, String url) throws Exception 
    {
		HttpPost request = new HttpPost(url);
		Vector<NameValuePair> nameValue = new Vector<NameValuePair>();
		nameValue.add(new BasicNameValuePair("input1", input1));
		nameValue.add(new BasicNameValuePair("input2", input2));
		nameValue.add(new BasicNameValuePair("input3", input3));
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
    
	// Back Key ����
	private Handler mFinishHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				mFlag = false;
			}
		}
	};
	
	private boolean mFlag = false;
	
	//������� ���� �޼����� ���� �ڵ鷯 ��ü

    Handler handler=new Handler(){

     public void handleMessage(android.os.Message msg) {

      //ī��Ʈ�� ����.

      count++;
      send_count++;

      if(count==100){

       sec++; //�ʸ� �ø���

       count=0;//ī��Ʈ�� �ʱ�ȭ �Ѵ�.

      }

      if(sec==60){

       min++; //���� �ø���

       sec=0; //�ʸ� �ʱ�ȭ �Ѵ�.

      }

      if(min>=4){

		start_tp = false;
		cnt = 1;
		bt[25].setText("����");
		thread.stopForever();
		resetTime();
		
		//��ư �ٹ̱�
		for (int i = 0; i < 25; i++) {
			//bt[i].setOnClickListener(this);
			bt[i].setEnabled(false);
			bt[i].setVisibility(View.VISIBLE);	//26���ʹ� ��ư �����
			bt[i].setText("��");
		}
			
		  new AlertDialog.Builder(GameActivity.this)
			.setTitle("Ÿ�Ӿƿ�")
			.setMessage("��� ���� �ð��� �ʰ��߽��ϴ�.")
			.setPositiveButton("Ȯ��", null)
			.show();

      }

      //8�ڸ��� ����ϱ� ���� ����ó���ϱ�

      m2=count/10; //10���� ���� ��

      s1=sec%10;

      s2=sec/10;

      mi1=min%10;

      //������ �ڸ����� ��Ÿ���� �̹��� ���� ���ҽ��� �ٲ��ش�.

      imgM2.setImageResource(img[m2]);

      imgS1.setImageResource(img[s1]);

      imgS2.setImageResource(img[s2]);

      imgMi1.setImageResource(img[mi1]);
     }

    };
    
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_VOLUME_UP: // ������
			mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
			return true;
    	case KeyEvent.KEYCODE_VOLUME_DOWN: // �����ٿ�
    		mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
			return true;
		case KeyEvent.KEYCODE_BACK:
			if (!mFlag) {
				Toast.makeText(getApplicationContext(),
						"'�ڷ�' ��ư�� �ѹ� �� �����ø� ����˴ϴ�.", Toast.LENGTH_SHORT).show();
				mFlag = true;
				mFinishHandler.sendEmptyMessageDelayed(0, 2000);
				return false;
			} else {
				finish();
			}
		default:
			break;
		}
		return super.onKeyDown(keyCode, event);
	}
    
	@Override
	public void onClick(View v) {
			
		if(cnt==99)
		{
			start_tp = false;
			cnt = 1;
			bt[25].setText("����");
			thread.stopForever();
			//��ư �ٹ̱�
			for (int i = 0; i < 25; i++) {
				//bt[i].setOnClickListener(this);
				bt[i].setEnabled(false);
				bt[i].setVisibility(View.VISIBLE);	//26���ʹ� ��ư �����
				bt[i].setText("��");
			}

			
			favoriteDialog();
			
			return;
		}
		//���� ��ư ������ �ʱⰪ ���� 
		if (v == bt[25]) 
		{	
			if (start_tp == false )
			{
				/* ó������" */
				start_tp = true;
				resetTime();
				bt[25].setText("����");
				int[] j = new int[99];
				for (mix = 0; mix < 25; mix++) {
					bt[mix].setEnabled(true);		
					int ran = random1.nextInt(25);
					if (j[ran] != ran + 1)
					{
						j[ran] = ran + 1;		//���� �ִ� �������� ��
						bt[mix].setText(Integer.toString(j[ran]));
					} 
					else if (j[ran] == ran + 1) 
					{
						mix--;					//���� ������ �ٽ� ��
					}
				}
				
				
				try{
					//������ ���� ��Ű��
					thread.start();
				}catch(Exception e){ //�ͼ��� �߻��ϸ� 
					//������ ����
					thread.stopForever();
			       //������ ��ü �ٽ� ����
			       thread=new TimeThread(handler);
			       //�����带 �����Ѵ�.
			       thread.start();
				}
				
			}
			/* �ٽ� ó������" */
			else
			{
				start_tp = false;
				cnt = 1;
				bt[25].setText("����");
				thread.stopForever();
				resetTime();
				//��ư �ٹ̱�
				for (int i = 0; i < 25; i++) {
					//bt[i].setOnClickListener(this);
					bt[i].setEnabled(false);
					bt[i].setVisibility(View.VISIBLE);	//26���ʹ� ��ư �����
					bt[i].setText("��");
				}
			}
		}
		/* ������� */
		if (v == bt[26]) {
			if (start_tp == true )
			{
				start_tp = false;
				cnt = 1;
				bt[25].setText("����");
				thread.stopForever();
				resetTime();
				//��ư �ٹ̱�
				for (int i = 0; i < 25; i++) {
					//bt[i].setOnClickListener(this);
					bt[i].setEnabled(false);
					bt[i].setVisibility(View.VISIBLE);	//26���ʹ� ��ư �����
					bt[i].setText("��");
				}
			}
			
			Intent i = new Intent(GameActivity.this, Web_Board.class);
			startActivity(i);
		} 
		else 
		{
			for (i = 0; i < 25; i++) {
				if (v == bt[i]) {
					b[i] = Integer.parseInt(bt[i].getText().toString());
					
					if (b[i] == cnt) {
						bt[i].startAnimation(ani);		//���ϸ��̼� �߰�
						if (cnt >= 26) {
							bt[i].setVisibility(View.INVISIBLE);	//26���ʹ� ��ư �����
							cnt++;
						} else {
							int num = random2.nextInt(25) + 26;
							for (int k = 0; k < 25; k++) {
								if (num == Integer.parseInt(bt[k].getText()
										.toString())) {
									num = random2.nextInt(25) + 26;		//�����ϰ� �ٽ� ��ư ����
									k = -1;
								} else {
								}
							}
							bt[i].setText(Integer.toString((num)));
							cnt++;
					}
					mSoundManager.playSound(1);
				}
				else
					mSoundManager.playSound(2);
			}
		}
		}
	}
	
	//�ð� �ʱ�ȭ �ϴ� �޼ҵ�

    public void resetTime(){

     count=0;
     
     send_count=0;

     sec=0;

     min=0;

     mi1=0;s2=0;s1=0;m2=0;

     

     //������ �ڸ����� ��Ÿ���� �̹��� ���� ���ҽ��� �ٲ��ش�.
	
	  imgM2.setImageResource(img[m2]);
	
	  imgS1.setImageResource(img[s1]);
	
	  imgS2.setImageResource(img[s2]);
	
	  imgMi1.setImageResource(img[mi1]);
	     	
	  count=0;
    }
    
    private void favoriteDialog() {
		final EditText renameText = new EditText(this);
		renameText.setHint("�̸��� �Է��ϼ���.");

		new Handler().postDelayed(new Runnable(){
		       public void run(){
		   	   InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		       imm.showSoftInput(renameText, 0);
		       }
		      },150);
		
		AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
		alt_bld.setTitle(String.valueOf(min)+"��"+String.valueOf(sec)+"."+
    					String.valueOf(m2)+"��");
		
		alt_bld.setMessage("����� ����Ͻðڽ��ϴ�.")
				.setView(renameText)
				.setCancelable(false)
				.setPositiveButton("Ȯ��", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						try {
							message = (String)sendTimeData(renameText.getText().toString(), String.valueOf(send_count), String.valueOf(min)+"��"+String.valueOf(sec)+"."+
			    					String.valueOf(m2)+"��");
				    	} catch (Exception e) {
				    		
				    	}
						
						if (message.toString().compareTo("SUCCESS") == 0)
							Toast.makeText(getApplicationContext(), "����� ����Ǿ����ϴ�.", Toast.LENGTH_LONG).show();
						else
							Toast.makeText(getApplicationContext(), "��� ���忡 �����Ͽ����ϴ�.", Toast.LENGTH_LONG).show();
						
					}
				})
				.setNegativeButton("���", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		AlertDialog alert = alt_bld.create();
		alert.show();
	}
    
//////////////////////////////
//
// Java ��� ��� ���� Listener
//
//////////////////////////////

// CaulyAdViewListener
//	���� ���ۿ� ���� ���� ó���� �ʿ� ���� ���,
//	Activity�� "implements CaulyAdViewListener" �κ� �����ϰ� ���� ����.

@Override
public void onReceiveAd(CaulyAdView adView, boolean isChargeableAd) {
// ���� ���� ���� & ����� ��� ȣ���.
// ���ŵ� ���� ���� ������ ��� isChargeableAd ���� false ��.
if (isChargeableAd == false) {
//Log.d("CaulyExample", "free banner AD received.");
}
else {
//Log.d("CaulyExample", "normal banner AD received.");
}
}

@Override
public void onFailedToReceiveAd(CaulyAdView adView, int errorCode, String errorMsg) {
// ��� ���� ���� ������ ��� ȣ���.
//Log.d("CaulyExample", "failed to receive banner AD.");
}

@Override
public void onShowLandingScreen(CaulyAdView adView) {
// ���� ��ʸ� Ŭ���Ͽ� ���� �������� ���� ��� ȣ���.
//Log.d("CaulyExample", "banner AD landing screen opened.");
}    

@Override
public void onCloseLandingScreen(CaulyAdView adView) {
// ���� ��ʸ� Ŭ���Ͽ� ���� ���� �������� ���� ��� ȣ���.
//Log.d("CaulyExample", "banner AD landing screen closed.");
}

// Activity ��ư ó��
// - Java ��� ���� ���� ��ư
public void onReloadJavaAdView(View button) {
javaAdView.reload();
}

//////////////////////////////
//
// Java ��� ���� ���� Listener
//
//////////////////////////////

// CaulyInterstitialAdListener
//	���� ������ ���, ���� ���� �� �ڵ����� ������� �����Ƿ�,
//	�ݵ�� onReceiveInterstitialAd �޼ҵ忡�� ���� ó���� �־�� �Ѵ�.

@Override
public void onReceiveInterstitialAd(CaulyInterstitialAd ad, boolean isChargeableAd) {
// ���� ���� ������ ��� ȣ���.
// ���ŵ� ���� ���� ������ ��� isChargeableAd ���� false ��.
if (isChargeableAd == false) {
//Log.d("CaulyExample", "free interstitial AD received.");
}
else {
// ���� ����
ad.show();
//Log.d("CaulyExample", "normal interstitial AD received.");
}


}	

@Override
public void onFailedToReceiveInterstitialAd(CaulyInterstitialAd ad, int errorCode, String errorMsg) {
// ���� ���� ���� ������ ��� ȣ���.
//Log.d("CaulyExample", "failed to receive interstitial AD.");
}

@Override
public void onClosedInterstitialAd(CaulyInterstitialAd ad) {
// ���� ���� ���� ��� ȣ���.
//Log.d("CaulyExample", "interstitial AD closed.");
}
}