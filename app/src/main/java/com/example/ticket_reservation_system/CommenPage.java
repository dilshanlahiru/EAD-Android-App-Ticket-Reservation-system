package com.example.ticket_reservation_system;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class CommenPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commen_page);

        // Find the ImageView for each clickable image
        ImageView image1 = findViewById(R.id.image1);
        ImageView image2 = findViewById(R.id.image2);
        ImageView image3 = findViewById(R.id.image3);

        // Set click listeners for each image
        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the click on image 1
                // For example, start a new activity or perform an action
                // Replace 'YourActivity1.class' with the actual activity you want to start
                Intent intent = new Intent(CommenPage.this, Previous_Reservation_List.class);
                startActivity(intent);
            }
        });

        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the click on image 2
                // For example, start a new activity or perform an action
                // Replace 'YourActivity2.class' with the actual activity you want to start
                Intent intent = new Intent(CommenPage.this, ScheduleList.class);
                startActivity(intent);
            }
        });

        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the click on image 3
                // For example, start a new activity or perform an action
                // Replace 'YourActivity3.class' with the actual activity you want to start
                Intent intent = new Intent(CommenPage.this, ViewProfile.class);
                startActivity(intent);
            }
        });
    }
}