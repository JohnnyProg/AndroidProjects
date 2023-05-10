package com.example.projekt2.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.projekt2.dataBase.Element;
import com.example.projekt2.dataBase.ElementRepository;

import java.util.List;

public class ElementViewModel extends AndroidViewModel {
    private final ElementRepository mRepository;
    private final LiveData<List<Element>> mAllElements;
    public ElementViewModel(@NonNull Application application)
    {
        super(application);
        mRepository = new ElementRepository(application);
        mAllElements = mRepository.getAllElements();
//z repozytorium
    }
    public LiveData<List<Element>> getAllElements() {
        return mAllElements;
    }
    public void deleteAll() {
        mRepository.deleteAll();
    }
    public void insertElement(Element element) {
        mRepository.insert(element);
    }
    public void deleteElement(Element element) {
        mRepository.deleteElement(element);
    }
    public void update(Element element) {
        mRepository.update(element);
    }

}