package com.example.krcho.clozet.request;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.krcho.clozet.R;
import com.loopj.android.image.SmartImageView;

/**
 * Created by krcho on 2015-11-15.
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    SmartImageView image;
    TextView brand, title, price;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        image = (SmartImageView) itemView.findViewById(R.id.image);
        brand = (TextView) itemView.findViewById(R.id.brand);
        title = (TextView) itemView.findViewById(R.id.title);
        price = (TextView) itemView.findViewById(R.id.price);
    }

    public void update(final Product data) {
        image.setImageUrl(data.getProduct_photo());
        brand.setText(data.getProduct_brand());
        title.setText(data.getProduct_name());
//        price.setText(data.getPrice());
    }
}
