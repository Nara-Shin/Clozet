package org.sgen.club.sgenapplication;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Created by HOJUNGPC on 2016-01-09.
 */
public class CallListView extends LinearLayout {

    private Context context;
    private TextView textView_view_call_list_name,textView_view_call_list_code,textView_view_call_list_price,textView_view_call_list_size,textView_view_call_list_amount;
    private FrameLayout frameLayout_view_call_list_color;
    private ImageView imageView_view_call_list_etc,imageView_view_call_list_cloth;

    public CallListView(Context context) {
        super(context);
        init(context);
    }
    private void init(Context context){
        this.context=context;
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_call_list_item_1,this,true);

        imageView_view_call_list_cloth=(ImageView)findViewById(R.id.imageView_view_call_list_cloth);
        imageView_view_call_list_etc=(ImageView)findViewById(R.id.imageView_view_call_list_etc);
        frameLayout_view_call_list_color=(FrameLayout)findViewById(R.id.frameLayout_view_call_list_color);
        textView_view_call_list_amount=(TextView)findViewById(R.id.textView_view_call_list_amount);
        textView_view_call_list_code=(TextView)findViewById(R.id.textView_view_call_list_code);
        textView_view_call_list_price=(TextView)findViewById(R.id.textView_view_call_list_price);
        textView_view_call_list_size=(TextView)findViewById(R.id.textView_view_call_list_size);
        textView_view_call_list_name=(TextView)findViewById(R.id.textView_view_call_list_name);



    }

    public void setTextView_view_call_list_name(String text){
        textView_view_call_list_name.setText(text);
    }
    public void setTextView_view_call_list_code(String text){
        textView_view_call_list_code.setText(text);
    }
    public void setTextView_view_call_list_price(String text){
        textView_view_call_list_price.setText(text);
    }
    public void setTextView_view_call_list_size(String text){
        textView_view_call_list_size.setText(text);
    }
    public void setTextView_view_call_list_amount(String text){
        textView_view_call_list_amount.setText(text);
    }

    public void setImageView_view_call_list_etc(String imageURL){
        Glide.with(getContext()).load(imageURL).into(imageView_view_call_list_etc);

    }

    public void setImageView_view_call_list_cloth(String imageURL){
        Glide.with(getContext()).load(imageURL).into(imageView_view_call_list_cloth);

    }

    public void setFrameLayout_view_call_list_color(String color){
        try{
            if(color.contains("#")){
                frameLayout_view_call_list_color.setBackgroundColor(Color.parseColor(color));
            }else{
                frameLayout_view_call_list_color.setBackgroundColor(Color.parseColor("#"+color));
            }
        }catch (Exception e){

        }

    }

}
