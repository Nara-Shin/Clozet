package com.example.krcho.clozet.gallery;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.krcho.clozet.R;

public class GalleryMatciongFragment extends Fragment {

    ImageView imageView;

    public GalleryMatciongFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery_matciong, container, false);

        imageView = (ImageView) view.findViewById(R.id.gallery_matching_image);

        return view;
    }

}
