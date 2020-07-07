package com.example.myapplication.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.BaseActivity;
import com.example.myapplication.LoginActivity;
import com.example.myapplication.R;
import com.example.myapplication.UserActivity;
import com.example.myapplication.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NotificationsFragment extends Fragment {
    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
    private NotificationsViewModel notificationsViewModel;
    public TextView mNama, mUsername, mEmail, mTelepon;
    FirebaseDatabase mFirebaseDatabase;
    Button signout, admin;
    DatabaseReference myRef;
    DatabaseReference mDatabase;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = mFirebaseDatabase.getReference();
        mNama = root.findViewById(R.id.tvNamaprofile);
        mEmail = root.findViewById(R.id.tvEmailprofile);
        mTelepon = root.findViewById(R.id.tvPhoneprofile);
        mUsername = root.findViewById(R.id.tvUsernameprofile);
        admin = root.findViewById(R.id.btn_admin);
        signout = root.findViewById(R.id.sign_out);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getContext(), LoginActivity.class));
                getActivity().finish();
            }
        });
        final String userId = BaseActivity.getUid();
        mDatabase.child("users").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                String username = usernameFromEmail(user.getEmail());
                mUsername.setText(username);
                mEmail.setText(user.getEmail());
                mNama.setText(user.getName());
                mTelepon.setText(user.getPhonenumber());
                String akses = user.getAkses();
                if (akses.equals("admin")){
                    admin.setVisibility(View.VISIBLE);
                    admin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent (getActivity(), UserActivity.class);
                            startActivity(intent);
                        }
                    });
                }
                else{
                    admin.setVisibility(View.GONE);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return root;
    }

    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }
}
