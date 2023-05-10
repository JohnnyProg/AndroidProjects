package com.example.projekt4.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.projekt4.R;
import com.example.projekt4.activities.fragments.FragmentDetails;
import com.example.projekt4.activities.fragments.FragmentList;
import com.example.projekt4.database.Picture;
import com.example.projekt4.viewModels.PictureViewModel;

public class ListActivity extends AppCompatActivity implements FragmentList.FragmentListener {

    private PictureViewModel viewModel;
    private FragmentDetails details;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Log.d("onCreate", "utworzono layout");
        viewModel = new ViewModelProvider(this).get(PictureViewModel.class);


        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            details = (FragmentDetails) getSupportFragmentManager().findFragmentById(R.id.fragmentDetails);
        } else {
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .add(R.id.fragment_container_view, FragmentList.class, null)
                        .commit();
            }
        }







    }

    @Override
    public void onSendMessage(Picture picture) {
        if(details != null) {
            details.setImage(picture);
        }else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view, FragmentDetails.class, null).commitNow();
            details = (FragmentDetails) getSupportFragmentManager().findFragmentById(R.id.fragment_container_view);
            details.setImage(picture);
        }
//        Toast.makeText(this, "przycisk klikniety", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        int orientation = getResources().getConfiguration().orientation;
        if( orientation == Configuration.ORIENTATION_PORTRAIT) {
            FragmentDetails test = null;
            try {
                test = (FragmentDetails) getSupportFragmentManager().findFragmentById(R.id.fragment_container_view);
            }catch (Exception e) {

            }
            if(test != null && test.isVisible()) { 
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view, FragmentList.class, null).commit();
                details = null;

                return;
            }
        }
        super.onBackPressed();
    }
}