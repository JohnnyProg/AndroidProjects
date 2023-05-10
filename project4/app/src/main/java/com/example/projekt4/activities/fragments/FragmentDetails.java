package com.example.projekt4.activities.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.projekt4.R;
import com.example.projekt4.database.Picture;


public class FragmentDetails extends Fragment {
    private ImageView image;

    public FragmentDetails() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e("onvievcreated", "//////////////////");
        image = view.findViewById(R.id.imageView);
//        if (savedInstanceState != null) {
//
//        }
    }

    public void setImage(Picture p) {
        byte[] array = p.getImage();
        Bitmap compressedBitmap = BitmapFactory.decodeByteArray(array,0,array.length);
        Log.e("proba wyswietlenia", "proba wyswieltna");
        image = getView().findViewById(R.id.imageView);
        Log.d("test", "cos działa");
        if(image == null) {
            Log.e("SetImage", "nulem jest obraz");
        }
        image.setImageBitmap(compressedBitmap);
    }
}