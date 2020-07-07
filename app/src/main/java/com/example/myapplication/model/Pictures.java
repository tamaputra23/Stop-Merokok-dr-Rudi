package com.example.myapplication.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Pictures{
    public String gambar1;
    public String gambar2;
    public String gambar3;
    public String gambar4;
    public Pictures() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
    public Pictures( String gambar1, String gamber2, String gamber3, String gamber4 ) {
        this.gambar1 = gambar1;
        this.gambar2 = gamber2;
        this.gambar3 = gamber3;
        this.gambar4 = gamber4;


    }
    public String getGambar1() {
        return gambar1;
    }
    public String getGambar2() {
        return gambar2;
    }
    public String getGambar3() {
        return gambar3;
    }
    public String getGambar4() {
        return getGambar4();
    }
}