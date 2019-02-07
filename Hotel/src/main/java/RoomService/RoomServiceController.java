package RoomService;

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
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RoomServiceController implements Initializable {
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
    private JFXButton room_service_save;

    @FXML
    private JFXTextField room_service_name;

    @FXML
    private JFXTextField room_service_num;

    @FXML
    private JFXButton room_service_cancel;

    @FXML
    private JFXTextField room_service_req;

    @FXML
    void addRoomService(ActionEvent event) {
        Boolean gone = false;
        Boolean found = false;
        String name = room_service_name.getText();
        if(!room_service_num.getText().isEmpty() && !rooms.contains(room_service_num.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Wrong room number");
            alert.showAndWait();
            return;
        }
        int roomId = Integer.parseInt(room_service_num.getText());
        String requirement = room_service_req.getText();
        String currentRequirement = "";
        if(name.isEmpty() || room_service_num.getText().isEmpty() || requirement.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Enter in all fields");
            alert.showAndWait();
            return;
        }
        String qu = "SELECT c.requirement, c.isGone " +
                "FROM CUSTOMER c " +
                "WHERE c.name = '" + name + "' AND c.roomId = " + roomId + " ";
        //System.out.println("Cheng's part");
        System.out.println(qu);
        ResultSet rs = databaseHandler.execQuery(qu);
        try{
            while(rs.next()){
                gone = rs.getBoolean("isGone");
                if(gone){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("The guest has already checked out");
                    alert.showAndWait();
                    return;
                }
                currentRequirement = rs.getString("requirement");
                found = true;
            }
        }catch (SQLException ex){
            Logger.getLogger(RoomServiceController.class.getName()).log(Level.SEVERE,null,ex);
        }
        if(!found){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("No Such guest");
            alert.showAndWait();
            return;
        }
        if( requirement.length()+currentRequirement.length()>200){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("The requirement length is longer than 200");
            alert.showAndWait();
            return;
        }
        System.out.println(currentRequirement);

        requirement = currentRequirement + " ; " + requirement;
        String newqu = "UPDATE Customer " +
                "SET requirement = '" + requirement +
                "' WHERE name = '" + name + "' AND roomId = " + roomId + " ";
        System.out.println(newqu);

        if (databaseHandler.execAction(newqu)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Success");
            alert.showAndWait();

        }

        databaseHandler.checkData();
    }

    @FXML
    void cancelRoomService(ActionEvent event) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

}

