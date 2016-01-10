package com.example.krcho.clozet.promotion;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.krcho.clozet.R;
import com.loopj.android.image.SmartImageView;

public class PromotionDialogActivity extends Activity {

//    intent.putExtra("name", name);
//    intent.putExtra("stock", stock);
//    intent.putExtra("url", url);
//    intent.putExtra("image", image);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_promotion_dialog);

        ((SmartImageView) findViewById(R.id.image))
                .setImageUrl(getIntent().getStringExtra("image"));

        ((TextView) findViewById(R.id.text))
                .setText(getIntent().getStringExtra("name") + "의 재고가 \n"
                        + getIntent().getIntExtra("stock", 0) + "점 남았습니다.");

        ((ImageButton) findViewById(R.id.cancle)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ((ImageButton) findViewById(R.id.go)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri = Uri.parse(getIntent().getStringExtra("url"));
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });


    }
}
