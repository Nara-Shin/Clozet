package org.sgen.club.sgenapplication;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;
import org.sgen.club.sgenapplication.gcm.QuickstartPreferences;
import org.sgen.club.sgenapplication.gcm.RegistrationIntentService;
import org.sgen.club.sgenapplication.net.CommonHttpClient;
import org.sgen.club.sgenapplication.net.NetDefine;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends Activity {
    //http://godeung.woobi.co.kr/clozet/lib/gcm/sendPushMessageLib.php?admin=true&regid=eceXF367p1s:APA91bHFsorUFTXIkxCOlNHTv8XnZjMvFRf5ibRx296ATF9Qwf89PPVZHdDxqSpqzYl69ZkBV2KCS9aCbNoJ3ocUOKafS3_vECQ-dR-okJ-9vB15KQLuTXnS1v8jDQOxLFJyy3-sKjiW&message={%22room%22:%221%22,%22prdname%22:%22%ED%8C%AC%EC%B8%A0%22,%22img%22:%22100001.jpg%22,%22size%22:%22M%22,%22color%22:%22888888%22,%22count%22:%223%22}
    private ImageView imageView_activity_main_logo, imageView_activity_main_noti, imageView_activity_main_request, imageView_activity_main_search, imageView_activity_main_cancel, imageView_activity_main_accept, imageView_activity_main_search_tab;
    private TextView textView_activity_main_room_num;
    private ListView listView_activity_main_request, listView_activity_main_search;
    private EditText editText_main_activity_search;
    private LinearLayout linearLayout_activity_main_request, linearLayout_activity_main_search;
    private FrameLayout frameLayout_activity_main_request_tab, frameLayout_activity_main_search_tab, frameLayout_activity_main_cancel, frameLayout_activity_main_accept, frameLayout_activity_main_search_button;
    private final int REQUEST_CODE_ACCEPT = 1001;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private CallListAdapter adapter;
    private String roomNumber;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private final String NOTI_URL = "http://lakecenter.ipdisk.co.kr:7777/public/HDD1/Share/noti_blank.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        imageView_activity_main_noti.setVisibility(View.VISIBLE);
        Glide.with(MainActivity.this).load(NOTI_URL).into(imageView_activity_main_noti);
        adapter = CallListAdapter.getInstance();

        Intent intent = getIntent();//인텐트
        processIntent(intent);
        processReceiver();
        setClickListner();
        setTouchListner();
    }

    private void findView() {
        imageView_activity_main_logo = (ImageView) findViewById(R.id.imageView_activity_main_logo);
        imageView_activity_main_noti = (ImageView) findViewById(R.id.imageView_activity_main_noti);
        imageView_activity_main_request = (ImageView) findViewById(R.id.imageView_activity_main_request);
        imageView_activity_main_search = (ImageView) findViewById(R.id.imageView_activity_main_search);
        textView_activity_main_room_num = (TextView) findViewById(R.id.textView_activity_main_room_num);
        linearLayout_activity_main_request = (LinearLayout) findViewById(R.id.linearLayout_activity_main_request);
        linearLayout_activity_main_search = (LinearLayout) findViewById(R.id.linearLayout_activity_main_search);

        listView_activity_main_search = (ListView) findViewById(R.id.listView_activity_main_search);
        listView_activity_main_request = (ListView) findViewById(R.id.listView_activity_main_request);

        imageView_activity_main_search_tab = (ImageView) findViewById(R.id.imageView_activity_main_search_tab);
        imageView_activity_main_accept = (ImageView) findViewById(R.id.imageView_activity_main_accept);
        editText_main_activity_search = (EditText) findViewById(R.id.editText_main_activity_search);
        imageView_activity_main_cancel = (ImageView) findViewById(R.id.imageView_activity_main_cancel);
        frameLayout_activity_main_request_tab = (FrameLayout) findViewById(R.id.frameLayout_activity_main_request_tab);
        frameLayout_activity_main_search_tab = (FrameLayout) findViewById(R.id.frameLayout_activity_main_search_tab);
        frameLayout_activity_main_accept = (FrameLayout) findViewById(R.id.frameLayout_activity_main_accept);
        frameLayout_activity_main_cancel = (FrameLayout) findViewById(R.id.frameLayout_activity_main_cancel);
        frameLayout_activity_main_search_button = (FrameLayout) findViewById(R.id.frameLayout_activity_main_search_button);
    }


    private void processReceiver() {
        /*mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(final Context context, Intent intent) {

            }
        };*/

        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        } else {
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
                LogProcess.normalLog(getClass(), "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        processIntent(intent);
    }

    private void processIntent(Intent intent) {
        if (intent.hasExtra(QuickstartPreferences.REGISTRATION_COMPLETE)) {


            SharedPreferences sharedPreferences =
                    PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
            boolean sentToken = sharedPreferences
                    .getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
            if (sentToken) {


                String token = sharedPreferences.getString("gcm", "");
                String androidId = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                LogProcess.normalLog(getClass(), "token : "+token+" , "+"androidId : "+androidId);


                RequestParams requestParams = new RequestParams();
                requestParams.put("gcm_id", token);
                requestParams.put("android_id", androidId);

                CommonHttpClient.post(NetDefine.JOIN_URL, requestParams, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        try {
                            String member_code="";
                            String confirm_message="";
                            if(response.has("confirm_message")){
                                 confirm_message = response.getString("confirm_message");
                            }else{
//                                System.exit(0);
                            }
                            if(response.has("clerk_code")){
                                 member_code = response.getString("clerk_code");
                            }else{
//                                System.exit(0);
                            }
                            LogProcess.normalLog(getClass(), "confirm_message : " + confirm_message);

                            if (confirm_message.equals("success")) {
                                Toast.makeText(MainActivity.this, "회원가입을 축하한다.", Toast.LENGTH_SHORT).show();

                            } else if (confirm_message.equals("fail")) {
                                Toast.makeText(MainActivity.this, "회원가입을 거절한다.", Toast.LENGTH_SHORT).show();
                                System.exit(0);
                            }

                            //여기서 로그인처리 하세요


                        } catch (JSONException e) {
                            LogProcess.errorLog(e);
                        }
                    }
                });
            }


        }


        roomNumber = intent.getStringExtra("roomNumber");
        LogProcess.normalLog(getClass(), "processIntent rommNumber : " + roomNumber);

        if (roomNumber == null) return;
        textView_activity_main_room_num.setText("0" + roomNumber);
        String count = intent.getStringExtra("count");
        String color = intent.getStringExtra("color");
        String prdName = intent.getStringExtra("prdName");
        String size = intent.getStringExtra("size");
        String imageUrl = intent.getStringExtra("imageUrl");
        String code = intent.getStringExtra("code");
        String price = intent.getStringExtra("price");
        String stockURL = intent.getStringExtra("stockURL");

        LogProcess.normalLog(getClass(), "color : " + color);

        LogProcess.normalLog(getClass(), "roomNumber : " + roomNumber);
        LogProcess.normalLog(getClass(), "count : " + count);
        LogProcess.normalLog(getClass(), "prdName : " + prdName);
        LogProcess.normalLog(getClass(), "sizeUrl : " + size);
        LogProcess.normalLog(getClass(), "imageUrl : " + imageUrl);
        LogProcess.normalLog(getClass(), "code : " + code);
        LogProcess.normalLog(getClass(), "price : " + price);
        LogProcess.normalLog(getClass(), "stockURL : " + stockURL);
        adapter = CallListAdapter.getInstance();
