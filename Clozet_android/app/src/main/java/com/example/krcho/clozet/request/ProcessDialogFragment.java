package com.example.krcho.clozet.request;


import android.app.Dialog;
import android.content.DialogInterface;
import android.media.Image;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class ProcessDialogFragment extends DialogFragment {
    public static ProcessDialogFragment instance;

    public static ImageView imageView;
    public static TextView textView;
    public static ImageButton imageButton;
    public static int processnum = 1;

    public static ProcessDialogFragment newInstance(int num) {
        instance = new ProcessDialogFragment();
        Bundle args = new Bundle();
        args.putInt("process", num);
        instance.setArguments(args);
        return instance;
    }

    public static ProcessDialogFragment getInstance() throws Exception{
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
                        dismiss();
                        break;
                    case 2:
                        dismiss();
                        break;
                    case 3:
                        EvaluateDialogFragment dialogFragment = EvaluateDialogFragment.newInstance();
                        dialogFragment.show(getFragmentManager(), "test");
                        dismiss();
                        break;
                }

            }
        });

        switch (processnum) {
            case 1:
                imageView.setImageDrawable(getActivity().getDrawable(R.drawable.push_1));
                break;
            case 2:
                imageView.setImageDrawable(getActivity().getDrawable(R.drawable.push_2));
                break;
            case 3:
                imageView.setImageDrawable(getActivity().getDrawable(R.drawable.push_3));
                imageButton.setImageDrawable(getActivity().getDrawable(R.drawable.ok_btn));
                break;
        }
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

    public void goNext() {
        instance = null;
        if (processnum < 3){
            ProcessDialogFragment frag = ProcessDialogFragment.newInstance(processnum+1);
            frag.show(getFragmentManager(), "test");
        }
        dismiss();
    }
}
