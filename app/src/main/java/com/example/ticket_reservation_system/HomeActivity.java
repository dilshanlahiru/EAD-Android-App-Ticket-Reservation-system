package com.example.ticket_reservation_system;

import android.content.Intent;
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

                login(nic, password);
//                Log.e("Registration", "nic: " + nic +" "+ "pw:" +password);

//                // For simplicity, show a toast message
//                if (!nic.isEmpty() && !password.isEmpty()) {
//                    // Check if NIC and password are not empty
//                    Toast.makeText(HomeActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
//                } else {
//                    // Handle empty NIC or password
//                    Toast.makeText(HomeActivity.this, "Please enter NIC and password", Toast.LENGTH_SHORT).show();
//                }
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
private void login (String email, String password){
    JSONObject requestBody = new JSONObject();
    Log.e("Registration", "nic: " + email +" "+ "pw:" +password);
    try {
//        node
//        requestBody.put("nic", email);
//        requestBody.put("password", password);
//        .net
        requestBody.put("email", email);
        requestBody.put("password", password);
//        node hard code
//        requestBody.put("nic", "123");
//        requestBody.put("password", "123");
//        nic: 123456789V pw:password123


    } catch (JSONException e) {
        e.printStackTrace();
    }

    // Define the URL of your API
//        String url = "http://localhost:5004/api/users/register";
    String url = "http://10.0.2.2:5286/api/User/login";
//    String url = "http://10.0.2.2:5004/api/users/login";
    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
            Request.Method.POST,
            url,
            requestBody,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    // Handle the API response here
                    // You can display a success message or perform other actions
                    Toast.makeText(HomeActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // Handle errors here
                    // You can display an error message or perform other error-handling actions
                    String errorMessage = error.getMessage();
                    Log.e("Registration", "Error: " + errorMessage);
                    Toast.makeText(HomeActivity.this, "Registration failed: " + errorMessage, Toast.LENGTH_SHORT).show();
                }
            }
    );

    // Add the request to the Volley request queue
    Volley.newRequestQueue(this).add(jsonObjectRequest);
}



}
