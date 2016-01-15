package com.example.krcho.clozet.promotion;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.krcho.clozet.R;
import com.example.krcho.clozet.request.Product;
import com.loopj.android.image.SmartImageView;

/**
 * Created by krmpr on 16. 1. 15..
 */
public class PromotionViewHolder extends RecyclerView.ViewHolder {

    TextView brandName;
    TextView productName;
    TextView price;
    SmartImageView image;

    PromotionDelegate delegate;

    public PromotionViewHolder(View itemView) {
        super(itemView);

        brandName = (TextView) itemView.findViewById(R.id.text_brand_name);
        productName = (TextView) itemView.findViewById(R.id.text_product_name);
        price = (TextView) itemView.findViewById(R.id.text_price);
        image = (SmartImageView) itemView.findViewById(R.id.image_image);


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

    }

}

interface PromotionDelegate {
    void onClickItem(Product data);
}