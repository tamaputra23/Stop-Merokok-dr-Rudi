package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

public class ArtikelActivity extends AppCompatActivity {
    LinearLayout LL1, LL2, LL3,  LL4, LL5, LL6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_artikel_activity);

        LL1 = findViewById(R.id.LL_Artikel1);
        LL2 = findViewById(R.id.LL_Artikel2);
        LL3 = findViewById(R.id.LL_Artikel3);
        LL4 = findViewById(R.id.LL_Artikel4);
        LL5 = findViewById(R.id.LL_Artikel5);
        LL6 = findViewById(R.id.LL_Artikel6);
    }
    public void onStart(){
        super.onStart();
        String tmp = getIntent().getStringExtra("nomor");
        if(tmp.equals("1")){
            LL1.setVisibility(View.VISIBLE);
        }
        else if(tmp.equals("2")){
            LL2.setVisibility(View.VISIBLE);
        }
        else if(tmp.equals("3")){
            LL3.setVisibility(View.VISIBLE);
        }
        else if(tmp.equals("4")){
            LL4.setVisibility(View.VISIBLE);
        }
        else if(tmp.equals("5")){
            LL5.setVisibility(View.VISIBLE);
        }
        else if(tmp.equals("6")){
            LL6.setVisibility(View.VISIBLE);
        }
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
