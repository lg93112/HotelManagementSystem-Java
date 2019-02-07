import CheckOut.checkOutGuest;
import ManagerRoomPrice.SetRoomPriceController;
import database.DatabaseHandler;
import entity.Room;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.Test;

import org.testfx.framework.junit5.ApplicationTest;
import static org.testfx.assertions.api.Assertions.assertThat;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.*;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

public class checkOutUITest extends ApplicationTest {
    checkOutGuest checkout =  new checkOutGuest();
    DatabaseHandler databaseHandler = new DatabaseHandler();
    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/deleteGuest.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void validtest() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                String[] q = showGuest();
                JFXTextField f1 = lookup("#check_out_guest_name").query();
                JFXTextField f2 = lookup("#check_out_room_number").query();
                JFXButton bt1 = lookup("#check_out").query();
                JFXButton bt2 = lookup("#check_out_cancel").query();
                assertEquals("Guest Name", f1.getPromptText() );
                assertEquals("Room Number", f2.getPromptText() );
                assertThat(bt1).hasText("CheckOut");
                assertThat(bt2).hasText("Cancel");
                f1.setText(q[0]);
                f2.setText(q[1]);
                bt1.fire();
                bt2.fire();
            }
        });
    }

    @Test
    public void invalidGuestAndRoomTest() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                JFXTextField f1 = lookup("#check_out_guest_name").query();
                JFXTextField f2 = lookup("#check_out_room_number").query();
                JFXButton bt1 = lookup("#check_out").query();
                JFXButton bt2 = lookup("#check_out_cancel").query();
                assertEquals("Guest Name", f1.getPromptText() );
                assertEquals("Room Number", f2.getPromptText() );
                assertThat(bt1).hasText("CheckOut");
                assertThat(bt2).hasText("Cancel");
                f1.setText("Wenwenwenwen");
                f2.setText("666");
                bt1.fire();
                f1.setText("Wenwenwenwen");
                f2.setText("101");
                bt1.fire();
            }
        });
    }

    @Test
    public void invalidMissingFieldtTest() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                JFXTextField f1 = lookup("#check_out_guest_name").query();
                JFXTextField f2 = lookup("#check_out_room_number").query();
                JFXButton bt1 = lookup("#check_out").query();
                JFXButton bt2 = lookup("#check_out_cancel").query();
                assertEquals("Guest Name", f1.getPromptText() );
                assertEquals("Room Number", f2.getPromptText() );
                assertThat(bt1).hasText("CheckOut");
                assertThat(bt2).hasText("Cancel");
                bt1.fire();
            }
        });
    }

    @Test
    public void AlreadyCheckedOutGuestTest() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                JFXTextField f1 = lookup("#check_out_guest_name").query();
                JFXTextField f2 = lookup("#check_out_room_number").query();
                JFXButton bt1 = lookup("#check_out").query();
                JFXButton bt2 = lookup("#check_out_cancel").query();
                String[] result = checkedOutGuest();
                f1.setText(result[0]);
                f2.setText(result[1]);
                bt1.fire();
                bt2.fire();
            }
        });
    }


    public String[] showGuest(){
        String[] result = new String[2];
        String qu = "SELECT c.name, c.roomId FROM CUSTOMER c WHERE c.isGone = false";
        ResultSet rs = databaseHandler.execQuery(qu);
        try {
            while (rs.next()) {
                result[0] = rs.getString("name");
                result[1] = String.valueOf(rs.getInt("roomId"));
                return result;
            }
        } catch (SQLException ex){
            Logger.getLogger(SetRoomPriceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String[] checkedOutGuest(){
        String[] result = new String[2];
        String qu = "SELECT c.name, c.roomId FROM CUSTOMER c WHERE c.isGone = true";
        ResultSet rs = databaseHandler.execQuery(qu);
        try {
            while (rs.next()) {
                result[0] = rs.getString("name");
                result[1] = String.valueOf(rs.getInt("roomId"));
                return result;
            }
        } catch (SQLException ex){
            Logger.getLogger(SetRoomPriceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}