package com.example.krcho.clozet.gallery;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.krcho.clozet.MyAccount;
import com.example.krcho.clozet.R;
import com.example.krcho.clozet.network.CommonHttpClient;
import com.example.krcho.clozet.network.NetDefine;
import com.example.krcho.clozet.request.Product;
import com.hojung.nfc.HojungNFCReadLibrary;
import com.hojung.nfc.interfaces.OnHojungNFCListener;
import com.hojung.nfc.model.NfcModel;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.image.SmartImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;

public class GalleryDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout popup;
    private ImageView image, backBtn, deleteBtn, barcodeBtn, likeBtn, shareBtn, clothesImage;
    private SmartImageView popupImage;
    private TextView brandNameText, productNameText, priceText;
    private Button cancelBtn, goBtn, tshritsBtn, skirtBtn;
    private String filePath, sampleFilePath;
    private Bitmap bitmap;
    private boolean isBarcodeActive = true;
    private HashMap<Integer, Product> list = new HashMap<>(2);
    private int curruntPopup;

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
        setContentView(R.layout.activity_gallery_detail);

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
        image = (ImageView) findViewById(R.id.gallery_detail_image);
        popup = (LinearLayout) findViewById(R.id.gallery_detail_popup);
        backBtn = (ImageView) findViewById(R.id.btn_back);
        deleteBtn = (ImageView) findViewById(R.id.btn_delete);
        barcodeBtn = (ImageView) findViewById(R.id.btn_barcode);
        likeBtn = (ImageView) findViewById(R.id.btn_like);
        shareBtn = (ImageView) findViewById(R.id.btn_share);
        tshritsBtn = (Button) findViewById(R.id.btn_tshirts);
        skirtBtn = (Button) findViewById(R.id.btn_skirt);
        cancelBtn = (Button) findViewById(R.id.btn_cancel);
        goBtn = (Button) findViewById(R.id.btn_go);

        popupImage = (SmartImageView) findViewById(R.id.image_image);
        clothesImage = (ImageView) findViewById(R.id.image_clothes);
        brandNameText = (TextView) findViewById(R.id.text_brand_name);
        productNameText = (TextView) findViewById(R.id.text_product_name);
        priceText = (TextView) findViewById(R.id.text_price);

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

        String[] split = filePath.split("\\$");
        if (split[1].equals("") && split[2].equals("")) { // 바코드 등록이 되어있지 않은 경우
            list.put(0, new GalleryModel());
            list.put(1, new GalleryModel());
        } else {
            searchBarcode(split[1], split[2]);
        }
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
                RequestParams params = new RequestParams();
                params.put("barcode", list.get(0).getBarcode());
                try {
                    params.put("member_code", MyAccount.getInstance().getMember_code());
                } catch (Exception e) {
                    params.put("member_code", PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(MyAccount.MEMBERCODE, ""));
                }

                CommonHttpClient.post(NetDefine.SET_LIKE, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);

                        Log.d("like", response.toString());

                        try {
                            list.get(0).setLike(response.getInt("status") == 1);
                            if (list.get(0).isLike()) {
                                likeBtn.setImageDrawable(getApplicationContext().getDrawable(R.drawable.btn_like_click));
                            } else {
                                likeBtn.setImageDrawable(getApplicationContext().getDrawable(R.drawable.btn_like));
                            }
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "바코드가 등록되어있지 않습니다.", Toast.LENGTH_SHORT);
                        }

                    }
                });
                break;

            case R.id.btn_share:
                String type = "image/*";
                String mediaPath = filePath;

                createInstagramIntent(type, mediaPath);
                break;

            case R.id.btn_tshirts:
                brandNameText.setText(list.get(0).getProduct_brand());
                productNameText.setText(list.get(0).getProduct_name());
                priceText.setText(String.valueOf(list.get(0).getProduct_price()));
                popupImage.setImageUrl(list.get(0).getProduct_photo());
                clothesImage.setImageDrawable(getDrawable(R.drawable.btn_tshirts));
                popup.setVisibility(View.VISIBLE);
                tshritsBtn.setVisibility(View.GONE);
                skirtBtn.setVisibility(View.GONE);
                curruntPopup = 0;
                break;

            case R.id.btn_skirt:
                brandNameText.setText(list.get(1).getProduct_brand());
                productNameText.setText(list.get(1).getProduct_name());
                priceText.setText(String.valueOf(list.get(1).getProduct_price()));
                popupImage.setImageUrl(list.get(1).getProduct_photo());
                clothesImage.setImageDrawable(getDrawable(R.drawable.btn_skirt));
                popup.setVisibility(View.VISIBLE);
                tshritsBtn.setVisibility(View.GONE);
                skirtBtn.setVisibility(View.GONE);
                curruntPopup = 1;
                break;

            case R.id.btn_cancel:
                popup.setVisibility(View.GONE);
                tshritsBtn.setVisibility(View.VISIBLE);
                skirtBtn.setVisibility(View.VISIBLE);
                break;

            case R.id.btn_go:
                Intent i = new Intent(Intent.ACTION_VIEW);
                Uri u = Uri.parse(list.get(curruntPopup).getProduct_url());
                i.setData(u);
                startActivity(i);
                break;
        }
    }

    public void searchBarcode(final String barcode1, final String barcode2) {

        if (!barcode1.equals("")) { // 상의 바코드가 있을 경우
            tshritsBtn.setEnabled(true);
            RequestParams params = new RequestParams();
            params.put("barcode", barcode1);
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
                        list.put(0, new GalleryModel(response, barcode1));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        if (!barcode2.equals("")) { // 하의 바코드가 있을 경우
            skirtBtn.setEnabled(true);
            RequestParams params = new RequestParams();
            params.put("barcode", barcode2);
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
                        list.put(1, new GalleryModel(response, barcode2));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private void createInstagramIntent(String type, String mediaPath){

        // Create the new Intent using the 'Send' action.
        Intent share = new Intent(Intent.ACTION_SEND);

        // Set the MIME type
        share.setType(type);

        // Create the URI from the media
        File media = new File(mediaPath);
        Uri uri = Uri.fromFile(media);

        // Add the URI to the Intent.
        share.putExtra(Intent.EXTRA_STREAM, uri);

        // Broadcast the Intent.
        startActivity(Intent.createChooser(share, "Share to"));
    }
}
