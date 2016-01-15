package com.example.krcho.clozet.promotion;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.krcho.clozet.R;
import com.example.krcho.clozet.network.CommonHttpClient;
import com.example.krcho.clozet.network.NetDefine;
import com.example.krcho.clozet.request.Product;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion);
        init();
    }

    private void init() {
//        gridView = (GridView) findViewById(R.id.promotion_list);
//        gridView.setAdapter(new PromotionAdapter(getApplicationContext(), R.layout.item_promotion, createItemList()));
//        gridView.setOnItemClickListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.promotion_list);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        adapter = new PromotionRecyclerAdapter(list, new PromotionDelegate(){
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


        CommonHttpClient.post(NetDefine.PROMOTION, new RequestParams(), new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray array = response.getJSONArray("products");

                    for (int i = 0 ; i < array.length(); i++){
                        Product temp = new Product();
                        temp.setPromotionData(array.getJSONObject(i));
                        list.add(temp);
                    }

                    adapter.setList(list);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }
}
