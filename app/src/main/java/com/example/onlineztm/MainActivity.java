package com.example.onlineztm;

import androidx.annotation.NonNull;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    Button busStopsButton;
    Button exitButton;
    Button logoutButton;
    Button timetableButton;
    Integer actionType;

    private String userId;
    private String userEmail;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseAuth.AuthStateListener authListener;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        actionType = 0;

        busStopsButton = findViewById(R.id.busStopsButton);
        busStopsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionType = 1;
                launchMapsActivity();
            }
        });

        exitButton = findViewById(R.id.exitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        timetableButton = findViewById(R.id.timetableButton);
        timetableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchTimetableActivity();
            }
        });

        logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Toast.makeText(MainActivity.this, "You have been signed out", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    mUser = firebaseUser;
                    if(mUser.isAnonymous()) {
                        Toast.makeText(MainActivity.this, "Signed as Anonymous User", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        //sharedPreferences = getPreferences(MODE_PRIVATE);
                        //userEmail = sharedPreferences.getString("UserEmail", "");
                        Toast.makeText(MainActivity.this,"Welcome back, " + mUser.getEmail(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };
        mAuth.addAuthStateListener(authListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(authListener);
    }

    private void launchMapsActivity() {
        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent);
    }

    private void launchTimetableActivity() {
        Intent intent = new Intent(MainActivity.this, TimetableActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent);
    }
}
