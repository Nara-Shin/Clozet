package org.sgen.club.sgenapplication;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by HOJUNGPC on 2016-01-09.
 */
public class CallListAdapter extends BaseAdapter {
    private ArrayList<CallListItem> items;
    private Context context;
    private static CallListAdapter adapter;
    public ArrayList<String> requestCodeArray;


    public static CallListAdapter getInstance(){
        if(adapter==null){
            adapter=new CallListAdapter();
        }
        return adapter;
    }



    private CallListAdapter(){

        items=new ArrayList<>();
        requestCodeArray=new ArrayList<>();

    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public CallListItem getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public void addItem(CallListItem item){
        items.add(item);
        this.notifyDataSetChanged();
    }
    public void removeItem(CallListItem item){
        items.remove(item);
        this.notifyDataSetChanged();

    }
    public void removeAllItem(){
        items.clear();
        this.notifyDataSetChanged();

    }


    public ArrayList<CallListItem> getAllItem(){

        return items;
    }
    public ArrayList<CallListItem> findItemsToCode(String clothCode){
        ArrayList<CallListItem> resultItems=new ArrayList<>();

        for(CallListItem item:items){
            if(item.getCode().equals(clothCode)){
                resultItems.add(item);
            }
        }
        return resultItems;
    }

    public void addAllItems(ArrayList<CallListItem> items){
        this.items.clear();
        for(CallListItem item:items){
            addItem(item);
        }
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null){
            if(items.get(position).getName()!=null){
                CallListItem item=items.get(position);

                LogProcess.normalLog(getClass(), item.toString());
                CallListView view=new CallListView(parent.getContext());
                view.setFrameLayout_view_call_list_color(item.getColor());
                view.setImageView_view_call_list_cloth(item.getImageURL_cloth());
                view.setImageView_view_call_list_etc(item.getStockURL());
                view.setTextView_view_call_list_amount(item.getAmount());
                view.setTextView_view_call_list_code(item.getCode());
                view.setTextView_view_call_list_name(item.getName());
                view.setTextView_view_call_list_price(item.getPrice());
                view.setTextView_view_call_list_size(item.getSize());
                convertView=view;
            }

        }


        return convertView;
    }
}
