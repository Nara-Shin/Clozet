package com.example.krcho.clozet.gallery;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.krcho.clozet.R;
import com.example.krcho.clozet.promotion.PromotionModel;

import java.util.List;

public class GalleryAdapter extends ArrayAdapter<GalleryModel> {

	private Context context;
	private LayoutInflater inflater;
	private List<GalleryModel> items;
	private int itemLayout;
	private static Typeface fontBold, fontThin;

	public GalleryAdapter(Context context, int itemLayout, List<GalleryModel> items) {
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
	public GalleryModel getItem(int position) {
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

		ImageView image = (ImageView)convertView.findViewById(R.id.gallery_image);
		ImageView like = (ImageView)convertView.findViewById(R.id.gallery_like);

		GalleryModel model = items.get(position);

		image.setImageDrawable(model.getImage());
		if (model.getLike()) {
			like.setImageDrawable(context.getDrawable(R.drawable.btn_like_click));
		} else {
			like.setImageDrawable(context.getDrawable(R.drawable.btn_like));
		}

		return convertView;
	}

}
