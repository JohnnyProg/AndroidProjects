package com.example.gryta_1_lab;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private static final int BUTTON_ID  = 62;
    private static final int TEXTVIEW_ID = 510;

    private EditText name;
    private EditText lastName;
    private EditText grades;
    private Button przycisk;
    private ActivityResultLauncher<Intent> mActivityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        name = findViewById(R.id.name);
        lastName = findViewById(R.id.lastName);
        grades = findViewById(R.id.ocenyNumber);
        przycisk = findViewById(R.id.ocenyButton);

        mActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                //w <> typ wyniku - tutaj ActivityResult, może
                //też być Uri
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult( ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {

                            Intent resultIntent = result.getData();
                            double srednia = resultIntent.getDoubleExtra("Zdane", 0);
                            addButtonToConstraintLayout(srednia);
                        }}});

        Intent intencja = new Intent(MainActivity.this,  Grades.class);

        Toast toast = Toast.makeText(this, //kontekst-zazwyczajreferencja do Activity
                "Kocham Piwo", //napis do wyświetlenia
        Toast.LENGTH_SHORT); //długość




        przycisk.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
//                        intencja.putExtra("grade_number", 5);
                        intencja.putExtra("grade_number", Integer.parseInt(grades.getText().toString()));
                        mActivityResultLauncher.launch(intencja);
                        toast.show();
                    }
               }
        );
        name.setOnFocusChangeListener(
                new View.OnFocusChangeListener()
                {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if(!hasFocus) {
                            setButtonVisibility();
                        }
                        if(!hasFocus && name.getText().toString().equals("")) {
                            setButtonVisibility();
                            name.setError("podaj imie");
                        }
                    }
                }
        );

        lastName.setOnFocusChangeListener(
                new View.OnFocusChangeListener()
                {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if(!hasFocus) {
                            setButtonVisibility();
                        }
                        if(!hasFocus && lastName.getText().toString().equals("")) {
                            lastName.setError("podaj nazwisko");
                        }
                    }
                }
        );

        grades.setOnFocusChangeListener(
                new View.OnFocusChangeListener()
                {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if(!hasFocus) {
                            setButtonVisibility();
                        }
                        if(!hasFocus && !isNumeric(grades.getText().toString())) {
                            grades.setError("podaj numer");
                        }else if(!hasFocus) {
                            int number = Integer.parseInt(grades.getText().toString());
                            if (number < 5 || number > 15) {
                                grades.setError("zły zakres wartości");
                            }
                        }
                    }
                }
        );


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("B_Visib", przycisk.getVisibility());
        TextView text = findViewById(TEXTVIEW_ID);
        if (text != null) {
            outState.putDouble("average", Double.parseDouble(text.getText().toString()));
        }
        super.onSaveInstanceState(outState);
    }

    //przykład odtworzenia napisu na etykiecie tekstowej
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        przycisk.setVisibility(savedInstanceState.getInt("B_Visib"));

        if (savedInstanceState.containsKey("average")) {
            addButtonToConstraintLayout(savedInstanceState.getDouble("average"));
        }
    }

    private static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }


    private void setButtonVisibility() {
        boolean isVisible = true;

        if (name.getText().toString().equals("")) {
            isVisible = false;
        }
        if (lastName.getText().toString().equals("")) {
            isVisible = false;
        }

        if(!isNumeric(grades.getText().toString())) {
            isVisible = false;
        }else {
            int number = Integer.parseInt(grades.getText().toString());
            if (number < 5 || number > 15) {
                isVisible = false;
            }
        }

        if(isVisible) {
            przycisk.setVisibility(View.VISIBLE);
        }else {
            przycisk.setVisibility(View.INVISIBLE);
        }
    }

    @SuppressLint("ResourceType")
    private void addButtonToConstraintLayout(double srednia) {
        Toast zdane = Toast.makeText(this, //kontekst-zazwyczajreferencja do Activity
                "Gratulacje", //napis do wyświetlenia
                Toast.LENGTH_SHORT); //długość

        Toast niezdane = Toast.makeText(this, //kontekst-zazwyczajreferencja do Activity
                "Wysyłam podanie o zaliczenie warunkowe", //napis do wyświetlenia
                Toast.LENGTH_SHORT); //długość

        LinearLayout layout = findViewById(R.id.place_for_button);
        Button btn = new Button(this);
        TextView text = new TextView(this);
        text.setText(Double.toString(srednia));
        if(srednia > 3) {
            btn.setText("Super");
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    zdane.show();
                    finish();
                }
            });
        }else {
            btn.setText("Tym razem mi nie poszło");
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    niezdane.show();
                    finish();
                }
            });
        }
        btn.setId(BUTTON_ID);
        text.setId(TEXTVIEW_ID);
        btn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        layout.addView(text);
        layout.addView(btn);

    }
}