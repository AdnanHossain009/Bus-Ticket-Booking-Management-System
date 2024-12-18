package com.example.testing_bus_booking_ticket_management_system_new;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class database {

    public static Connection connectionDb()
    {

        String url = "jdbc:mysql://localhost:3306/busdata";
        String username = "root";
        String password = "Gx#7!dQp9@TfL2K";

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded susccessfully");
        }
        catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }

        try{
            Connection connection = DriverManager.getConnection(url,username,password);
            System.out.println("Database connection established successfully!");
            return connection;
        }

        catch (SQLException e){
            System.out.println("Database Driver not found: " + e.getMessage());
        }

        return null;
    }
}
