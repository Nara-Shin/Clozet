package com.example.krcho.clozet.gallery;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.krcho.clozet.R;

public class GalleryMatchingStartActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout cancelBtn;
    private ImageView startBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_matching_start);

        init();
    }

    private void init() {
        cancelBtn = (RelativeLayout) findViewById(R.id.btn_cancel);
        startBtn = (ImageView) findViewById(R.id.btn_start);

        cancelBtn.setOnClickListener(this);
        startBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancel:
                finish();
                break;

            case R.id.btn_start:
                startActivity(new Intent(GalleryMatchingStartActivity.this, GalleryMatchingSelectActivity.class));
                finish();
                break;
        }
    }
}
