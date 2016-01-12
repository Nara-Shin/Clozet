package com.example.krcho.clozet.request;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.krcho.clozet.R;
import com.loopj.android.image.SmartImageView;

/**
 * Created by krcho on 2015-11-15.
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    SmartImageView image;
    TextView brand, title;
    LinearLayout sizes, colors;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        image = (SmartImageView) itemView.findViewById(R.id.image);
        brand = (TextView) itemView.findViewById(R.id.brand);
        title = (TextView) itemView.findViewById(R.id.title);
        sizes = (LinearLayout) itemView.findViewById(R.id.size_container);
        colors = (LinearLayout) itemView.findViewById(R.id.color_container);
    }

    public void update(final Product data) {
        image.setImageUrl(data.getProduct_photo());
        brand.setText(data.getProduct_brand());
        title.setText(data.getProduct_name());

        sizes.removeAllViews();

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int)convertDpToPixel(36, itemView.getContext()), (int)convertDpToPixel(36, itemView.getContext()));
        layoutParams.setMargins(0, 0, 5, 0);
        RelativeLayout.LayoutParams colorViewParams = new RelativeLayout.LayoutParams((int)convertDpToPixel(12, itemView.getContext()), (int)convertDpToPixel(12, itemView.getContext()));

        for (final String item : data.getSizes()){
            Button size = new Button(itemView.getContext());
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
                }
            });
            this.sizes.addView(size);
        }

        colors.removeAllViews();

        for (final String item : data.getColors()){
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
                }
            });
            view.setBackgroundColor(Color.parseColor("#" + item));
            color.addView(view);

            this.colors.addView(color);
        }
    }

    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }
}
