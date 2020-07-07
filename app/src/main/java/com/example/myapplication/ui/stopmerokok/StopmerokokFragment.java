package com.example.myapplication.ui.stopmerokok;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.ArtikelActivity;
import com.example.myapplication.ArtikelView;
import com.example.myapplication.R;
import com.example.myapplication.model.artikel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StopmerokokFragment extends Fragment {
    RecyclerView recyclerView;
    DatabaseReference myRef;
    private StopmerokokViewModel StopmerokokViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        StopmerokokViewModel =
                ViewModelProviders.of(this).get(StopmerokokViewModel.class);
        View root = inflater.inflate(R.layout.fragment_stopmerokok, container, false);
        myRef = FirebaseDatabase.getInstance().getReference().child("artikel");
        recyclerView = root.findViewById(R.id.recyclerView3);
        RecyclerView.LayoutManager mLayoutManager = new
                GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(false);
        return root;
    }
    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<artikel, ArtikelView> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<artikel, ArtikelView>(
                        artikel.class,
                        R.layout.artikelview,
                        ArtikelView.class,
                        myRef
                ) {
                    @Override
                    protected void populateViewHolder(ArtikelView viewHolder, artikel model, int i) {
                        viewHolder.setDetails(getContext(), model.getJudul(), model.getNomor());
                    }

                    @Override
                    public ArtikelView onCreateViewHolder(ViewGroup parent, int viewType) {

                        final ArtikelView viewHolder = super.onCreateViewHolder(parent, viewType);
                        viewHolder.setOnClickListener(new ArtikelView.ClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                TextView mNomor = view.findViewById(R.id.tvNomor);
                                String sNomor = mNomor.getText().toString();
                                Intent intent = new Intent(getContext(), ArtikelActivity.class);
                                intent.putExtra("nomor", sNomor);
                                startActivity(intent);
                            }

                        });
                        return viewHolder;
                    }
                };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }
}
