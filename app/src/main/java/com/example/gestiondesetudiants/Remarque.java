package com.example.gestiondesetudiants;

public class Remarque {
    String date, title;

    public Remarque(String date, String title) {
        this.date = date;
        this.title = title;
    }

    public Remarque() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
