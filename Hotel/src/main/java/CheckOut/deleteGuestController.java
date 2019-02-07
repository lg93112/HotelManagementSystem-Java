package CheckOut;

import CheckIn.addGuestController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import database.DatabaseHandler;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class deleteGuestController implements Initializable {

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
    private JFXButton check_out;

    @FXML
    private JFXButton check_out_cancel;

    @FXML
    private JFXTextField check_out_guest_name;

    @FXML
    private JFXTextField check_out_room_number;

    @FXML
    void CheckOutGuest(ActionEvent event) {
        String name = check_out_guest_name.getText();
        String roomId = check_out_room_number.getText();

        if (name.isEmpty() ||  roomId.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Enter in all fields");
            alert.showAndWait();
            return;
        }

        if(!rooms.contains(roomId)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Wrong room number");
            alert.showAndWait();
            return;
        }

        String qu1 = "SELECT COUNT(*) AS total FROM CUSTOMER WHERE roomId = " + roomId
                + " AND name = '" + name + "'" + " AND isGone = false ";
//        System.out.println(qu1);
        ResultSet rs = databaseHandler.execQuery(qu1);
        try {
            while (rs.next()) {
                int total = rs.getInt("total");
                if (total == 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("We can't checkout due to certain error");
                    alert.showAndWait();
                    return;
                }
            }

        } catch (SQLException ex){
            Logger.getLogger(addGuestController.class.getName()).log(Level.SEVERE, null, ex);
        }

        String qu = "UPDATE Customer "
                + "SET isGone = true "
                + "WHERE roomId = " + roomId
                + " AND name = '" + name + "'";
        System.out.println(qu);

        if (databaseHandler.execAction(qu)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Success");
            alert.showAndWait();

        }

        databaseHandler.checkData();

    }

    @FXML
    void CheckOutCancel(ActionEvent event) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();

    }



}

