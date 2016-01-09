package com.example.krcho.clozet.promotion;

import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.GridView;

import com.example.krcho.clozet.R;

import java.util.ArrayList;
import java.util.List;

public class PromotionActivity extends AppCompatActivity {

    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion);
        init();
    }

    private void init() {
        gridView = (GridView)findViewById(R.id.promotion_list);
        gridView.setAdapter(new PromotionAdapter(getApplicationContext(), R.layout.item_promotion, createItemList()));
    }

    private List<PromotionModel> createItemList(){
        List<PromotionModel> items = new ArrayList<>();
        for(int i=0; i<20; i++){
            PromotionModel model = new PromotionModel();
            model.setBrandName("BRAND NAME " + i);
            model.setProductName("버튼 투 포켓 티셔츠 " + i);
            model.setPrice(32800);
            model.setImage((BitmapDrawable) getResources().getDrawable(R.drawable.change_btn_click));
            items.add(model);
        }
        return items;
    }
}
