package com.example.shinnara.clozet_remanager;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by ShinNara on 2015-11-25.
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    TextView room, name;
    private Button mAgreeButton;
    private Button mRejectButton;
    Button leftbtn, rigbtn;


    public RecyclerViewHolder(View itemView) {
        super(itemView);
        room = (TextView) itemView.findViewById(R.id.room);
        name = (TextView) itemView.findViewById(R.id.name);
        mAgreeButton = (Button) itemView.findViewById(R.id.bt_agree);
        mRejectButton = (Button) itemView.findViewById(R.id.bt_reject);
        leftbtn = (Button) itemView.findViewById(R.id.bt_re_agree);
        rigbtn = (Button) itemView.findViewById(R.id.bt_re_reject);
    }

    public void update(final Request data) {
        room.setText(data.getRoom() + "번 방");
        name.setText(data.getName() + "님");

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(itemView.getContext(), "클릭함", Toast.LENGTH_LONG).show();
            }
        });

        leftbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(itemView.getContext(), "수락이요", Toast.LENGTH_LONG).show();
            }
        });

        rigbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(itemView.getContext(), "즐~~~", Toast.LENGTH_LONG).show();
            }
        });
    }
}

