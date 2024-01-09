package com.example.gestiondesetudiants;

public class dataAbsence {
    private String key;
    private String clé_étudiant;
    private long date;
    private String isAbsence;

    public dataAbsence() {}

    public dataAbsence(String clé_étudiant, long date, String isAbsence) {
        //this.key = key;
        this.clé_étudiant = clé_étudiant;
        this.date = date;
        this.isAbsence = isAbsence;}

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getClé_étudiant() {
        return clé_étudiant;
    }

    public void setClé_étudiant(String clé_étudiant) {
        this.clé_étudiant = clé_étudiant;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getIsAbsence() {
        return isAbsence;
    }

    public void setIsAbsence(String isAbsence) {
        this.isAbsence = isAbsence;
    }
}
