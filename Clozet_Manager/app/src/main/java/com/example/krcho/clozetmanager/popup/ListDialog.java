package com.example.krcho.clozetmanager.popup;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.krcho.clozetmanager.R;

public class ListDialog extends Dialog{
//2개 이상 신청 시
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//윈도우 매니저 : 최상단 뷰 추가.
		WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
		lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
		lpWindow.dimAmount = 0.8f;
		getWindow().setAttributes(lpWindow);
		//setContentView : 화면전환 -> 레이아웃.리스트 다이얼로그로!
		setContentView(R.layout.recycler_dialog);

		setLayout();
		setTitle(mTitle);
		setContent(mContent);
		setClickListener(mLeftClickListener , mRightClickListener);
	}

	public ListDialog(Context context) {
		// Dialog 배경을 투명 처리 해준다.
		super(context , android.R.style.Theme_Translucent_NoTitleBar);
	}

	public ListDialog(Context context, String title,
					  View.OnClickListener singleListener) {
		super(context , android.R.style.Theme_Translucent_NoTitleBar);
		this.mTitle = title;
		this.mLeftClickListener = singleListener;
	}

	public ListDialog(Context context, String title, String content,
					  View.OnClickListener leftListener, View.OnClickListener rightListener) {
		super(context , android.R.style.Theme_Translucent_NoTitleBar);
		this.mTitle = title;
		this.mContent = content;
		this.mLeftClickListener = leftListener;
		this.mRightClickListener = rightListener;
	}
	
	private void setTitle(String title){
		mTitleView.setText(title);
	}
	
	private void setContent(String content){
		mContentView.setText(content);
	}
	
	private void setClickListener(View.OnClickListener left , View.OnClickListener right){
		if(left!=null && right!=null){
			mLeftButton.setOnClickListener(left);
			mRightButton.setOnClickListener(right);
		}else if(left!=null && right==null){
			mLeftButton.setOnClickListener(left);
		}else {
			
		}
	}

	/*
	 * Layout
	 */
	private TextView mTitleView;
	private TextView mContentView;
	private Button mLeftButton;
	private Button mRightButton;
	private String mTitle;
	private String mContent;
	

	private View.OnClickListener mLeftClickListener;
	private View.OnClickListener mRightClickListener;

	/*
	 * Layout
	 */
	private void setLayout(){
		mTitleView = (TextView) findViewById(R.id.tv_title);
		mContentView = (TextView) findViewById(R.id.tv_content);
		mLeftButton = (Button) findViewById(R.id.bt_left);
		mRightButton = (Button) findViewById(R.id.bt_right);
	}
	
}








