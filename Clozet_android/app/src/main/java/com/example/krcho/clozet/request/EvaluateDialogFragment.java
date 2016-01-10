package com.example.krcho.clozet.request;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.RatingBar;

import com.example.krcho.clozet.R;

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
                dismiss();
                getActivity().finish();
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
