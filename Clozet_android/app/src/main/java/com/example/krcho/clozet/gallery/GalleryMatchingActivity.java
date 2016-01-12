package com.example.krcho.clozet.gallery;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.krcho.clozet.R;

public class GalleryMatchingActivity extends AppCompatActivity {

    private UpPagerAdapter upPagerAdapter;
    private DownPagerAdapter downPagerAdapter;
    private ViewPager upPager, downPager;
    private Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_matching);

        init();
    }

    private void init() {
        saveBtn = (Button) findViewById(R.id.btn_save);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        upPager = (ViewPager) findViewById(R.id.pager_up);
        downPager = (ViewPager) findViewById(R.id.pager_down);

        upPagerAdapter = new UpPagerAdapter(getSupportFragmentManager());
        upPager.setAdapter(upPagerAdapter);
        downPagerAdapter= new DownPagerAdapter(getSupportFragmentManager());
        downPager.setAdapter(downPagerAdapter);
    }

    private class UpPagerAdapter extends FragmentStatePagerAdapter {
        public UpPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public android.support.v4.app.Fragment getItem(int num) {

//            RecentAlbumFragment.albums = recentAlbumsList;

//            switch (num) {
//                case 0:
//                    return new GalleryMatciongFragment();
//                case 1:
//                    return new RecentAlbumFragment();
//            }

            return new GalleryMatciongFragment();
        }

        @Override
        public int getCount() {
            return 3;
        }

        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

    }

    private class DownPagerAdapter extends FragmentStatePagerAdapter {
        public DownPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public android.support.v4.app.Fragment getItem(int num) {

//            RecentAlbumFragment.albums = recentAlbumsList;

//            switch (num) {
//                case 0:
//                    return new GalleryMatciongFragment();
//                case 1:
//                    return new RecentAlbumFragment();
//            }

            return new GalleryMatciongFragment();
        }

        @Override
        public int getCount() {
            return 3;
        }

        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

    }
}
