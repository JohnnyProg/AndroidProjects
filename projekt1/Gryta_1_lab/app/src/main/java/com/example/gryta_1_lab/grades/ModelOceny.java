package com.example.gryta_1_lab.grades;

public class ModelOceny {
    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public int getOcena() {
        return ocena;
    }

    public void setOcena(int ocena) {
        if (ocena > 5) {
            ocena = 5;
        }else if(ocena < 2) {
            ocena = 2;
        }
        this.ocena = ocena;
    }

    public ModelOceny(String nazwa, int ocena) {
        this.nazwa = nazwa;
        this.setOcena(ocena);
    }

    private String nazwa;
    private int ocena;


}
