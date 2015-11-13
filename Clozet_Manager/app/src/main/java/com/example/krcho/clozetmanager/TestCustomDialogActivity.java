package com.example.krcho.clozetmanager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;
// popup -> CustomDialog
import com.example.krcho.clozetmanager.popup.CustomDialog;

public class TestCustomDialogActivity extends Activity {
//1개 신청 시
	private CustomDialog mCustomDialog;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//팝업다이얼로그로 만들기 (아래 주석)
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		//버튼 2개 있는 메인화면
	}

	public void onClickView(View v){
		switch (v.getId()) {
			case R.id.bt_main://1개일 때
				mCustomDialog = new CustomDialog(this,
						"",
						"해당 상품이 요청되었습니다.",
						leftClickListener,
						rightClickListener);
				mCustomDialog.setTitle("X번 룸");
				mCustomDialog.show();
				break;

			case R.id.bt_tow://2개 이상일 때
				mCustomDialog = new CustomDialog(this,
						"",
						"상품이 요청되었습니다.",
						leftClickListener,
						rightClickListener);
				mCustomDialog.setTitle("XX번 룸");
				mCustomDialog.show();
				break;

		}
	}

	private View.OnClickListener leftClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			//수락버튼 누른 경우
			Toast.makeText(getApplicationContext(), "수락!!",
					Toast.LENGTH_SHORT).show();
		}
	};

	private View.OnClickListener rightClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			//거절버튼 누른 경우
			Toast.makeText(getApplicationContext(), "거절!!",
					Toast.LENGTH_SHORT).show();
			mCustomDialog.dismiss();
		}
	};
}