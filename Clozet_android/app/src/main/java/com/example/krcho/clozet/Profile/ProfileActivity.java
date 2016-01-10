package com.example.krcho.clozet.Profile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
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

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mContext = this;

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
        setClickListeners();
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

    public void setClickListeners() {
        ((RelativeLayout) findViewById(R.id.weight_container)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ab = new AlertDialog.Builder(mContext);
                ab.setTitle("몸무게를 입력하세요!");
                final EditText input = new EditText(mContext);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                ab.setView(input);
                ab.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        sp.edit().putInt(MyAccount.MEMBERINFOWEIGHT, Integer.parseInt(input.getText().toString())).commit();
                        MyAccount.getInstance().setWeight(sp.getInt(MyAccount.MEMBERINFOWEIGHT, -1));

                        getUserInfo();
                    }
                });
                ab.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = ab.create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
        });

        ((RelativeLayout) findViewById(R.id.height_container)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ab = new AlertDialog.Builder(mContext);
                ab.setTitle("키를 입력하세요!");
                final EditText input = new EditText(mContext);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                ab.setView(input);
                ab.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        sp.edit().putInt(MyAccount.MEMBERINFOHEIGHT, Integer.parseInt(input.getText().toString())).commit();
                        MyAccount.getInstance().setHeight(sp.getInt(MyAccount.MEMBERINFOHEIGHT, -1));

                        getUserInfo();
                    }
                });
                ab.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = ab.create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
        });

        ((RelativeLayout) findViewById(R.id.age_container)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ab = new AlertDialog.Builder(mContext);
                ab.setTitle("나이를 입력하세요!");
                final EditText input = new EditText(mContext);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                ab.setView(input);
                ab.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        sp.edit().putInt(MyAccount.MEMBERINFOAGE, Integer.parseInt(input.getText().toString())).commit();
                        MyAccount.getInstance().setAge(sp.getInt(MyAccount.MEMBERINFOAGE, -1));

                        getUserInfo();
                    }
                });
                ab.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = ab.create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
        });

        ((RelativeLayout) findViewById(R.id.sex_container)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ab = new AlertDialog.Builder(mContext);
                ab.setTitle("성별을 입력하세요!");
                String[] ar = {"남성", "여성"};
                ab.setSingleChoiceItems(ar, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        switch (which) {
                            case 0:
                                sp.edit().putString(MyAccount.MEMBERINFOSEX, "M").commit();
                                break;
                            case 1:
                                sp.edit().putString(MyAccount.MEMBERINFOSEX, "W").commit();
                                break;
                        }

                        MyAccount.getInstance().setSex(sp.getString(MyAccount.MEMBERINFOSEX, ""));

                    }
                });

                ab.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        getUserInfo();
                    }
                });

                AlertDialog dialog = ab.create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
        });
    }
}
