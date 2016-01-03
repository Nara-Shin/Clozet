package com.example.krcho.clozet.request;

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
import com.example.krcho.clozet.network.CommonHttpClient;
import com.example.krcho.clozet.network.NetDefine;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SelectOptionsActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    RecyclerAdapter adapter;
    private ArrayList<Request> list = new ArrayList<>();
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
                addProduct("8801069178370");
            }
        });

        setRecyclerView();

        addProduct(getIntent().getStringExtra("barcode"));

    }


    public void addProduct(String barcode) {

        RequestParams params = new RequestParams();
//        params.put("barcode", rawResult.getText()); // 8801069178370
        params.put("barcode", barcode);
        params.put("member_code", MyAccount.getInstance().getMember_code());

        CommonHttpClient.post(NetDefine.SEARCH_BARCODE, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                Log.d("response", response.toString());
                try {
                    Request temp = new Request();
                    temp.setCode(response.getString("product_code"));
                    temp.setBrand(response.getString("product_brand"));
                    temp.setName(response.getString("product_name"));
                    temp.setDetail(response.getString("product_detail"));
                    temp.setPrice(response.getInt("product_price"));
                    temp.setPhoto_url(response.getString("product_photo"));

                    String size = response.getString("product_sizes");
                    temp.addSize(size.split(","));

                    String colors = response.getString("product_colors");
                    temp.addColors(colors.split(","));

                    list.add(temp);
                }catch (JSONException e){
                    e.printStackTrace();
                }
                adapter.setList(list);
                Toast.makeText(getApplicationContext(), "추가", Toast.LENGTH_LONG).show();

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
            Toast.makeText(getApplicationContext(), "요청", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
