package com.example.ticket_reservation_system;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class UpdateResevations extends AppCompatActivity {

    private TextView trainNameTextView;
    private TextView startTextView;
    private TextView destinationTextView;
    private EditText seatcountEditText;
    private Button updateReservationButton;
    private String trinName, start, destination, nic, resID, sheduleID;
    private DatabaseHelper databaseHelper = new DatabaseHelper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_resevations);
        Intent intent = getIntent();
        resID = intent.getStringExtra("id");
        Log.e("resID", "Res id: "+ resID);
        trainNameTextView = findViewById(R.id.eTrainName);
        startTextView = findViewById(R.id.eStart);
        destinationTextView = findViewById(R.id.eDestination);
        seatcountEditText = findViewById(R.id.editTextNumber);
        updateReservationButton = findViewById(R.id.buttonUpdateReservation);

//        DatabaseHelper databaseHelper = new DatabaseHelper(this);
//        nic = databaseHelper.getNIC();
        nic  = databaseHelper.getNIC();

        getData(resID);

        updateReservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();

            }
        });
    }

    private void getData(String id){
        JSONObject requestBody = new JSONObject();
        String url = "http://10.0.2.2:5286/api/Reservation/"+id;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                requestBody, // Pass the JSON data if needed
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject scheduleObject = response.getJSONObject("schedule");
                            trainNameTextView.setText(scheduleObject.getString("trainName"));
                            startTextView.setText(scheduleObject.getString("start"));
                            destinationTextView.setText(scheduleObject.getString("destination"));
                            seatcountEditText.setText(response.getString("seats"));
                            sheduleID = response.getString("scheduleId");
                            Log.e("Data", "seat: " + response.getInt("seats"));
                            Log.e ("data awa", "data awa");

                        } catch (JSONException e) {
                            throw new RuntimeException(e);

                        }
//                        Log.e ("update success", "update success");

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
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }

    private void update (){
        Log.e("Update data", "nic: " + nic+", resID: " + resID +", scheduleID: "+ sheduleID);

        JSONObject requestBody = new JSONObject();

        try {
            requestBody.put("id", resID);
            requestBody.put("scheduleId", sheduleID);
            requestBody.put("travelerNIC", nic);
            requestBody.put("status", 0);
            requestBody.put("seats", seatcountEditText.getText().toString());

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        String url = "http://10.0.2.2:5286/api/Reservation";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.PUT,
                url,
                requestBody, // Pass the JSON data if needed
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(UpdateResevations.this, "Update successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UpdateResevations.this, Previous_Reservation_List.class);
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


}