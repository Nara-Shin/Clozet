package com.example.krcho.clozet.promotion;

import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.krcho.clozet.MyAccount;
import com.example.krcho.clozet.R;
import com.example.krcho.clozet.network.CommonHttpClient;
import com.example.krcho.clozet.network.NetDefine;
import com.example.krcho.clozet.request.Product;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.image.SmartImageView;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by krmpr on 16. 1. 15..
 */
public class PromotionViewHolder extends RecyclerView.ViewHolder {

    TextView brandName;
    TextView productName;
    TextView price;
    SmartImageView image;
    ImageView like;

    PromotionDelegate delegate;

    public PromotionViewHolder(View itemView) {
        super(itemView);

        brandName = (TextView) itemView.findViewById(R.id.text_brand_name);
        productName = (TextView) itemView.findViewById(R.id.text_product_name);
        price = (TextView) itemView.findViewById(R.id.text_price);
        image = (SmartImageView) itemView.findViewById(R.id.image_image);
        like = (ImageView) itemView.findViewById(R.id.promotion_like);


    }

    public void update(final Product data, final PromotionDelegate dele) {
        brandName.setText(data.getProduct_brand());
        productName.setText(data.getProduct_name());
        price.setText(data.getProduct_price() + "");
        image.setImageUrl(data.getProduct_photo());

        delegate = dele;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dele.onClickItem(data);
            }
        });

        if (data.isLike()) {
            like.setImageDrawable(itemView.getContext().getApplicationContext().getDrawable(R.drawable.btn_like_click));
        } else {
            like.setImageDrawable(itemView.getContext().getApplicationContext().getDrawable(R.drawable.btn_like));
        }

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                        try {
                            data.setLike(response.getInt("status") == 1);
                            if (data.isLike()) {
                                like.setImageDrawable(itemView.getContext().getApplicationContext().getDrawable(R.drawable.btn_like_click));
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
}

interface PromotionDelegate {
    void onClickItem(Product data);
}