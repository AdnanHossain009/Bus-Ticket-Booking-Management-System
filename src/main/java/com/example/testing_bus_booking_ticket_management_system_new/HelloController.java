package com.example.testing_bus_booking_ticket_management_system_new;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import okhttp3.OkHttpClient;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class HelloController {

    public ImageView logo_imageView1;
    @FXML
    private Button close;

    @FXML
    private MediaView mediaView;
    private File file;
    private MediaPlayer mediaPlayer;
    private Media media;

    @FXML
    private TextField showPassword;

    @FXML
    private CheckBox login_selectShowPassword;

    @FXML
    private Button goto_login;

    @FXML
    private Button goto_signup;

    @FXML
    private Button login;

    @FXML
    private AnchorPane login_form;

    @FXML
    private AnchorPane main_form;

    @FXML
    private PasswordField password;

    @FXML
    private Button signup_btn;

    @FXML
    private TextField signup_confirm_password;

    @FXML
    private TextField signup_email;

    @FXML
    private TextField signup_first_name;

    @FXML
    private AnchorPane signup_form;

    @FXML
    private TextField signup_last_name;

    @FXML
    private TextField signup_password;

    @FXML
    private TextField signup_usename;

    @FXML
    private TextField username;

    private final OkHttpClient client = new OkHttpClient();

    public HelloController() {
    }

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    private double x = 0;
    private double y = 0;

    public void goToSignup() {
        login_form.setVisible(false); // Hide login form
        signup_form.setVisible(true); // Show signup form
    }

    public void goToLogin() {
        signup_form.setVisible(false); // Hide signup form
        login_form.setVisible(true); // Show login form
    }

    public void showPassword() {
        if (login_selectShowPassword.isSelected()) {
            showPassword.setText(password.getText());
            showPassword.setVisible(true);
            password.setVisible(false);
        }
        else {
            password.setText(showPassword.getText());
            showPassword.setVisible(false);
            password.setVisible(true);
        }
    }

    public void signUp() {
        String sql = "INSERT INTO admin (firstname, lastname, username, email, password) VALUES (?, ?, ?, ?, ?)";

        connect = database.connectionDb();

        Alert alert;

        try {
            // Validate fields
            if (signup_usename.getText().isEmpty() || signup_password.getText().isEmpty() ||
                    signup_confirm_password.getText().isEmpty() || signup_email.getText().isEmpty() ||
                    signup_first_name.getText().isEmpty() || signup_last_name.getText().isEmpty()) {

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
                return;
            }

            // Check if passwords match
            if (!signup_password.getText().equals(signup_confirm_password.getText())) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Passwords do not match");
                alert.showAndWait();
                return;
            }

            // Check if username already exists
            String checkUsername = "SELECT username FROM admin WHERE username = ?";
            prepare = connect.prepareStatement(checkUsername);
            prepare.setString(1, signup_usename.getText());
            result = prepare.executeQuery();

            if (result.next()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Username already exists. Please choose a different username.");
                alert.showAndWait();
                return;
            }

            // Insert data into the database
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, signup_first_name.getText());
            prepare.setString(2, signup_last_name.getText());
            prepare.setString(3, signup_usename.getText());
            prepare.setString(4, signup_email.getText());
            prepare.setString(5, signup_password.getText());

            int rows = prepare.executeUpdate();

            if (rows > 0) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Account created successfully!");
                alert.showAndWait();

                // Go back to login form
                goToLogin();
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Failed to create account. Please try again.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void login() {
        String sql = "Select * from admin where username = ? and password = ?";

        connect = database.connectionDb();

        Alert alert;

        try {

            if (connect == null) { // Check if the connection is null
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Database connection failed. Please try again later.");
                alert.showAndWait();
                return;
            }

            if (username.getText().isEmpty() || password.getText().isEmpty()) {

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();

            } else {
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, username.getText());
                prepare.setString(2, password.getText());

                result = prepare.executeQuery();

                if (result.next()) {

                    getData.username = username.getText();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Login");
                    alert.showAndWait();

                    login.getScene().getWindow().hide();

                    Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));

                    Stage stage = new Stage();
                    Scene scene = new Scene(root);

                    stage.setOnCloseRequest(event -> {
                        event.consume(); // Preventing the window from closing
                        System.out.println("Close button is disabled.");
                    });

                    root.setOnMousePressed((MouseEvent event) -> {
                        x = event.getSceneX();
                        y = event.getSceneX();
                    });

                    root.setOnMouseDragged((MouseEvent event) -> {
                        stage.setX(event.getSceneX() - x);
                        stage.setY(event.getSceneX() - y);
                    });

                    //stage.initStyle(StageStyle.TRANSPARENT);
                    //stage.initStyle(StageStyle.UNDECORATED);

                    //Image icon = new Image("rimuru_icon.jpg");
                    //stage.getIcons().add(icon);

                    stage.setResizable(false);

                    stage.setTitle("Bus Ticket Booking Management System");


                    stage.setScene(scene);
                    stage.show();

                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong username and password");
                    alert.showAndWait();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (result != null) result.close();
                if (prepare != null) prepare.close();
                if (connect != null) connect.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public void close_new() {
        System.exit(0);
    }

    @FXML
    public void initialize() {
        login_form.setVisible(true);
        signup_form.setVisible(false);

        file = new File("E:\\Java FX testing and Desktop Application\\Testing Bus Booking Ticket Management System new\\src\\main\\resources\\Animations_Bus_Management.mp4");
        media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);

        mediaPlayer.setOnError(() -> {
            System.out.println("MediaPlayer Error: " + mediaPlayer.getError().getMessage());
        });

        media.setOnError(() -> {
            System.out.println("Media Error: " + media.getError().getMessage());
        });

        //mediaView.setFitWidth(800);
        //mediaView.setFitHeight(600);

        mediaPlayer.setAutoPlay(true);

    }

}