module com.example.testing_bus_booking_ticket_management_system_new {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires okhttp3;
    requires com.google.gson;
    requires java.sql;
    requires javafx.media;


    opens com.example.testing_bus_booking_ticket_management_system_new to javafx.fxml;
    exports com.example.testing_bus_booking_ticket_management_system_new;
}