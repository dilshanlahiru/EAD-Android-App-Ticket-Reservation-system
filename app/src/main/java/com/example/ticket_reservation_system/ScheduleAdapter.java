package com.example.ticket_reservation_system;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder> {

    private Context context;
    private List <Schedule> scheduleList;

    private EditText popupSeatCount;


    public  ScheduleAdapter (Context context, List<Schedule> scheduleList){
        this.context = context;
        this.scheduleList = scheduleList;
    }

    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_schedule, parent, false);
        return new ScheduleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, int position) {
        Schedule schedule = scheduleList.get(position);
        holder.bind(schedule);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                View dialogview = LayoutInflater.from(view.getRootView().getContext()).inflate(R.layout.pop_up_schedul_details, null);
                TextView popupTrainName = dialogview.findViewById(R.id.popupTrainName);
                TextView popupStart = dialogview.findViewById(R.id.popupStart);
                TextView popupDestination = dialogview.findViewById(R.id.popupDestination);
                TextView popUpStartTime = dialogview.findViewById(R.id.popupStartTime);
                TextView popUpDestinatinTime = dialogview.findViewById(R.id.popupDestinationTime);
                popupSeatCount = dialogview.findViewById(R.id.editTextNumber);
                Button reserveButton = dialogview.findViewById(R.id.button1);

                popupTrainName.setText(schedule.getTrainName());
                popupStart.setText(schedule.getStart());
                popupDestination.setText(schedule.getDestination());
                popUpStartTime.setText(schedule.getStartDateTime());
                popUpDestinatinTime.setText(schedule.getDestinationDateTime());

                DatabaseHelper databaseHelper = new DatabaseHelper(context);
                String nic = databaseHelper.getNIC();

                reserveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (!popupSeatCount.getText().toString().isEmpty()){
                            reserve("",schedule.getScheduleId(),nic,popupSeatCount.getText().toString());
                        }else {
                            Toast.makeText(context, "Please Fill the Seat Count" , Toast.LENGTH_SHORT).show();
                        }


                    }
                });
                builder.setView(dialogview);
                builder.setCancelable(true);
                builder.show();

            }
        });
    }

    @Override
    public int getItemCount() {
//        return 0;
        return scheduleList.size();
    }

    private void reserve1 (){
        String seatCount = popupSeatCount.getText().toString();
        if(seatCount.isEmpty()){
            Log.e("Registration", "Seat count is: " + "empty");
        }else {
            Log.e("Registration", "Seat count is: " + seatCount);
        }


    }
    private void reserve(String id, String scheduleID, String travellerNIC,String seats){
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("id", id);
            requestBody.put("scheduleId", scheduleID);
            requestBody.put("travelerNIC", travellerNIC);
            requestBody.put("status", 0);
            requestBody.put("seats", seats);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        String url = "http://10.0.2.2:5286/api/Reservation";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Toast.makeText(context, "Reservation successfully" , Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(context, Previous_Reservation_List.class);
                        context.startActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String errorMessage = error.getMessage();
                        Log.e("Registration", "Error: " + errorMessage);
                        Toast.makeText(context, "Reservation failed" , Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // Add the request to the Volley request queue
        Volley.newRequestQueue(context).add(jsonObjectRequest);
    }



    public class ScheduleViewHolder extends RecyclerView.ViewHolder {
        private TextView trainNameTextView;
        private TextView startTextView;
        private TextView destinationTextView;
        private TextView startTimeTextView;
        private  TextView destinationTimeTextView;

        public ScheduleViewHolder(@NonNull View itemView) {
            super(itemView);
            trainNameTextView = itemView.findViewById(R.id.trainNameTextView);
            startTextView = itemView.findViewById(R.id.startTextView);
            destinationTextView = itemView.findViewById(R.id.destinationTextView);
            startTimeTextView = itemView.findViewById(R.id.startDateTime);
            destinationTimeTextView = itemView.findViewById(R.id.destinationDateTime);
        }

        public void bind(Schedule schedule) {
            trainNameTextView.setText("Train: "+ schedule.getTrainName());
            startTextView.setText("From: "+ schedule.getStart());
            destinationTextView.setText("To: "+ schedule.getDestination());
            startTimeTextView.setText("Arrives at: "+ schedule.getStartDateTime());
            destinationTimeTextView.setText("Departure at: "+ schedule.getDestinationDateTime());
        }
    }

}
