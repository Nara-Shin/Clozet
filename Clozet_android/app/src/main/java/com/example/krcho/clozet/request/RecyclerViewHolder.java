package com.example.krcho.clozet.request;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.krcho.clozet.R;
import com.example.krcho.clozet.network.CommonHttpClient;
import com.loopj.android.image.SmartImageView;

import java.net.URI;

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

    public void update(final Request data) {
        image.setImageUrl(data.getPhoto_url());
        brand.setText(data.getBrand());
        title.setText(data.getName());
//        price.setText(data.getPrice());
    }
}
