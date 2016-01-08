package com.example.krcho.clozet;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by HOJU on 2016-01-03.
 */
public class MainSideView extends LinearLayout implements View.OnClickListener {



    private Context context;
    private ImageView imageView_sidebar_logo,imageView_sidebar_promotion,imageView_sidebar_gallery,imageView_sidebar_setting,imageView_sidebar_cancel;
    public MainSideView(Context context) {

        super(context);
        init(context);
    }

    public MainSideView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }
    public void init(Context context){
        this.context=context;
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_main_sidebar, this, true);
        imageView_sidebar_promotion=(ImageView)findViewById(R.id.imageView_sidebar_promotion);
        imageView_sidebar_gallery=(ImageView)findViewById(R.id.imageView_sidebar_gallery);
        imageView_sidebar_logo=(ImageView)findViewById(R.id.imageView_sidebar_logo);
        imageView_sidebar_setting=(ImageView)findViewById(R.id.imageView_sidebar_setting);
        imageView_sidebar_cancel=(ImageView)findViewById(R.id.imageView_sidebar_cancel);
        this.bringToFront();





    }


    @Override
    public void onClick(View v) {
        int view_id=v.getId();

        if(view_id==imageView_sidebar_cancel.getId()){



        }else if(view_id==imageView_sidebar_promotion.getId()){
            imageView_sidebar_promotion.setImageResource(R.drawable.btn_promotion_click);
            imageView_sidebar_setting.setImageResource(R.drawable.btn_setting_unclick);
            imageView_sidebar_gallery.setImageResource(R.drawable.btn_gallery_unclick);

        }else if(view_id==imageView_sidebar_gallery.getId()){
            imageView_sidebar_promotion.setImageResource(R.drawable.btn_promotion_unclick);
            imageView_sidebar_setting.setImageResource(R.drawable.btn_setting_unclick);
            imageView_sidebar_gallery.setImageResource(R.drawable.btn_gallery_click);

        }else if(view_id==imageView_sidebar_setting.getId()){
            imageView_sidebar_promotion.setImageResource(R.drawable.btn_promotion_unclick);
            imageView_sidebar_setting.setImageResource(R.drawable.btn_setting_click);
            imageView_sidebar_gallery.setImageResource(R.drawable.btn_gallery_unclick);

        }else if(view_id==imageView_sidebar_logo.getId()){

        }

    }

}