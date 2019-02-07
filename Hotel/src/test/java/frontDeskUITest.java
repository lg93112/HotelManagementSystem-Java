import FrontDesk.frontDesk;
import ManagerRoomPrice.SetRoomPriceController;
import database.DatabaseHandler;
import entity.Restaurant;
import entity.Room;
import javafx.application.Platform;
import org.junit.jupiter.api.Test;

import org.testfx.framework.junit5.ApplicationTest;
import static org.testfx.assertions.api.Assertions.assertThat;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import static org.junit.Assert.*;
import static org.testfx.matcher.control.LabeledMatchers.hasText;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class frontDeskUITest extends ApplicationTest {
    frontDesk frontdesk = new frontDesk();
    DatabaseHandler databaseHandler = new DatabaseHandler();
    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/frontDeskMenu.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void nametest() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Button bt1 = lookup("#front_desk_guest").query();
                assertThat(bt1).hasText("Guest");
                bt1.fire();
            }
        });
    }

    @Test
    public void showroomtest() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                JFXTextField f1 = lookup("#startDateText").query();
                JFXTextField f2 = lookup("#endDateText").query();
                TableView<Room> v = lookup("#tableView").query();
                JFXButton b1 = lookup("#okBtn").query();
                JFXButton b2 = lookup("#resetBtn").query();
                b2.fire();
                f1.setText("2018-11-01");
                f2.setText("2018-11-09");
                b1.fire();
                assert(!v.getItems().isEmpty());
            }
        });
    }

    @Test
    public void showroomInvalidInputtest() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                JFXTextField f1 = lookup("#startDateText").query();
                JFXTextField f2 = lookup("#endDateText").query();
                TableView<Room> v = lookup("#tableView").query();
                JFXButton b1 = lookup("#okBtn").query();
                JFXButton b2 = lookup("#resetBtn").query();
                b2.fire();
                f1.setText("2018-11-01");
                f2.setText("2018-10-01");
                b1.fire();
                f1.setText("zadqwq");
                f2.setText("qweqeq");
                b1.fire();
                assert(v.getItems().isEmpty());
            }
        });
    }

    @Test
    public void showguests() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                String[] q = showGuest();
                JFXTextField f1 = lookup("#Guest_info_name").query();
                JFXTextField f2 = lookup("#Guest_info_room").query();
                TextField t1 = lookup("#guest_check_in").query();
                JFXButton b1 = lookup("#guest_info_OK").query();
                JFXButton b2 = lookup("#guest_info_Reset").query();
                b2.fire();
                f1.setText(q[0]);
                f2.setText(q[1]);
                b1.fire();
                assert(!t1.getText().isEmpty());
            }
        });
    }

    @Test
    public void showguestMissingInfoTest() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                String[] q = showGuest();
                JFXTextField f1 = lookup("#Guest_info_name").query();
                JFXTextField f2 = lookup("#Guest_info_room").query();
                TextField t1 = lookup("#guest_check_in").query();
                JFXButton b1 = lookup("#guest_info_OK").query();
                JFXButton b2 = lookup("#guest_info_Reset").query();
                b2.fire();
                b1.fire();
                assert(t1.getText().isEmpty());
            }
        });
    }

    @Test
    public void showguestWrongRoomNumberTest() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                String[] q = showGuest();
                JFXTextField f1 = lookup("#Guest_info_name").query();
                JFXTextField f2 = lookup("#Guest_info_room").query();
                TextField t1 = lookup("#guest_check_in").query();
                JFXButton b1 = lookup("#guest_info_OK").query();
                JFXButton b2 = lookup("#guest_info_Reset").query();
                b2.fire();
                f1.setText("Gary");
                f2.setText("666");
                b1.fire();
                assert(t1.getText().isEmpty());
            }
        });
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

    @Test
    public void yelptest() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ComboBox<String> c1 = lookup("#recommend_term").query();
                ComboBox<String> c2 = lookup("#recommend_type").query();
                JFXButton b1 = lookup("#search").query();
                JFXButton b2 = lookup("#recommend_reset").query();
                TableView<Restaurant> v1 = lookup("#yelptable").query();
                b2.fire();
                c1.setValue("restaurants");
                c2.setValue("chinese");
                b1.fire();
                assert(!v1.getItems().isEmpty());
                b2.fire();
                c1.setValue("arts");
                c2.setValue("theater");
                b1.fire();
                assert(!v1.getItems().isEmpty());
                b2.fire();
                c1.setValue("food");
                c2.setValue("bubbletea");
                b1.fire();
                assert(!v1.getItems().isEmpty());
                b2.fire();
                c1.setValue("nightlife");
                c2.setValue("bars");
                b1.fire();
                assert(!v1.getItems().isEmpty());
                b2.fire();
                c1.setValue("shopping");
                c2.setValue("fashion");
                b1.fire();
                assert(!v1.getItems().isEmpty());
            }
        });
    }
}