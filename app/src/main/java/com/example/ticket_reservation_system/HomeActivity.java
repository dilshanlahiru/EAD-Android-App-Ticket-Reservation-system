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


        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        String userNameT = databaseHelper.getUserName();
        if(!userNameT.isEmpty() ){
            Toast.makeText(HomeActivity.this, "Hi "+ userNameT, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(HomeActivity.this, ViewProfile.class);
            startActivity(intent);

        }

        // Set an onClickListener for the Login button
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the text from the EditText fields
                String nic = editTextNic.getText().toString();
                String password = editTextPassword.getText().toString();

                login(nic, password);
//                Intent intent = new Intent(HomeActivity.this, ViewProfile.class);
//                startActivity(intent);

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
        requestBody.put("email", email);
        requestBody.put("password", password);

    } catch (JSONException e) {
        e.printStackTrace();
    }

    // Define the URL of your API
        String url = "http://10.0.2.2:5286/api/User/login";
//    String url = "http://10.0.2.2:5004/api/users/register";
//    String url = "https://eadappbackend.azurewebsites.net/api/User/login";

//    String url = "http://10.0.2.2:5004/api/users/login";
    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
            Request.Method.POST,
            url,
            requestBody,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        int status = response.getInt("status");
                        Log.e("status", "status: " + status);
                        if (status == 0){
                            String userId = response.getString("id");
                            String nic = response.getString("nic");
                            String userName = response.getString("name");

                            DatabaseHelper dbHelper = new DatabaseHelper(HomeActivity.this);
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
                                Log.e("login success", "Error: " + "losgin sucess");
                                Toast.makeText(HomeActivity.this, "login success", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(HomeActivity.this, ViewProfile.class);
                                startActivity(intent);
//                                Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
//                                startActivity(intent);
                            } else {
                                // Error inserting data
                                Toast.makeText(HomeActivity.this, "Error inserting data", Toast.LENGTH_SHORT).show();
                            }

                            // Close the database
                            db.close();


//                            Log.e("login success", "Error: " + "losgin sucess");
//                            Toast.makeText(HomeActivity.this, "login success", Toast.LENGTH_SHORT).show();
                        }else {
                            Log.e("deactivated", "Error: " + "Your account deactivate");
                            Toast.makeText(HomeActivity.this, "Your Account De-Activated", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }


                    // Handle the API response here
                    // You can display a success message or perform other actions
//                    Log.e("login success", "Error: " + "losgin sucess");
//                    Toast.makeText(HomeActivity.this, "login success", Toast.LENGTH_SHORT).show();
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // Handle errors here
                    // You can display an error message or perform other error-handling actions
                    String errorMessage = error.getMessage();
//                    Log.e("login failed", "Error: " + errorMessage.substring(1,3));
                    Log.e("login failed", "Error: " + errorMessage);
                    Toast.makeText(HomeActivity.this, "Incorrect UN or PW" , Toast.LENGTH_SHORT).show();
                }
            }
    );

    // Add the request to the Volley request queue
    Volley.newRequestQueue(this).add(jsonObjectRequest);
}



}
