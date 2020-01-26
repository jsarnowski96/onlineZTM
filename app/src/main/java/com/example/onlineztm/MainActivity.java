package com.example.onlineztm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;



public class MainActivity extends AppCompatActivity {

    Button mapActivityButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapActivityButton = findViewById(R.id.mapActivityButton);
        mapActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchMapActivity();
            }
        });
    }

    private void launchMapActivity() {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}
