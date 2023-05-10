package com.example.projekt2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.projekt2.R;

public class AddRecordActivity extends AppCompatActivity {

    Button buttonSave;
    Button buttonExit;
    Button buttonWebsite;
    EditText textManufacturer;
    EditText textModel;
    EditText textVersion;
    EditText textWebsite;

    public static final String INTENT_MANUFACTURER = "manufacturer";
    public static final String INTENT_MODEL = "model";
    public static final String INTENT_VERSION = "version";
    public static final String INTENT_WEBSITE = "website";
    public static final String INTENT_ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);

        buttonSave = findViewById(R.id.button_save);
        buttonExit = findViewById(R.id.button_cancel);
        buttonWebsite = findViewById(R.id.button_website);

        textManufacturer = findViewById(R.id.edit_text_manufacturer);
        textModel = findViewById(R.id.edit_text_model);
        textVersion = findViewById(R.id.edit_text_version);
        textWebsite = findViewById(R.id.edit_text_website);

        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        if (intent.getStringExtra(MainActivity.INTENT_EDIT ) != null) {
            textManufacturer.setText(data.getString(INTENT_MANUFACTURER));
            textModel.setText(data.getString(INTENT_MODEL));
            textVersion.setText(data.getString(INTENT_VERSION));
            textWebsite.setText(data.getString(INTENT_WEBSITE));
        }

        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra(INTENT_MANUFACTURER, textManufacturer.getText().toString());
                returnIntent.putExtra(INTENT_MODEL, textModel.getText().toString());
                returnIntent.putExtra(INTENT_VERSION, textVersion.getText().toString());
                returnIntent.putExtra(INTENT_WEBSITE, textWebsite.getText().toString());
                if (intent.getStringExtra(MainActivity.INTENT_EDIT) != null) {
                    returnIntent.putExtra(INTENT_ID, data.getLong(INTENT_ID));
                }
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });

        buttonWebsite.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String adres = textWebsite.getText().toString();
                if (!adres.startsWith("http://")) {
                    adres = "http://" + adres;
                }

                Intent zamiarPrzegladarki = new Intent(Intent.ACTION_VIEW, Uri.parse(adres));

                startActivity(zamiarPrzegladarki);
            }
        });

        textManufacturer.setOnFocusChangeListener( new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b && textManufacturer.getText().toString().equals("")) {
                    textManufacturer.setError("Pole nie może być puste");
                }
            }
        });

        textModel.setOnFocusChangeListener( new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b && textModel.getText().toString().equals("")) {
                    textModel.setError("Pole nie może być puste");
                }
            }
        });

        textVersion.setOnFocusChangeListener( new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b && textVersion.getText().toString().equals("")) {
                    textVersion.setError("Pole nie może być puste");
                }
            }
        });

        textWebsite.setOnFocusChangeListener( new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b && textWebsite.getText().toString().equals("")) {
                    textWebsite.setError("Pole nie może być puste");
                }
            }
        });
    }
}