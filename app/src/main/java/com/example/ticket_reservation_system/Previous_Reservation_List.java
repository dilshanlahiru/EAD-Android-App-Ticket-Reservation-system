package com.example.ticket_reservation_system;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
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

public class Previous_Reservation_List extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Button addReservationButton;
    private ReservationAdapter adapter;
    private List<Reservation> reservationList;
    private DatabaseHelper databaseHelper = new DatabaseHelper(this);
    private String nic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_reservation_list);


        nic  = databaseHelper.getNIC();
        recyclerView = findViewById(R.id.reservationRecyclerView);
        addReservationButton = findViewById(R.id.addReservationButton);

        // Initialize your RecyclerView and Adapter here
        reservationList = fetchReservationData(); // Implement this method to fetch data from the API

        // Create and set the adapter for the RecyclerView
        adapter = new ReservationAdapter(this, reservationList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);




        addReservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Previous_Reservation_List.this, ScheduleList.class);
                startActivity(intent);


                // Handle the "Add Reservation" button click
                // You can open a new activity or a dialog to create a new reservation
            }
        });
    }

    private void updateRecyclerView(List<Reservation> reservations) {
        adapter = new ReservationAdapter(this, reservations);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }





    private List<Reservation> fetchReservationData() {

        // Return a list of Reservation objects
        List<Reservation> reservations = new ArrayList<>();

        JSONObject requestBody = new JSONObject();
        String url = "http://10.0.2.2:5286/api/Reservation/traverler/"+nic;
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
                                        scheduleObject.getInt("status"), object.getBoolean("isPast")));

                                // Extract and handle data from each JSON object here
                            }
                            updateRecyclerView(reservations);

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



        // Replace this with your API call to get reservation data



//        reservations.add(new Reservation("Train 1", "Start 1", "Destination 1"));
//        reservations.add(new Reservation("12345", "ABC123",
//                "SCH456", "Sample Express", "City A", "City B",
//                "2023-10-12 14:30:00", "2023-10-15 08:00:00",
//                "2023-10-15 12:30:00", 2, 1, false));
//
//        reservations.add(new Reservation("12345", "ABC123",
//                "SCH456", "large Express", "City c", "City d",
//                "2023-10-12 14:30:00", "2023-10-15 08:00:00",
//                "2023-10-15 12:30:00", 3, 1, false));

        // Add more reservations as needed
        return reservations;
    }

    private String formatDateTime(String dateTime) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);
            Date date = inputFormat.parse(dateTime);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return dateTime; // Return original value in case of an error
        }
    }

    private void showReservationDetailsPopup(Reservation reservation) {
        // Create a Dialog to display the details of the selected reservation
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_reservation_details);

        // Find and set text for TextViews in the popup
        TextView popupTrainName = dialog.findViewById(R.id.popupTrainName);
        TextView popupStart = dialog.findViewById(R.id.popupStart);
        TextView popupDestination = dialog.findViewById(R.id.popupDestination);

        // Populate the TextViews with reservation details
        popupTrainName.setText("Train Name: " + reservation.getTrainName());
        popupStart.setText("Start: " + reservation.getStart());
        popupDestination.setText("Destination: " + reservation.getDestination());

        // You can add more TextViews and set their text here

        // Show the dialog
        dialog.show();
    }

}