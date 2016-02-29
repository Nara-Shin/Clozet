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
	 * onResume onPause, onNewIntent �� �� ���� ���ּž��մϴ�.
	 * lib ���� �ȿ�  HojungNFCLibrary_v01.jar ���� �ְ� �ؿ��� ���� �ڵ��Ͻø� �˴ϴ�.
	 * write app ���� ���� Write �Ͻð� HojungNFCLibrary_v01 ���̺귯�� �����ʸ� ���Ͽ� ��Ʈ�� ���� read �Ͻø� �˴ϴ�.
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
				//0��° �� ���
				// model- Ÿ�԰� ���̷ε�  �ƹ��ų� ��� ����(Write �ۿ��� ���� ����� �ν� ����.)
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
		//���� �ٽ� �����Ҷ� ndef adapter ����
		Log.d(TAG,"onResume");

		Log.d(TAG,"intent : "+getIntent().getAction());
		Intent intent=getIntent();
		hojungNFCReadLibrary.onResume(intent);
		
	}

	@Override
	public void onPause() {
		super.onPause();
		Log.d(TAG,"onPause");
		//���� ���� �ҋ�  ndef ����
		hojungNFCReadLibrary.onPause();
	}

	@Override
	public void onNewIntent(Intent intent) {
		Log.d(TAG,"onNewIntent");
		//����Ʈ�� �޾ƿ��� �κ� 
		hojungNFCReadLibrary.onNewIntent(intent);
	}
	
	
}
