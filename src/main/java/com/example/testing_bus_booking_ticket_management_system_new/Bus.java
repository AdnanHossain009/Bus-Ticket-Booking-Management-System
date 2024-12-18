package com.example.testing_bus_booking_ticket_management_system_new;

public class Bus {
    private String busId;
    private String busName;
    private int totalSeats;
    private int bookedSeats;

    // Constructors, Getters, and Setters
    public int getAvailableSeats() {
        return totalSeats - bookedSeats;
    }
}
