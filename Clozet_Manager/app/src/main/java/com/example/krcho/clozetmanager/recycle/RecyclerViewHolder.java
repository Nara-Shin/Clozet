package com.example.krcho.clozetmanager.recycle;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.krcho.clozetmanager.R;

/**
 * Created by krcho on 2015-11-15.
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    TextView room, name;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        room = (TextView) itemView.findViewById(R.id.room);
        name = (TextView) itemView.findViewById(R.id.name);
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
    }
}
