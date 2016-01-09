package com.example.krcho.clozet.promotion;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.krcho.clozet.R;

import java.util.List;

public class PromotionAdapter extends ArrayAdapter<PromotionModel> {

	private Context context;
	private LayoutInflater inflater;
	private List<PromotionModel> items;
	private int itemLayout;
	private static Typeface fontBold, fontThin;

	public PromotionAdapter(Context context, int itemLayout, List<PromotionModel> items) {
		super(context, itemLayout, items);
		this.context = context;
		this.itemLayout = itemLayout;
		this.items = items;
		this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.fontBold = Typeface.createFromAsset(context.getAssets(), "NotoSansCJKkr-Bold.otf");
		this.fontThin = Typeface.createFromAsset(context.getAssets(), "NotoSansCJKkr-Thin.otf");
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public PromotionModel getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int id) {
		return id;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null) {
			convertView = inflater.inflate(itemLayout, parent, false);
		}

		TextView brandName = (TextView)convertView.findViewById(R.id.text_brand_name);
		TextView productName = (TextView)convertView.findViewById(R.id.text_product_name);
		TextView price = (TextView)convertView.findViewById(R.id.text_price);
		ImageView image = (ImageView)convertView.findViewById(R.id.image_image);

		PromotionModel model = items.get(position);

		brandName.setText(model.getBrandName());
		productName.setText(model.getProductName());
		price.setText(String.valueOf(model.getPrice()));
		image.setImageDrawable(model.getImage());

		brandName.setTypeface(fontThin);
		productName.setTypeface(fontBold);
		price.setTypeface(fontBold);

		return convertView;
	}

//	@Override
//	public void onBindViewHolder(ViewHolder holder, int position) {
//		PromotionModel item = items.get(position);
//		holder.itemView.setTag(item);
//		holder.brandName.setText(item.getBrandName());
//		holder.productName.setText(item.getProductName());
//		holder.price.setText(item.getPrice());
//		holder.image.setImageBitmap(item.getImage());
//
//	}
//
//	@Override
//	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//		View view = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent,false);
//		if(parent.getHeight() > 0){
//			view.getLayoutParams().height = parent.getHeight() / 6;
//		}
//		return new ViewHolder(view);
//	}
//
//	public static class ViewHolder extends RecyclerView.ViewHolder {
//
//		public TextView brandName;
//		public TextView productName;
//		public TextView price;
//		public ImageView image;
//
//		public ViewHolder(View itemView) {
//			super(itemView);
//
//			brandName = (TextView)itemView.findViewById(R.id.text_brand_name);
//			productName = (TextView)itemView.findViewById(R.id.text_product_name);
//			price = (TextView)itemView.findViewById(R.id.text_price);
//			image = (ImageView)itemView.findViewById(R.id.image_image);
//		}
//	}

}
