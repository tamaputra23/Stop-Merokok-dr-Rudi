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
import android.widget.TextView;

import com.example.myapplication.model.Task;
import com.example.myapplication.model.User;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DateActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference myRef;
    DatabaseReference mDatabase;
    SharedPreferences mSharedPref; //for saving sort settings
    LinearLayoutManager mLayoutManager; //for sorting
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String tmp = getIntent().getStringExtra("uid");
        myRef = FirebaseDatabase.getInstance().getReference().child("data").child(tmp);
        recyclerView = findViewById(R.id.recyclerView3);
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
        FirebaseRecyclerAdapter<Task, DateHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Task, DateHolder>(
                        Task.class,
                        R.layout.dateview,
                        DateHolder.class,
                        myRef
                ) {
                    @Override
                    protected void populateViewHolder(DateHolder viewHolder, Task model, int i) {
                        viewHolder.setDetails(getApplicationContext(), model.getAuthor(), model.getDate(), model.getJumlahbtg(), model.getJumlahhsrt(),model.getHasrat(),
                                model.getJam(), model.getJumlahsehari(), model.getGambar());
                    }
                    @Override
                    public DateHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                        final DateHolder viewHolder = super.onCreateViewHolder(parent, viewType);
                        viewHolder.setOnClickListener(new DateHolder.ClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                            }

                        });
                        return viewHolder;
                    }
                };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }
    public static class DateHolder extends RecyclerView.ViewHolder  {
        View mView;
        TextView tvnama, tvDate, tvjmlhbtg, tvHasrat, tvHasrat2, tvJam, tvJumlahsehari, tvGambar;
        public DateHolder(View itemView) {
            super(itemView);
            mView = itemView;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mClickListener.onItemClick(view, getAdapterPosition());
                }
            });
        }
        public void setDetails(Context ctx, String author, String date, String jumlahbtg, String jumlahhsrt, String hasrat, String jam, String jumlah, String gambar){
            tvnama = mView.findViewById(R.id.tvNama1);
            tvDate = mView.findViewById(R.id.tvTanggal);
            tvjmlhbtg = mView.findViewById(R.id.tvBatang);
            tvHasrat = mView.findViewById(R.id.tvHasrat);
            tvHasrat2 = mView.findViewById(R.id.tvHasrat2);
            tvJam = mView.findViewById(R.id.tvJam);
            tvJumlahsehari = mView.findViewById(R.id.tvJumlah);
            tvGambar = mView.findViewById(R.id.tvGambar);
            tvnama.setText(author);
            tvDate.setText(date);
            tvjmlhbtg.setText("Hari ini sudah merokok sebanyak " + jumlahbtg + " batang.");
            tvHasrat.setText("Memiliki hasrat merokok sudah sebanyak " + jumlahhsrt + " kali.");
            tvHasrat2.setText("Sudah sebanyak " + hasrat + " kali tidak memiliki hasrat merokok.");
            tvJam.setText(jam);
            tvJumlahsehari.setText("Sudah membuka aplikasi sebanyak " + jumlah + " kali dalam sehari");
            tvGambar.setText("Telah membuka poster sampai gambar ke-"+ gambar);
        }
        private DateHolder.ClickListener mClickListener;
        //interface to send callbacks
        public interface ClickListener{
            void onItemClick(View view, int position);
        }
        public void setOnClickListener(DateHolder.ClickListener clickListener){
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