//        CallListItem item = new CallListItem();
//        item.setColor(color);
//        item.setAmount(count);
//        item.setImageURL_cloth(imageUrl);
//        item.setSize(size);
//        item.setCode(code);
//        item.setName(prdName);
//        item.setPrice(price);
//        item.setStockURL(stockURL);
//        LogProcess.normalLog(getClass(), item.toString());
//        adapter.addItem(item);
        listView_activity_main_request.setAdapter(adapter);
        listView_activity_main_search.setAdapter(adapter);
        allViewGone();
        linearLayout_activity_main_request.setVisibility(View.VISIBLE);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_ACCEPT) {
            allViewGone();
            if (adapter.getAllItem().isEmpty()) {
                imageView_activity_main_noti.setVisibility(View.VISIBLE);
            } else {
                linearLayout_activity_main_request.setVisibility(View.VISIBLE);
            }


        }

    }

    public void setClickListner() {
        frameLayout_activity_main_request_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adapter.getCount() <= 0) {

                    imageView_activity_main_noti.setVisibility(View.VISIBLE);
                    imageView_activity_main_request.setImageResource(R.drawable.tab_request_click);
                    imageView_activity_main_search_tab.setImageResource(R.drawable.tab_search_unclick);
                    linearLayout_activity_main_search.setVisibility(View.GONE);
                    linearLayout_activity_main_request.setVisibility(View.GONE);
                } else {

                    textView_activity_main_room_num.setText("0" + adapter.getItem(0).getRoom_number());
                    imageView_activity_main_request.setImageResource(R.drawable.tab_request_click);
                    imageView_activity_main_search_tab.setImageResource(R.drawable.tab_search_unclick);
                    imageView_activity_main_noti.setVisibility(View.GONE);
                    linearLayout_activity_main_search.setVisibility(View.GONE);
                    linearLayout_activity_main_request.setVisibility(View.VISIBLE);
                    listView_activity_main_request.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }

            }
        });
        frameLayout_activity_main_search_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView_activity_main_request.setImageResource(R.drawable.tab_request_unclick);
                imageView_activity_main_search_tab.setImageResource(R.drawable.tab_search_click);
                imageView_activity_main_noti.setVisibility(View.GONE);
                linearLayout_activity_main_search.setVisibility(View.VISIBLE);
                linearLayout_activity_main_request.setVisibility(View.GONE);
                listView_activity_main_search.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }
        });

        imageView_activity_main_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String findStr = editText_main_activity_search.getText().toString();
                Toast.makeText(MainActivity.this, "Text : " + findStr, Toast.LENGTH_SHORT).show();
                ArrayList<CallListItem> items = adapter.getAllItem();
                ArrayList<CallListItem> founditems = new ArrayList<CallListItem>();

                for (CallListItem item : items) {
                    if (item.getCode().equals(findStr)) {
                        founditems.add(item);
                    }
                }
                CallListSearchAdapter adapter = CallListSearchAdapter.getInstance();

                if (founditems.size() > 0) {
                    adapter.changeAllItem(founditems);

                } else {
                    adapter.removeAllItem();
                }
                listView_activity_main_search.setAdapter(adapter);
            }
        });
        frameLayout_activity_main_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(MainActivity.this, "거절은 거절한다.", Toast.LENGTH_SHORT).show();
            }
        });
        frameLayout_activity_main_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, AcceptActivity.class);
                intent.putExtra("roomNumber", roomNumber);

                ArrayList<CallListItem> items = adapter.getAllItem();
                for (CallListItem item : items) {
                    RequestParams params = new RequestParams();
                    params.put("request_code", item.getRequest_code());
                    params.put("confirm", "okay");
                    CommonHttpClient.post(NetDefine.CONFIRM_URL, params, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);
                            try {
                                if (response.has("confirm_message")) {
                                    LogProcess.normalLog(getClass(), "confirm_message : " + response.getString("confirm_message"));
                                }
                            } catch (Exception e) {
                                LogProcess.errorLog(e);
                            }

                        }
                    });
                }
                startActivityForResult(intent, REQUEST_CODE_ACCEPT);
            }
        });
    }

    public void setTouchListner() {
        frameLayout_activity_main_accept.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    imageView_activity_main_accept.setImageResource(R.drawable.accept_btn);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {

                    imageView_activity_main_accept.setImageResource(R.drawable.accept_btn_click);
                }
                return false;
            }
        });

        frameLayout_activity_main_cancel.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    imageView_activity_main_cancel.setImageResource(R.drawable.cancel_btn_click);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {

                    imageView_activity_main_cancel.setImageResource(R.drawable.cancel_btn);

                }
                return false;
            }
        });
    }


    private void allViewGone() {
        linearLayout_activity_main_search.setVisibility(View.GONE);
        linearLayout_activity_main_request.setVisibility(View.GONE);
        imageView_activity_main_noti.setVisibility(View.GONE);
    }

    private void onGetImageView_activity_main_request_Sginal() {
        imageView_activity_main_request.setImageResource(R.drawable.tab_request_click_request);


        imageView_activity_main_noti.setVisibility(View.GONE);
        linearLayout_activity_main_search.setVisibility(View.GONE);
        linearLayout_activity_main_request.setVisibility(View.VISIBLE);
    }

}
