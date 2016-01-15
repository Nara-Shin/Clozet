package org.sgen.club.sgenapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;
import org.sgen.club.sgenapplication.gcm.QuickstartPreferences;
import org.sgen.club.sgenapplication.net.CommonHttpClient;
import org.sgen.club.sgenapplication.net.NetDefine;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by HOJU on 2016-01-09.
 */
public class AcceptActivity extends Activity {
    private Context context;


    private FrameLayout frameLayout_fragment_accept_cancel, frameLayout_fragment_accept_accept;
    private ImageView imageView_fragment_accept_accept, imageView_fragment_accept_cancel;
    private TextView textView_fragment_1, textView_fragment_2;
    private ListView listView_fragment_accept;
    private CallListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept);

        Intent intent = getIntent();

        String roomNumber = intent.getStringExtra("roomNumber");

        adapter = CallListAdapter.getInstance();
        if(roomNumber==null){
            roomNumber=adapter.getItem(0).getRoom_number();
        }
        init(AcceptActivity.this);
        textView_fragment_1.setText("해당 의류( " + listView_fragment_accept.getAdapter().getCount() + "벌 )를");
        textView_fragment_2.setText("\"" + roomNumber + "번\" 피팅룸에 전달해주세요");

    }

    private void init(final Context context) {

        frameLayout_fragment_accept_cancel = (FrameLayout) findViewById(R.id.frameLayout_fragment_accept_cancel);
        frameLayout_fragment_accept_accept = (FrameLayout) findViewById(R.id.frameLayout_fragment_accept_accept);
        imageView_fragment_accept_accept = (ImageView) findViewById(R.id.imageView_fragment_accept_accept);
        imageView_fragment_accept_cancel = (ImageView) findViewById(R.id.imageView_fragment_accept_cancel);
        textView_fragment_1 = (TextView) findViewById(R.id.textView_fragment_1);
        textView_fragment_2 = (TextView) findViewById(R.id.textView_fragment_2);
        listView_fragment_accept = (ListView) findViewById(R.id.listView_fragment_accept);
        listView_fragment_accept.setAdapter(adapter);
        frameLayout_fragment_accept_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "수락이 완료되었습니다.", Toast.LENGTH_SHORT).show();

                if(!adapter.requestCodeArray.isEmpty()){

                    for(String str:adapter.requestCodeArray){
                        RequestParams params=new RequestParams();
                        params.put("request_code", str);
                        params.put("confirm", "deliveryokay");
                        CommonHttpClient.post(NetDefine.CONFIRM_URL, params, new JsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                super.onSuccess(statusCode, headers, response);
                                try {
                                    if (response.has("confirm_message")) {
                                        LogProcess.normalLog(getClass(), "accept_confirm_message : " + response.getString("confirm_message"));
                                        Toast.makeText(AcceptActivity.this,"accept_confirm_message : " + response.getString("confirm_message"),Toast.LENGTH_SHORT).show();

                                        if(response.getString("confirm_message").equals("success")){
                                            Intent intent=new Intent().putExtra("finish_success",true);
                                            AcceptActivity.this.setResult(RESULT_OK, intent);
                                            finish();
                                        }else{
                                            Toast.makeText(AcceptActivity.this,"전송 실패 다시 시도",Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                } catch (Exception e) {
                                    LogProcess.errorLog(e);
                                }
                            }
                        });
                    }
                }
                adapter.removeAllItem();

            }
        });
        frameLayout_fragment_accept_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<CallListItem> items=adapter.getAllItem();
                if(!items.isEmpty()){

                    RequestParams params=new RequestParams();
                    params.put("request_code",items.get(0).getRequest_code());
                    params.put("confirm", "refuse");
                    CommonHttpClient.post(NetDefine.CONFIRM_URL, params, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);
                            try {
                                if (response.has("confirm_message")) {
                                    LogProcess.normalLog(getClass(), "cancel confirm_message : " + response.getString("confirm_message"));
                                    if (response.getString("confirm_message").equals("fail")) {
                                        Toast.makeText(context,"서버에서도 거절은 거절한다",Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent().putExtra("reject_success",false);
                                        AcceptActivity.this.setResult(RESULT_OK, intent);
                                        AcceptActivity.this.finish();
                                    }else{
                                        Toast.makeText(context, "거절은 거절하려고 했지만 봐준다.", Toast.LENGTH_SHORT).show();
                                        adapter.removeAllItem();
                                        Intent intent=new Intent().putExtra("reject_success",true);
                                        AcceptActivity.this.setResult(RESULT_OK, intent);
                                        AcceptActivity.this.finish();
                                    }
                                }
                            } catch (Exception e) {
                                LogProcess.errorLog(e);
                            }

                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);
                        }
                    });

                }

            }
        });
        imageView_fragment_accept_accept.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    imageView_fragment_accept_accept.setImageResource(R.drawable.finish_btn_click);

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    imageView_fragment_accept_accept.setImageResource(R.drawable.finish_btn);
                }
                return false;
            }
        });
    }



    public void setListView_fragment_accept_adapter(CallListAdapter adapter) {
        listView_fragment_accept.setAdapter(adapter);
    }
}
