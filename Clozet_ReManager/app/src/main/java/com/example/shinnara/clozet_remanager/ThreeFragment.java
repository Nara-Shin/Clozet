package com.example.shinnara.clozet_remanager;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by ShinNara on 2015-11-24.
 */
public class ThreeFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.threefragment, container, false);

        Button button = (Button) v.findViewById(R.id.bt_three);
        button.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.bt_three:
                Toast.makeText(getActivity(), "ThreeFragment", Toast.LENGTH_SHORT)
                        .show();
                break;

        }

    }

}
