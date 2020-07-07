package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.myapplication.model.Task;
import com.example.myapplication.model.Task2;
import com.example.myapplication.model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends BaseActivity {
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_stop, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        try{
            View view = navView.getMenu().findItem(R.id.navigation_home).getActionView();
            Button button = (Button) view.findViewById(R.id.button);
            //button.setOnClickListener(this);
            // similarly for others menu in BottomNavigationView
        } catch (Exception e) {

        }
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

    }
    @Override
    public void onStart() {
        super.onStart();
        final String userId = BaseActivity.getUid();
        String jumlahbtg = "null";
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        // Check auth on Activity start
        if (mAuth.getCurrentUser() != null) {
            onAuthSuccess(mAuth.getCurrentUser());
        }
        else{
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }
    private void onAuthSuccess(FirebaseUser user) {
        // Go to MainActivity
    }
    private void writeNewPost(String userId,  String date,String jumlahbtg) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String currentdate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        Task2 task = new Task2(userId, date, jumlahbtg);
        Map<String, Object> postValues = task.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/date/" + userId, postValues);

        mDatabase.updateChildren(childUpdates);
    }
}
