package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "SignInActivity";

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    private EditText mEmailField;
    private EditText mPasswordField, edtPhonenumber, edtNameField;
    private Button mSignUpButton;
    private TextView mSignInButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        // Views
        mEmailField = findViewById(R.id.etEmailsignup);
        mPasswordField = findViewById(R.id.etPasswordsignup);
        edtNameField = findViewById(R.id.etName);
        edtPhonenumber = findViewById(R.id.etPhone);
        mSignUpButton = findViewById(R.id.btn_Signup);
        mSignInButton = findViewById(R.id.tvSignin);
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();

        // Check auth on Activity start
        if (mAuth.getCurrentUser() != null) {
            onAuthSuccess(mAuth.getCurrentUser());
        }

    }
    private void signUp() {
        Log.d(TAG, "signUp");
        if (!validateForm()) {
            return;
        }
        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();
        if (password.length()<= 6){
            Toast.makeText(RegisterActivity.this, "Password is too short! it should be at least 6 characters",
                    Toast.LENGTH_SHORT).show();
        }

        else{
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "createUser:onComplete:" + task.isSuccessful());


                            if (task.isSuccessful()) {
                                onAuthSuccess(task.getResult().getUser());
                            } else {
                                Toast.makeText(RegisterActivity.this, "Sign Up Failed",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
    private void onAuthSuccess(FirebaseUser user) {
        String name = edtNameField.getText().toString();
        String author = edtNameField.getText().toString();
        String email = mEmailField.getText().toString();
        String phonenumber = edtPhonenumber.getText().toString();
        String akses = "user";
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String jumlahbtg = "null";
        String hasrat = "null";
        String jumlahhsrt = "null";
        // Write new user
        writeNewUser(user.getUid(), email ,name, phonenumber, akses);
        writeNewPost1(user.getUid(),date, jumlahbtg);
        // Go to MainActivity
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        finish();
    }
    private boolean validateForm() {
        boolean result = true;
        if (TextUtils.isEmpty(mEmailField.getText().toString())) {
            mEmailField.setError("Required");
            result = false;
        } else {
            mEmailField.setError(null);
        }
        if (TextUtils.isEmpty(mPasswordField.getText().toString())) {
            mPasswordField.setError("Required");
            result = false;
        } else {
            mPasswordField.setError(null);
        }

        return result;
    }
    // [START basic_write]
    private void writeNewUser(String userId, String email, String name, String phonenumber, String  akses) {
        User user = new User(email, name, phonenumber, akses);
        mDatabase.child("users").child(userId).setValue(user);
    }
    // [END basic_write]
    private void writeNewPost1(String userId, String date,  String jumlahbtg) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String currentdate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String key = mDatabase.child("order").push().getKey();
        com.example.myapplication.model.Task2 task = new com.example.myapplication.model.Task2(userId, date, jumlahbtg);
        Map<String, Object> postValues = task.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/date/" + userId , postValues);

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
