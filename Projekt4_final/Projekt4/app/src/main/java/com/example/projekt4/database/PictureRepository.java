package com.example.projekt4.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class PictureRepository {
    private PictureDAO mPictureDao;
    private LiveData<List<Picture>> mAllPictures;

    public PictureRepository(Application application) {
        PictureRoomDatabase pictureRoomDatabase = PictureRoomDatabase.getDatabase(application);

        mPictureDao = pictureRoomDatabase.elementDao();
        mAllPictures = mPictureDao.getSortedPictures();
    }

    public LiveData<List<Picture>> getAllPictures() {
        return mAllPictures;
    }

    public void deleteAll() {
        PictureRoomDatabase.databaseWriteExecutor.execute(() -> {
            mPictureDao.deleteAll();
        });
    }

    public void insert(Picture picture) {
        PictureRoomDatabase.databaseWriteExecutor.execute(() -> {
            mPictureDao.insert(picture);
        });
    }

    public void deletePicture(Picture picture) {
        PictureRoomDatabase.databaseWriteExecutor.execute(() -> {
            mPictureDao.deletePicture(picture);
        });
    }

    public void update(Picture picture){
        PictureRoomDatabase.databaseWriteExecutor.execute(() -> {
            mPictureDao.update(picture);
        });
    }
}
