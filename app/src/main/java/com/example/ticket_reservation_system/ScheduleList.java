package com.example.ticket_reservation_system;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

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

public class ScheduleList extends AppCompatActivity {

    private RecyclerView recyclerView;

    private  ScheduleAdapter adapter;

    private List<Schedule> scheduleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_list);

        recyclerView = findViewById(R.id.scheduleRecyclerView);
        scheduleList = fetchScheduleData();
        adapter = new ScheduleAdapter(this, scheduleList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void updateRecycleView (List<Schedule> schedules){
        adapter = new ScheduleAdapter(this, schedules);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private  List<Schedule> fetchScheduleData (){
        List <Schedule> schedules = new ArrayList<>();

        JSONObject requestBody = new JSONObject();

        String url = "http://10.0.2.2:5286/api/Schedule";
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
                            updateRecycleView(schedules);

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



        return schedules;
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