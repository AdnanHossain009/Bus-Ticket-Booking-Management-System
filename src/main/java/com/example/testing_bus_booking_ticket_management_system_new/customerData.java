package com.example.testing_bus_booking_ticket_management_system_new;

import java.sql.Date;

public class customerData {

    private Integer customerNum;
    private String firstName;
    private String lastName;
    private String gender;
    private String phoneNum;
    private Integer busId;
    private String location;
    private String type;
    private Integer seatNum;
    private Double total;
    private Date date;


    public customerData(int customerNum, String firstName, String lastName, String gender, String phoneNum, Integer busId, String location, String type, Integer seatNum, Double total, Date date) {
        this.customerNum = customerNum;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.phoneNum = phoneNum;
        this.busId = busId;
        this.location = location;
        this.type = type;
        this.seatNum = seatNum;
        this.total = total;
        this.date = date;
    }

    public Integer getCustomerNum() {
        return customerNum;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public Integer getBusId() {
        return busId;
    }

    public String getLocation() {
        return location;
    }

    public String getType() {
        return type;
    }

    public Integer getSeatNum() {
        return seatNum;
    }

    public Double getTotal() {
        return total;
    }

    public Date getDate() {
        return date;
    }
}
