package com.example.outilcuisson;

import java.io.Serializable;

public class Cuisson implements Serializable {

    private String plat;
    private int heure;
    private int minute;
    private int degree;

    public Cuisson(String plat, int heure, int minute, int degree) {
        this.plat = plat;
        this.heure = heure;
        this.minute = minute;
        this.degree = degree;
    }

    public String getPlat() {
        return plat;
    }

    public int getHeure() {
        return heure;
    }

    public int getMinute() {
        return minute;
    }

    public int getDegree() {
        return degree;
    }
}