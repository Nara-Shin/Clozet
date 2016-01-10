package com.example.krcho.clozet.Profile;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.krcho.clozet.MyAccount;
import com.example.krcho.clozet.R;
import com.loopj.android.image.SmartImageView;

public class ProfileActivity extends AppCompatActivity {
    ImageButton back;
    TextView likeCount;
    TextView textWegiht, textHeight, textAge, textSex;
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
        textWegiht = (TextView) findViewById(R.id.text_weight);
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
    }

    public void getUserInfo() {
        textWegiht.setText(MyAccount.getInstance().getWeight());
        textHeight.setText(MyAccount.getInstance().getHeight());
        textSex.setText(MyAccount.getInstance().getSex());
        textAge.setText(MyAccount.getInstance().getAge());
    }
}
