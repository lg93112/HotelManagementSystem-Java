import ManagerRoomPrice.SetRoomPriceController;
import RoomService.addRoomService;
import com.jfoenix.controls.JFXTextField;
import database.DatabaseHandler;
import org.junit.jupiter.api.Test;
import java.util.Collections;
import org.testfx.framework.junit5.ApplicationTest;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import com.jfoenix.controls.JFXButton;

import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.application.Platform;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RoomServiceTest extends ApplicationTest{
    addRoomService addroomservice = new addRoomService();
    DatabaseHandler databaseHandler = new DatabaseHandler();

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/RoomService.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void addvalidservice() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                String[] q = showGuest();
                JFXTextField f1 = lookup("#room_service_name").query();
                JFXTextField f2 = lookup("#room_service_num").query();
                JFXTextField f3 = lookup("#room_service_req").query();
                JFXButton b = lookup("#room_service_save").query();
                JFXButton b2 = lookup("#room_service_cancel").query();
                f1.setText(q[0]);
                f2.setText(q[1]);
                f3.setText("1");
                b.fire();
                String Req = checkRequirement(q[0], q[1]);
                assert (Req.indexOf("1") >= 0);
                b2.fire();
            }
        });
    }

    @Test
    public void invalidmissingfield() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                JFXTextField f1 = lookup("#room_service_name").query();
                JFXTextField f2 = lookup("#room_service_num").query();
                JFXTextField f3 = lookup("#room_service_req").query();
                JFXButton b = lookup("#room_service_save").query();
                f2.setText("101");
                b.fire();
            }
        });
    }

    @Test
    public void invalidlongreqfield() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                String[] q = showGuest();
                JFXTextField f1 = lookup("#room_service_name").query();
                JFXTextField f2 = lookup("#room_service_num").query();
                JFXTextField f3 = lookup("#room_service_req").query();
                JFXButton b = lookup("#room_service_save").query();
                f1.setText(q[0]);
                f2.setText(q[1]);
                f3.setText(String.join("", Collections.nCopies(201, "1")));
                b.fire();
            }
        });
    }

    @Test
    public void invalidWrongRoomNumberTest() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                String[] q = showGuest();
                JFXTextField f1 = lookup("#room_service_name").query();
                JFXTextField f2 = lookup("#room_service_num").query();
                JFXTextField f3 = lookup("#room_service_req").query();
                JFXButton b = lookup("#room_service_save").query();
                f1.setText(q[0]);
                f2.setText("aaaa");
                f3.setText("dog");
                b.fire();
                f1.setText("Garyyyy");
                f2.setText("101");
                f3.setText("dog");
                b.fire();
            }
        });
    }

    @Test
    public void GuestNonExistOrAlreadyCheckedOutTest() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                String[] q = AlreadyCheckedOutGuest();
                JFXTextField f1 = lookup("#room_service_name").query();
                JFXTextField f2 = lookup("#room_service_num").query();
                JFXTextField f3 = lookup("#room_service_req").query();
                JFXButton b = lookup("#room_service_save").query();
                f1.setText("Garyyyy");
                f2.setText("101");
                f3.setText("dog");
                b.fire();
                f1.setText(q[0]);
                f2.setText(q[1]);
                f3.setText("dog");
                b.fire();
            }
        });
    }

    public String checkRequirement(String name, String room){
        int roomN = Integer.parseInt(room);
        String Req = "";
        String qu3 = "SELECT c.requirement FROM CUSTOMER c " +
                "WHERE c.name = '" + name + "' AND c.roomId = " + roomN + " ";
        ResultSet rs3 = databaseHandler.execQuery(qu3);
        try {
            while (rs3.next()) {
                Req = rs3.getString("requirement");
            }
        } catch (SQLException ex){
            Logger.getLogger(SetRoomPriceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Req;
    }

    public String[] showGuest(){
        String[] result = new String[2];
        String qu = "SELECT c.name, c.roomId FROM CUSTOMER c";
        ResultSet rs = databaseHandler.execQuery(qu);
        try {
            while (rs.next()) {
                result[0] = rs.getString("name");
                result[1] = String.valueOf(rs.getInt("roomId"));
            }
        } catch (SQLException ex){
            Logger.getLogger(SetRoomPriceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String[] AlreadyCheckedOutGuest(){
        String[] result = new String[2];
        String qu = "SELECT c.name, c.roomId FROM CUSTOMER c WHERE c.isGone = true";
        ResultSet rs = databaseHandler.execQuery(qu);
        try {
            while (rs.next()) {
                result[0] = rs.getString("name");
                result[1] = String.valueOf(rs.getInt("roomId"));
            }
        } catch (SQLException ex){
            Logger.getLogger(SetRoomPriceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
