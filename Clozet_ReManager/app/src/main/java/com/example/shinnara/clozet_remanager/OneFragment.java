package com.example.shinnara.clozet_remanager;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * Created by ShinNara on 2015-11-24.
 */
public class OneFragment extends android.support.v4.app.DialogFragment implements View.OnClickListener {

    public static OneFragment newInstance() {
        OneFragment frag = new OneFragment();
        Bundle args = new Bundle();
        frag.setArguments(args);
        return frag;
    }

    public static boolean isScreenOn(Context context) {
        return ((PowerManager)context.getSystemService(Context.POWER_SERVICE)).isScreenOn();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int cont=1;
        int roomNum=1;

        View v = inflater.inflate(R.layout.onefragment, container, false);
        //방번호
        TextView room = (TextView) v.findViewById(R.id.num_room);
        room.setText("" + roomNum);
        //이미지
        ImageView image = (ImageView) v.findViewById(R.id.img_cloth);
        image.setImageResource(R.drawable.cloth);
        //사이즈
        ImageView size = (ImageView) v.findViewById(R.id.img_size);
        size.setImageResource(R.drawable.m);
        //색상
        LinearLayout color = (LinearLayout) v.findViewById(R.id.color);
        color.setBackgroundColor(Color.rgb(25,26,28));

        //요청횟수
        TextView count = (TextView) v.findViewById(R.id.count);
        count.setText(""+cont);


        Button button = (Button) v.findViewById(R.id.bt_one);
        button.setOnClickListener(this);

        Button buttonReject = (Button) v.findViewById(R.id.bt_reject);
        buttonReject.setOnClickListener(this);

        LinearLayout colorLayout = (LinearLayout) v.findViewById(R.id.color);
        colorLayout.setBackgroundColor(Color.rgb(100, 100, 100));

        return v;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.bt_one:
                Toast.makeText(getActivity(), "Agree", Toast.LENGTH_SHORT)
                        .show();
                dismiss();
                break;
            case R.id.bt_reject:
                Toast.makeText(getActivity(), "Reject", Toast.LENGTH_SHORT).show();
                dismiss();

        }

    }

}
