package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.model.Poster;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.Map;

public class Main2Activity extends AppCompatActivity {
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference myRef;
    DatabaseReference mDatabase;
    PhotoView photoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView coba = findViewById(R.id.tvcova);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = mFirebaseDatabase.getReference();
        String tmp = getIntent().getStringExtra("nama");
        //coba.setText(tmp);
        photoView = findViewById(R.id.ivPoster);
        mDatabase.child("gambar").child("poster").child(tmp).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Poster Order = dataSnapshot.getValue(Poster.class);
                String poster = Order.gambar;
                TextView coba = findViewById(R.id.tvcova);
                //coba.setText(Order.judul);
                Picasso.get().load(poster).into(photoView);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    public void back (View view){
        onBackPressed();
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
