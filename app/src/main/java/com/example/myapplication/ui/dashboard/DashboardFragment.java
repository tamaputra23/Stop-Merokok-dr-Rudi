package com.example.myapplication.ui.dashboard;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Main2Activity;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.ViewHolder;
import com.example.myapplication.model.model;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class DashboardFragment extends Fragment {
    RecyclerView recyclerView;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference myRef;
    DatabaseReference mDatabase;
    SharedPreferences mSharedPref; //for saving sort settings
    LinearLayoutManager mLayoutManager; //for sorting
    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        myRef = FirebaseDatabase.getInstance().getReference().child("gambar").child("thumbnail");
        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new
                GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(false);
        return root;
    }
    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<model, ViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<model, ViewHolder>(
                        model.class,
                        R.layout.picturesview,
                        ViewHolder.class,
                        myRef
                ) {
                    @Override
                    protected void populateViewHolder(ViewHolder viewHolder, model model, int i) {
                        viewHolder.setDetails(getContext(), model.getJudul(), model.getNama());

                    }

                    @Override
                    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                        final ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);
                        viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                TextView mNama = view.findViewById(R.id.tvNama);
                                String sNama = mNama.getText().toString();
                                Intent intent = new Intent(getActivity(), Main2Activity.class);
                                intent.putExtra("nama", sNama);
                                startActivity(intent);
                            }

                        });
                        return viewHolder;
                    }
                };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }
}

