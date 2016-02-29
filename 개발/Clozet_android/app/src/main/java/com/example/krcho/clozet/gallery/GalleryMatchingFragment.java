package com.example.krcho.clozet.gallery;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.krcho.clozet.R;

public class GalleryMatchingFragment extends Fragment {

    ImageView imageView;
    Bitmap bitmap;

    public GalleryMatchingFragment(Bitmap bitmap) {
        this.bitmap = bitmap;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery_matciong, container, false);

        imageView = (ImageView) view.findViewById(R.id.gallery_matching_image);
        imageView.setImageBitmap(bitmap);

        return view;
    }

}
