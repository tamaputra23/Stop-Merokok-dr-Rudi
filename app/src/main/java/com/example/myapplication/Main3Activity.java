package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Main3Activity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }
    public void onStart(){
        super.onStart();
        final String userId = BaseActivity.getUid();
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String jumlahbtg = "null";
        writeNewPost1(userId,date, jumlahbtg);
        Intent intent = new Intent(getApplication(), MainActivity.class);
        startActivity(intent);
        finish();
    }
    private void writeNewPost1(String userId, String date,  String jumlahbtg) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String currentdate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String key = mDatabase.child("order").push().getKey();
        com.example.myapplication.model.Task2 task = new com.example.myapplication.model.Task2(userId, date, jumlahbtg);
        Map<String, Object> postValues = task.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/date/" + userId , postValues);

        mDatabase.updateChildren(childUpdates);
    }

}
