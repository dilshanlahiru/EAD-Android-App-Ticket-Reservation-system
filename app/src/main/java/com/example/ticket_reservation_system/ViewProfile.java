package com.example.ticket_reservation_system;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ViewProfile extends AppCompatActivity {

    private ImageView userImageView;
    private TextView usernameTextView;
    private TextView nicTextView;
    private TextView statusTextView;
    private Button deactivateButton;
    private Button editProfileButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);


        usernameTextView = findViewById(R.id.usernameTextView);
        nicTextView = findViewById(R.id.nicTextView);
        statusTextView = findViewById(R.id.statusTextView);
        deactivateButton = findViewById(R.id.deactivateButton);
        editProfileButton = findViewById(R.id.editProfileButton);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        String userName = databaseHelper.getUserName();
        String nic = databaseHelper.getNIC();
        String userId = databaseHelper.getUserID();

        // Set the retrieved value in the appropriate views
        // Set the user's image
        usernameTextView.setText(userName); // Set the user data in the username view
        nicTextView.setText(nic); // Set the user data in the NIC view
        statusTextView.setText("Active");

        deactivateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(ViewProfile.this, HomeActivity.class);
//                startActivity(intent);
                deactivateAccount(userId);

            }

        });

        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewProfile.this, UpdateProfile.class);
                startActivity(intent);

            }

        });
    }

    private void deactivateAccount(String id) {
        JSONObject requestBody = new JSONObject();
        // If you're sending JSON data in the request body, you can add data here if needed.

        String url = "http://10.0.2.2:5286/api/User/" + id + "/1";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.PUT,
                url,
                requestBody, // Pass the JSON data if needed
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle the response here if required
                        Toast.makeText(ViewProfile.this, "Deactivation Completed" , Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ViewProfile.this, HomeActivity.class);
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle errors here
                        // You can display an error message or perform other error-handling actions
                        String errorMessage = error.getMessage();
                        Log.e("Deactivation Failed", "Error: " + errorMessage);
                        Toast.makeText(ViewProfile.this, "Deactivation Failed" , Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // Add the request to the Volley request queue
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }



}