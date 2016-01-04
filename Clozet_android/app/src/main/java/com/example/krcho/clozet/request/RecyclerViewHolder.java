package com.example.krcho.clozet.request;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.krcho.clozet.R;
import com.loopj.android.image.SmartImageView;

/**
 * Created by krcho on 2015-11-15.
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    SmartImageView image;
    TextView brand, title, price;
    LinearLayout sizes, colors;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        image = (SmartImageView) itemView.findViewById(R.id.image);
        brand = (TextView) itemView.findViewById(R.id.brand);
        title = (TextView) itemView.findViewById(R.id.title);
        price = (TextView) itemView.findViewById(R.id.price);
        sizes = (LinearLayout) itemView.findViewById(R.id.size_container);
        colors = (LinearLayout) itemView.findViewById(R.id.color_container);
    }

    public void update(final Product data) {
        image.setImageUrl(data.getProduct_photo());
        brand.setText(data.getProduct_brand());
        title.setText(data.getProduct_name());
        price.setText(data.getProduct_price()+"");

        for(String item : data.getSizes()){
            Button size = new Button(itemView.getContext());
            size.setBackgroundColor(Color.BLACK);
            size.setTextColor(Color.WHITE);
            size.setText(item);
            size.setLayoutParams(new LinearLayout.LayoutParams(200, 200));
            this.sizes.addView(size);
        }

        for(String item : data.getColors()){
            Button color = new Button(itemView.getContext());
            color.setBackgroundColor(Color.parseColor("#"+item));
            color.setLayoutParams(new LinearLayout.LayoutParams(200, 200));
            this.colors.addView(color);
        }
    }
}
