package com.example.testing_bus_booking_ticket_management_system_new;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;

public class HelloApplication extends Application {

    private double x = 0.0;
    private double y = 0.0;

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("hello-view.fxml"));
        Scene scene = new Scene(root);
        //stage.setTitle("Bus Booking Ticket Management System...!");

        root.setOnMousePressed((event) -> {
            this.x = event.getSceneX();
            this.y = event.getSceneY();
        });

        root.setOnMouseDragged((event) -> {
            stage.setX(event.getScreenX() - this.x);
            stage.setY(event.getScreenY() - this.y);
            stage.setOpacity(0.8);
        });

        root.setOnMouseReleased((event) -> {
            stage.setOpacity(1.0);
        });

        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}