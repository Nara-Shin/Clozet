package com.example.shinnara.clozet_remanager;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by ShinNara on 2015-11-24.
 */
public class TwoFragment extends Fragment implements View.OnClickListener {

    RecyclerView recyclerView;
    LinearLayoutManager recyclerLayoutManager;
    ExampleRecyclerAdapter adapter;
    ArrayList<Request> list = new ArrayList<>();
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.twofragment, container, false);

        for (int i = 0; i < 10; i++) {
            Request temp = new Request();
            temp.setRoom(i);
            temp.setName("test");

            list.add(temp);
        }

        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view_example);
        recyclerLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(recyclerLayoutManager);
        adapter = new ExampleRecyclerAdapter(list);
        recyclerView.setAdapter(adapter);






        return v;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {



        }

    }

}
