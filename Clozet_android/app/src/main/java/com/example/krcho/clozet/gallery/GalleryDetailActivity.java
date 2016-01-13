package com.example.krcho.clozet.gallery;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.krcho.clozet.R;

import java.io.File;

public class GalleryDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout popup;
    private ImageView image, backBtn, deleteBtn, barcodeBtn, likeBtn, shareBtn, tshritsBtn, skirtBtn;
    private Button cancelBtn, goBtn;
    private String filePath, sampleFilePath;
    private Bitmap bitmap;
    private boolean isBarcodeActive = true;
    private boolean isLike = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_detail);

        init();
    }

    private void init() {
        image = (ImageView) findViewById(R.id.gallery_detail_image);
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
        cancelBtn.setOnClickListener(this);
        goBtn.setOnClickListener(this);

        Intent intent = getIntent();
        filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Clozet/" + intent.getExtras().getString("fileName");
        sampleFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Clozet/Sample/" + intent.getExtras().getString("fileName");
        bitmap = BitmapFactory.decodeFile(filePath);
        image.setImageBitmap(bitmap);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;

            case R.id.btn_delete:
                File bigFile = new File(filePath);
                File sampleFile = new File(sampleFilePath);
                bigFile.delete();
                sampleFile.delete();
                finish();
                break;

            case R.id.btn_barcode:
                if (isBarcodeActive) {
                    isBarcodeActive = false;
                    barcodeBtn.setImageDrawable(getDrawable(R.drawable.btn_gallery_barcode_off));
                    tshritsBtn.setVisibility(View.GONE);
                    skirtBtn.setVisibility(View.GONE);
                } else {
                    isBarcodeActive = true;
                    barcodeBtn.setImageDrawable(getDrawable(R.drawable.btn_gallery_barcode_on));
                    tshritsBtn.setVisibility(View.VISIBLE);
                    skirtBtn.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.btn_like:
                if (isLike) {
                    isLike = false;
                    likeBtn.setImageDrawable(getDrawable(R.drawable.btn_gallery_like));
                } else {
                    isLike = true;
                    likeBtn.setImageDrawable(getDrawable(R.drawable.btn_gallery_like_click));
                }
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
