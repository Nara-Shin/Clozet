package com.example.krcho.clozet.request;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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

/**
 * Created by krcho on 2015-11-15.
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    SmartImageView image;
    TextView brand, title;
    LinearLayout sizes, colors;

    ImageButton delete, like;

    RecyclerViewHolderDelegate delegate;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        image = (SmartImageView) itemView.findViewById(R.id.image);
        brand = (TextView) itemView.findViewById(R.id.brand);
        title = (TextView) itemView.findViewById(R.id.title);
        sizes = (LinearLayout) itemView.findViewById(R.id.size_container);
        colors = (LinearLayout) itemView.findViewById(R.id.color_container);

        delete = (ImageButton) itemView.findViewById(R.id.order_delete);
        like = (ImageButton) itemView.findViewById(R.id.order_like);
    }

    public void update(final Product data, RecyclerViewHolderDelegate dele) {
        this.delegate = dele;
        image.setImageUrl(data.getProduct_photo());
        brand.setText(data.getProduct_brand());
        title.setText(data.getProduct_name());

        if (data.isLike()) {
            like.setImageDrawable(itemView.getContext().getApplicationContext().getDrawable(R.drawable.like_btn_click2));
        }

        sizes.removeAllViews();

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int) convertDpToPixel(36, itemView.getContext()), (int) convertDpToPixel(36, itemView.getContext()));
        layoutParams.setMargins(0, 0, 5, 0);
        RelativeLayout.LayoutParams colorViewParams = new RelativeLayout.LayoutParams((int) convertDpToPixel(12, itemView.getContext()), (int) convertDpToPixel(12, itemView.getContext()));

        for (final String item : data.getSizes()) {
            final Button size = new Button(itemView.getContext());
            size.setBackground(itemView.getContext().getDrawable(R.drawable.box_order));
            size.setGravity(Gravity.CENTER);
            size.setTextColor(Color.parseColor("#ffc5c5"));
            size.setTextSize(14);
            size.setText(item);
            size.setLayoutParams(new ViewGroup.LayoutParams(layoutParams));
            size.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    size.setBackgroundColor(itemView.getContext().getColor(R.color.pink));
//                    size.setTextColor(itemView.getContext().getColor(R.color.navy));
                    data.setSelectedSize(item);
                    for (int i = 0; i < sizes.getChildCount(); i++) {
                        sizes.getChildAt(i).setBackground(itemView.getContext().getDrawable(R.drawable.box_order));
                    }
                    v.setBackgroundColor(Color.parseColor("#3C63B9"));
                }
            });
            this.sizes.addView(size);
        }

        colors.removeAllViews();

        for (final String item : data.getColors()) {
            View view = new View(itemView.getContext());
            view.setLayoutParams(colorViewParams);

            RelativeLayout color = new RelativeLayout(itemView.getContext());
            color.setBackground(itemView.getContext().getDrawable(R.drawable.box_order));
            color.setGravity(Gravity.CENTER);
            color.setLayoutParams(new ViewGroup.LayoutParams(layoutParams));
            color.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    color.setBackgroundColor(itemView.getContext().getColor(R.color.pink));
//                    color.setTextColor(itemView.getContext().getColor(R.color.navy));
                    data.setSelectedColor(item);

                    for (int i = 0; i < colors.getChildCount(); i++) {
                        colors.getChildAt(i).setBackground(itemView.getContext().getDrawable(R.drawable.box_order));
                    }
                    v.setBackgroundColor(Color.parseColor("#3C63B9"));
                }
            });
            view.setBackgroundColor(Color.parseColor("#" + item));
            color.addView(view);

            this.colors.addView(color);
        }

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (delegate != null) {
                    delegate.onClickDelete(data);
                }

            }
        });

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                barcode=최대 20자리문자열
//                member_code=6자리 회원코드

                RequestParams params = new RequestParams();
                params.put("barcode", data.getBarcode());
                try {
                    params.put("member_code", MyAccount.getInstance().getMember_code());
                } catch (Exception e) {
                    params.put("member_code", PreferenceManager.getDefaultSharedPreferences(itemView.getContext().getApplicationContext()).getString(MyAccount.MEMBERCODE, ""));
                }

                CommonHttpClient.post(NetDefine.SET_LIKE, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        Log.d("response", response.toString());
                        try {
                            data.setLike(response.getInt("status") == 1);
                            if (data.isLike()) {
                                like.setImageDrawable(itemView.getContext().getApplicationContext().getDrawable(R.drawable.like_btn_click2));
                            } else {
                                like.setImageDrawable(itemView.getContext().getApplicationContext().getDrawable(R.drawable.btn_like));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

            }
        });
    }

    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }
}

interface RecyclerViewHolderDelegate {
    void onClickDelete(Product product);
}
