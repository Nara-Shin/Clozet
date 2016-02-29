package com.example.krcho.clozet.gallery;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.krcho.clozet.MyAccount;
import com.example.krcho.clozet.R;
import com.example.krcho.clozet.network.CommonHttpClient;
import com.example.krcho.clozet.network.NetDefine;
import com.example.krcho.clozet.promotion.PromotionModel;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.TextViewHolder> {
	private Context context;
	private int itemLayout;
	private List<GalleryModel> items;
	private int mode;

	public GalleryAdapter(Context context, int itemLayout, List<GalleryModel> items, int mode) {
		this.context = context;
		this.itemLayout = itemLayout;
		this.items = items;
		this.mode = mode;
	}

	@Override
	public TextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
		return new TextViewHolder(view);
	}

	@Override
	public void onBindViewHolder(final TextViewHolder holder, final int position) {

		holder.image.setImageBitmap(items.get(position).getImage());

		if (items.get(position).getBarcode() == null) {
			holder.like.setVisibility(View.GONE);
		}

		if (items.get(position).isLike()) {
			holder.like.setImageResource(R.drawable.btn_like_click);
		} else {
			holder.like.setImageResource(R.drawable.btn_like);
		}

		holder.image.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mode == 0) {
					Intent intent = new Intent(context, GalleryDetailActivity.class);
					intent.putExtra("fileName", items.get(position).getFileName());
					intent.putExtra("barcode", items.get(position).getBarcode());
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.startActivity(intent);
				} else if (mode == 1) {
					Intent intent = new Intent(context, GalleryMatchingActivity.class);
					intent.putExtra("fileName", items.get(position).getFileName());
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.startActivity(intent);
				}
			}
		});

		holder.like.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				RequestParams params = new RequestParams();
				params.put("barcode", items.get(position).getBarcode());
				try {
					params.put("member_code", MyAccount.getInstance().getMember_code());
				} catch (Exception e) {
					params.put("member_code", PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext()).getString(MyAccount.MEMBERCODE, ""));
				}

				CommonHttpClient.post(NetDefine.SET_LIKE, params, new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
						super.onSuccess(statusCode, headers, response);
						try {
							items.get(position).setLike(response.getInt("status") == 1);
							if (items.get(position).isLike()) {
								holder.like.setImageResource(R.drawable.btn_like_click);
							} else {
								holder.like.setImageResource(R.drawable.btn_like);
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}

					}
				});
			}
		});
	}

	@Override
	public int getItemCount() {
		return items.size();
	}

	public class TextViewHolder extends RecyclerView.ViewHolder {
		private ImageView image;
		private ImageView like;

		public TextViewHolder(View itemView) {
			super(itemView);
			image = (ImageView)itemView.findViewById(R.id.gallery_image);
			like = (ImageView)itemView.findViewById(R.id.gallery_like);
		}
	}
}