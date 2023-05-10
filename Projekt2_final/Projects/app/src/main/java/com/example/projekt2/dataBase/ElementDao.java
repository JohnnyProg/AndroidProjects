package com.example.projekt2.dataBase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ElementDao {

    //adnotacja określająca, że metoda wstawia element do bazy
    //w przypadku konfliktu operacja będzie przerwana
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(Element element);

    //adnotacja pozwalająca na wykonanie dowolnego polecenia np. skasowania wszystkich elementów
    @Query("DELETE FROM nazwa_tabeli")
    void deleteAll();

    //metoda zwraca listę elementów opakowaną w pojemnik live data pozwalający na odbieranie
    //powiadomień o zmianie danych. Room wykonuje zapytani w innym wątku
    //live data powiadamia obserwatora w głównym wątku aplikacji
    @Query("SELECT * FROM nazwa_tabeli ORDER BY model ASC")
    LiveData<List<Element>> getAlphabetizedElements();

    @Delete
    void deleteElement(Element element);

    @Update
    void update(Element element);
}
