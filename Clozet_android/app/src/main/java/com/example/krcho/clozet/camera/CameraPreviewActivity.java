package com.example.krcho.clozet.camera;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.krcho.clozet.MyAccount;
import com.example.krcho.clozet.R;
import com.example.krcho.clozet.barcode.BarcodeDetactActivity;
import com.example.krcho.clozet.network.CommonHttpClient;
import com.example.krcho.clozet.network.NetDefine;
import com.example.krcho.clozet.request.Product;
import com.hojung.nfc.HojungNFCReadLibrary;
import com.hojung.nfc.interfaces.OnHojungNFCListener;
import com.hojung.nfc.model.NfcModel;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;

public class CameraPreviewActivity extends AppCompatActivity implements View.OnClickListener {

    private String path, samplePath, newPath, newSamplePath;
    private Bitmap bitmap, background;
    private ImageView previewImage, backgroundImage;
    private Button imageBarcode1Btn, imageBarcode2Btn, barcodeBtn, cancelBtn, saveBtn;
    private File file, sampleFile;
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
        setContentView(R.layout.activity_camera_preview);

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
        Intent intent = getIntent();
        path = intent.getExtras().getString("file");
        samplePath = intent.getExtras().getString("sampleFile");
        bitmap = BitmapFactory.decodeFile(path);
        file = new File(path);
        sampleFile = new File(samplePath);

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

        previewImage.setImageBitmap(bitmap);
        backgroundImage.setImageBitmap(blurBitmap(background));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_image_barcode:
                break;

            case R.id.btn_image_barcode2:
                break;

            case R.id.btn_barcode:
                startActivityForResult(new Intent(getApplicationContext(), BarcodeDetactActivity.class), 1000);
                break;

            case R.id.btn_cancel:
                finish();
                break;

            case R.id.btn_save:
                file.renameTo(new File(path));
                sampleFile.renameTo(new File(samplePath));
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == RESULT_OK && data.getStringExtra("barcode") != null) {
            findCategory(data.getStringExtra("barcode"));
        }
    }

    public void findCategory(final String barcode) {

        RequestParams params = new RequestParams();
        params.put("barcode", barcode);

        CommonHttpClient.post(NetDefine.FIND_CATEGORY, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("response", response.toString());

                try {
                    if (response.getString("prd_category").equals("1")) { // 상의
                        // 원본 파일명 바꾸기
                        String[] split = path.split("\\$");
                        newPath = split[0] + "$" + barcode + "$" + split[2] + "$" + split[3];

                        // 샘플 파일명 바꾸기
                        String[] sampleSplit = samplePath.split("\\$");
                        newSamplePath = sampleSplit[0] + "$" + barcode + "$" + sampleSplit[2] + "$" + sampleSplit[3];

                        searchBarcode(barcode, 1);
                    }
                    if (response.getString("prd_category").equals("2")) { // 하의
                        // 원본 파일명 바꾸기
                        String[] split = path.split("\\$");
                        newPath = split[0] + "$" + split[1] + "$" + barcode + "$" + split[3];

                        // 샘플 파일명 바꾸기
                        String[] sampleSplit = samplePath.split("\\$");
                        newSamplePath = sampleSplit[0] + "$" + sampleSplit[1] + "$" + barcode + "$" + sampleSplit[3];

                        searchBarcode(barcode, 2);
                    }

                    path = newPath;
                    samplePath = newSamplePath;
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "잘못된 데이터입니다. 다시 시도해주세요!", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
    }

    public void searchBarcode(final String barcode, final int category) {
        RequestParams params = new RequestParams();
        params.put("barcode", barcode);
        try {
            params.put("member_code", MyAccount.getInstance().getMember_code());
        } catch (Exception e) {
            params.put("member_code", PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(MyAccount.MEMBERCODE, ""));
        }

        CommonHttpClient.post(NetDefine.SEARCH_BARCODE, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("response", response.toString());
                try {
                    if (category == 1) {
                        imageBarcode1Btn.setVisibility(View.VISIBLE);
                    }
                    if (category == 2){
                        imageBarcode2Btn.setVisibility(View.VISIBLE);
                    }
                    Toast.makeText(getApplicationContext(), response.getString("product_name"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
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
