package com.example.krcho.clozet.barcode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.krcho.clozet.R;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class BarcodeDetactActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private int mCameraId = -1; // 화면내에서 백/프론트 변경시 필요
    private ZXingScannerView mScannerView;
    private boolean frontCamera = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);                // Set the scanner view as the content view
    }


    @Override
    public void onResume() {
        super.onResume();

        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera(1);          // Start camera on resume : 1: frontcamera / ? : backcamera
        mScannerView.setAutoFocus(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }


    @Override
    public void handleResult(Result rawResult) {
        setResult(RESULT_OK, new Intent().putExtra("barcode", rawResult.getText()));
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_barcode_detact, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_camera) {
            if (frontCamera) {
                frontCamera = false;
                changeCamera();
            } else {
                frontCamera = true;
                changeCamera();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public void changeCamera() {
        mScannerView.stopCamera();

        if (frontCamera) {
            mScannerView.startCamera(1);
        } else {
            mScannerView.startCamera(0);
        }
    }
}
