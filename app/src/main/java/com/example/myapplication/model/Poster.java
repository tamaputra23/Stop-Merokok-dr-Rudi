package com.example.myapplication.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Poster{
    public String gambar;
    public Poster() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
    public Poster( String gambar) {
        this.gambar = gambar;
    }
    public String getGambar() {
        return gambar;
    }
}
