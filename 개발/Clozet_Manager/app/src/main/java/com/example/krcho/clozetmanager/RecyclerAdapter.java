package com.example.krcho.clozetmanager;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.krcho.clozetmanager.popup.CustomDialog;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by ShinNara on 2015-11-13.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    Context context;
    List<Recycler_item> items;
    int item_layout;

    public RecyclerAdapter(Context context, List<Recycler_item> items, int item_layout) {
        this.context = context;
        this.items = items;
        this.item_layout = item_layout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_cardview, null);
        setClickListener(mAgreeClickListner, mRejectClickListener);
        return new ViewHolder(v);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Recycler_item item = items.get(position);
        Drawable drawable = context.getResources().getDrawable(item.getImage());
        holder.image.setBackground(drawable);
        holder.title.setText(item.getTitle());
        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, item.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setClickListener(View.OnClickListener left , View.OnClickListener right){
        if(left!=null && right!=null){
            mAgreeButton.setOnClickListener(left);
            mRejectButton.setOnClickListener(right);
        }else if(left!=null && right==null){
            mAgreeButton.setOnClickListener(left);
        }else {

        }
    }


    private View.OnClickListener agreeClickListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //수락버튼 누른 경우
            Toast.makeText(context, "수락!!", Toast.LENGTH_SHORT).show();
        }
    };

    private View.OnClickListener rightClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //거절버튼 누른 경우
            Toast.makeText(context, "거절!!", Toast.LENGTH_SHORT).show();
        }
    };

    private Button mAgreeButton;
    private Button mRejectButton;

    private View.OnClickListener mAgreeClickListner;
    private View.OnClickListener mRejectClickListener;

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;
        CardView cardview;
        Button leftButton;
        Button rightButton;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            title = (TextView) itemView.findViewById(R.id.title);
            cardview = (CardView) itemView.findViewById(R.id.cardview);
            leftButton = (Button) itemView.findViewById(R.id.bt_agree);
            rightButton = (Button) itemView.findViewById(R.id.bt_reject);
        }
    }
}
