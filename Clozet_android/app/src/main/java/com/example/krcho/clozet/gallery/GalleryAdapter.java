package com.example.krcho.clozet.gallery;

import android.content.Context;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
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
		final ImageView like = (ImageView)convertView.findViewById(R.id.gallery_like);

		final GalleryModel model = items.get(position);

		image.setImageBitmap(model.getImage());

		if (GalleryActivity.items.get(position).getBarcode() == null) {
			like.setVisibility(View.GONE);
		}

		if (model.isLike()) {
			like.setImageDrawable(context.getDrawable(R.drawable.btn_like_click));
		} else {
			like.setImageDrawable(context.getDrawable(R.drawable.btn_like));
		}

		like.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				RequestParams params = new RequestParams();
				params.put("barcode", model.getBarcode());
				try {
					params.put("member_code", MyAccount.getInstance().getMember_code());
				} catch (Exception e) {
					params.put("member_code", PreferenceManager.getDefaultSharedPreferences(getContext().getApplicationContext()).getString(MyAccount.MEMBERCODE, ""));
				}

				CommonHttpClient.post(NetDefine.SET_LIKE, params, new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
						super.onSuccess(statusCode, headers, response);
						try {
							model.setLike(response.getInt("status") == 1);
							if (model.isLike()) {
								like.setImageDrawable(getContext().getApplicationContext().getDrawable(R.drawable.btn_like_click));
							} else {
								like.setImageDrawable(getContext().getApplicationContext().getDrawable(R.drawable.btn_like));
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}

					}
				});
			}
		});

		return convertView;
	}

}
