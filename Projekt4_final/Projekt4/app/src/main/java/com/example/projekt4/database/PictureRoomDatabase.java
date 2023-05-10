package com.example.projekt4.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Picture.class}, version = 1, exportSchema = false)
public abstract class PictureRoomDatabase extends RoomDatabase
{
    //abstrakcyjna metoda zwracająca DAO
    public abstract PictureDAO elementDao();
    //implementacja singletona
    private static volatile PictureRoomDatabase INSTANCE;

    //konstruktor singletona
    static PictureRoomDatabase getDatabase(final Context context) {
        //tworzymy nowy obiekt tylko gdy żaden nie istnieje
        if (INSTANCE == null) {
            synchronized (PictureRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), PictureRoomDatabase.class,"obrazki")
                            //ustawienie obiektu obsługującego
                            //zdarzenia związane z bazą
                            .addCallback(sRoomDatabaseCallback)
                            //najprostsza migracja – skasowanie
                            //i utworzenie bazy od nowa
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    //usługa wykonawcza do wykonywania zadań w osobnym wątku
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    //obiekt obsługujący wywołania zwrotne (call backs) związane ze zdarzeniami bazy danych
    //np. onCreate, onOpen

    private static RoomDatabase.Callback  sRoomDatabaseCallback = new RoomDatabase.Callback() {
        //uruchamiane przy tworzeniu bazy (pierwsze
        //uruchomienie aplikacji, gdy baza nie istnieje)

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            //wykonanie operacji w osobnym wątku. Parametrem metody execute() jest obiekt implementujący //interfejs Runnable, może być zastąpiony //wyrażeniem lambda
            databaseWriteExecutor.execute(() -> {
                PictureDAO dao = INSTANCE.elementDao();
//                Picture p1 = new Picture("tytol obrazu", "Jan Gryta", new Date(654654654), new byte[3]);
//                dao.insert(p1);
//                Picture p2 = new Picture("tytol obrazu 2", "Jan Paweł II", new Date(98798798), new byte[3]);
//                dao.insert(p2);
                //tworzenie elementów (obiektów klasy Element)
                //i dodawanie ich do bazy
                //za pomocą metody insert() z obiektu dao
                //tutaj możemy określić początkową zawartość
                //bazy danych
                //...
            });
        }
    };
}
