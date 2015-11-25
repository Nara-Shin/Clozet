package com.example.krcho.clozetmanager;

import android.app.Activity;
import android.app.Dialog;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.krcho.clozetmanager.popup.CustomDialog;

/**
 * Created by ShinNara on 2015-11-16.
 */
public class CustomDialogActivity extends Activity {
    private CustomDialog mCustomDialog;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

    }

    public Dialog onClickView(View v) {
        switch (v.getId()) {
            case R.id.bt_main://1개일 때
                mCustomDialog = new CustomDialog(this);
                mCustomDialog.setContentView(R.layout.custom_dialog);
                TextView title = (TextView) mCustomDialog.findViewById(R.id.tv_title);
                ImageView image = (ImageView) mCustomDialog.findViewById(R.id.cloth_image);
                image.setImageResource(R.drawable.a);
                ImageView size = (ImageView) mCustomDialog.findViewById(R.id.size_image);
                image.setImageResource(R.drawable.m);

                break;
        }
        return mCustomDialog;
    }

}
