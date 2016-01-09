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

import com.example.krcho.clozet.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EvaluateDialogFragment extends DialogFragment {

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
