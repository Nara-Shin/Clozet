package com.example.krcho.clozet.camera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.krcho.clozet.R;

public class CameraPreviewActivity extends AppCompatActivity implements View.OnClickListener {

    private String path;
    private Bitmap bitmap, background;
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

        Log.d("path", path);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 6;
        background = BitmapFactory.decodeFile(path, options);

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

        if (CameraGuideActivity.mCameraFacing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            previewImage.setImageBitmap(imgRotate(bitmap, -90));
            backgroundImage.setImageBitmap(imgRotate(blurBitmap(background), -90));
        } else {
            previewImage.setImageBitmap(imgRotate(bitmap, 90));
            backgroundImage.setImageBitmap(imgRotate(blurBitmap(background), 90));
        }
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
                finish();
                break;

            case R.id.btn_save:
                break;
        }
    }

    private Bitmap imgRotate(Bitmap bmp, int rotate){
        int width = bmp.getWidth();
        int height = bmp.getHeight();

        Matrix matrix = new Matrix();
        matrix.postRotate(rotate);

        Bitmap resizedBitmap = Bitmap.createBitmap(bmp, 0, 0, width, height, matrix, true);
        bmp.recycle();

        return resizedBitmap;
    }

    public Bitmap blurBitmap(Bitmap bitmap){

        //Let's create an empty bitmap with the same size of the bitmap we want to blur
        Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        //Instantiate a new Renderscript
        RenderScript rs = RenderScript.create(getApplicationContext());

        //Create an Intrinsic Blur Script using the Renderscript
        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));

        //Create the Allocations (in/out) with the Renderscript and the in/out bitmaps
        Allocation allIn = Allocation.createFromBitmap(rs, bitmap);
        Allocation allOut = Allocation.createFromBitmap(rs, outBitmap);

        //Set the radius of the blur
        blurScript.setRadius(25.f);

        //Perform the Renderscript
        blurScript.setInput(allIn);
        blurScript.forEach(allOut);

        //Copy the final bitmap created by the out Allocation to the outBitmap
        allOut.copyTo(outBitmap);

        //After finishing everything, we destroy the Renderscript.
        rs.destroy();

        return outBitmap;
    }
}
