package com.example.gryta_1_lab;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gryta_1_lab.grades.InteraktywnyAdapterTablicy;
import com.example.gryta_1_lab.grades.ModelOceny;

import java.util.ArrayList;

public class Grades extends AppCompatActivity {

    Button average;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades);
        Bundle pakunek = getIntent().getExtras();
        int grades_number = pakunek.getInt("grade_number");

        ArrayList<ModelOceny> mDane = new ArrayList<ModelOceny>();

        String[] nazwyPrzedmiotow = getResources().getStringArray(R.array.przedmioty);


        for (int i = 0; i < grades_number; i++) {
            mDane.add(new ModelOceny(nazwyPrzedmiotow[i], 2));
        }

        InteraktywnyAdapterTablicy adapter = new InteraktywnyAdapterTablicy(this, mDane);
        RecyclerView mListaOcen = findViewById(R.id.lista_ocen_rv);

        mListaOcen.setAdapter(adapter);
        mListaOcen.setLayoutManager(new LinearLayoutManager(this));

        average = findViewById(R.id.averageButton);

        average.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double sum = 1.0;
                for (int i = 0; i < grades_number; i++) {
                    sum += mDane.get(i).getOcena();
                }

                Intent intent = new Intent();
                intent.putExtra("Zdane", sum / grades_number);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }
}
