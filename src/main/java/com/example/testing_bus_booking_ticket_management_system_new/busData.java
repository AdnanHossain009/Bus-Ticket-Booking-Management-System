package com.example.testing_bus_booking_ticket_management_system_new;

import java.sql.Date;

public class busData {

    private Integer busID;
    private String location;
    private String status;
    private Double price;
    private Date date;

    public busData(Integer busID, String location, String type, Double price, Date date) {
        this.busID = busID;
        this.location = location;
        this.status = type;
        this.price = price;
        this.date = date;
    }

    public Integer getBusID() {
        return busID;
    }

    public String getLocation() {
        return location;
    }

    public String getStatus() {
        return status;
    }

    public Double getPrice() {
        return price;
    }

    public Date getDate() {
        return date;
    }
}

