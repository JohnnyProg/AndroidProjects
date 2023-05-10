package com.example.projekt4.activities.fragments;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.projekt4.R;
import com.example.projekt4.activities.MainActivity;
import com.example.projekt4.adapters.PictureListAdapter;
import com.example.projekt4.database.Picture;
import com.example.projekt4.viewModels.PictureViewModel;

public class FragmentList extends Fragment implements PictureListAdapter.onItemClickListener2{

    private PictureListAdapter adapter;
    private PictureViewModel viewModel;
    private FragmentListener fragmentListener;
    private ImageView image;
    public FragmentList() {
        // Required empty public constructor
    }

    @Override
    public void onItemClickListener2(Picture picture) {
//        Toast.makeText(getContext(), "wyswietlenie obrazu", Toast.LENGTH_SHORT).show();
//        byte[] array = picture.getImage();
//        Bitmap compressedBitmap = BitmapFactory.decodeByteArray(array,0,array.length);
//        image.setImageBitmap(compressedBitmap);
        fragmentListener.onSendMessage(picture);
    }


    public interface FragmentListener {
        void onSendMessage(Picture picture);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("onCreate", "onCreate fragment");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("onCreateView", "onCreateView inflate");
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d("onViewCreated", "juz konfiguracja");
        super.onViewCreated(view, savedInstanceState);
        adapter = new PictureListAdapter(getContext(),this);
        RecyclerView recyclerView = view.findViewById(R.id.lista_obrazow);
        if (recyclerView == null) {
//            Toast.makeText(getContext(), "Costam123", Toast.LENGTH_SHORT).show();
        }
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel = new ViewModelProvider(getActivity()).get(PictureViewModel.class);
        viewModel.getmAllPictures().observe(this, pictures -> {
            Toast.makeText(getContext(), "Costam123", Toast.LENGTH_SHORT).show();
            adapter.setPictureList(pictures);
        });
        image = view.findViewById(R.id.imageView2);
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        if (activity instanceof FragmentListener) {
            fragmentListener = (FragmentListener) activity;
        }else {
            throw new ClassCastException( activity.toString() + " musi implementować interfejs: ");
        }
    }
}