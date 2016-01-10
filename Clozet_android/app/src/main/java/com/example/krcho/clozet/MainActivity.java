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
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.krcho.clozet.Profile.ProfileActivity;
import com.example.krcho.clozet.camera.CameraGuideActivity;
import com.example.krcho.clozet.gallery.GalleryActivity;
import com.example.krcho.clozet.gcm.QuickstartPreferences;
import com.example.krcho.clozet.gcm.RegistrationIntentService;
import com.example.krcho.clozet.network.CommonHttpClient;
import com.example.krcho.clozet.network.NetDefine;
import com.example.krcho.clozet.request.SelectOptionsActivity;
import com.example.krcho.clozet.promotion.PromotionActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.hojung.nfc.HojungNFCReadLibrary;
import com.hojung.nfc.interfaces.OnHojungNFCListener;
import com.hojung.nfc.model.NfcModel;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.image.SmartImageView;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout dlDrawer;
    private SmartImageView cImage;
    private RelativeLayout cancelBtn;
    private LinearLayout profileBtn;
    private ImageView imageView_main_change,imageView_main_savepic,imageView_main_sidebar, promotionBtn, galleryBtn;

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
*/
        cImage = (SmartImageView) findViewById(R.id.image);
        cImage.setImageUrl(NetDefine.BASE_URL + "img/view/main/cont_nonfc.png");

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
//                Toast.makeText(MainActivity.this, "type : " + models[0].getTypeStr() + " , " + "payload : " + models[0].getPayloadStr(), Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, models[0].getTypeStr() + "의 피팅룸에 입장했습니다!", Toast.LENGTH_SHORT).show();
                // ralph, bean
                RequestParams params = new RequestParams();
                params.put("fitroom_code", models[0].getPayloadStr());

                CommonHttpClient.post(NetDefine.FIND_BRAND_INFO, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        Log.d("response", response.toString());
                        CurrentFittingRoom.getInstance().setInfo(response);
                        cImage.setImageUrl(CurrentFittingRoom.getInstance().getImg_url());
                    }
                });
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

                if (sentToken) {
                    String token = sharedPreferences.getString("gcm", "");
                    String androidID =
                            Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                    Log.i(TAG, "GCM Registration Token: " + token);
//        Log.i(TAG, "GCMID : " + instanceID.getId());
                    Log.i(TAG, "ANDROID ID : " + androidID);

                    RequestParams requestParams = new RequestParams();
                    requestParams.put("gcm_id", token);
                    requestParams.put("android_id", androidID);

                    Log.d("params", requestParams.toString());

                    CommonHttpClient.post(NetDefine.JOIN_SERVICE, requestParams, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            Log.d(TAG, response.toString());
                            Log.d(TAG, "Status : " + statusCode);
                            String confirmMessage;
                            String memberCode;
                            try {
                                confirmMessage = response.getString("confirm_message");
                                memberCode = response.getString("member_code");
                                MyAccount.getInstance().setMember_code(memberCode);

                                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                sp.edit().putString(MyAccount.MEMBERCODE, memberCode).commit();

                                MyAccount.getInstance().setWeight(sp.getInt(MyAccount.MEMBERINFOWEIGHT, -1));
                                MyAccount.getInstance().setHeight(sp.getInt(MyAccount.MEMBERINFOHEIGHT, -1));
                                MyAccount.getInstance().setAge(sp.getInt(MyAccount.MEMBERINFOAGE, -1));
                                MyAccount.getInstance().setSex(sp.getString(MyAccount.MEMBERINFOSEX, ""));

                                Log.d(TAG, "confirmMessage : " + confirmMessage);
                                Log.d(TAG, "memberCode : " + memberCode);
                            } catch (Exception e) {
                            }

                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            Log.d(TAG, "Status : " + statusCode);
                        }
                    });
                }
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
        dlDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        imageView_main_change = (ImageView) findViewById(R.id.imageView_main_change);
        imageView_main_savepic = (ImageView) findViewById(R.id.imageView_main_savepic);
        imageView_main_sidebar = (ImageView) findViewById(R.id.imageView_main_sidebar);
        profileBtn = (LinearLayout) findViewById(R.id.sidebar_btn_profile);
        promotionBtn = (ImageView) findViewById(R.id.sidebar_btn_promotion);
        galleryBtn = (ImageView) findViewById(R.id.sidebar_btn_gallery);
        cancelBtn = (RelativeLayout) findViewById(R.id.sidebar_btn_cancel);

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            }
        });
        promotionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PromotionActivity.class));
            }
        });
        galleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GalleryActivity.class));
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlDrawer.closeDrawers();
            }
        });
        imageView_main_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SelectOptionsActivity.class));

            }
        });
        imageView_main_savepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CameraGuideActivity.class));
            }
        });
        imageView_main_sidebar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlDrawer.openDrawer(GravityCompat.START);
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
