package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.model.Pictures;
import com.example.myapplication.model.Poster;
import com.example.myapplication.model.Task;
import com.example.myapplication.model.Task2;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class PosterActivity extends AppCompatActivity {
    CarouselView carouselView;
    DatabaseReference mDatabase;
    String gambar1;
    String gambar2;
    String gambar3;
    String gambar4;
    int [] images = new int[4];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poster);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        carouselView = findViewById(R.id.carouselView);
        carouselView.setPageCount(images.length);
        carouselView.setImageListener(imageListener);
        carouselView.setImageClickListener(new ImageClickListener() {
            @Override
            public void onClick(final int position) {
                //Toast.makeText(PosterActivity.this, "Clicked item: "+ position, Toast.LENGTH_SHORT).show();
                final String userId = BaseActivity.getUid();
                String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                mDatabase.child("data").child(userId).child(date).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Task user = dataSnapshot.getValue(Task.class);
                        String author = user.author;
                        String jumlahbtg = user.jumlahbtg;
                        String date = user.date;
                        String jam = user.jam;
                        String hasrat = user.hasrat;
                        String sJumlahhsrt = user.jumlahhsrt;
                        String jumlahsehari = user.jumlahsehari;
                        String gambar = String.valueOf(position);
                        writeNewPost(userId, author , jumlahbtg, date, sJumlahhsrt, hasrat,jam, jumlahsehari, gambar);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
                onBackPressed();
            }
        });
}

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(images[position]);

        }
    };
    public void onStart(){
        super.onStart();
        Random randomGenerator = new Random();
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        while (numbers.size() < 4) {

            int random = randomGenerator .nextInt(4);
            if (!numbers.contains(random)) {
                numbers.add(random);
            }
        }
        int[] cab1 = new int[numbers.size()];
        System.out.println(numbers);

        images[numbers.get(0)] = R.drawable.gambar1;
        images[numbers.get(1)] = R.drawable.gambar2;
        images[numbers.get(2)] = R.drawable.gambar3;
        images[numbers.get(3)] = R.drawable.gambar4;
        mDatabase.child("gambar").child("poster2").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Pictures user = dataSnapshot.getValue(Pictures.class);
                 gambar1 = user.gambar1;
                 gambar2 = user.gambar2;
                 gambar3 = user.gambar3;
                 gambar4 = user.gambar4;

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    private void writeNewPost(String userId, String author, String jumlahbtg, String date, String jumlahhsrt, String hasrat, String jam, String jumlahsehari, String gambar) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        String currentdate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        Task task = new Task(userId, author, jumlahbtg, date, jumlahhsrt, hasrat, jam, jumlahsehari, gambar);
        //com.example.myapplication.model.Task2 task2 = new com.example.myapplication.model.Task2(userId, date, jumlahbtg);
        Map<String, Object> postValues = task.toMap();
        //Map<String, Object> postValues2 = task2.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/data/" + userId + "/" + currentdate, postValues);
        //childUpdates.put("/date/" + userId, postValues2);


        mDatabase.updateChildren(childUpdates);
    }
    @Override
    public  boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
