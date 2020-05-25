package com.example.onlineztm;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    Button busStopsButton;
    Button exitButton;
    Button logoutButton;
    Integer actionType;

    private String userId;
    private String userEmail;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getPreferences(MODE_PRIVATE);
        userId = sharedPreferences.getString("FireBaseUid", "");
        userEmail = sharedPreferences.getString("UserEmail", "");

        FirebaseAuth.AuthStateListener mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth mAuth) {
                FirebaseUser user = mAuth.getCurrentUser();
                if(user != null) {
                    mUser = user;
                    if(mUser.isAnonymous()) {
                        Toast.makeText(MainActivity.this, "You are now using this app as Anonymous User", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(MainActivity.this,"Welcome back, " + userEmail, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };

        mAuth = FirebaseAuth.getInstance();
        mAuth.addAuthStateListener(mAuthListener);

        setContentView(R.layout.activity_main);

        actionType = 0;

        busStopsButton = findViewById(R.id.busStopsButton);
        busStopsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionType = 1;
                launchMapActivity();
            }
        });

        exitButton = findViewById(R.id.exitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /*busGpsLocationButton = findViewById(R.id.busGpsLocationButton);
        busGpsLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionType = 2;
                launchMapActivity();
            }
        });
        */

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
    }

    private void launchMapActivity() {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}
