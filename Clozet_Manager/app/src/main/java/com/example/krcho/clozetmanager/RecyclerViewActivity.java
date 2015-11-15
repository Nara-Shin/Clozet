package com.example.krcho.clozetmanager;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ShinNara on 2015-11-13.
 */
public class RecyclerViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //윈도우 매니저 : 최상단 뷰 추가.
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);
        setContentView(R.layout.recycler_main);

        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        List<Recycler_item> items=new ArrayList<>();
        Recycler_item[] item = new Recycler_item[5];
        item[0]=new Recycler_item(R.drawable.a,"#1");
        item[1]=new Recycler_item(R.drawable.b,"#2");
        item[2]=new Recycler_item(R.drawable.c,"#3");
        item[3]=new Recycler_item(R.drawable.d,"#4");
        item[4]=new Recycler_item(R.drawable.e,"#5");

        int i;
        for(i=0;i<5;i++){
            items.add(item[i]);
        }

        recyclerView.setAdapter(new RecyclerAdapter(getApplicationContext(),items,R.layout.recycler_main));
    }

   /* public RecyclerViewActivity(){

    }*/

    /*
     public CustomDialog(Context context , String content ,
			View.OnClickListener leftListener ,	View.OnClickListener rightListener) {
		super(context , android.R.style.Theme_Translucent_NoTitleBar);
		//this.mTitle = title;
		this.mContent = content;
		this.mLeftClickListener = leftListener;
		this.mRightClickListener = rightListener;
	}*/


}
