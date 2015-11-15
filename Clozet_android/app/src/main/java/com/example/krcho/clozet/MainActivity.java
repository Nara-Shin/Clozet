package com.example.krcho.clozet;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.krcho.clozet.barcode.BarcodeDetactActivity;
import com.example.krcho.clozet.camera.FrontCameraActivity;
import com.example.krcho.clozet.camera.FrontCameraActivityLolliPop;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TextView change, shot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setViewPager();

        change = (TextView) findViewById(R.id.txt_change);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), BarcodeDetactActivity.class));
            }
        });
        shot = (TextView) findViewById(R.id.txt_shot);
        shot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
                    startActivity(new Intent(getApplicationContext(), FrontCameraActivity.class));
                }else {
                    startActivity(new Intent(getApplicationContext(), FrontCameraActivityLolliPop.class));
                }

            }
        });

    }

    public void setViewPager(){

    }

    private void initNFC() {
//        HojungNFCCheckingLibrary nfc = new HojungNFCCheckingLibrary(this);
//        NfcModel model = nfc.getNFCData(getIntent());
//        model.getPayloadStr(); // tag에 저장된 string
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        initNFC();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
