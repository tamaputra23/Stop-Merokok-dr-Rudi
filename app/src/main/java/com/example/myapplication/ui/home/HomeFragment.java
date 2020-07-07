package com.example.myapplication.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.BaseActivity;
import com.example.myapplication.PosterActivity;
import com.example.myapplication.R;
import com.example.myapplication.model.Task;
import com.example.myapplication.model.Task2;
import com.example.myapplication.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class HomeFragment extends Fragment {
    Spinner spJumlah;
    RadioGroup radioGroupNb;
    RadioButton radioButtonNb;
    LinearLayout pertanyaan1;
    LinearLayout pertanyaan2;
    Button next1, next2;
    String rbPertanyaan2;
    RadioButton rb_ya, rb_tidak;
    private DatabaseReference mDatabase;
    private HomeViewModel homeViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        spJumlah = root.findViewById(R.id.spJumlah);
        radioGroupNb = root.findViewById(R.id.rg_pertanyaan1);
        rb_ya = root.findViewById(R.id.rb_ya);
        rb_tidak = root.findViewById(R.id.rb_tidak);
        pertanyaan1 = root.findViewById(R.id.linPertanyaan1);
        pertanyaan2 = root. findViewById(R.id.linPertanyaan2);
        next1 = root.findViewById(R.id.btn_Next);
        next2 = root.findViewById(R.id.btn_Next2);
        next1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task1();
                pertanyaan1.setVisibility(View.GONE);
                pertanyaan2.setVisibility(View.VISIBLE);
            }
        });
        next2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    task2();
                    Intent intent = new Intent(getActivity(), PosterActivity.class);
                    startActivity(intent);
            }
        });
        return root;
    }

    public void task1 (){
        final String userId = BaseActivity.getUid();
        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                String author = user.name;
                String jumlahbtg = spJumlah.getSelectedItem().toString();
                String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                String jam = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
                String hasrat = "0";
                String jumlahhsrt = "0";
                String gambar = "0";
                int sehari = Integer.parseInt(hasrat) + Integer.parseInt(jumlahhsrt);
                writeNewPost(userId, author, jumlahbtg, date, jumlahhsrt, hasrat, jam, String.valueOf(sehari), gambar);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    public void task2 (){
        final String userId = BaseActivity.getUid();
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        mDatabase.child("data").child(userId).child(date).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Task user = dataSnapshot.getValue(Task.class);
                String author = user.author;
                String jumlahbtg = user.jumlahbtg;
                String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                String jam1 = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
                String jam = user.jam + ", " + jam1;
                if (rb_ya.isChecked()){
                    String hasrat = user.hasrat;
                    String sJumlahhsrt = user.jumlahhsrt;
                    final int iJumlahhsrt = Integer.parseInt(sJumlahhsrt) + 1;
                    int sehari = Integer.parseInt(hasrat) + iJumlahhsrt;
                    String gambar = "0";
                    writeNewPost(userId, author , jumlahbtg, date, String.valueOf(iJumlahhsrt), hasrat, jam, String.valueOf(sehari), gambar);
                }
                else if(rb_tidak.isChecked()){
                    String hasrat = user.hasrat;
                    String sJumlahhsrt = user.jumlahhsrt;
                    int iHasrat = Integer.parseInt(hasrat) + 1;
                    int sehari = Integer.parseInt(sJumlahhsrt) + iHasrat;
                    String gambar = "0";
                    writeNewPost(userId, author , jumlahbtg, date, sJumlahhsrt, String.valueOf(iHasrat),jam, String.valueOf(sehari), gambar);
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    public void onStart(){
        super.onStart();
        final String userId = BaseActivity.getUid();
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        mDatabase.child("date").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Task2 user = dataSnapshot.getValue(Task2.class);
                String date1 = user.date;
                String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                if (date1.equals(date)){
                    String btg = user.jumlahbtg;
                    if (btg.equals("null")){
                        pertanyaan1.setVisibility(View.VISIBLE);
                        pertanyaan2.setVisibility(View.GONE);
                    }
                    else {
                        pertanyaan2.setVisibility(View.VISIBLE);
                        pertanyaan1.setVisibility(View.GONE);
                    }
                }
                else{
                    pertanyaan1.setVisibility(View.VISIBLE);
                    pertanyaan2.setVisibility(View.GONE);
                }

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
        com.example.myapplication.model.Task2 task2 = new com.example.myapplication.model.Task2(userId, date, jumlahbtg);
        Map<String, Object> postValues = task.toMap();
        Map<String, Object> postValues2 = task2.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/data/" + userId + "/" + currentdate, postValues);
        childUpdates.put("/date/" + userId, postValues2);


        mDatabase.updateChildren(childUpdates);
    }
}
