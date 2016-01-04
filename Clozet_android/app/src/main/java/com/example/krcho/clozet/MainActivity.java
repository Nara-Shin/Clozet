package com.example.krcho.clozet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.krcho.clozet.barcode.BarcodeDetactActivity;
import com.example.krcho.clozet.camera.CameraGuideActivity;
import com.example.krcho.clozet.gcm.QuickstartPreferences;
import com.example.krcho.clozet.gcm.RegistrationIntentService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.hojung.nfc.HojungNFCReadLibrary;
import com.hojung.nfc.interfaces.OnHojungNFCListener;
import com.hojung.nfc.model.NfcModel;
import com.loopj.android.image.SmartImageView;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView change, shot;
    private SmartImageView cImage;
    private ImageView imageView_main_change,imageView_main_savepic,imageView_main_sidebar;
    private FrameLayout frameLayout_sideBar_container,frameLayout_sideBar_close;
    private MainSideView mainSideView;

    //NFC
    HojungNFCReadLibrary hojungNFCReadLibrary;

    Context mContext;

    //GCM
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "MainActivity";
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        mainSideView=new MainSideView(MainActivity.this);


        setUIView();

       /* change = (TextView) findViewById(R.id.txt_change);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        shot = (TextView) findViewById(R.id.txt_shot);
        shot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });

        cImage = (SmartImageView) findViewById(R.id.image);
        cImage.setImageUrl("http://125.209.199.91/clozet/img/view/main/cont_nonfc.png");*/

        //NFC

        //NFC is use?
        android.nfc.NfcAdapter mNfcAdapter = android.nfc.NfcAdapter.getDefaultAdapter(mContext);

        try {
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
        } catch (Exception e) {

        }

        hojungNFCReadLibrary = new HojungNFCReadLibrary(getIntent(), MainActivity.this, new OnHojungNFCListener() {

            @Override
            public void onReceiveMessage(NfcModel[] models) {
                // TODO Auto-generated method stub
                //0��° �� ���
                // model- Ÿ�԰� ���̷ε�  �ƹ��ų� ��� ����(Write �ۿ��� ���� ����� �ν� ����.)
                Toast.makeText(MainActivity.this, "type : " + models[0].getTypeStr() + " , " + "payload : " + models[0].getPayloadStr(), Toast.LENGTH_SHORT).show();

                // ralph, bean

                if (models[0].getTypeStr().equals("ralph")){
                    cImage.setImageUrl("http://125.209.199.91/clozet/img/view/main/cont_2.png");
                }else if (models[0].getTypeStr().equals("ralph")){
                    cImage.setImageUrl("http://125.209.199.91/clozet/img/view/main/cont_1.png");
                }
            }

            @Override
            public void onError(String arg0) {
                // TODO Auto-generated method stub

            }
        });


        //GCM

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(context);
                boolean sentToken = sharedPreferences
                        .getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
            }
        };

        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }

    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }


    public void setUIView() {
        imageView_main_change=(ImageView)findViewById(R.id.imageView_main_change);
        imageView_main_savepic=(ImageView)findViewById(R.id.imageView_main_savepic);
        imageView_main_sidebar=(ImageView)findViewById(R.id.imageView_main_sidebar);
        frameLayout_sideBar_container=(FrameLayout)findViewById(R.id.frameLayout_sideBar_container);
        frameLayout_sideBar_close=(FrameLayout)findViewById(R.id.frameLayout_sideBar_close);
        imageView_main_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView_main_change.setImageResource(R.drawable.change_btn_click);
                imageView_main_savepic.setImageResource(R.drawable.csave_btn_unclick);
                startActivity(new Intent(getApplicationContext(), BarcodeDetactActivity.class));

            }
        });
        imageView_main_savepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView_main_change.setImageResource(R.drawable.change_btn_unclick);
                imageView_main_savepic.setImageResource(R.drawable.csave_btn_click);
                startActivity(new Intent(getApplicationContext(), CameraGuideActivity.class));
            }
        });
        imageView_main_sidebar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frameLayout_sideBar_container.setVisibility(View.VISIBLE);
                frameLayout_sideBar_container.bringToFront();
                frameLayout_sideBar_container.removeAllViews();
                frameLayout_sideBar_container.addView(mainSideView);
                frameLayout_sideBar_close.setVisibility(View.VISIBLE);


            }
        });
        frameLayout_sideBar_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frameLayout_sideBar_container.setVisibility(View.GONE);
                frameLayout_sideBar_close.setVisibility(View.GONE);
            }
        });

    }

    private void initNFC() {
        try {
            Log.d("NFC", "intent : " + getIntent().getAction());
            Intent intent = getIntent();
            hojungNFCReadLibrary.onResume(intent);
        } catch (Exception e) {

        }

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
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
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
