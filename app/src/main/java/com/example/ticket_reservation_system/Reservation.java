package com.example.ticket_reservation_system;

public class Reservation {
    private String reservationId;
    private String trainId;
    private String scheduleId;
    private String trainName;
    private String start;
    private String destination;
    private String bookingDateTime;
    private String startDateTime;
    private String destinationDateTime;
    private int seats;
    private int status;
    private boolean isPast;

    public Reservation() {

    }

    public Reservation(String reservationId, String trainId, String scheduleId, String trainName, String start, String destination, String bookingDateTime, String startDateTime, String destinationDateTime, int seats, int status, boolean isPast) {
        this.reservationId = reservationId;
        this.trainId = trainId;
        this.scheduleId = scheduleId;
        this.trainName = trainName;
        this.start = start;
        this.destination = destination;
        this.bookingDateTime = bookingDateTime;
        this.startDateTime = startDateTime;
        this.destinationDateTime = destinationDateTime;
        this.seats = seats;
        this.status = status;
        this.isPast = isPast;
    }

    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    public String getTrainId() {
        return trainId;
    }

    public void setTrainId(String trainId) {
        this.trainId = trainId;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getBookingDateTime() {
        return bookingDateTime;
    }

    public void setBookingDateTime(String bookingDateTime) {
        this.bookingDateTime = bookingDateTime;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getDestinationDateTime() {
        return destinationDateTime;
    }

    public void setDestinationDateTime(String destinationDateTime) {
        this.destinationDateTime = destinationDateTime;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isPast() {
        return isPast;
    }

    public void setPast(boolean past) {
        isPast = past;
    }

}
