package com.example.shinnara.clozet_remanager;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

    public RecyclerViewHolder(View itemView) {

        super(itemView);
        room = (TextView) itemView.findViewById(R.id.room);
        name = (TextView) itemView.findViewById(R.id.name);
        mAgreeButton = (Button) itemView.findViewById(R.id.bt_agree);
        mRejectButton = (Button) itemView.findViewById(R.id.bt_reject);

    }

    public void update(final Request data) {
        room.setText(data.getRoom() + "번 방");
        name.setText(data.getName() + "님");

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(itemView.getContext(), "Click", Toast.LENGTH_LONG).show();
            }
        });

        mAgreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(itemView.getContext(), "Agree", Toast.LENGTH_LONG).show();
                String str = room.getText().toString();
                Log.d("countqqq", TwoFragment.list.size() + "");
                TwoFragment.list.remove(data);
                Log.d("countqqq", TwoFragment.list.size() + "");
                for( Request r: TwoFragment.list){
                    Log.d("countqqq", r.getRoom() + "");
                }
                ExampleRecyclerAdapter adapter=new ExampleRecyclerAdapter(TwoFragment.list);
                TwoFragment.recyclerView.setAdapter(adapter);

            }
        });

        mRejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(itemView.getContext(), "Reject", Toast.LENGTH_LONG).show();
                String str = room.getText().toString();
                Log.d("countqqq", TwoFragment.list.size() + "");
                TwoFragment.list.remove(data);
                Log.d("countqqq", TwoFragment.list.size() + "");
                for( Request r: TwoFragment.list){
                    Log.d("countqqq", r.getRoom() + "");
                }
                ExampleRecyclerAdapter adapter=new ExampleRecyclerAdapter(TwoFragment.list);
                TwoFragment.recyclerView.setAdapter(adapter);
            }
        });
    }
}

