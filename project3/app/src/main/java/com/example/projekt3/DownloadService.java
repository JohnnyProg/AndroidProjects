package com.example.projekt3;

import static android.app.Notification.FOREGROUND_SERVICE_IMMEDIATE;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.atomic.AtomicLong;

import javax.net.ssl.HttpsURLConnection;

public class DownloadService extends IntentService {

    //akcje które potrafi wykonać usługa (może być więcej niż jedna)
    public static final String AKCJA_ZADANIE1 =
            "com.example.intent_service.action.zadanie1";
    //tekstowe identyfikatory parametrów potrzebnych do
    //wykonania akji (może być więcej niż jeden)
    private static final String PARAMETR1 =
            "com.example.intent_service.extra.parametr1";
    //usługi wymagają wyświetlania powiadomień
    private static final int ID_POWIADOMIENIA = 1;
    private static final String ID_KANALU = "channel1";
    private NotificationManager mNotificationManager;
    private static final int ROZMIAR_BLOKU = 1350;

    private Notification.Builder notificationBuilder;
    public static void startService(Context context, String parametr) {
        Intent intent = new Intent(context, DownloadService.class);
        intent.setAction(AKCJA_ZADANIE1);
        intent.putExtra(PARAMETR1, parametr);
        context.startService(intent);
    }

    public DownloadService() {
        super("DownloadService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
//        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        przygotujKanalPowiadomien();
        startForeground(ID_POWIADOMIENIA, createNotification());
        Log.d("Powiadomienie", "Utworzono powiadomienie");
        if (intent != null) {
            String action = intent.getAction();
            if(action.equals(AKCJA_ZADANIE1)) {
                String param1 = intent.getStringExtra(PARAMETR1);
                doTask(param1);
            }else {
                throw new RuntimeException("nie ma takiego zadania");
            }
        }
    }

    private void przygotujKanalPowiadomien() {
        mNotificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        //Android 8/Oreo wymaga kanału powiadomień
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.app_name);
            //tworzymy kanał – nadajemy mu jakieś ID (stała typu String)
            NotificationChannel kanal =
                    new NotificationChannel(ID_KANALU, name,
                            NotificationManager.IMPORTANCE_LOW);
            //tworzymy kanał w menadżerze powiadomień
            mNotificationManager.createNotificationChannel(kanal);
        }
        Log.d("kanal powiadomien", "kanal powiadomien utworzony");
    }

    private Notification createNotification() {
        Intent intentPowiadomienia = new Intent(this, MainActivity.class);

//        intentPowiadomienia.putExtra()//...

        //budujemy stos z aktywnościami, których użytkownik oczekuje po powrocie
        //w zadaniu mamy tylko jedną aktywność
        TaskStackBuilder budowniczyStosu = TaskStackBuilder.create(this);
        budowniczyStosu.addParentStack(MainActivity .class);
        budowniczyStosu.addNextIntent(intentPowiadomienia);

        PendingIntent intencjaOczekujaca = budowniczyStosu.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        //budujemy powiadomienie

        notificationBuilder = new Notification.Builder(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            notificationBuilder
                    .setContentTitle("Pobieranie pliku")
                    .setProgress(100, 10, false)
                    .setContentIntent(intencjaOczekujaca)
                    .setSmallIcon(R.drawable.baseline_rowing_24)
                    .setWhen(System.currentTimeMillis())
                    .setForegroundServiceBehavior(FOREGROUND_SERVICE_IMMEDIATE )
                    .setPriority(Notification.PRIORITY_HIGH);
        }
//        if (//jeżeli pobieranie trwa...
//        {
//            budowniczyPowiadomien.setOngoing(false);
//        } else {
        notificationBuilder.setOngoing(true);
//        }
        //ustawiamy kanał powiadomień dla tworzonego powiadomienia
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationBuilder.setChannelId(ID_KANALU);
        }
        //tworzymy i zwracamy powiadomienie
        return notificationBuilder.build();
    }

    private void doTask(String adres_url) {
        String file_url = null;
        //publishProgress(0);
        int mRozmiar = 0;
        String mTyp = null;
        HttpsURLConnection mHttpsURLConnection = null;
        InputStream mInputStreamNetwork = null;
        DataInputStream mDataInputStream = null;
        FileOutputStream mFileoutputStream = null;

        PostepInfo postepInfo = new PostepInfo();
        try {
            URL url = new URL(adres_url);
            mHttpsURLConnection = (HttpsURLConnection) url.openConnection();
            mHttpsURLConnection.setRequestMethod("GET");
            mRozmiar = mHttpsURLConnection.getContentLength();
            mTyp = mHttpsURLConnection.getContentType();
            postepInfo.setmRozmiar(mRozmiar);
            Log.d("Utworzono polaczenie z strona", "utworzono polaczenie ze strona");

//            URL url_file = new URL(file_url);
//            File plikRoboczy = new File(url_file.getFile());
            File plikWyjsciowy = new File(Environment.getExternalStorageDirectory() + File.separator + "data_test");
            if(plikWyjsciowy.exists()) {
                plikWyjsciowy.delete();
            }
            plikWyjsciowy.createNewFile();
            Log.d("file", "otwarcie pliku wyjsciowego");
            mInputStreamNetwork = mHttpsURLConnection.getInputStream();
            mDataInputStream = new DataInputStream(mInputStreamNetwork);
            Log.d("stream", "otwarcie dataStream dla strony");

            mFileoutputStream = new FileOutputStream(plikWyjsciowy.getPath());

            byte bufor[] = new byte[ROZMIAR_BLOKU];
            int pobrano = mDataInputStream.read(bufor, 0, ROZMIAR_BLOKU);
            Log.d("dane", "pobranie pierwszego elementu");
            int mPobrane = 0;
            AtomicLong startTime = new AtomicLong(System.currentTimeMillis());
            while (pobrano != -1) {
                mFileoutputStream.write(bufor, 0, pobrano);
                mPobrane = mPobrane + pobrano;
                pobrano = mDataInputStream.read(bufor, 0, ROZMIAR_BLOKU);


                postepInfo.setmPobranychBajtow(mPobrane);
                Intent zamiar1 = new Intent(AKCJA_ZADANIE1);
                //zamiar1.setAction(AKCJA_ZADANIE1);
                zamiar1.putExtra("obiekt", postepInfo);
                LocalBroadcastManager.getInstance(this).sendBroadcast(zamiar1);
                if(System.currentTimeMillis() - startTime.get() > 500) {
                    notificationBuilder.setProgress(mRozmiar, mPobrane, false);
                    notificationBuilder.setContentText(postepInfo.getProcent() + "%");
                    notificationBuilder.build();
                    mNotificationManager.notify(ID_POWIADOMIENIA, notificationBuilder.build());
                    startTime.set(System.currentTimeMillis());
                }

                //                Log.d("data", "pobrano " + Integer.toString(mPobrane) + "/" + Integer.toString(mRozmiar));
            }
            Log.d("data", "pobranie calego pliku");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mHttpsURLConnection != null) mHttpsURLConnection.disconnect();
        }

        if(mInputStreamNetwork != null) {
            try {
                mInputStreamNetwork.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(mFileoutputStream != null) {
            try {
                mFileoutputStream.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
