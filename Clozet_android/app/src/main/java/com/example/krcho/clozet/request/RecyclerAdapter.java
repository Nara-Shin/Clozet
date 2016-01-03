package com.example.krcho.clozet.request;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.krcho.clozet.R;

import java.util.ArrayList;

/**
 * Created by krcho on 2015-11-15.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    private ArrayList<Product> list = new ArrayList<>();

    public RecyclerAdapter(ArrayList<Product> list) {
        Log.d("test", "construct");
        this.list.clear();
        this.list.addAll(list);
    }

    public void setList(ArrayList<Product> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("test", "onCreateViewHolder");
        final Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_request, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        Log.d("test", "update");
        holder.update(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
