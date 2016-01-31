package com.example.krcho.clozet.promotion;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class PromotionActivity extends AppCompatActivity {

    ArrayList<Product> list = new ArrayList<>();

    private RecyclerView recyclerView;
    private PromotionRecyclerAdapter adapter;
    private RelativeLayout backBtn;

    //    private Typeface fontBold, fontThin;
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
        setContentView(R.layout.activity_promotion);
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
//        gridView = (GridView) findViewById(R.id.promotion_list);
//        gridView.setAdapter(new PromotionAdapter(getApplicationContext(), R.layout.item_promotion, createItemList()));
//        gridView.setOnItemClickListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.promotion_list);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        adapter = new PromotionRecyclerAdapter(list, new PromotionDelegate() {
            @Override
            public void onClickItem(Product data) {
                startActivity(new Intent(getApplicationContext(), PromotionDialog.class).putExtra("data", data));
            }
        });
        recyclerView.setAdapter(adapter);

        backBtn = (RelativeLayout) findViewById(R.id.btn_back);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        fontBold = Typeface.createFromAsset(getAssets(), "NotoSansCJKkr-Bold.otf");
//        fontThin = Typeface.createFromAsset(getAssets(), "NotoSansCJKkr-Thin.otf");

//        brandText.setTypeface(fontThin);
//        productText.setTypeface(fontBold);
//        priceText.setTypeface(fontBold);

        RequestParams params = new RequestParams();
        try {
            params.put("member_code", MyAccount.getInstance().getMember_code());
        } catch (Exception e) {
            params.put("member_code", PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(MyAccount.MEMBERCODE, ""));
        }

        CommonHttpClient.post(NetDefine.PROMOTION, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray array = response.getJSONArray("products");

                    for (int i = 0; i < array.length(); i++) {
                        Product temp = new Product();
                        temp.setPromotionData(array.getJSONObject(i));
                        list.add(temp);
                    }

                    adapter.setList(list);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
