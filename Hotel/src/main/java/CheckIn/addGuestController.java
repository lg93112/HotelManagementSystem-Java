package CheckIn;

import java.util.*;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import database.DatabaseHandler;
import javafx.fxml.Initializable;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.Date;
import java.text.SimpleDateFormat;

public class addGuestController implements Initializable{

    DatabaseHandler databaseHandler;
    private Set<String> rooms;
    private String[] roomnumbers;

    public void initialize(URL url, ResourceBundle rb){
        databaseHandler = new DatabaseHandler();
        rooms = new HashSet<String>();
        roomnumbers = new String[] {"101", "102", "103", "104", "105", "106", "107", "108", "109", "110", "201", "202", "203", "204", "205", "206", "207", "208", "209", "210", "301", "302", "303", "304", "305", "306", "307", "308", "309", "310"};
        Collections.addAll(rooms, roomnumbers);
    }
    @FXML
    private AnchorPane rootPane;

    @FXML
    private JFXButton save_button;

    @FXML
    private JFXButton cancel_button;

    @FXML
    private JFXTextField requirements;

    @FXML
    private JFXTextField check_out_date;

    @FXML
    private JFXTextField guest_name;

    @FXML
    private Text priceText;

    @FXML
    private JFXButton Total_price_button;

    @FXML
    private JFXTextField room_number;

    @FXML
    private JFXTextField check_in_date;

    @FXML
    void Show_total_price(ActionEvent event) {
        int totalPrice = calculateTotalPrice();
        System.out.println(totalPrice);
        if (totalPrice == 0) {
            priceText.setText("$?");
            return;
        }
        else if(totalPrice == -1){
            priceText.setText("Wrong date");
            return;
        }
        else if(totalPrice == -2){
            priceText.setText("Wrong Room Number");
            return;
        }
        priceText.setText("$" + totalPrice);

    }

    int calculateDateInterval(String d1, String d2) throws Exception {
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = formatter1.parse(d1);
        Date date2 = formatter1.parse(d2);
        long difference =  (date2.getTime()-date1.getTime())/86400000;
        if (difference <= 0) {
            throw new Exception();
        }
        return (int) Math.abs(difference);

    }

    int calculateTotalPrice() {
        String roomId = room_number.getText();
        if(!roomId.isEmpty() && !rooms.contains(roomId)){return -2;}
        String checkInDate = check_in_date.getText();
        String checkOutDate = check_out_date.getText();
        int oneDayPrice = 0;
        int dayInterval = 0;

        if (checkInDate.isEmpty() || checkOutDate.isEmpty() || roomId.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Enter Room Number, Check-in Date, and Check-out Date");
            alert.showAndWait();
            return 0;
        }

        String qu = "SELECT price FROM ROOMTYPE, ROOM WHERE " +
                "ROOM.roomTypeId = ROOMTYPE.roomTypeId " +
                "AND ROOM.roomId = " + roomId;
        ResultSet rs = databaseHandler.execQuery(qu);
        try {
            while (rs.next()) {
                oneDayPrice = rs.getInt("price");
            }
        } catch (SQLException ex){
            Logger.getLogger(addGuestController.class.getName()).log(Level.SEVERE, null, ex);

        }
        try {
            dayInterval = calculateDateInterval(checkInDate, checkOutDate);
        } catch (Exception ex){
            return -1;
        }

        return oneDayPrice * dayInterval;

    }

    @FXML
    void addGuest(ActionEvent event) {

        String name = guest_name.getText();
        String roomId = room_number.getText();
        String checkInDate = check_in_date.getText();
        String checkOutDate = check_out_date.getText();
        String requirement = requirements.getText();

        if(!roomId.isEmpty() && !rooms.contains(roomId)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Wrong Room Number");
            alert.showAndWait();
            return;
        }

        int totalPrice = calculateTotalPrice();
        if(totalPrice == -1){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Invalid Date Format");
            alert.showAndWait();
            priceText.setText("Wrong date");
            return;
        }
        else{priceText.setText("$" + totalPrice);}


        if (name.isEmpty() || checkInDate.isEmpty() || checkOutDate.isEmpty() || roomId.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Enter in all fields");
            alert.showAndWait();
            return;
        }


        try {
            String valid = "SELECT c.roomId FROM CUSTOMER c " +
                    "WHERE c.roomId = " + roomId + " AND (('"
                    + checkInDate + "' BETWEEN c.checkInDate AND c.checkOutDate) OR ('"
                    + checkOutDate + "' BETWEEN c.checkInDate AND c.checkOutDate))";
            System.out.println("here");
            System.out.println(valid);
            ResultSet rs = databaseHandler.execQuery(valid);
            if (rs.next()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Not available room");
                alert.showAndWait();
                return;
            }
        }catch (SQLException ex){
                Logger.getLogger(addGuestController.class.getName()).log(Level.SEVERE,null,ex);
            }

        String qu = "INSERT INTO CUSTOMER VALUES("
                + "'" + name + "',"
                + roomId + ","
                + "'" + checkInDate + "',"
                + "'" + checkOutDate + "',"
                + "'" + requirement + "',"
                + "'" + "false" + "',"
                + totalPrice
                + ")";
        System.out.println(qu);

        if (databaseHandler.execAction(qu)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Success");
            alert.showAndWait();

        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Failed");
            alert.showAndWait();
        }
        databaseHandler.checkData();
    }


    @FXML
    void cancel(ActionEvent event) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

}

