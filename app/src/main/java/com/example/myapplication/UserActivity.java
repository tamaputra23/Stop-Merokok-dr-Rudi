package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.model.User;
import com.example.myapplication.model.model;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class UserActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference myRef;
    DatabaseReference mDatabase;
    SharedPreferences mSharedPref; //for saving sort settings
    LinearLayoutManager mLayoutManager; //for sorting
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myRef = FirebaseDatabase.getInstance().getReference().child("users");
        recyclerView = findViewById(R.id.recyclerView2);
        mSharedPref = getSharedPreferences("SortSettings", MODE_PRIVATE);
        String mSorting = mSharedPref.getString("Sort", "newest"); //where if no settingsis selected newest will be default
        if (mSorting.equals("newest")) {
            mLayoutManager = new LinearLayoutManager(this);
            //this will load the items from bottom means newest first
            mLayoutManager.setReverseLayout(true);
            mLayoutManager.setStackFromEnd(true);
        } else if (mSorting.equals("oldest")) {
            mLayoutManager = new LinearLayoutManager(this);
            //this will load the items from bottom means oldest first
            mLayoutManager.setReverseLayout(false);
            mLayoutManager.setStackFromEnd(false);
        }
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = mFirebaseDatabase.getReference();
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(false);
    }
    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<User, UserHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<User, UserHolder>(
                        User.class,
                        R.layout.userview,
                        UserHolder.class,
                        myRef
                ) {
                    @Override
                    protected void populateViewHolder(UserHolder viewHolder, User model, int i) {
                        viewHolder.setDetails(getApplicationContext(), model.getName(), model.getEmail(), model.getPhonenumber(), model.getId());
                    }
                    @Override
                    public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                        final UserHolder viewHolder = super.onCreateViewHolder(parent, viewType);
                        viewHolder.setOnClickListener(new UserHolder.ClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                final DatabaseReference postRef = getRef(position);
                                String key = postRef.getKey();
                                TextView tvmUid = view.findViewById(R.id.tvuid);
                                String mUid = tvmUid.getText().toString();
                                Intent intent = new Intent(getApplicationContext(), DateActivity.class);
                                intent.putExtra("uid", key);
                                startActivity(intent);
                            }

                        });
                        return viewHolder;
                    }
                };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }
    public static class UserHolder extends RecyclerView.ViewHolder  {
        View mView;
        TextView tvnama, tvemail, tvphone, tvuid;
        public UserHolder(View itemView) {
            super(itemView);
            mView = itemView;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mClickListener.onItemClick(view, getAdapterPosition());
                }
            });
        }
        public void setDetails(Context ctx, String name, String email, String phonenumber, String Uid){
            tvnama = mView.findViewById(R.id.tvNama);
            tvemail = mView.findViewById(R.id.tvEmail);
            tvphone = mView.findViewById(R.id.tvTelepon);
            tvuid = mView.findViewById(R.id.tvuid);
            tvnama.setText(name);
            tvemail.setText(email);
            tvphone.setText(phonenumber);
            tvuid.setText(Uid);
        }
        private UserHolder.ClickListener mClickListener;
        //interface to send callbacks
        public interface ClickListener{
            void onItemClick(View view, int position);
        }
        public void setOnClickListener(UserHolder.ClickListener clickListener){
            mClickListener = clickListener;
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
