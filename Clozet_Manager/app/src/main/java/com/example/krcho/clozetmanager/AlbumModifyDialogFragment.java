package com.example.krcho.clozetmanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.krcho.clozetmanager.R;

import org.json.JSONObject;


public class AlbumModifyDialogFragment extends android.support.v4.app.DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters
    EditText edt_albumName;
    private ImageView iv_button;
    AlertDialog dialogs;

    public static AlbumModifyDialogFragment newInstance() {
        AlbumModifyDialogFragment fragment = new AlbumModifyDialogFragment();
        Bundle args = new Bundle();
//        args.putString("name", name);
//        args.putInt("tootid", tootid);
//        args.putInt("albumId", albumId);
        fragment.setArguments(args);
        return fragment;
    }

    private String name;
    private int tootid;
    private int albumId;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getArguments() != null) {
            name = getArguments().getString("name");
            tootid = getArguments().getInt("tootid");
            albumId = getArguments().getInt("albumId");
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomDialog);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_album_modify_dialog, null);


        iv_button = (ImageView) view.findViewById(R.id.btn_change_category);
        iv_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        edt_albumName = (EditText) view.findViewById(R.id.edt_dialog_album_create_albumname);
        builder.setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, int which) {
                                if (isChecked()) {
                                }
                            }
                        }

                ).

                setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MyOndismiss(dialog);
                            }
                        }

                );

        edt_albumName.setText(name);


        dialogs = builder.create();


        return dialogs;


    }



    private boolean isChecked() {

        if (edt_albumName.getText().toString().trim().length() == 0) {

            return false;
        }
        return true;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDismiss(DialogInterface dialog) {
    }

    public void MyOndismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onCancel(DialogInterface dialog) {
        MyOndismiss(dialog);
    }
}