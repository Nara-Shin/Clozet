package com.example.hojungnfcreaderexample;

import com.hojung.nfc.HojungNFCReadLibrary;
import com.hojung.nfc.interfaces.OnHojungNFCListener;
import com.hojung.nfc.model.NfcModel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	
	/**
	 * onResume onPause, onNewIntent 는 꼭 구현 해주셔야합니다.
	 * lib 폴더 안에  HojungNFCLibrary_v01.jar 파일 넣고 밑에와 같이 코딩하시면 됩니다.
	 * write app 으로 먼저 Write 하시고 HojungNFCLibrary_v01 라이브러리 리스너를 통하여 스트링 값을 read 하시면 됩니다.
	*/
	final String TAG="MainActivity";
	HojungNFCReadLibrary hojungNFCReadLibrary;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		hojungNFCReadLibrary=new HojungNFCReadLibrary(getIntent(), MainActivity.this, new OnHojungNFCListener() {
			
			@Override
			public void onReceiveMessage(NfcModel[] models) {
				// TODO Auto-generated method stub
				//0번째 모델 사용
				// model- 타입과 페이로드  아무거나 사용 가능(Write 앱에서 쓰기 해줘야 인식 가능.)
				Toast.makeText(MainActivity.this, "type : "+models[0].getTypeStr()+" , "+"payload : "+models[0].getPayloadStr(), Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onError(String arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	@Override
	public void onResume() {
		super.onResume();
		//앱이 다시 실행할때 ndef adapter 실행
		Log.d(TAG,"onResume");

		Log.d(TAG,"intent : "+getIntent().getAction());
		Intent intent=getIntent();
		hojungNFCReadLibrary.onResume(intent);
		
	}

	@Override
	public void onPause() {
		super.onPause();
		Log.d(TAG,"onPause");
		//앱이 정지 할떄  ndef 정지
		hojungNFCReadLibrary.onPause();
	}

	@Override
	public void onNewIntent(Intent intent) {
		Log.d(TAG,"onNewIntent");
		//인텐트를 받아오는 부분 
		hojungNFCReadLibrary.onNewIntent(intent);
	}
	
	
}
