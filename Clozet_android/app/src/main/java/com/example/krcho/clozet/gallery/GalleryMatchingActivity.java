package com.example.krcho.clozet.gallery;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.krcho.clozet.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GalleryMatchingActivity extends AppCompatActivity {

    private UpPagerAdapter upPagerAdapter;
    private DownPagerAdapter downPagerAdapter;
    private ViewPager upPager, downPager;
    private Button saveBtn;
    private String filePath;
    private Bitmap bitmap;
    private File[] fileList;
    private LoadImageTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_gallery_matching);

        init();
    }

    private void init() {
        saveBtn = (Button) findViewById(R.id.btn_save);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap upBitmap = upPagerAdapter.getBitmap(task.upPosition);
                Bitmap downBitmap = downPagerAdapter.getBitmap(task.downPosition);
                Bitmap saveBitmap = Bitmap.createScaledBitmap(upBitmap, upBitmap.getWidth(), upBitmap.getHeight()*2, true);
                Paint p = new Paint();
                p.setDither(true);
                p.setFlags(Paint.ANTI_ALIAS_FLAG);
                Canvas c = new Canvas(saveBitmap);
                c.drawBitmap(upBitmap, 0, 0, p);
                c.drawBitmap(downBitmap, 0, upBitmap.getHeight(), p);
                upBitmap.recycle();
                downBitmap.recycle();

                String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                File saveFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Clozet/Clozet_" + timestamp + "$$$.jpg");
                File saveSampleFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Clozet/Sample/Clozet_" + timestamp + "$$$.jpg");
                try {
                    FileOutputStream fos = new FileOutputStream(saveFile);
                    saveBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.close();

                    FileOutputStream fosSample = new FileOutputStream(saveSampleFile);
                    saveBitmap.compress(Bitmap.CompressFormat.JPEG, 25, fosSample);
                    fosSample.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                finish();
            }
        });

        upPager = (ViewPager) findViewById(R.id.pager_up);
        downPager = (ViewPager) findViewById(R.id.pager_down);

        Intent intent = getIntent();
        filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Clozet/" + intent.getExtras().getString("fileName");
//        bitmap = BitmapFactory.decodeFile(filePath);

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Clozet");
        fileList = mediaStorageDir.listFiles();
        task = new LoadImageTask();
        task.execute(fileList);
    }

    private class LoadImageTask extends AsyncTask<File[], Void, Bitmap[]> {

        public int upPosition, downPosition;

        @Override
        protected Bitmap[] doInBackground(File[]... params) {
            Bitmap[] bitmap = new Bitmap[params[0].length - 1];

            for(int i=1; i<params[0].length; i++) {
                bitmap[i-1] = BitmapFactory.decodeFile(params[0][i].getPath());
            }

//            Log.d("test", bitmap[0].toString());

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap[] bitmap) {
            super.onPostExecute(bitmap);

            Bitmap[] upBitmap = new Bitmap[bitmap.length];
            Bitmap[] downBitmap = new Bitmap[bitmap.length];

            for(int i=0; i<bitmap.length; i++) {
                upBitmap[i] = Bitmap.createBitmap(bitmap[i], 0, 0, bitmap[i].getWidth(), bitmap[i].getHeight()/2);
                downBitmap[i] = Bitmap.createBitmap(bitmap[i], 0, bitmap[i].getHeight() / 2, bitmap[i].getWidth(), bitmap[i].getHeight() / 2);
            }

            upPagerAdapter = new UpPagerAdapter(getSupportFragmentManager(), upBitmap);
            upPager.setAdapter(upPagerAdapter);
            upPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    upPosition = position;
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            downPagerAdapter= new DownPagerAdapter(getSupportFragmentManager(), downBitmap);
            downPager.setAdapter(downPagerAdapter);
            downPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    downPosition = position;
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }
    }

    private class UpPagerAdapter extends FragmentStatePagerAdapter {

        Bitmap[] bitmap;
        GalleryMatchingFragment[] fragments;
        int num;

        public UpPagerAdapter(FragmentManager fm, Bitmap[] bitmap) {
            super(fm);
            this.bitmap = bitmap;

            fragments = new GalleryMatchingFragment[bitmap.length];

            for (int i=0; i<bitmap.length; i++) {
                fragments[i] = new GalleryMatchingFragment(bitmap[i]);
            }
        }

        public android.support.v4.app.Fragment getItem(int num) {
            this.num = num;
            Log.d("num1", String.valueOf(num));
            return new GalleryMatchingFragment(bitmap[num]);
        }

        @Override
        public int getCount() {
            return bitmap.length;
        }

        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        public Bitmap getBitmap(int num) {
            return this.bitmap[num];
        }

    }

    private class DownPagerAdapter extends FragmentStatePagerAdapter {

        Bitmap[] bitmap;
        GalleryMatchingFragment[] fragments;
        int num;

        public DownPagerAdapter(FragmentManager fm, Bitmap[] bitmap) {
            super(fm);
            this.bitmap = bitmap;

            fragments = new GalleryMatchingFragment[bitmap.length];

            for (int i=0; i<bitmap.length; i++) {
                fragments[i] = new GalleryMatchingFragment(bitmap[i]);
            }
        }

        public android.support.v4.app.Fragment getItem(int num) {
            this.num = num;
            Log.d("num2", String.valueOf(num));
            return new GalleryMatchingFragment(bitmap[num]);
        }



        @Override
        public int getCount() {
            return bitmap.length;
        }

        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        public Bitmap getBitmap(int num) {
            return this.bitmap[num];
        }

    }
}
