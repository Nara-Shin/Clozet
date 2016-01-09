package com.example.krcho.clozet.request;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        return inflater.inflate(R.layout.fragment_evaluate_dialog, container, false);
    }

}
