package com.example.ticket_reservation_system;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Find the EditText fields and buttons
        EditText editTextNic = findViewById(R.id.editTextNic);
        EditText editTextPassword = findViewById(R.id.editTextPassword);
        Button buttonLogin = findViewById(R.id.buttonLogin);
        Button buttonRegister = findViewById(R.id.buttonRegister);

        // Set an onClickListener for the Login button
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the text from the EditText fields
                String nic = editTextNic.getText().toString();
                String password = editTextPassword.getText().toString();

                // For simplicity, show a toast message
                if (!nic.isEmpty() && !password.isEmpty()) {
                    // Check if NIC and password are not empty
                    Toast.makeText(HomeActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                } else {
                    // Handle empty NIC or password
                    Toast.makeText(HomeActivity.this, "Please enter NIC and password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set an onClickListener for the Register button to navigate to RegisterActivity
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the RegisterActivity when the Register button is clicked
                Intent intent = new Intent(HomeActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
