package com.example.krcho.clozet.promotion;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.krcho.clozet.R;
import com.example.krcho.clozet.request.Product;
import com.loopj.android.image.SmartImageView;

public class PromotionDialog extends AppCompatActivity {
    private SmartImageView image;
    private TextView brandText, productText, priceText;
    private Button cancelBtn, goBtn;
    private Product product;
    private ImageView category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion_dialog2);

        product = (Product) getIntent().getSerializableExtra("data");

        brandText = (TextView) findViewById(R.id.text_brand_name);
        brandText.setText(product.getProduct_brand());
        productText = (TextView) findViewById(R.id.text_product_name);
        productText.setText(product.getProduct_name());
        priceText = (TextView) findViewById(R.id.text_price);
        priceText.setText(product.getProduct_price() + "");

        cancelBtn = (Button) findViewById(R.id.btn_cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        goBtn = (Button) findViewById(R.id.btn_go);
        goBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(product.getMarket_URL())));
            }
        });
        image = (SmartImageView) findViewById(R.id.image_image);
        image.setImageUrl(product.getProduct_photo());

        category = (ImageView) findViewById(R.id.btn_clothes);
        if (product.isProduct_category()){
            category.setImageDrawable(getDrawable(R.drawable.btn_tshirts));
        }else {
            category.setImageDrawable(getDrawable(R.drawable.btn_skirt));
        }


    }
}
