package com.example.projekt3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button buttonDownloadInfo;
    private Button buttonDownloadData;

    private EditText editTextAdres;
    private ProgressBar progressBar;
    private TextView textSizeResult;
    private TextView textTypeResult;
    private TextView textDownloadedResult;
    private static final int CODE_WRITE_EXTERNAL_STORAGE = 748;


    BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonDownloadInfo = findViewById(R.id.button_download_info);
        buttonDownloadData = findViewById(R.id.button_download_data);

        editTextAdres = findViewById(R.id.editText_adres);
        progressBar = findViewById(R.id.progressBar);
        textSizeResult = findViewById(R.id.text_size_result);
        textTypeResult = findViewById(R.id.text_type_result);
        textDownloadedResult = findViewById(R.id.text_downloaded_result);

        //listener do pobrania informacji
        buttonDownloadInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = editTextAdres.getText().toString();
                if(!url.startsWith("https://")) {
                    url = "https://" + url;
                }
                AsyncGetInfo zadanie = new AsyncGetInfo(MainActivity.this);
                zadanie.execute(url);
            }
        });

        //listener do pobrania danych
        buttonDownloadData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ActivityCompat.requestPermissions(MainActivity.this, new String[]
                                { Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        CODE_WRITE_EXTERNAL_STORAGE);

            }
        });
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //Log.d("asdas","test123");
                PostepInfo postep = intent.getExtras().getParcelable("obiekt");
                //Log.d("postep", Integer.toString(postep.getmPobranychBajtow()) + "/" + Integer.toString(postep.getmRozmiar()));
                progressBar.setProgress(postep.getProcent());
                textDownloadedResult.setText(postep.getmPobranychBajtow() + "");
            }
        };

    }

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter(DownloadService.AKCJA_ZADANIE1));
        Log.d("onStart", "zarejestrowano receiver");
    }

    @Override
    protected void onStop() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        Log.e("OnStop", "wyrejestrowano receiver");
        super.onStop();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case CODE_WRITE_EXTERNAL_STORAGE:
                if (permissions.length > 0 &&
                        permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE) &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //otrzymaliśmy uprawnienia możemy rozpocząć np. pobieranie pliku
                    Log.d("uprawnienia", "otrzymalismy uprawnienia");
                    String url = editTextAdres.getText().toString();
                    if(!url.startsWith("https://")) {
                        url = "https://" + url;
                    }
                    DownloadService.startService(MainActivity.this, url);
                }
                else
                {
                    //nie otrzymaliśmy uprawnień
                }
                break;
            default:
                //nieznany kod żądania – poprawić kod aplikacji I dodać obsługę kodu
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("bytes", textDownloadedResult.getText().toString());
        outState.putInt("progress", progressBar.getProgress());
        outState.putString("size", textSizeResult.getText().toString());
        outState.putString("type", textTypeResult.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        textDownloadedResult.setText(savedInstanceState.getString("bytes"));
        progressBar.setProgress(savedInstanceState.getInt("progress"));
        textSizeResult.setText(savedInstanceState.getString("size"));
        textTypeResult.setText(savedInstanceState.getString("type"));
    }
}