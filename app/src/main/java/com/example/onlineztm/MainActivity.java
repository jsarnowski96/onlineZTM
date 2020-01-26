package com.example.onlineztm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;



public class MainActivity extends AppCompatActivity {

    Button busStopsButton;
    //Button busGpsLocationButton;
    Button exitButton;
    Integer actionType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    }

    private void launchMapActivity() {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}
