package com.example.krcho.clozet;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.krcho.clozet.barcode.BarcodeDetactActivity;
import com.example.krcho.clozet.camera.FrontCameraActivity;
import com.example.krcho.clozet.camera.FrontCameraActivityLolliPop;
import com.hojung.nfc.HojungNFCReadLibrary;
import com.hojung.nfc.interfaces.OnHojungNFCListener;
import com.hojung.nfc.model.NfcModel;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TextView change, shot;

    //NFC
    HojungNFCReadLibrary hojungNFCReadLibrary;

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

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
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(new Intent(getApplicationContext(), FrontCameraActivity.class));
                } else {
                    startActivity(new Intent(getApplicationContext(), FrontCameraActivityLolliPop.class));
                }

            }
        });

        //NFC

        //NFC is use?
        android.nfc.NfcAdapter mNfcAdapter= android.nfc.NfcAdapter.getDefaultAdapter(mContext);

        if (!mNfcAdapter.isEnabled()) {

            AlertDialog.Builder alertbox = new AlertDialog.Builder(mContext);
            alertbox.setTitle("Info");
            alertbox.setMessage("본 서비스를 이용하기 위해 NFC를 사용하셔야 합니다.");
            alertbox.setPositiveButton("Turn On", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        Intent intent = new Intent(Settings.ACTION_NFC_SETTINGS);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                        startActivity(intent);
                    }
                }
            });
            alertbox.setNegativeButton("Close", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alertbox.show();

        }

        hojungNFCReadLibrary = new HojungNFCReadLibrary(getIntent(), MainActivity.this, new OnHojungNFCListener() {

            @Override
            public void onReceiveMessage(NfcModel[] models) {
                // TODO Auto-generated method stub
                //0��° �� ���
                // model- Ÿ�԰� ���̷ε�  �ƹ��ų� ��� ����(Write �ۿ��� ���� ����� �ν� ����.)
                Toast.makeText(MainActivity.this, "type : " + models[0].getTypeStr() + " , " + "payload : " + models[0].getPayloadStr(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String arg0) {
                // TODO Auto-generated method stub

            }
        });

    }

    public void setViewPager() {

    }

    private void initNFC() {
        Log.d("NFC", "intent : " + getIntent().getAction());
        Intent intent = getIntent();
        hojungNFCReadLibrary.onResume(intent);

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
    protected void onPause() {
        super.onPause();
        Log.d("NFC", "onPause");
        hojungNFCReadLibrary.onPause();
    }


    @Override
    public void onNewIntent(Intent intent) {
        Log.d("NFC", "onNewIntent");
        hojungNFCReadLibrary.onNewIntent(intent);
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
