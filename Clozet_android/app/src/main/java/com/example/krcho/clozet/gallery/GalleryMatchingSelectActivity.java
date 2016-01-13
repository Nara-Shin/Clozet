package com.example.krcho.clozet.gallery;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;

import com.example.krcho.clozet.R;

import java.util.ArrayList;
import java.util.List;

public class GalleryMatchingSelectActivity extends AppCompatActivity implements View.OnClickListener,
        AdapterView.OnItemClickListener {

    private Button cancelBtn, selectBtn;
    private GridView gridView;
    private int selectNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_matching_select);

        init();
    }

    private void init() {
        cancelBtn = (Button) findViewById(R.id.btn_cancel);
        selectBtn = (Button) findViewById(R.id.btn_select);
        gridView = (GridView) findViewById(R.id.gallery_matching_list);

        cancelBtn.setOnClickListener(this);
        selectBtn.setOnClickListener(this);
        gridView.setAdapter(new GalleryAdapter(getApplicationContext(), R.layout.item_gallery, createItemList()));
        gridView.setOnItemClickListener(this);
    }

    private List<GalleryModel> createItemList(){
        List<GalleryModel> items = new ArrayList<>();
        for(int i=0; i<10; i++){
            GalleryModel model = new GalleryModel();
            model.setBrandName("BRAND NAME " + i);
            model.setProductName("버튼 투 포켓 티셔츠 " + i);
            model.setPrice(32800);
//            model.setImage(getResources().getDrawable(R.drawable.change_btn_click));
            items.add(model);
        }
        return items;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancel:
                finish();
                break;

            case R.id.btn_select:
                startActivity(new Intent(GalleryMatchingSelectActivity.this, GalleryMatchingActivity.class));
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selectNum++;
    }
}
