package com.example.ticket_reservation_system;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class RegisterActivity extends AppCompatActivity {
    private EditText nicEditText, fullNameEditText, emailEditText, passwordEditText;
    private Button registerButton;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nicEditText = findViewById(R.id.nic);
        fullNameEditText = findViewById(R.id.fullName);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.Password);
        registerButton = findViewById(R.id.buttonRegister);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();

            }

        });

    }
    private void registerUser() {
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("id", "");
            requestBody.put("name", fullNameEditText.getText().toString());
            requestBody.put("email", emailEditText.getText().toString());
            requestBody.put("password", passwordEditText.getText().toString());
            requestBody.put("nic", nicEditText.getText().toString());
            requestBody.put("role", 2);
            requestBody.put("status", 0);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        String url = "http://10.0.2.2:5286/api/User";
//        Log.e("name", "data: " + fullNameEditText.getText().toString());
//        Log.e("email", "data: " + emailEditText.getText().toString());
//        Log.e("password", "data: " + passwordEditText.getText().toString());
//        Log.e("nic", "data: " + nicEditText.getText().toString());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle the API response here
                        // You can display a success message or perform other actions

                        try {
                            String userId = response.getString("id");
                            String nic = response.getString("nic");
                            String userName = response.getString("name");
                            int status = response.getInt("status");

                            Log.e("response", "data: " + userId + nic + userName+ status);

                            // Create or open the database
                            DatabaseHelper dbHelper = new DatabaseHelper(RegisterActivity.this);
                            SQLiteDatabase db = dbHelper.getWritableDatabase();

                            // Clear the table before adding new data
                            db.execSQL("DELETE FROM users");

                            // Insert the data into the database
                            ContentValues values = new ContentValues();
                            values.put("user_id", userId);
                            values.put("nic", nic);
                            values.put("user_name", userName);
                            values.put("status", status);

                            long newRowId = db.insert("users", null, values);

                            if (newRowId != -1) {
                                // Data inserted successfully
                                Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                                startActivity(intent);
                            } else {
                                // Error inserting data
                                Toast.makeText(RegisterActivity.this, "Error inserting data", Toast.LENGTH_SHORT).show();
                            }

                            // Close the database
                            db.close();

                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }

//                        Toast.makeText(RegisterActivity.this, "Registration successfull" , Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle errors here
                        // You can display an error message or perform other error-handling actions
                        String errorMessage = error.getMessage();
                        Log.e("Registration", "Error: " + errorMessage);
//                        Log.d("RegisterActivity", "onErrorResponse called");
                        Toast.makeText(RegisterActivity.this, "Registration failed: " + errorMessage, Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // Add the request to the Volley request queue
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }


}



//public class RegisterActivity extends AppCompatActivity {
//
//    private EditText editTextNic;
//    private EditText editTextFullName;
//    private EditText editTextDob;
//    private EditText editTextPhoneNumber;
//    private EditText editTextEmail;
//    private EditText editTextPassword;
//    private Button buttonRegister;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_register);
//
//        // Initialize UI elements
////        editTextNic = findViewById(R.id.editTextNic);
////        editTextFullName = findViewById(R.id.editTextFullName);
////        editTextDob = findViewById(R.id.editTextDob);
////        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
////        editTextEmail = findViewById(R.id.editTextEmail);
////        editTextPassword = findViewById(R.id.editTextPassword);
////        buttonRegister = findViewById(R.id.buttonRegister);
//
//        // Set click listener for the Register button
//        buttonRegister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Call the method to make the API request
//                registerUser();
//            }
//        });
//    }
//
//    private void registerUser() {
//        // Create a JSON object for the POST request body
//        JSONObject requestBody = new JSONObject();
//        try {
////            requestBody.put("nic", editTextNic.getText().toString());
////            requestBody.put("fullName", editTextFullName.getText().toString());
////            requestBody.put("dob", editTextDob.getText().toString());
////            requestBody.put("phoneNumber", editTextPhoneNumber.getText().toString());
////            requestBody.put("email", editTextEmail.getText().toString());
////            requestBody.put("password", editTextPassword.getText().toString());
////            requestBody.put("accountStatus", true);
////            requestBody.put("nic", "123456");
////            requestBody.put("fullName", "John Doe");
////            requestBody.put("dob", "1995-12-12");
////            requestBody.put("phoneNumber", "1234567890");
////            requestBody.put("accountStatus", true);
////            requestBody.put("email", "john.doe@example.com");
////            requestBody.put("password", "password123");
//            requestBody.put("id", "");
//            requestBody.put("name", "new2");
//            requestBody.put("email", "new2");
//            requestBody.put("password", "new2");
//            requestBody.put("nic", "123456");
//            requestBody.put("role", 0);
//            requestBody.put("status", 0);
//
////            {
////                "id": "",
////                    "name": "new2",
////                    "email": "new2",
////                    "password": "new2",
////                    "nic": 4567,
////                    "role": 0,
////                    "status": 0
////            }
//
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        // Define the URL of your API
////        String url = "http://localhost:5004/api/users/register";
////        String url = "http://10.0.2.2:5004/api/users/register";
//        String url = "http://10.0.2.2:5286/api/User";
////        String url = "https://eadtestapp.azurewebsites.net/api/users/register";
//
//        // Create a JsonObjectRequest to send the POST request
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
//                Request.Method.POST,
//                url,
//                requestBody,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        // Handle the API response here
//                        // You can display a success message or perform other actions
//                        Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // Handle errors here
//                        // You can display an error message or perform other error-handling actions
//                        String errorMessage = error.getMessage();
//                        Log.e("Registration", "Error: " + errorMessage);
//                        Toast.makeText(RegisterActivity.this, "Registration failed: " + errorMessage, Toast.LENGTH_SHORT).show();
//                    }
//                }
//        );
//
//        // Add the request to the Volley request queue
//        Volley.newRequestQueue(this).add(jsonObjectRequest);
//    }
//}
