package com.example.testing_bus_booking_ticket_management_system_new;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.File;
import java.net.URL;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;


public class dashBoardController implements Initializable {

    private MediaView mediaView;
    private File file;
    private MediaPlayer mediaPlayer;
    private Media media;

    @FXML
    private ImageView logo_imageView1;
    @FXML
    private TextField cityInput;

    @FXML
    private Button fetchButton;

    @FXML
    private Label weatherLabel;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button availableB_addBtn;

    @FXML
    private Button availableB_btn;

    @FXML
    private TextField availableB_busID;

    @FXML
    private TableColumn<busData, String> availableB_coi_busID;

    @FXML
    private TableColumn<busData, Date> availableB_coi_date;

    @FXML
    private TableColumn<busData, String> availableB_coi_location;

    @FXML
    private TableColumn<busData, String> availableB_coi_price;

    @FXML
    private TableColumn<busData, String> availableB_coi_status;

    @FXML
    private DatePicker availableB_date;

    @FXML
    private Button availableB_deleteBtn;

    @FXML
    private AnchorPane availableB_form;

    @FXML
    private TextField availableB_location;

    @FXML
    private TextField availableB_price;

    @FXML
    private Button availableB_resetBtn;

    @FXML
    private TextField availableB_search;

    @FXML
    private ComboBox<?> availableB_status;

    @FXML
    private TableView<busData> availableB_tableview;

    @FXML
    private Button availableB_updateBtn;

    @FXML
    private Button bookingTicket_btn;

    @FXML
    private ComboBox<?> bookingTicket_busID;

    @FXML
    private DatePicker bookingTicket_date;

    @FXML
    private TextField bookingTicket_firstName;

    @FXML
    private AnchorPane bookingTicket_form;

    @FXML
    private ComboBox<?> bookingTicket_gender;

    @FXML
    private TextField bookingTicket_lastName;

    @FXML
    private ComboBox<?> bookingTicket_location;

    @FXML
    private TextField bookingTicket_phoneNum;

    @FXML
    private Button bookingTicket_resetBtn;

    @FXML
    private Label bookingTicket_sci_busID;

    @FXML
    private Label bookingTicket_sci_date;

    @FXML
    private Label bookingTicket_sci_firstName;

    @FXML
    private Label bookingTicket_sci_gender;

    @FXML
    private Label bookingTicket_sci_lastName;

    @FXML
    private Label bookingTicket_sci_location;

    @FXML
    private Button bookingTicket_sci_pay;

    @FXML
    private Label bookingTicket_sci_phoneNum;

    @FXML
    private Button bookingTicket_sci_receipt;

    @FXML
    private Label bookingTicket_sci_ticketNum;

    @FXML
    private Label bookingTicket_sci_total;

    @FXML
    private Label bookingTicket_sci_type;

    @FXML
    private Button bookingTicket_selectBtn;

    @FXML
    private ComboBox<?> bookingTicket_ticketNum;

    @FXML
    private ComboBox<?> bookingTicket_type;

    @FXML
    private Button close;

    @FXML
    private AnchorPane customer_form;

    @FXML
    private Button customers_btn;

    @FXML
    private TableColumn<customerData, String> customers_busID;

    @FXML
    private TableColumn<customerData, String> customers_customerNum;

    @FXML
    private TableColumn<customerData, String> customers_date;

    @FXML
    private TableColumn<customerData, String> customers_firstName;

    @FXML
    private TableColumn<customerData, String> customers_gender;

    @FXML
    private TableColumn<customerData, String> customers_lastName;

    @FXML
    private TableColumn<customerData, String> customers_location;

    @FXML
    private TableColumn<customerData, String> customers_phoneNum;

    @FXML
    private TextField customers_search;

    @FXML
    private TableView<customerData> customers_tableview;

    @FXML
    private TableColumn<customerData, String> customers_ticketNum;

    @FXML
    private TableColumn<customerData, String> customers_type;

    @FXML
    private Label dashboard_availableB;

    @FXML
    private Button dashboard_btn;

