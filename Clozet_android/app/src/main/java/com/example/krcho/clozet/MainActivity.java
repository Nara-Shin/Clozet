package com.example.krcho.clozet;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.krcho.clozet.camera.FrontCameraActivity;
import com.example.krcho.clozet.camera.FrontCameraActivityLolliPop;
import com.hojung.nfc.HojungNFCCheckingLibrary;
import com.hojung.nfc.model.NfcModel;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button front = (Button) findViewById(R.id.front_camera);
        front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
                startActivity(new Intent(getApplicationContext(), FrontCameraActivity.class));
//                }else {
//                    startActivity(new Intent(getApplicationContext(), FrontCameraActivityLolliPop.class));
//                }
            }
        });

        Button front_l = (Button) findViewById(R.id.front_camera_l);
        front_l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), FrontCameraActivityLolliPop.class));
            }
        });


    }

    private void initNFC() {
        HojungNFCCheckingLibrary nfc = new HojungNFCCheckingLibrary(this);
        NfcModel model = nfc.getNFCData(getIntent());
        model.getPayloadStr(); // tag에 저장된 string
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
