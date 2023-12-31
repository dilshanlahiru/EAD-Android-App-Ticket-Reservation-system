package com.example.ticket_reservation_system;



import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder>{

    private Context context;
    private List<Reservation> reservationList;

    public ReservationAdapter(Context context, List<Reservation> reservationList) {
        this.context = context;
        this.reservationList = reservationList;
    }

    @NonNull
    @Override
//    public ReservationAdapter.ReservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return null;
//    }
    public ReservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_reservation, parent, false);
        return new ReservationViewHolder(view);
    }
    @Override
//    public void onBindViewHolder(@NonNull ReservationAdapter.ReservationViewHolder holder, int position) {
//
//    }
    public void onBindViewHolder(@NonNull ReservationViewHolder holder, int position) {
        Reservation reservation = reservationList.get(position);
        holder.bind(reservation);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Dialog dialog = new Dialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                View dialogview = LayoutInflater.from(view.getRootView().getContext()).inflate(R.layout.popup_reservation_details, null);
//                dialog.setContentView(R.layout.popup_reservation_details);
                // Find and set text for TextViews in the popup
                TextView popupTrainName = dialogview.findViewById(R.id.popupTrainName);
                TextView popupStart = dialogview.findViewById(R.id.popupStart);
                TextView popupDestination = dialogview.findViewById(R.id.popupDestination);
                TextView popupStartTime = dialogview.findViewById(R.id.popupStartDateTime);
                TextView popupDestinationTime = dialogview.findViewById(R.id.popupDestinaionDateTime);
                TextView seatCount = dialogview.findViewById(R.id.popupseatCount);
                TextView availability = dialogview.findViewById(R.id.popupStatus);
                Button editButton = dialogview.findViewById(R.id.button1);
                Button deleteButton = dialogview.findViewById(R.id.button2);


                // Populate the TextViews with reservation details
                popupTrainName.setText( reservation.getTrainName());
                popupStart.setText(reservation.getStart());
                popupDestination.setText(reservation.getDestination());
                popupStartTime.setText(reservation.getStartDateTime());
                popupDestinationTime.setText(reservation.getDestinationDateTime());
                seatCount.setText(""+reservation.getSeats());
                availability.setText(checkConditionPopUp(reservation.isPast()));

                editButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("click", "click edit button" );
                        if (reservation.getStatus()==1){
                            Toast.makeText(context, " Changes can only be made up to 5 days before the reservation date", Toast.LENGTH_SHORT).show();

                        }else {
                            Intent intent = new Intent(view.getContext(), UpdateResevations.class);
                            intent.putExtra("id", reservation.getReservationId());
                            view.getContext().startActivity(intent);
                        }
                        // Handle the "Add Reservation" button click
                        // You can open a new activity or a dialog to create a new reservation
                    }
                });
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (reservation.getStatus()==1){
                            Toast.makeText(context, " Changes can only be made up to 5 days before the reservation date", Toast.LENGTH_SHORT).show();

                        }else {
                            Log.e("click", "click delete button");
                            deleteReservation(reservation.getReservationId());
                        }
//                        Intent intent = new Intent(context, CommenPage.class);
//                        context.startActivity(intent);
                        // Handle the "Add Reservation" button click
                        // You can open a new activity or a dialog to create a new reservation
                    }
                });


                builder.setView(dialogview);
                builder.setCancelable(true);
                builder.show();

                Log.e("click", "click "+ reservation.getTrainName() );



            }
        });
    }

    public  String checkConditionPopUp(boolean input) {
        String out =null;
        if(input){
            out = "Expired";
        }else {
            out = "Available";
        }
        return out;
    }

    @Override
    public int getItemCount() {
//        return 0;
        return reservationList.size();
    }

    private void deleteReservation (String id){
        Log.e("ID", "ID: " + id);
        String url = Config.BASE_URL+"/api/Reservation/" + id;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.DELETE,
                url,
                null, // No request body for DELETE requests
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

//                        DatabaseHelper databaseHelper = new DatabaseHelper(context);
//                        SQLiteDatabase db = databaseHelper.getWritableDatabase();
//                        db.execSQL("DELETE FROM reservations");
                        Toast.makeText(context, "Delete successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, CommenPage.class);
                        context.startActivity(intent);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String errorMessage = error.getMessage();
                        Log.e("Deletion", "Deletion Error: " + errorMessage);
                        Toast.makeText(context, "Deletion failed", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // Add the request to the Volley request queue
        Volley.newRequestQueue(context).add(jsonObjectRequest);
    }


    public  class ReservationViewHolder extends RecyclerView.ViewHolder {
        private TextView trainNameTextView;
        private TextView startTextView;
        private TextView destinationTextView;
        private TextView seatsTextView;
        private TextView statusTextView;

        public ReservationViewHolder(@NonNull View itemView) {
            super(itemView);
            trainNameTextView = itemView.findViewById(R.id.trainNameTextView);
            startTextView = itemView.findViewById(R.id.startTextView);
            destinationTextView = itemView.findViewById(R.id.destinationTextView);
            seatsTextView = itemView.findViewById(R.id.seatsTextView);
            statusTextView = itemView.findViewById(R.id.v_status);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle item click to show details (e.g., in a pop-up)
//                    Reservation selectedReservation = reservationList.get(getAdapterPosition());
//                    Reservation selectedReservation = reservationList.get(getAdapterPosition());
//                    Log.e("click", "click "+ selectedReservation.getReservationId() );
//                    showReservationDetailsPopup(selectedReservation);
                    // Call a method to display details (e.g., showReservationDetailsPopup(selectedReservation))
                }
            });
        }

        public void bind(Reservation reservation) {
            trainNameTextView.setText("Train: " + reservation.getTrainName());
            startTextView.setText("From: " + reservation.getStart());
            destinationTextView.setText("To: " + reservation.getDestination());
            seatsTextView.setText("Seats: " + reservation.getSeats());
            statusTextView.setText(checkCondition(reservation.isPast()));
        }

        public  String checkCondition(boolean input) {
            String out =null;
            if(input){
                out = "Reservation Status: Expired";
            }else {
                out = "Reservation Status: Available";
            }
            return out;
        }

    }






}
