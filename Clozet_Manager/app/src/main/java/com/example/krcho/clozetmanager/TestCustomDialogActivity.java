package com.example.krcho.clozetmanager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
// popup -> CustomDialog
import com.example.krcho.clozetmanager.popup.CustomDialog;

public class TestCustomDialogActivity extends Activity {

	private CustomDialog mCustomDialog;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	public void onClickView(View v){
		switch (v.getId()) {
		case R.id.bt_main:
			mCustomDialog = new CustomDialog(this, 
					"8월의 크리스마스!!",
					"영화보러가자~!!!",
					leftClickListener, 
					rightClickListener);
			mCustomDialog.show();
			break;
		}
	}

	private View.OnClickListener leftClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Toast.makeText(getApplicationContext(), "왼쪽버튼 Click!!", 
					Toast.LENGTH_SHORT).show();
		}
	};

	private View.OnClickListener rightClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Toast.makeText(getApplicationContext(), "오른쪽버튼 Click!!", 
					Toast.LENGTH_SHORT).show();
			mCustomDialog.dismiss();
		}
	};
}