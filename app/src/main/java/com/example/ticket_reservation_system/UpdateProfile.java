package com.example.ticket_reservation_system;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class UpdateProfile extends AppCompatActivity {

    private ImageView userImageView;
    private TextView  nicTextView;
    private EditText  fullNameEditText, emailEditText, passwordEditText;
    private Button editProfileButton;

    private String email, userName, password, nic,userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        nicTextView = findViewById(R.id.vnic);
        fullNameEditText = findViewById(R.id.fullName);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.Password);
        editProfileButton = findViewById(R.id.buttonRegister);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        nic = databaseHelper.getNIC();
        userID= databaseHelper.getUserID();
        getData(userID);
        nicTextView.setText("NIC : "+nic);
        fullNameEditText.setText(userName);
        emailEditText.setText(email);

        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                updateUser(userID);
                if (fullNameEditText.getText().toString().isEmpty() ||
                        emailEditText.getText().toString().isEmpty() ||
                        passwordEditText.getText().toString().isEmpty()) {
                    // Display an error message or provide some feedback to the user
                    // For example, you can show a Toast message:
                    Toast.makeText(UpdateProfile.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    // All fields are not empty, proceed with the update
                    updateUser(userID);
                }

            }
        });


    }

    private void updateUser (String id){
        JSONObject requestBody = new JSONObject();

        try {
            requestBody.put("id", id);
            requestBody.put("name", fullNameEditText.getText().toString());
            requestBody.put("email", emailEditText.getText().toString());
            requestBody.put("password", passwordEditText.getText().toString());
            requestBody.put("nic", nic);
            requestBody.put("role", 2);
            requestBody.put("status", 0);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        String url = "http://10.0.2.2:5286/api/User";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.PUT,
                url,
                requestBody, // Pass the JSON data if needed
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(UpdateProfile.this, "Update successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UpdateProfile.this, HomeActivity.class);
                        startActivity(intent);
                        Log.e ("update success", "update success");

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e ("update faild", "update faild");
                        }
                }
        );

        // Add the request to the Volley request queue
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }

    private void getData (String id) {
        JSONObject requestBody = new JSONObject();
        String url = "http://10.0.2.2:5286/api/User/"+id;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                requestBody, // Pass the JSON data if needed
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                       try {
                           email = response.getString("email");
                           userName = response.getString("name");
                           fullNameEditText.setText(userName);
                           emailEditText.setText(email);
                           Log.e("get data", "get data: " + email+userName);

                       } catch (Exception e) {
                           throw new RuntimeException(e);
                       }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error get data", "error get data " + email+userName);

                    }
                }
        );

        // Add the request to the Volley request queue
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }
}