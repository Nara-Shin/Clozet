package com.example.krcho.clozet.gallery;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.krcho.clozet.MyAccount;
import com.example.krcho.clozet.R;
import com.example.krcho.clozet.network.CommonHttpClient;
import com.example.krcho.clozet.network.NetDefine;
import com.example.krcho.clozet.request.Product;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class GalleryActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,
        View.OnClickListener {

    private RelativeLayout backBtn;
    private ImageView dateBtn, brandBtn, likeBtn;
    private GridView gridView;
    private ProgressBar progressBar;
    private File[] fileList;
    public static List<GalleryModel> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        init();
    }

    @Override
    protected void onResume() {
        super.onResume();

        progressBar.setVisibility(View.VISIBLE);

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Clozet");
        File sampleStorageDir = new File(mediaStorageDir.getPath() + "/Sample");
        fileList = sampleStorageDir.listFiles();
        new LoadImageTask().execute(fileList);
    }

    private void init() {
        gridView = (GridView) findViewById(R.id.gallery_list);
        gridView.setOnItemClickListener(this);

        backBtn = (RelativeLayout) findViewById(R.id.btn_back);
        dateBtn = (ImageView) findViewById(R.id.gallery_tab_date);
        brandBtn = (ImageView) findViewById(R.id.gallery_tab_brand);
        likeBtn = (ImageView) findViewById(R.id.gallery_tab_like);
        progressBar = (ProgressBar) findViewById(R.id.gallery_progress);

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
                String[] split = fileList[i].getPath().split("\\$");
                if (split[1].equals("") && split[2].equals("")) { // 바코드 등록이 되어있지 않은 경우
                    items.add(new GalleryModel(bitmap[i], fileList[i].getName()));
                } else {
                    searchBarcode(split[1], bitmap[i], fileList[i].getName());
                }
            }

            gridView.setAdapter(new GalleryAdapter(getApplicationContext(), R.layout.item_gallery, items));
            progressBar.setVisibility(View.GONE);
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
        Intent intent = new Intent(GalleryActivity.this, GalleryDetailActivity.class);
        intent.putExtra("fileName", items.get(position).getFileName());
        intent.putExtra("barcode", items.get(position).getBarcode());
        startActivity(intent);
    }

    public void searchBarcode(final String barcode, final Bitmap bitmap, final String fileName) {
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
                    items.add(new GalleryModel(response, barcode, bitmap, fileName));
                    gridView.setAdapter(new GalleryAdapter(getApplicationContext(), R.layout.item_gallery, items));
                    progressBar.setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
