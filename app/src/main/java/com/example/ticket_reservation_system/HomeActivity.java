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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {
    private boolean stateT = false;
    private DatabaseHelper databaseHelper = new DatabaseHelper(this);
    private String userIDT ,userNameT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Find the EditText fields and buttons
        EditText editTextNic = findViewById(R.id.editTextNic);
        EditText editTextPassword = findViewById(R.id.editTextPassword);
        Button buttonLogin = findViewById(R.id.buttonLogin);
        Button buttonRegister = findViewById(R.id.buttonRegister);

        try {

            userIDT = databaseHelper.getUserID();
            userNameT = databaseHelper.getUserName();
            checkBackendStatus(userIDT);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
//        if(!userNameT.isEmpty() ){
//            Toast.makeText(HomeActivity.this, "Hi "+ userNameT, Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(HomeActivity.this, ViewProfile.class);
//            startActivity(intent);
//
//        }

        // Set an onClickListener for the Login button
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the text from the EditText fields
                String nic = editTextNic.getText().toString();
                String password = editTextPassword.getText().toString();

                login(nic, password);
//                Intent intent = new Intent(HomeActivity.this, CommenPage.class);
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
        String url = Config.BASE_URL+"/api/User/login";

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
                                fetchSchedules();
                                fetchReservations();
                                Intent intent = new Intent(HomeActivity.this, CommenPage.class);
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

    private void checkBackendStatus (String id) {
        JSONObject requestBody = new JSONObject();
        String url = Config.BASE_URL+"/api/User/"+id;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                requestBody, // Pass the JSON data if needed
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

//                        cleanSQLLITEDatabases ();
//                        addSchedules ();
//                        addReservations(id);
                        fetchSchedules();
                        fetchReservations();
                        try {
                            if(response.getInt("status")==0){
                                    stateT= true;
                                Toast.makeText(HomeActivity.this, "Hi "+ userNameT, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(HomeActivity.this, CommenPage.class);
                                startActivity(intent);
                            }else if (response.getInt("status")==1){
                                stateT= false;
                                Toast.makeText(HomeActivity.this, "Your Account Deactivated", Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(HomeActivity.this, ViewProfile.class);
//                                startActivity(intent);
                            }


                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        if (id != null){
                            Toast.makeText(HomeActivity.this, "Hi "+ userNameT +" , Loading in offline mode", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(HomeActivity.this, CommenPage.class);
                            startActivity(intent);
                        }else {
                            Toast.makeText(HomeActivity.this, "Auto Login Failed", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(HomeActivity.this, CommenPage.class);
//                        startActivity(intent);
                        }

                    }
                }
        );

        // Add the request to the Volley request queue
        Volley.newRequestQueue(this).add(jsonObjectRequest);

    }

    private void cleanSQLLITEDatabases (){
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.execSQL("DELETE FROM schedules");
        db.execSQL("DELETE FROM reservations");
        db.close();
    }

    private void fetchSchedules (){

        List<Schedule> schedules = new ArrayList<>();

        JSONObject requestBody = new JSONObject();

        String url = Config.BASE_URL+"/api/Schedule";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest( // Use JsonArrayRequest instead of JsonObjectRequest
                Request.Method.GET,
                url,
                requestBody.names(), // Pass the JSON data if needed
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("URL data", "URL data: methanta awa ");

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject object = response.getJSONObject(i);
                                schedules.add(new Schedule(object.getString("id"),object.getString("trainId"),object.getString("trainName"),
                                        object.getString("start"),object.getString("destination"),formatDateTime(object.getString("startDateTime"))
                                        ,formatDateTime(object.getString("destinationDateTime")),
                                        object.getInt("status")));
//
                            }
//                            updateRecyclerView(reservations);
                            addSchedules(schedules);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error get data", "Error getting data: " + error);

                    }
                }
        );



// Add the request to the Volley request queue
        Volley.newRequestQueue(this).add(jsonArrayRequest);

    }

    private void fetchReservations (){
        List<Reservation> reservations = new ArrayList<>();
        String nic = databaseHelper.getNIC();;

        JSONObject requestBody = new JSONObject();
        String url = Config.BASE_URL+"/api/Reservation/traverler/"+nic;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest( // Use JsonArrayRequest instead of JsonObjectRequest
                Request.Method.GET,
                url,
                requestBody.names(), // Pass the JSON data if needed
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("URL data", "URL data: methanta awa ");

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject object = response.getJSONObject(i);
                                JSONObject scheduleObject = object.getJSONObject("schedule");

//                                Log.e("URL data", "URL data: id: " + formatDateTime(object.getString("bookingDateTime")));
//                                Log.e("URL data", "URL data: Name: " + scheduleObject.getString("trainName"));
                                reservations.add(new Reservation(object.getString("id"), object.getString("trainId"),
                                        object.getString("scheduleId"), scheduleObject.getString("trainName"),
                                        scheduleObject.getString("start"), scheduleObject.getString("destination"),
                                        formatDateTime(object.getString("bookingDateTime")), formatDateTime(scheduleObject.getString("startDateTime")),
                                        formatDateTime(scheduleObject.getString("destinationDateTime")), object.getInt("seats"),
                                        object.getInt("status"), object.getBoolean("isPast")));

                                // Extract and handle data from each JSON object here
                            }

                            addResevations(reservations);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error get data", "Error getting data: " + error);

                    }
                }
        );



        Volley.newRequestQueue(this).add(jsonArrayRequest);

    }

    private  void  addSchedules (List<Schedule> schedules){
        Log.e("URL data", "mewa execute wenawa");
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.execSQL("DELETE FROM schedules");
        db.close();
        databaseHelper.insertSchedules(schedules);

    }
    private void addResevations (List<Reservation> reservations){
        Log.e("URL data", "mewa execute wenawa");
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.execSQL("DELETE FROM reservations");
        db.close();
        databaseHelper.insertReservations(reservations);
    }


    private String formatDateTime(String dateTime) {
        try {
            SimpleDateFormat inputFormat1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
            SimpleDateFormat inputFormat2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);

            Date date;
            try {
                date = inputFormat1.parse(dateTime);
            } catch (ParseException e) {
                // If the first format fails, try the second format
                date = inputFormat2.parse(dateTime);
            }

            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return dateTime; // Return original value in case of an error
        }
    }


}
