package com.example.ticket_reservation_system;



import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
                Button editButton = dialogview.findViewById(R.id.button1);
                Button deleteButton = dialogview.findViewById(R.id.button2);

                // Populate the TextViews with reservation details
                popupTrainName.setText( reservation.getTrainName());
                popupStart.setText(reservation.getStart());
                popupDestination.setText(reservation.getDestination());
                editButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("click", "click edit button" );
                        Intent intent = new Intent(view.getContext(),UpdateResevations.class);
                        intent.putExtra("id", reservation.getReservationId());
                        view.getContext().startActivity(intent);
                        // Handle the "Add Reservation" button click
                        // You can open a new activity or a dialog to create a new reservation
                    }
                });
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("click", "click delete button" );
                        deleteReservation(reservation.getReservationId());

                        Intent intent = new Intent(context, Previous_Reservation_List.class);
                        context.startActivity(intent);
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

    @Override
    public int getItemCount() {
//        return 0;
        return reservationList.size();
    }

    private void deleteReservation (String id){
        Log.e("ID", "ID: " + id);
        String url = "http://10.0.2.2:5286/api/Reservation/" + id;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.DELETE,
                url,
                null, // No request body for DELETE requests
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context, "Delete successfully", Toast.LENGTH_SHORT).show();

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

        public ReservationViewHolder(@NonNull View itemView) {
            super(itemView);
            trainNameTextView = itemView.findViewById(R.id.trainNameTextView);
            startTextView = itemView.findViewById(R.id.startTextView);
            destinationTextView = itemView.findViewById(R.id.destinationTextView);
            seatsTextView = itemView.findViewById(R.id.seatsTextView);

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
        }

    }






}
