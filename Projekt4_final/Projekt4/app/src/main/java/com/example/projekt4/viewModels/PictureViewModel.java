package com.example.projekt4.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.projekt4.database.Picture;
import com.example.projekt4.database.PictureRepository;

import java.util.List;

public class PictureViewModel extends AndroidViewModel {
    private final PictureRepository repository;
    private LiveData<List<Picture>> mAllElements;
    public PictureViewModel(@NonNull Application application) {
        super(application);
        this.repository = new PictureRepository(application);
        mAllElements = repository.getAllPictures();
    }

    public LiveData<List<Picture>> getmAllPictures() {
        return mAllElements;
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public void insert(Picture picture) {
        repository.insert(picture);
    }

    public void deletePicture(Picture picture) {
        repository.deletePicture(picture);
    }
}
