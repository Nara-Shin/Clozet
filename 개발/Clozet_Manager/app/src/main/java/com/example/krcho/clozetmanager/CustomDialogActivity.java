package com.example.krcho.clozetmanager;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.krcho.clozetmanager.popup.CustomDialog;

/**
 * Created by ShinNara on 2015-11-16.
 */
public class CustomDialogActivity extends AppCompatActivity {
    private CustomDialog mCustomDialog;
    private Context mContext;
    private Button one, two;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mContext = this;
        //mCustomDialog = new CustomDialog(this);
        //mCustomDialog.setContentView(R.layout.custom_dialog);
        one = (Button) this.findViewById(R.id.bt_main);
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ss","s");
                AlbumModifyDialogFragment dialogFragment = AlbumModifyDialogFragment.newInstance();
                dialogFragment.show(getSupportFragmentManager(), "AlbumCreateDialogFragment");
            }
        });
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
                View ll = findViewById(R.id.color_image);
                ll.setBackgroundColor(Color.RED);
                break;
        }
        return mCustomDialog;
    }

}
