package com.example.onlineztm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.api.GoogleApiActivity;
import com.google.android.gms.maps.GoogleMap;

public class MainActivity extends AppCompatActivity {

    Button mapActivityButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mapActivityButton = findViewById(R.id.mapActivityButton);
        mapActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchMapActivity();
            }
        });

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void launchMapActivity() {
        Intent intent = new Intent(this, GoogleMap.class);
        startActivity(intent);
    }
}
