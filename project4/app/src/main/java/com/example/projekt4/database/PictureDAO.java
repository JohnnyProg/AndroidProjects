package com.example.projekt4.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PictureDAO {

    //adnotacja określająca, że metoda wstawia element do bazy
    //w przypadku konfliktu operacja będzie przerwana
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(Picture picture);

    //adnotacja pozwalająca na wykonanie dowolnego polecenia np. skasowania wszystkich elementów
    @Query("DELETE FROM pictures")
    void deleteAll();

    //metoda zwraca listę elementów opakowaną w pojemnik live data pozwalający na odbieranie
    //powiadomień o zmianie danych. Room wykonuje zapytani w innym wątku
    //live data powiadamia obserwatora w głównym wątku aplikacji
    @Query("SELECT * FROM pictures ORDER BY date ASC")
    LiveData<List<Picture>> getSortedPictures();

    @Delete
    void deletePicture(Picture picture);

    @Update
    void update(Picture picture);
}

