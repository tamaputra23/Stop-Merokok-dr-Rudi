package com.example.myapplication.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
    public String email;
    public String name;
    public String phonenumber;
    public String akses;
    public String id;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User( String email, String name, String phonenumber, String akses) {
        this.email = email;
        this.name = name;
        this.phonenumber = phonenumber;
        this.akses = akses;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }
    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
    public String getAkses() {
        return akses;
    }
    public void setAkses(String akses) {
        this.akses = akses;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
}