package com.example.krcho.clozetmanager.popup;

import android.app.Dialog;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;
import com.example.krcho.clozetmanager.R;

public class CustomDialog extends Dialog{
//1개 신청 시
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//윈도우 매니저 : 최상단 뷰 추가.
		WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();    
		lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
		lpWindow.dimAmount = 0.8f;
		getWindow().setAttributes(lpWindow);
		//setContentView : 화면전환 -> 레이아웃.커스텀 다이얼로그로!
		setContentView(R.layout.custom_dialog);
		
		setLayout();
		setTitle(mTitle);
		setContent(mContent);
		setImage(mImageView);
		setmSizeImage(mSizeImage);
		setCount(mCount);
		setClickListener(mLeftClickListener, mRightClickListener);
	}
	
	public CustomDialog(Context context) {
		// Dialog 배경을 투명 처리 해준다.
		super(context, android.R.style.Theme_Translucent_NoTitleBar);
	}
	
	public CustomDialog(Context context , String title , 
			View.OnClickListener singleListener) {
		super(context , android.R.style.Theme_Translucent_NoTitleBar);
		this.mTitle = title;
		this.mLeftClickListener = singleListener;
	}
	
	public CustomDialog(Context context , String content ,/* ImageView image, */String count,
			View.OnClickListener leftListener ,	View.OnClickListener rightListener) {
		super(context , android.R.style.Theme_Translucent_NoTitleBar);
		//this.mTitle = title;
		//this.mImageView=image;
		this.mContent = content;
		this.mCount = count;
		this.mLeftClickListener = leftListener;
		this.mRightClickListener = rightListener;
	}

	private void setImage(ImageView image){
		mImageView.setImageResource(R.drawable.a);
	}

	private void setmSizeImage(ImageView image){
		mSizeImage.setImageResource(R.drawable.m);
	}

	private void setCount(String content){
		mCountView.setText(content);
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
	private ImageView mImageView;
	private Button mLeftButton;
	private Button mRightButton;
	private String mTitle;
	private String mContent;
	private ImageView mSizeImage;
	private TextView mCountView;
	private String mCount;

	
	
	private View.OnClickListener mLeftClickListener;
	private View.OnClickListener mRightClickListener;
	
	/*
	 * Layout
	 */
	private void setLayout(){
		mTitleView = (TextView) findViewById(R.id.tv_title);
		mImageView = (ImageView) findViewById(R.id.cloth_image);
		mSizeImage = (ImageView) findViewById(R.id.size_image);
		mContentView = (TextView) findViewById(R.id.tv_content);
		mLeftButton = (Button) findViewById(R.id.bt_left);
		mRightButton = (Button) findViewById(R.id.bt_right);
		mCountView = (TextView) findViewById(R.id.count_content);
	}
	
}









