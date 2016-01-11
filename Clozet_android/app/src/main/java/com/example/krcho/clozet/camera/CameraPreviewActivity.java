package com.example.krcho.clozet.camera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.krcho.clozet.R;

public class CameraPreviewActivity extends AppCompatActivity implements View.OnClickListener {

    private String path;
    private Bitmap bitmap;
    private ImageView previewImage, backgroundImage;
    private Button imageBarcode1Btn, imageBarcode2Btn, barcodeBtn, cancelBtn, saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_preview);

        init();
    }

    private void init() {
        Intent intent = getIntent();
        path = intent.getExtras().getString("file");
        bitmap = BitmapFactory.decodeFile(path);

        previewImage = (ImageView) findViewById(R.id.camera_preview_image);
        backgroundImage = (ImageView) findViewById(R.id.camera_preview_background);
        imageBarcode1Btn = (Button) findViewById(R.id.btn_image_barcode);
        imageBarcode2Btn = (Button) findViewById(R.id.btn_image_barcode2);
        barcodeBtn = (Button) findViewById(R.id.btn_barcode);
        cancelBtn = (Button) findViewById(R.id.btn_cancel);
        saveBtn = (Button) findViewById(R.id.btn_save);

        imageBarcode1Btn.setOnClickListener(this);
        imageBarcode2Btn.setOnClickListener(this);
        barcodeBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
        saveBtn.setOnClickListener(this);

        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        previewImage.setImageBitmap(bitmap);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_image_barcode:
                break;

            case R.id.btn_image_barcode2:
                break;

            case R.id.btn_barcode:
                break;

            case R.id.btn_cancel:
                break;

            case R.id.btn_save:
                break;
        }
    }
}
