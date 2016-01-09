package com.example.krcho.clozet.request;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.krcho.clozet.MyAccount;
import com.example.krcho.clozet.R;
import com.example.krcho.clozet.barcode.BarcodeDetactActivity;
import com.example.krcho.clozet.network.CommonHttpClient;
import com.example.krcho.clozet.network.NetDefine;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SelectOptionsActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    RecyclerAdapter adapter;
    private ArrayList<Product> list = new ArrayList<>();
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_options);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBarcodeDection();
            }
        });

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

    public void addProduct(String barcode) {

        RequestParams params = new RequestParams();
        params.put("barcode", barcode);
        params.put("member_code", MyAccount.getInstance().getMember_code());

        CommonHttpClient.post(NetDefine.SEARCH_BARCODE, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("response", response.toString());
                try {
                    list.add(new Product(response));
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "잘못된 데이터입니다. 다시 시도해주세요!", Toast.LENGTH_LONG).show();
                }
                adapter.setList(list);
                Toast.makeText(getApplicationContext(), "추가", Toast.LENGTH_LONG).show();

                if (list.size() >= 2) {
                    fab.hide();
                }
            }
        });


    }

    public void setRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new RecyclerAdapter(list);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_select_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.request) {
//            member_code=6자리 회원코드
//            option_code=6자리 옵션 코드
            RequestParams params = new RequestParams();
            params.put("member_code", MyAccount.getInstance().getMember_code());
            String code = "";
            for (Product prod : list) {
                code += prod.getOptionCode() + "#";
            }
            if (code.contains("-1")) {
                Toast.makeText(getApplicationContext(), "선택하지 않은 옵션이 있습니다", Toast.LENGTH_LONG).show();
                return false;
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
                            ProcessDialogFragment dialogFragment = ProcessDialogFragment.newInstance();
                            dialogFragment.show(getSupportFragmentManager(), "test");
                        }
                    } catch (JSONException e){
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
