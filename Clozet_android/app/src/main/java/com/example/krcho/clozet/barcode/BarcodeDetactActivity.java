package com.example.krcho.clozet.barcode;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class BarcodeDetactActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private int mCameraId = -1; // 화면내에서 백/프론트 변경시 필요
    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_barcode_detact);

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
        Toast.makeText(getApplicationContext(), rawResult.getText(), Toast.LENGTH_LONG).show(); // 화면전환 -->
    }
}
