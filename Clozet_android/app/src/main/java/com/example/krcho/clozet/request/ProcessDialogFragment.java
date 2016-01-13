package com.example.krcho.clozet.request;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

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
public class ProcessDialogFragment extends DialogFragment {
    public static ProcessDialogFragment instance;

    public static ImageView imageView;
    public static TextView textView;
    public static ImageButton imageButton;
    public static int processnum = 1;
    public static int items = 1;
    public static int request_code;

    public static ProcessDialogFragment newInstance(int num, int request_code) {
        instance = new ProcessDialogFragment();
        Bundle args = new Bundle();
        if (num < 0) {
            args.putInt("process", 1);
            args.putInt("items", num * -1);
        } else {
            args.putInt("process", num);
        }
        args.putInt("request_code", request_code);
        instance.setArguments(args);
        return instance;
    }

    public static ProcessDialogFragment getInstance() throws Exception {
        if (instance == null) {
            throw new Exception();
        }
        return instance;
    }

    public ProcessDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        processnum = getArguments().getInt("process");
        items = getArguments().getInt("items");
        request_code = getArguments().getInt("request_code");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_process_dialog, container);

        textView = (TextView) v.findViewById(R.id.text);
        imageView = (ImageView) v.findViewById(R.id.image);
        imageButton = (ImageButton) v.findViewById(R.id.imgbtn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (processnum) {
                    case 1:
                        cancel();
                        break;
                    case 2:
                        cancel();
                        break;
                    case 3:
                        EvaluateDialogFragment dialogFragment = EvaluateDialogFragment.newInstance();
                        dialogFragment.show(getFragmentManager(), "test");
                        dismiss();
                        break;
                    default:
                        cancel();
                }

            }
        });

        switch (processnum) {
            case 1:
                if (items < 1) {
                    textView.setText("해당 상품을 교환 요청 하였습니다.");
                } else {
                    textView.setText("선택한 " + items + "개의 상품을 교환 요청 하였습니다.");
                }
                imageView.setImageDrawable(getActivity().getDrawable(R.drawable.push_1));
                break;
            case 2:
                textView.setText("직원이 요청을 수락하였습니다.");
                imageView.setImageDrawable(getActivity().getDrawable(R.drawable.push_2));
                break;
            case 3:
                textView.setText("배달완료!");
                imageView.setImageDrawable(getActivity().getDrawable(R.drawable.push_3));
                imageButton.setImageDrawable(getActivity().getDrawable(R.drawable.ok_btn));
                break;

        }
        return v;
    }

    public void cancel() {
        RequestParams params = new RequestParams();
        params.put("request_code", request_code);
        params.put("confirm", "cancel");

        CommonHttpClient.post(NetDefine.CONFIRM_CHANGE, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response.getString("confirm_message").equals("success")) {
                        dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
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

    public void goNext() {
        instance = null;
        if (processnum < 3) {
            ProcessDialogFragment frag = ProcessDialogFragment.newInstance(processnum + 1, request_code);
            frag.show(getFragmentManager(), "test");
        }
        dismiss();
    }
}
