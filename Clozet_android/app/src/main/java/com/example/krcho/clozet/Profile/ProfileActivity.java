package com.example.krcho.clozet.Profile;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.krcho.clozet.MyAccount;
import com.example.krcho.clozet.R;
import com.example.krcho.clozet.network.CommonHttpClient;
import com.example.krcho.clozet.network.NetDefine;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.image.SmartImageView;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ProfileActivity extends AppCompatActivity {
    ImageButton back;
    TextView likeCount;
    TextView textWeight, textHeight, textAge, textSex;
    SmartImageView ad1, ad2;
    ImageButton btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        back = (ImageButton) findViewById(R.id.btn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        likeCount = (TextView) findViewById(R.id.like_count);
        textWeight = (TextView) findViewById(R.id.text_weight);
        textHeight = (TextView) findViewById(R.id.text_height);
        textAge = (TextView) findViewById(R.id.text_age);
        textSex = (TextView) findViewById(R.id.text_sex);

        ad1 = (SmartImageView) findViewById(R.id.ad_image_1);
        ad2 = (SmartImageView) findViewById(R.id.ad_image_2);

        btn_logout = (ImageButton) findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getUserInfo();
        getAdInfo();
    }

    public void getUserInfo() {
        textWeight.setText(MyAccount.getInstance().getWeight() + "");
        textHeight.setText(MyAccount.getInstance().getHeight() + "");
        textAge.setText(MyAccount.getInstance().getAge() + "");
        textSex.setText(MyAccount.getInstance().getSex() + "");

        RequestParams params = new RequestParams();
        try {
            params.put("member_code", MyAccount.getInstance().getMember_code());
        } catch (Exception e) {
            params.put("member_code", PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(MyAccount.MEMBERCODE, ""));
        }
        CommonHttpClient.post(NetDefine.LIKE_COUNT, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("krcho", response.toString());
                try {
                    likeCount.setText(response.getInt("like_count") + "");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getAdInfo() {
        ad1.setImageUrl(NetDefine.BASE_URL + "img/promotion/0.png");
        ad2.setImageUrl(NetDefine.BASE_URL + "img/promotion/1.png");
    }
}
