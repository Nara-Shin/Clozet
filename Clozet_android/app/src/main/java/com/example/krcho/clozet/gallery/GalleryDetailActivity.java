package com.example.krcho.clozet.gallery;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.krcho.clozet.R;

public class GalleryDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout popup;
    private ImageView backBtn, deleteBtn, barcodeBtn, likeBtn, shareBtn, tshritsBtn, skirtBtn;
    private Button cancelBtn, goBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_detail);

        init();
    }

    private void init() {
        popup = (LinearLayout) findViewById(R.id.gallery_detail_popup);
        backBtn = (ImageView) findViewById(R.id.btn_back);
        deleteBtn = (ImageView) findViewById(R.id.btn_delete);
        barcodeBtn = (ImageView) findViewById(R.id.btn_barcode);
        likeBtn = (ImageView) findViewById(R.id.btn_like);
        shareBtn = (ImageView) findViewById(R.id.btn_share);
        tshritsBtn = (ImageView) findViewById(R.id.btn_tshirts);
        skirtBtn = (ImageView) findViewById(R.id.btn_skirt);
        cancelBtn = (Button) findViewById(R.id.btn_cancel);
        goBtn = (Button) findViewById(R.id.btn_go);

        backBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
        barcodeBtn.setOnClickListener(this);
        likeBtn.setOnClickListener(this);
        shareBtn.setOnClickListener(this);
        tshritsBtn.setOnClickListener(this);
        skirtBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;

            case R.id.btn_delete:

                break;

            case R.id.btn_barcode:

                break;

            case R.id.btn_like:

                break;

            case R.id.btn_share:

                break;

            case R.id.btn_tshirts:
                popup.setVisibility(View.VISIBLE);
                break;

            case R.id.btn_skirt:
                popup.setVisibility(View.VISIBLE);
                break;

            case R.id.btn_cancel:
                popup.setVisibility(View.GONE);
                break;

            case R.id.btn_go:

                break;
        }
    }
}
