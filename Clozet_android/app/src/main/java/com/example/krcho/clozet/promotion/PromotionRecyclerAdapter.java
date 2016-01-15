package com.example.krcho.clozet.promotion;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.krcho.clozet.R;
import com.example.krcho.clozet.request.Product;
import com.example.krcho.clozet.request.RecyclerViewHolder;

import java.util.ArrayList;

/**
 * Created by krmpr on 16. 1. 15..
 */
public class PromotionRecyclerAdapter extends RecyclerView.Adapter<PromotionViewHolder> {

    private ArrayList<Product> list = new ArrayList<>();

    PromotionDelegate delegate;
    public PromotionRecyclerAdapter(ArrayList<Product> l, PromotionDelegate dele){
        super();
        setList(l);
        this.delegate = dele;
    }

    public void setList(ArrayList<Product> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public PromotionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("test", "onCreateViewHolder");
        final Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_promotion, parent, false);
        return new PromotionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PromotionViewHolder holder, int position) {
        Log.d("test", "update");
        holder.update(list.get(position), delegate);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
