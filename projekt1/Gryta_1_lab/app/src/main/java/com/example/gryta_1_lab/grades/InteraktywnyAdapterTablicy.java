package com.example.gryta_1_lab.grades;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gryta_1_lab.R;

import java.util.List;

public class InteraktywnyAdapterTablicy extends RecyclerView.Adapter<InteraktywnyAdapterTablicy.OcenyViewHolder> {
    //lista przechowujące modele ocen
    private List<ModelOceny> mListaOcen;
    //odwołanie do layoutu
    private LayoutInflater mPompka;
    //konstruktor

    public InteraktywnyAdapterTablicy(Activity kontekst, List<ModelOceny> listaOcen) {
        mPompka = kontekst.getLayoutInflater();
        this.mListaOcen = listaOcen;
    }
    //wypełnia wiersz przechowywany w pojemniku danymi dla określonego wiersza
    @Override
    public void onBindViewHolder(@NonNull OcenyViewHolder ocenyViewHolder, int numerWiersza) {

        ModelOceny grade = mListaOcen.get(numerWiersza);
        ocenyViewHolder.name.setText(grade.getNazwa());
        switch (grade.getOcena()) {
            case 2:
                ocenyViewHolder.gradeGroup.check(R.id.grade_2);
                break;
            case 3:
                ocenyViewHolder.gradeGroup.check(R.id.grade_3);
                break;
            case 4:
                ocenyViewHolder.gradeGroup.check(R.id.grade_4);
                break;
            case 5:
                ocenyViewHolder.gradeGroup.check(R.id.grade_5);
                break;
        }
    }

    //tworzy główny element layout i tworzy pojemnik (holder) dla danego wiersza
    //MOJE: TWORZY NOWY WIERSZ
    @NonNull
    @Override
    public OcenyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        //utworzenie layoutu wiersza na podstawie XMLa
        View wiersz = mPompka.inflate(R.layout.wiersz_oceny, viewGroup, false);
        //zwrócenie nowego obiektu holdera
        return new OcenyViewHolder(wiersz);
    }

    //zwraca liczbę elementów
    @Override
    public int getItemCount() {
//...
//w zależności od kolekcji, w której przechowywane są elementy
        return mListaOcen.size();
    }

    ;
//pojemnik przechowujący referencje do potrzebnych elementów wiersza nadaje się też jako obiekt implementujący listenery - każdy wiersz ma własny holder
    public class OcenyViewHolder extends RecyclerView.ViewHolder implements RadioGroup.OnCheckedChangeListener
    {
        //pola przechowujące referencje do elementów wiersza
        TextView name;
        RadioGroup gradeGroup;
        View glowny;
        //odczytuje referencje do elementów i ustawia listenery
        public OcenyViewHolder(@NonNull View glownyElementWiersza) {
            super(glownyElementWiersza);
            //odczytanie referencji do elementów wiersza
            name = glownyElementWiersza.findViewById(R.id.SubjectName);
            gradeGroup = glownyElementWiersza.findViewById(R.id.gradesGroup);
            glowny = glownyElementWiersza;
            //ustawienie obsługi zdarzeń w komponentacz znajdujących się w wierszu
            gradeGroup.setOnCheckedChangeListener(this);
        }

        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            ModelOceny model = mListaOcen.get(getAdapterPosition());
            RadioButton button = glowny.findViewById(i);
            model.setOcena(Integer.parseInt(button.getText().toString()));
            mListaOcen.set(getAdapterPosition(), model);
//            radioGroup.check(R.id.grade_4);
        }
        //implementacje interfejsów obsługujących zdarzenia
        //...
    }
}