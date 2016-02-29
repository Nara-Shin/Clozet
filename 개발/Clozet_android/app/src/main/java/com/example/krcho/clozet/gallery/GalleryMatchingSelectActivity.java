package com.example.krcho.clozet.gallery;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import com.example.krcho.clozet.R;
import com.hojung.nfc.HojungNFCReadLibrary;
import com.hojung.nfc.interfaces.OnHojungNFCListener;
import com.hojung.nfc.model.NfcModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GalleryMatchingSelectActivity extends AppCompatActivity implements View.OnClickListener {

    private Button cancelBtn, selectBtn;
    private RecyclerView recyclerView;
    private File[] fileList;
    public List<GalleryModel> items;

    //NFC
    HojungNFCReadLibrary hojungNFCReadLibrary;
    Context mContext;

    private void initNFC() {
        try {
            Log.d("NFC", "intent : " + getIntent().getAction());
            Intent intent = getIntent();
            hojungNFCReadLibrary.onResume(intent);
        } catch (Exception e) {

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        initNFC();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("NFC", "onPause");
        hojungNFCReadLibrary.onPause();

    }


    @Override
    public void onNewIntent(Intent intent) {
        Log.d("NFC", "onNewIntent");
        hojungNFCReadLibrary.onNewIntent(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_matching_select);

        init();
        mContext = this;
        //NFC is use?
        android.nfc.NfcAdapter mNfcAdapter = android.nfc.NfcAdapter.getDefaultAdapter(mContext);
        hojungNFCReadLibrary = new HojungNFCReadLibrary(getIntent(), mContext, new OnHojungNFCListener() {

            @Override
            public void onReceiveMessage(NfcModel[] models) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onError(String arg0) {
                // TODO Auto-generated method stub

            }
        });
    }

    private void init() {
        cancelBtn = (Button) findViewById(R.id.btn_cancel);
        selectBtn = (Button) findViewById(R.id.btn_select);
        recyclerView = (RecyclerView) findViewById(R.id.gallery_matching_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        cancelBtn.setOnClickListener(this);
        selectBtn.setOnClickListener(this);

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Clozet");
        File sampleStorageDir = new File(mediaStorageDir.getPath() + "/Sample");
        fileList = sampleStorageDir.listFiles();
        new LoadImageTask().execute(fileList);
    }

    private class LoadImageTask extends AsyncTask<File[], Void, Bitmap[]> {

        @Override
        protected Bitmap[] doInBackground(File[]... params) {
            Bitmap[] bitmap = new Bitmap[params[0].length];

            // 불러올때마다 샘플링을 해서 속도가 느린거임
//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inSampleSize = 4;

            for(int i=0; i<params[0].length; i++) {
//                bitmap[i] = BitmapFactory.decodeFile(params[0][i].getPath(), options);
                bitmap[i] = BitmapFactory.decodeFile(params[0][i].getPath());
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap[] bitmap) {
            super.onPostExecute(bitmap);
            items = new ArrayList<>();

            for (int i=0; i<fileList.length; i++) {
                items.add(new GalleryModel(bitmap[i], fileList[i].getName()));
            }

            recyclerView.setAdapter(new GalleryAdapter(getApplicationContext(), R.layout.item_gallery, items, 1));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancel:
                finish();
                break;

            case R.id.btn_select:
                break;
        }
    }
}
