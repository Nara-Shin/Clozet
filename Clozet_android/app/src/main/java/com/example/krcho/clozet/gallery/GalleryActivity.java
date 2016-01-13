package com.example.krcho.clozet.gallery;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.krcho.clozet.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GalleryActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,
        View.OnClickListener {

    private RelativeLayout backBtn;
    private ImageView dateBtn, brandBtn, likeBtn;
    private GridView gridView;
    private File[] fileList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        init();
    }

    private void init() {
        gridView = (GridView) findViewById(R.id.gallery_list);
        gridView.setOnItemClickListener(this);

        backBtn = (RelativeLayout) findViewById(R.id.btn_back);
        dateBtn = (ImageView) findViewById(R.id.gallery_tab_date);
        brandBtn = (ImageView) findViewById(R.id.gallery_tab_brand);
        likeBtn = (ImageView) findViewById(R.id.gallery_tab_like);

        backBtn.setOnClickListener(this);
        dateBtn.setOnClickListener(this);
        brandBtn.setOnClickListener(this);
        likeBtn.setOnClickListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GalleryActivity.this, GalleryMatchingStartActivity.class));
            }
        });

        File root = new File("/storage/emulated/0/Pictures/Clozet/");
        fileList = root.listFiles();
        new LoadImageTask().execute(fileList);
    }

    private class LoadImageTask extends AsyncTask<File[], Void, Bitmap[]> {

        @Override
        protected Bitmap[] doInBackground(File[]... params) {
            Bitmap[] bitmap = new Bitmap[params[0].length];

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 4;

            for(int i=0; i<params[0].length; i++) {
                bitmap[i] = BitmapFactory.decodeFile(params[0][i].getPath(), options);
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap[] bitmap) {
            super.onPostExecute(bitmap);
            List<GalleryModel> items = new ArrayList<>();

            for (int i=0; i<fileList.length; i++) {
                GalleryModel model = new GalleryModel();
                model.setBrandName("BRAND NAME ");
                model.setProductName("버튼 투 포켓 티셔츠 ");
                model.setPrice(32800);
                model.setImage(bitmap[i]);
                items.add(model);
            }

            gridView.setAdapter(new GalleryAdapter(getApplicationContext(), R.layout.item_gallery, items));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(new Intent(GalleryActivity.this, GalleryDetailActivity.class));
    }
}
