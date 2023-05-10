package com.example.projekt2.dataBase;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ElementRepository {

    private ElementDao mElementDao;
    private LiveData<List<Element>> mAllElements;

    //konstruktor
    public ElementRepository(Application application) {
        ElementRoomDatabase elementRoomDatabase = ElementRoomDatabase.getDatabase(application);
        //repozytorium korzysta z obiektu DAO do odwołań
        //do bazy
        mElementDao = elementRoomDatabase.elementDao();
        mAllElements = mElementDao.getAlphabetizedElements();
    //… odczytanie wszystkich elementów
//        //z DAO
    }

    public LiveData<List<Element>> getAllElements() {
        return mAllElements;
    }


    public void deleteAll() {
        ElementRoomDatabase.databaseWriteExecutor.execute(() -> {
            mElementDao.deleteAll();
        });
    }

    public void insert(Element element) {
        ElementRoomDatabase.databaseWriteExecutor.execute(() -> {
            mElementDao.insert(element);
        });
    }

    public void deleteElement(Element element) {
        ElementRoomDatabase.databaseWriteExecutor.execute(() -> {
            mElementDao.deleteElement(element);
        });
    }

    public void update(Element element) {
        ElementRoomDatabase.databaseWriteExecutor.execute(() -> {
            mElementDao.update(element);
        });
    }
}