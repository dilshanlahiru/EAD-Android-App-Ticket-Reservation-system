package com.example.ticket_reservation_system;

public class Schedule {

    private String scheduleId;
    private String trainId;
    private String trainName;
    private String start;
    private String destination;
    private String startDateTime;
    private String destinationDateTime;
    private int status;

    public Schedule() {
    }

    public Schedule(String scheduleId, String trainId, String trainName, String start, String destination, String startDateTime, String destinationDateTime, int status) {
        this.scheduleId = scheduleId;
        this.trainId = trainId;
        this.trainName = trainName;
        this.start = start;
        this.destination = destination;
        this.startDateTime = startDateTime;
        this.destinationDateTime = destinationDateTime;
        this.status = status;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getTrainId() {
        return trainId;
    }

    public void setTrainId(String trainId) {
        this.trainId = trainId;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
