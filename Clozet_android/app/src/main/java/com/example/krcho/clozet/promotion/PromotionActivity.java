package com.example.krcho.clozet.promotion;

import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.krcho.clozet.R;

import java.util.ArrayList;
import java.util.List;

public class PromotionActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,
        View.OnClickListener {

    private LinearLayout popup;
    private GridView gridView;
    private RelativeLayout backBtn;
    private ImageView image;
    private TextView brandText, productText, priceText;
    private Button cancelBtn, goBtn;
    private Typeface fontBold, fontThin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion);
        init();
    }

    private void init() {
        gridView = (GridView) findViewById(R.id.promotion_list);
        gridView.setAdapter(new PromotionAdapter(getApplicationContext(), R.layout.item_promotion, createItemList()));
        gridView.setOnItemClickListener(this);

        popup = (LinearLayout) findViewById(R.id.promotion_popup);
        backBtn = (RelativeLayout) findViewById(R.id.btn_back);
        brandText = (TextView) findViewById(R.id.text_brand_name);
        productText = (TextView) findViewById(R.id.text_product_name);
        priceText = (TextView) findViewById(R.id.text_price);
        cancelBtn = (Button) findViewById(R.id.btn_cancel);
        goBtn = (Button) findViewById(R.id.btn_go);

        backBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
        goBtn.setOnClickListener(this);

        fontBold = Typeface.createFromAsset(getAssets(), "NotoSansCJKkr-Bold.otf");
        fontThin = Typeface.createFromAsset(getAssets(), "NotoSansCJKkr-Thin.otf");

//        brandText.setTypeface(fontThin);
//        productText.setTypeface(fontBold);
//        priceText.setTypeface(fontBold);
    }

    private List<PromotionModel> createItemList(){
        List<PromotionModel> items = new ArrayList<>();
        for(int i=0; i<10; i++){
            PromotionModel model = new PromotionModel();
            model.setBrandName("BRAND NAME " + i);
            model.setProductName("버튼 투 포켓 티셔츠 " + i);
            model.setPrice(32800);
            model.setImage((BitmapDrawable) getResources().getDrawable(R.drawable.change_btn_click));
            items.add(model);
        }
        return items;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        popup.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;

            case R.id.btn_cancel:
                popup.setVisibility(View.GONE);
                break;

            case R.id.btn_go:

                break;
        }
    }
}
