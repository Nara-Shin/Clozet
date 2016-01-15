package com.example.krcho.clozet.request;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.krcho.clozet.MyAccount;
import com.example.krcho.clozet.R;
import com.example.krcho.clozet.barcode.BarcodeDetactActivity;
import com.example.krcho.clozet.network.CommonHttpClient;
import com.example.krcho.clozet.network.NetDefine;
import com.hojung.nfc.HojungNFCReadLibrary;
import com.hojung.nfc.interfaces.OnHojungNFCListener;
import com.hojung.nfc.model.NfcModel;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class OrderActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private LinearLayoutManager recyclerManager;
    private RecyclerAdapter adapter;
    private ArrayList<Product> list = new ArrayList<>();
    private RelativeLayout backBtn, addBtn;
    private Button order;
    //NFC
    HojungNFCReadLibrary hojungNFCReadLibrary;
    Context mContext;

    private void initNFC() {
        try {
            Log.d("NFC", "intent : " + getIntent().getAction());
            Intent intent = getIntent();
            hojungNFCReadLibrary.onResume(intent);
        } catch (Exception e) {

        }

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        mContext = this;
        //NFC is use?
        android.nfc.NfcAdapter mNfcAdapter = android.nfc.NfcAdapter.getDefaultAdapter(mContext);
        hojungNFCReadLibrary = new HojungNFCReadLibrary(getIntent(), mContext, new OnHojungNFCListener() {

            @Override
            public void onReceiveMessage(NfcModel[] models) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onError(String arg0) {
                // TODO Auto-generated method stub

            }
        });
        backBtn = (RelativeLayout) findViewById(R.id.btn_back);
        addBtn = (RelativeLayout) findViewById(R.id.btn_add);
        order = (Button) findViewById(R.id.btn_order);

        backBtn.setOnClickListener(this);
        addBtn.setOnClickListener(this);
        order.setOnClickListener(this);

        order.setText("ORDER (0)");

        setRecyclerView();
        openBarcodeDection();
    }

    public void openBarcodeDection() {
        startActivityForResult(new Intent(getApplicationContext(), BarcodeDetactActivity.class), 1000);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == RESULT_OK && data.getStringExtra("barcode") != null) {
            addProduct(data.getStringExtra("barcode"));
        }
    }

    public void addProduct(final String barcode) {

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
                    list.add(new Product(response, barcode));
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "잘못된 데이터입니다. 다시 시도해주세요!", Toast.LENGTH_SHORT).show();
                }
                adapter.setList(list);
                Toast.makeText(getApplicationContext(), "추가", Toast.LENGTH_SHORT).show();
                order.setText("ORDER (" + list.size() + ")");

                if (list.size() >= 3) {
                    addBtn.setVisibility(View.GONE);
                }
            }
        });


    }


    boolean scrollCheck = false;
    public void setRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(recyclerManager);
        adapter = new RecyclerAdapter(list, new RecyclerViewHolderDelegate(){
            @Override
            public void onClickDelete(Product product) {
                if (list.remove(product)) {
                    Toast.makeText(OrderActivity.this, "항목을 삭제했습니다.", Toast.LENGTH_SHORT).show();
                    adapter.setList(list);    
                }else {
                    for(int i=0; i<list.size(); i++){
                        if (product.getProduct_code().equals(list.get(i).getProduct_code())){
                            list.remove(i);
                            Toast.makeText(OrderActivity.this, "항목을 삭제했습니다.", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                }

                order.setText("ORDER (" + list.size() + ")");
                
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.d("position", "x : " + dx + " / y : " + dy);

                int visibleItemCount = recyclerManager.getChildCount();
                int totalItemCount = recyclerManager.getItemCount();

                int firstVisibleItemPosition = recyclerManager.findFirstVisibleItemPosition();
                if (dy > 0 && visibleItemCount > 1 && !scrollCheck) {
                    scrollCheck = true;
                    Animation ani = new TranslateAnimation(0, 0, 0, 300);
                    ani.setDuration(500);
                    order.startAnimation(ani);
                    order.setVisibility(View.GONE);
                } else if (dy < 0 && scrollCheck && firstVisibleItemPosition == 0) {
                    scrollCheck = false;
                    order.setVisibility(View.VISIBLE);
                    Animation ani = new TranslateAnimation(0, 0, 300, 0);
                    ani.setDuration(500);
                    order.startAnimation(ani);

                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_select_options, menu);
        return false;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;

            case R.id.btn_add:
                openBarcodeDection();
                break;

            case R.id.btn_order:
                requestToChange();
                break;
        }
    }

    public void requestToChange() {
        //member_code=6자리 회원코드
        //option_code=6자리 옵션 코드
        RequestParams params = new RequestParams();
        try {
            params.put("member_code", MyAccount.getInstance().getMember_code());
        } catch (Exception e) {
            params.put("member_code", PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(MyAccount.MEMBERCODE, ""));
        }
        String code = "";
        for (Product prod : list) {
            code += prod.getOptionCode() + "#";
        }
        if (code.contains("-1")) {
            Toast.makeText(getApplicationContext(), "선택하지 않은 옵션이 있습니다", Toast.LENGTH_LONG).show();
            return;
        }
        params.put("option_code", code);
        params.put("fitroom_code", "100001");

        CommonHttpClient.post(NetDefine.REQUEST_CHANGE, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("response", response.toString());

                try {
                    if (response.getString("confirm_message").equals("success")) {
                        ProcessDialogFragment dialogFragment = ProcessDialogFragment.newInstance(list.size() * -1, response.getInt("request_code"));
                        dialogFragment.show(getSupportFragmentManager(), "test");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }
}