    @FXML
    private AreaChart<?, ?> dashboard_chart;

    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private Label dashboard_incomeToday;

    @FXML
    private Label dashboard_totalincome;

    @FXML
    private Button logout;

    @FXML
    private Button minimize;

    @FXML
    private Label username;


    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;

    public void availableBusAdd() {
        String addData = "Insert into bus (bus_id,location,status,price,date) values (?,?,?,?,?)";

        connect = database.connectionDb();

        try {

            Alert alert;

            if (availableB_busID.getText().isEmpty() ||
                    availableB_location.getText().isEmpty() ||
                    availableB_status.getSelectionModel().getSelectedItem() == null ||
                    availableB_price.getText().isEmpty() ||
                    availableB_date.getValue() == null) {

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank field");
                alert.showAndWait();
            } else {

                String check = "Select bus_id from bus where bus_id = '"
                        + availableB_busID.getText() + "'";

                statement = connect.createStatement();
                result = statement.executeQuery(check);

                if (result.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Bus ID : " + availableB_busID.getText() + " is already exists ...!!!");
                    alert.showAndWait();

                } else {


                    prepare = connect.prepareStatement(addData);
                    prepare.setString(1, availableB_busID.getText());
                    prepare.setString(2, availableB_location.getText());
                    prepare.setString(3, (String) availableB_status.getSelectionModel().getSelectedItem());
                    prepare.setString(4, availableB_price.getText());
                    prepare.setString(5, String.valueOf(availableB_date.getValue()));

                    prepare.executeUpdate();
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added ...!!!");
                    alert.showAndWait();

                    availableBShowBusData();
                    availableBusReset();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void availableBusUpdate() {
        String updateData = "Update bus set location = '" +
                availableB_location.getText() + "', status = '"
                + availableB_status.getSelectionModel().getSelectedItem()
                + "', price = '" + availableB_price.getText()
                + "', date = '" + availableB_date.getValue()
                + "' where bus_id = '" + availableB_busID.getText() + "'";

        connect = database.connectionDb();
        Alert alert;

        try {
            if (availableB_busID.getText().isEmpty() ||
                    availableB_location.getText().isEmpty() ||
                    availableB_status.getSelectionModel().getSelectedItem() == null ||
                    availableB_price.getText().isEmpty() ||
                    availableB_date.getValue() == null) {

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please select the item first");
                alert.showAndWait();


            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to update the bus ID: " + availableB_busID.getText() + "?");

                Optional<ButtonType> option = alert.showAndWait();

                if (option.get() == ButtonType.OK) {
                    prepare = connect.prepareStatement(updateData);
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated ...!!!");
                    alert.showAndWait();

                    availableBShowBusData();
                    availableBusReset();
                } else return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void availableBusDelete() {
        String deleteData = "Delete from bus where bus_id = '"
                + availableB_busID.getText() + "'";

        connect = database.connectionDb();

        try {
            Alert alert;

            if (availableB_busID.getText().isEmpty() ||
                    availableB_location.getText().isEmpty() ||
                    availableB_status.getSelectionModel().getSelectedItem() == null ||
                    availableB_price.getText().isEmpty() ||
                    availableB_date.getValue() == null) {

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please select the item first");
                alert.showAndWait();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to delete the bus ID: " + availableB_busID.getText() + "?");

                Optional<ButtonType> option = alert.showAndWait();
                if (option.get() == ButtonType.OK) {
                    statement = connect.createStatement();
                    statement.executeUpdate(deleteData);

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted ...!!!");
                    alert.showAndWait();

                    availableBShowBusData();
                    availableBusReset();
                } else return;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void availableBusReset() {
        availableB_busID.setText("");
        availableB_location.setText("");
        availableB_status.getSelectionModel().clearSelection();
        availableB_price.setText("");
        availableB_date.setValue(null);

    }

    private String[] statusList = {"Available", "Not Available"};

    public void comboBoxStatus() {
        List<String> listS = new ArrayList<>();

        for (String data : statusList) {
            listS.add(data);
        }

        ObservableList listStatus = FXCollections.observableArrayList(listS);
        availableB_status.setItems(listStatus);
    }

    public ObservableList<busData> availableBusBusData(ObservableList<busData> busListData) {
        if (busListData != null && !busListData.isEmpty()) {
            return busListData;
        }
        return availableBusBusData() ;
    }

    public ObservableList<busData> availableBusBusData() {

        ObservableList<busData> busListData = FXCollections.observableArrayList();

        String sql = "select * from bus";
        connect = database.connectionDb();
        try {

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            busData busD;

            while (result.next()) {
                busD = new busData(result.getInt("bus_id"),
                        result.getString("location"),
                        result.getString("status"),
                        result.getDouble("price"),
                        result.getDate("date"));

                busListData.add(busD);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return busListData;
    }

    private ObservableList<busData> availableBBusListData = FXCollections.observableArrayList();

    public void availableBShowBusData(ObservableList<busData> busListData) {

        //to save the currently selected item index....
        int selectedIndex = availableB_tableview.getSelectionModel().getSelectedIndex();

        availableBBusListData = availableBusBusData(busListData);

        availableB_coi_busID.setCellValueFactory(new PropertyValueFactory<>("busID"));
        availableB_coi_location.setCellValueFactory(new PropertyValueFactory<>("location"));
        availableB_coi_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        availableB_coi_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        availableB_coi_date.setCellValueFactory(new PropertyValueFactory<>("date"));

        availableB_tableview.setItems(availableBBusListData);

        if (selectedIndex >= 0 && selectedIndex < availableBBusListData.size()) {
            availableB_tableview.getSelectionModel().select(selectedIndex);
        }

    }

    public void availableBShowBusData() {

        //to save the currently selected item index....
        int selectedIndex = availableB_tableview.getSelectionModel().getSelectedIndex();

        availableBBusListData = availableBusBusData();

        availableB_coi_busID.setCellValueFactory(new PropertyValueFactory<>("busID"));
        availableB_coi_location.setCellValueFactory(new PropertyValueFactory<>("location"));
        availableB_coi_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        availableB_coi_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        availableB_coi_date.setCellValueFactory(new PropertyValueFactory<>("date"));

        availableB_tableview.setItems(availableBBusListData);

        if (selectedIndex >= 0 && selectedIndex < availableBBusListData.size()) {
            availableB_tableview.getSelectionModel().select(selectedIndex);
        }

    }

    public void availableBSelectBusData() {
        busData busD = availableB_tableview.getSelectionModel().getSelectedItem();
        //int num = availableB_tableview.getSelectionModel().getSelectedIndex();

        /*
        if ((num - 1) < -1) {
            return;
        }
        */

        if (busD == null) {
            return;
        }

        availableB_busID.setText(String.valueOf(busD.getBusID()));
        availableB_location.setText(busD.getLocation());

        //TO USE THIS METHOD WE NEED TO MAKE THE RETURN OF GETSTATUS MEMTHOD TO INT
        //availableB_status.getSelectionModel().select(busD.getStatus());

        availableB_price.setText(String.valueOf(busD.getPrice()));

        //String.valueOf(busD.getData())
        availableB_date.setValue(LocalDate.parse(String.valueOf(busD.getDate())));
    }

    // Assuming `tableView` is the TableView and `originalList` will contain all data.
    ObservableList<busData> originalList = FXCollections.observableArrayList();
    ObservableList<busData> filteredList = FXCollections.observableArrayList();

    // Calling this method whenever the search field is updated.
    public void updateTable(String searchQuery) {
        originalList = availableBBusListData;
        if (searchQuery.isEmpty()) {
            // If search query is empty, show all rows.
            availableB_tableview.setItems(originalList);
        } else {
            filteredList.clear();
            for (busData bus : originalList) {
                if (bus.getLocation().toLowerCase().contains(searchQuery.toLowerCase())) {
                    filteredList.add(bus);
                }
            }
            availableB_tableview.setItems(filteredList); // Update the table with filtered data.
        }
    }


    public void availableSearch() {

        FilteredList<busData> filter = new FilteredList<>(availableBBusListData, e -> true);

        availableB_search.textProperty().addListener((Observable, oldValue, newValue) -> {

           // updateTable(newValue);
            System.out.println("search field updated : " + newValue);

            filter.setPredicate(predicateBusData -> {
                //System.out.println("Checking bus : " + predicateBusData.getBusID());

                if (newValue == null || newValue.isEmpty()) {
                    System.out.println("search term is empty, showing all rows");
                    return true;
                }

                String searchKey = newValue.toLowerCase();

                System.out.println("Checking bus: " + predicateBusData.getBusID() +
                        ", Location: " + predicateBusData.getLocation() +
                        ", Status: " + predicateBusData.getStatus() +
                        ", Price: " + predicateBusData.getPrice() +
                        ", Date: " + predicateBusData.getDate());


                boolean match = String.valueOf(predicateBusData.getBusID()).toLowerCase().contains(searchKey) ||
                        (predicateBusData.getLocation() != null && predicateBusData.getLocation().toLowerCase().contains(searchKey)) ||
                        (predicateBusData.getStatus() != null && predicateBusData.getStatus().toLowerCase().contains(searchKey)) ||
                        String.valueOf(predicateBusData.getPrice()).toLowerCase().contains(searchKey) ||
                        (predicateBusData.getDate() != null && predicateBusData.getDate().toString().toLowerCase().contains(searchKey));

                System.out.println("Evaluating BusID: " + predicateBusData.getBusID() +
                        ", Location: " + predicateBusData.getLocation() +
                        ", Matches: " + match);

                System.out.println("match: " + match);

                return match;

            });

        });


        SortedList<busData> sortedList = new SortedList<>(filter);

        sortedList.comparatorProperty().bind(availableB_tableview.comparatorProperty());
        availableB_tableview.setItems(sortedList);

        Platform.runLater(() -> {
            availableB_tableview.setItems(sortedList);
        });

        availableB_tableview.refresh();
        availableBShowBusData(sortedList) ;
        //availableB_tableview.setItems(filter);


    }

    private double priceData = 0;
    private double totalP = 0;

    public void bookingTicketSelect() {
        String firstName = bookingTicket_firstName.getText();
        String lastName = bookingTicket_lastName.getText();
        String gender = (String) bookingTicket_gender.getSelectionModel().getSelectedItem();
        String phoneNumber = bookingTicket_phoneNum.getText();
        String date = String.valueOf(bookingTicket_date.getValue());

        String busId = (String) bookingTicket_busID.getSelectionModel().getSelectedItem();
        String location = (String) bookingTicket_location.getSelectionModel().getSelectedItem();
        String type = (String) bookingTicket_type.getSelectionModel().getSelectedItem();
        String ticketNum = (String) bookingTicket_ticketNum.getSelectionModel().getSelectedItem();

        Alert alert;

        if (firstName == null || lastName == null ||
                gender == null || phoneNumber == null ||
                date.isEmpty() || busId == null ||
                location == null || type == null ||
                ticketNum == null) {

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the fields");
            alert.showAndWait();

        } else {

            String totalPrice = "select price from bus where location = '" + location + "'";

            try {
                connect = database.connectionDb();

                prepare = connect.prepareStatement(totalPrice);
                result = prepare.executeQuery();

                if (result.next()) {
                    priceData = result.getDouble("price");
                }

                if (type == "First Class") {
                    totalP = (priceData + 1000);
                } else if (type == "Economy Class") {
                    totalP = (priceData);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            bookingTicket_sci_total.setText("$ " + String.valueOf(totalP));

            bookingTicket_sci_firstName.setText(firstName);
            bookingTicket_sci_lastName.setText(lastName);
            bookingTicket_sci_gender.setText(gender);
            bookingTicket_sci_phoneNum.setText(phoneNumber);
            bookingTicket_sci_date.setText(date);

            bookingTicket_sci_busID.setText(busId);
            bookingTicket_sci_location.setText(location);
            bookingTicket_sci_type.setText(type);
            bookingTicket_sci_ticketNum.setText(ticketNum);


            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("Successfully Selected..!!!");
            alert.showAndWait();

            bookingTicketReset();

        }
    }

    public void bookingTicketReset() {
        bookingTicket_firstName.setText("");
        bookingTicket_lastName.setText("");
        bookingTicket_gender.getSelectionModel().clearSelection();
        bookingTicket_phoneNum.setText("");
        bookingTicket_date.setValue(null);
    }

    private String[] genderL = {"Male", "Female", "Others"};

    public void genderList() {
        List<String> listG = new ArrayList<>();

        for (String data : genderL) {
            listG.add(data);
        }

        ObservableList gList = FXCollections.observableArrayList(listG);
        bookingTicket_gender.setItems(gList);
    }

    private int countRow;

    public void bookingTicketpay() {
        String firstName = bookingTicket_sci_firstName.getText();
        String lastName = bookingTicket_sci_lastName.getText();
        String gender = (String) bookingTicket_sci_gender.getText();
        String phoneNumber = bookingTicket_sci_phoneNum.getText();
        String date = bookingTicket_sci_date.getText();

        String busId = bookingTicket_sci_busID.getText();
        String location = bookingTicket_sci_location.getText();
        String type = bookingTicket_sci_type.getText();
        String seatNum = bookingTicket_sci_ticketNum.getText();

        String payData = "Insert into customer (customer_id,firstName,lastName,gender,phoneNumber,bus_id,location,type,seatNum,total,date) values (?,?,?,?,?,?,?,?,?,?,?)";

        connect = database.connectionDb();

        try {
            Alert alert;

            String countNum = "select count(id) from customer";
            statement = connect.createStatement();
            result = statement.executeQuery(countNum);

            while (result.next()) {
                countRow = result.getInt("count(id)");
            }

            if (bookingTicket_sci_firstName.getText().isEmpty()
                    || bookingTicket_sci_lastName.getText().isEmpty()
                    || bookingTicket_sci_gender.getText().isEmpty()
                    || bookingTicket_sci_phoneNum.getText().isEmpty()
                    || bookingTicket_sci_location.getText().isEmpty()
                    || bookingTicket_sci_type.getText().isEmpty()
                    || bookingTicket_sci_ticketNum.getText().isEmpty()
                    || bookingTicket_sci_busID.getText().isEmpty()
                    || bookingTicket_sci_ticketNum.getText().isEmpty()
                    || totalP == 0) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please select the information first ");
                alert.showAndWait();

            } else {

                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you Sure ? ");
                alert.showAndWait();

                prepare = connect.prepareStatement(payData);
                prepare.setString(1, String.valueOf(countRow + 1));
                prepare.setString(2, firstName);
                prepare.setString(3, lastName);
                prepare.setString(4, gender);
                prepare.setString(5, phoneNumber);
                prepare.setString(6, busId);
                prepare.setString(7, location);
                prepare.setString(8, type);
                prepare.setString(9, seatNum);
                prepare.setString(10, String.valueOf(totalP));
                prepare.setString(11, date);

                prepare.executeUpdate();

                String receiptData = "insert into customer_receipt (customer_id,total,date) VALUES(?,?,?)";

                //getData.number = (countRow + 1);

                prepare = connect.prepareStatement(receiptData);
                prepare.setString(1, String.valueOf(countRow + 1));
                prepare.setString(2, String.valueOf(totalP));
                prepare.setString(3, date);

                prepare.executeUpdate();

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully ...!!!");
                alert.showAndWait();

                bookingTicket_sci_firstName.setText("");
                bookingTicket_sci_lastName.setText("");
                bookingTicket_sci_gender.setText("");
                bookingTicket_sci_phoneNum.setText("");
                bookingTicket_sci_location.setText("");
                bookingTicket_sci_type.setText("");
                bookingTicket_sci_ticketNum.setText("");
                bookingTicket_sci_busID.setText("");
                bookingTicket_sci_date.setText("");
                bookingTicket_sci_total.setText("$0.00");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private double x = 0;
    private double y = 0;

    public void busIdList() {
        String busD = "select * from bus where status = 'Available'";

        connect = database.connectionDb();
        try {
            prepare = connect.prepareStatement(busD);
            result = prepare.executeQuery();

            ObservableList listB = FXCollections.observableArrayList();

            while (result.next()) {
                listB.add(result.getString("bus_id"));
            }
            bookingTicket_busID.setItems(listB);

            ticketNumList();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void LocationList() {
        String LocationL = "select * from bus where status = 'Available'";

        connect = database.connectionDb();
        try {
            prepare = connect.prepareStatement(LocationL);
            result = prepare.executeQuery();

            ObservableList listL = FXCollections.observableArrayList();

            while (result.next()) {
                listL.add(result.getString("location"));

            }
            bookingTicket_location.setItems(listL);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String[] listT = {"First Class", "Economy Class"};

    public void typeList() {
        List<String> tList = new ArrayList<>();

        for (String data : listT) {
            tList.add(data);
        }

        ObservableList listType = FXCollections.observableList(tList);

        bookingTicket_type.setItems(listType);
    }

    public void ticketNumList() {
        List<String> listTicket = new ArrayList<>();

        // 40 is the capacity for each bus
        for (int q = 1; q <= 40; q++) {
            listTicket.add(String.valueOf(q));
        }

        String removeSeat = "select seatNum from customer where bus_id = '"
                + bookingTicket_busID.getSelectionModel().getSelectedItem() + "'";

        connect = database.connectionDb();

        try {

            prepare = connect.prepareStatement(removeSeat);
            result = prepare.executeQuery();

            while (result.next()) {
                listTicket.remove(result.getString("seatNum"));
            }

            ObservableList listTi = FXCollections.observableArrayList(listTicket);
            bookingTicket_ticketNum.setItems(listTi);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logout() {

        try {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout ?");

            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {

                logout.getScene().getWindow().hide();

                Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);

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

                logo_imageView1.setVisible(true);

            } else return;


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<customerData> customersDataList(ObservableList<customerData> customerList) {
        if (customerList != null && !customerList.isEmpty()) {
            return customerList;
        }
        return customersDataList() ;
    }

    public ObservableList<customerData> customersDataList() {

        ObservableList<customerData> customerList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM customer";
        connect = database.connectionDb();

        try {

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            customerData custD;

            while (result.next()) {

                custD = new customerData(result.getInt("customer_id")
                        , result.getString("firstName")
                        , result.getString("lastName")
                        , result.getString("gender")
                        , result.getString("phoneNumber")
                        , result.getInt("bus_id")
                        , result.getString("location")
                        , result.getString("type")
                        , result.getInt("seatNum")
                        , result.getDouble("total")
                        , result.getDate("date"));

                customerList.add(custD);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return customerList;
    }

    private ObservableList<customerData> customersDataL = FXCollections.observableArrayList();

    public void customersShowDataList(ObservableList<customerData> customerList) {

        customersDataL = customersDataList(customerList);

        customers_customerNum.setCellValueFactory(new PropertyValueFactory<>("customerNum"));
        customers_ticketNum.setCellValueFactory(new PropertyValueFactory<>("seatNum"));
        customers_firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        customers_lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        customers_phoneNum.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));
        customers_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        customers_busID.setCellValueFactory(new PropertyValueFactory<>("busId"));
        customers_location.setCellValueFactory(new PropertyValueFactory<>("location"));
        customers_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        customers_date.setCellValueFactory(new PropertyValueFactory<>("date"));

        customers_tableview.setItems(customersDataL);

    }

    public void customersShowDataList() {

        customersDataL = customersDataList();

        customers_customerNum.setCellValueFactory(new PropertyValueFactory<>("customerNum"));
        customers_ticketNum.setCellValueFactory(new PropertyValueFactory<>("seatNum"));
        customers_firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        customers_lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        customers_phoneNum.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));
        customers_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        customers_busID.setCellValueFactory(new PropertyValueFactory<>("busId"));
        customers_location.setCellValueFactory(new PropertyValueFactory<>("location"));
        customers_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        customers_date.setCellValueFactory(new PropertyValueFactory<>("date"));

        customers_tableview.setItems(customersDataL);

    }

    public void customersSearch() {

        FilteredList<customerData> filter = new FilteredList<>(customersDataL, e -> true);

        customers_search.textProperty().addListener((Observable, oldValue, newValue) -> {

            filter.setPredicate(predicateCustomerData -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String searchKey = newValue.toLowerCase();

                if (predicateCustomerData.getCustomerNum().toString().contains(searchKey)) {
                    return true;
                } else if (predicateCustomerData.getSeatNum().toString().contains(searchKey)) {
                    return true;
                } else if (predicateCustomerData.getFirstName().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateCustomerData.getLastName().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateCustomerData.getGender().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateCustomerData.getPhoneNum().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateCustomerData.getBusId().toString().contains(searchKey)) {
                    return true;
                } else if (predicateCustomerData.getLocation().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateCustomerData.getTotal().toString().contains(searchKey)) {
                    return true;
                } else if (predicateCustomerData.getType().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateCustomerData.getDate().toString().contains(searchKey)) {
                    return true;
                } else return false;

            });
        });

        SortedList<customerData> sortList = new SortedList<>(filter);

        sortList.comparatorProperty().bind(customers_tableview.comparatorProperty());
        customers_tableview.setItems(sortList);

        customers_tableview.refresh();
        customersShowDataList(sortList);
    }

    public void defaultBtn() {
        dashboard_btn.setStyle("-fx-background-color: linear-gradient(to bottom, #242173, #9f9ce2)");
        availableB_btn.setStyle("-fx-background-color: transparent");
        bookingTicket_btn.setStyle("-fx-background-color: transparent");
        customers_btn.setStyle("-fx-background-color: transparent");

    }

    public void switchForm(ActionEvent event) {
        if (event.getSource() == dashboard_btn) {
            dashboard_form.setVisible(true);
            availableB_form.setVisible(false);
            bookingTicket_form.setVisible(false);
            customer_form.setVisible(false);

            dashboard_btn.setStyle("-fx-background-color: linear-gradient(to bottom, #242173, #9f9ce2)");
            availableB_btn.setStyle("-fx-background-color: transparent");
            bookingTicket_btn.setStyle("-fx-background-color: transparent");
            customers_btn.setStyle("-fx-background-color: transparent");

            dashboardDisplayAB();
            dashboardDisplayIT();
            dashboardDisplayTI();
            dashboardChart();

        } else if (event.getSource() == availableB_btn) {
            dashboard_form.setVisible(false);
            availableB_form.setVisible(true);
            bookingTicket_form.setVisible(false);
            customer_form.setVisible(false);

            availableB_btn.setStyle("-fx-background-color: linear-gradient(to bottom, #242173, #9f9ce2)");
            dashboard_btn.setStyle("-fx-background-color: transparent");
            bookingTicket_btn.setStyle("-fx-background-color: transparent");
            customers_btn.setStyle("-fx-background-color: transparent");

            availableBShowBusData();
            availableSearch();

        } else if (event.getSource() == bookingTicket_btn) {
            dashboard_form.setVisible(false);
            availableB_form.setVisible(false);
            bookingTicket_form.setVisible(true);
            customer_form.setVisible(false);

            bookingTicket_btn.setStyle("-fx-background-color: linear-gradient(to bottom, #242173, #9f9ce2)");
            availableB_btn.setStyle("-fx-background-color: transparent");
            dashboard_btn.setStyle("-fx-background-color: transparent");
            customers_btn.setStyle("-fx-background-color: transparent");

            busIdList();
            LocationList();
            typeList();
            ticketNumList();
            genderList();

        } else if (event.getSource() == customers_btn) {
            dashboard_form.setVisible(false);
            availableB_form.setVisible(false);
            bookingTicket_form.setVisible(false);
            customer_form.setVisible(true);

            customers_btn.setStyle("-fx-background-color: linear-gradient(to bottom, #242173, #9f9ce2)");
            availableB_btn.setStyle("-fx-background-color: transparent");
            bookingTicket_btn.setStyle("-fx-background-color: transparent");
            dashboard_btn.setStyle("-fx-background-color: transparent");

            customersShowDataList();
            customersSearch();
        }
    }

    private int countAB = 0;

    public void dashboardDisplayAB() {

        String sql = "SELECT COUNT(id) FROM bus WHERE status = 'Available'";

        connect = database.connectionDb();

        try {

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                countAB = result.getInt("COUNT(id)");
            }

            dashboard_availableB.setText(String.valueOf(countAB));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private double incomeToday = 0;

    public void dashboardDisplayIT() {

        java.util.Date date = new java.util.Date();

        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        String sql = "SELECT SUM(total) FROM customer WHERE date ='" + sqlDate + "'";

        connect = database.connectionDb();

        try {

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                incomeToday = result.getDouble("SUM(total)");
            }

            dashboard_incomeToday.setText("$" + String.valueOf(incomeToday));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private double totalIncome;

    public void dashboardDisplayTI() {

        String sql = "SELECT SUM(total) FROM customer";

        connect = database.connectionDb();

        try {

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                totalIncome = result.getDouble("SUM(total)");
            }

            dashboard_totalincome.setText("$" + String.valueOf(totalIncome));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void displayUserName() {
        username.setText(getData.username);
    }

    public void dashboardChart() {

        dashboard_chart.getData().clear();

        String sql = "SELECT date,SUM(total) FROM customer WHERE date IS NOT NULL GROUP BY date ORDER BY TIMESTAMP(date) ASC LIMIT 9";

        connect = database.connectionDb();

        XYChart.Series chart = new XYChart.Series();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                chart.getData().add(new XYChart.Data(result.getString(1), result.getInt(2)));
            }

            dashboard_chart.getData().add(chart);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void close() {
        System.exit(0);
    }

    public void minimize() {
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);

    }

    private String fetchWeather(String city) {
        String url = "https://weather-api138.p.rapidapi.com/weather?city_name=" + city;

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("x-rapidapi-key", "8e867736b8mshbefeeb84793a7a3p1cd6cajsn1b6b66ee196a")
                .addHeader("x-rapidapi-host", "weather-api138.p.rapidapi.com")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String jsonResponse = response.body().string();
                System.out.println("API Response: " + jsonResponse);  // Debugging log

                JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();

                String cityName = jsonObject.get("name").getAsString(); // Directly from top level
                JsonObject main = jsonObject.getAsJsonObject("main");
                String temperature = main.get("temp").getAsString();
                JsonObject weather = jsonObject.getAsJsonArray("weather").get(0).getAsJsonObject();
                String description = weather.get("description").getAsString();

                return "City : " + cityName + "\nTemperature : " + temperature + "°K\nWeather : " + description;
            } else {
                return "Failed to fetch weather data: " + response.code();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error fetching weather data.";
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //availableB_tableview.refresh();
        defaultBtn();       //default 4 button
        displayUserName();      //Admin name will change when different people log in....

        dashboardDisplayAB();   //showing the num of available buses, getting the data by reading from the 'bus; table from the database 'busdata'
        dashboardDisplayIT();   //showing income today
        dashboardDisplayTI();   //Total income
        dashboardChart();       //populating a chart using the data from the databse

        //

        //To show status of the bus available or not available
        comboBoxStatus();

        //to show dataa on the table
        availableBShowBusData();
        //availableSearch();

        availableB_tableview.setOnMouseClicked(event -> availableBSelectBusData());

        busIdList();
        LocationList();
        typeList();
        ticketNumList();
        genderList();

        customersShowDataList();

        fetchButton.setOnAction(event -> {
            String city = cityInput.getText().trim();
            if (!city.isEmpty()) {
                weatherLabel.setText("Fetching...");
                new Thread(() -> {
                    String weatherData = fetchWeather(city);
                    Platform.runLater(() -> weatherLabel.setText(weatherData));
                }).start();
            } else {
                weatherLabel.setText("Please enter a city name.");
            }
        });
    }

}