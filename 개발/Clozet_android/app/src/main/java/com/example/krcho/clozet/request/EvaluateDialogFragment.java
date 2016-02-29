package com.example.krcho.clozet.request;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.krcho.clozet.MyAccount;
import com.example.krcho.clozet.R;
import com.example.krcho.clozet.network.CommonHttpClient;
import com.example.krcho.clozet.network.NetDefine;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 */
public class EvaluateDialogFragment extends DialogFragment {

    RatingBar rating;
    ImageButton img_btn;

    public static EvaluateDialogFragment newInstance() {
        EvaluateDialogFragment instance = new EvaluateDialogFragment();
        Bundle args = new Bundle();
        instance.setArguments(args);
        return instance;
    }

    public EvaluateDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_evaluate_dialog, container);
        rating = (RatingBar) v.findViewById(R.id.rating);
        img_btn = (ImageButton) v.findViewById(R.id.ok_btn);
        img_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                member_code=6자리 회원코드
//                star_count=별개수

                RequestParams params = new RequestParams();
                try {
                    params.put("member_code", MyAccount.getInstance().getMember_code());
                } catch (Exception e) {
                    params.put("member_code", PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()).getString(MyAccount.MEMBERCODE, ""));
                }
                params.put("star_count", (int) rating.getRating());
                CommonHttpClient.post(NetDefine.EVALUATION_CLERK, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        Log.d("response", response.toString());
                        try {
                            if (response.getString("confirm_message").equals("success")) {
                                dismiss();
                                getActivity().finish();
                            } else {
                                Toast.makeText(getActivity().getApplicationContext(), "전송에 실패했습니다.", Toast.LENGTH_LONG).show();
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        return v;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }
}
