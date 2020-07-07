package com.example.myapplication.model;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Task2 {
    public String uid;
    public String author;
    public String jumlahbtg;
    public String date;
    public String jumlahhsrt;
    public String hasrat;
    public Task2(){}

    public Task2(String uid, String date,String jumlahbtg) {
        this.uid = uid;
        this.author = author;
        this.jumlahbtg = jumlahbtg;
        this.date = date;
        this.jumlahhsrt = jumlahhsrt;
        this.hasrat = hasrat;
    }
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("author", author);
        result.put("jumlahbtg", jumlahbtg);
        result.put("date", date);
        result.put("jumlahhsrt", jumlahhsrt);
        result.put("hasrat", hasrat);
        return result;
    }
    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getJumlahbtg() {
        return jumlahbtg;
    }
    public void setJumlahbtg(String jumlahbtg) {
        this.jumlahbtg = jumlahbtg;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getJumlahhsrt() {
        return jumlahhsrt;
    }
    public void setJumlahhsrt(String jumlahhsrt) {
        this.jumlahhsrt = jumlahhsrt;
    }
    public String getHasrat() {
        return hasrat;
    }
    public void setHasrat(String hasrat) {
        this.hasrat = hasrat;
    }
}
