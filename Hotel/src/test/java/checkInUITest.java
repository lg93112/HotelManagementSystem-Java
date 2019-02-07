import CheckIn.HotelAssistant;
import ManagerRoomPrice.SetRoomPriceController;
import database.DatabaseHandler;
import javafx.application.Platform;
import org.junit.jupiter.api.Test;
import java.util.*;
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
import java.util.Random;
import javafx.scene.text.Text;

import static org.junit.Assert.*;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

public class checkInUITest extends ApplicationTest {
    HotelAssistant assistant = new HotelAssistant();
    DatabaseHandler databaseHandler = new DatabaseHandler();
    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/addGuest.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }


    @Test
    public void validtest() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Random random = new Random();
                JFXTextField f1 = lookup("#requirements").query();
                JFXTextField f2 = lookup("#check_out_date").query();
                JFXTextField f3 = lookup("#guest_name").query();
                JFXTextField f5 = lookup("#room_number").query();
                JFXTextField f6 = lookup("#check_in_date").query();
                JFXButton bt1 = lookup("#save_button").query();
                JFXButton bt2 = lookup("#cancel_button").query();
                JFXButton bt3 = lookup("#Total_price_button").query();
                Text t1 = lookup("#priceText").query();
                String[] q = showGuest();
                f3.setText(q[0]);
                String[] rooms = {"101", "102", "103", "104", "105", "106", "107", "108", "109", "110", "201", "202", "203", "204", "205", "206", "207", "208", "209", "210", "301", "302", "303", "304", "305", "306", "307", "308", "309", "310"};
                String room = rooms[random.nextInt(30)];
                f5.setText(room);
                f1.setText("I need hot water");
                String[] years = {"2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019"};
                int year_idx = random.nextInt(8);
                String month = String.valueOf(random.nextInt(12)+1);
                int day = random.nextInt(27)+1;
                String day1 = String.valueOf(day);
                String day2 = String.valueOf(day+1);
                f2.setText(years[year_idx] + "-" + month + "-" + day2);
                f6.setText(years[year_idx] + "-" + month + "-" + day1);
                bt3.fire();
                assert(!t1.getText().isEmpty());
                bt1.fire();
                assert(findGuest(q[0], room));
                bt2.fire();
            }
        });
    }

    @Test
    public void NullDateAndRoomTest(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                JFXTextField f1 = lookup("#requirements").query();
                JFXTextField f2 = lookup("#check_out_date").query();
                JFXTextField f3 = lookup("#guest_name").query();
                JFXTextField f5 = lookup("#room_number").query();
                JFXTextField f6 = lookup("#check_in_date").query();
                JFXButton bt1 = lookup("#save_button").query();
                JFXButton bt2 = lookup("#cancel_button").query();
                JFXButton bt3 = lookup("#Total_price_button").query();
                Text t1 = lookup("#priceText").query();
                String[] q = showGuest();
                f3.setText(q[0]);
                f1.setText("I need hot water");
                bt3.fire();
                bt1.fire();
            }
        });
    }

    @Test
    public void InvalidDateTest(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                JFXTextField f1 = lookup("#requirements").query();
                JFXTextField f2 = lookup("#check_out_date").query();
                JFXTextField f3 = lookup("#guest_name").query();
                JFXTextField f5 = lookup("#room_number").query();
                JFXTextField f6 = lookup("#check_in_date").query();
                JFXButton bt1 = lookup("#save_button").query();
                JFXButton bt2 = lookup("#cancel_button").query();
                JFXButton bt3 = lookup("#Total_price_button").query();
                Text t1 = lookup("#priceText").query();
                String[] q = showGuest();
                f3.setText(q[0]);
                f5.setText(q[1]);
                f2.setText("2014-11-25");
                f6.setText("2014-12-01");
                bt1.fire();
                assert(findGuest(q[0], q[1]) == false);
            }
        });
    }

    @Test
    public void NotAvailableRoomTest(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                JFXTextField f1 = lookup("#requirements").query();
                JFXTextField f2 = lookup("#check_out_date").query();
                JFXTextField f3 = lookup("#guest_name").query();
                JFXTextField f5 = lookup("#room_number").query();
                JFXTextField f6 = lookup("#check_in_date").query();
                JFXButton bt1 = lookup("#save_button").query();
                JFXButton bt2 = lookup("#cancel_button").query();
                JFXButton bt3 = lookup("#Total_price_button").query();
                Text t1 = lookup("#priceText").query();
                f3.setText("chaichaichai");
                f5.setText("101");
                f2.setText("2018-01-02");
                f6.setText("2018-01-01");
                bt3.fire();
                bt1.fire();
            }
        });
    }

    @Test
    public void InvalidAllPossibleFieldsTest(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                JFXTextField f1 = lookup("#requirements").query();
                JFXTextField f2 = lookup("#check_out_date").query();
                JFXTextField f3 = lookup("#guest_name").query();
                JFXTextField f5 = lookup("#room_number").query();
                JFXTextField f6 = lookup("#check_in_date").query();
                JFXButton bt1 = lookup("#save_button").query();
                JFXButton bt2 = lookup("#cancel_button").query();
                JFXButton bt3 = lookup("#Total_price_button").query();
                Text t1 = lookup("#priceText").query();
                String[] q = showGuest();
                f3.setText(q[0]);
                f5.setText("999");
                f2.setText("x");
                f6.setText("1");
                bt3.fire();
                bt1.fire();
                f5.setText("101");
                f2.setText("xxx");
                f6.setText("ttt");
                bt3.fire();
                bt1.fire();
            }
        });
    }

    @Test
    public void ExistingGuestTest(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Random random = new Random();
                JFXTextField f1 = lookup("#requirements").query();
                JFXTextField f2 = lookup("#check_out_date").query();
                JFXTextField f3 = lookup("#guest_name").query();
                JFXTextField f5 = lookup("#room_number").query();
                JFXTextField f6 = lookup("#check_in_date").query();
                JFXButton bt1 = lookup("#save_button").query();
                JFXButton bt2 = lookup("#cancel_button").query();
                JFXButton bt3 = lookup("#Total_price_button").query();
                Text t1 = lookup("#priceText").query();
                f3.setText("chaichaichai");
                String[] rooms = {"101", "102", "103", "104", "105", "106", "107", "108", "109", "110", "201", "202", "203", "204", "205", "206", "207", "208", "209", "210", "301", "302", "303", "304", "305", "306", "307", "308", "309", "310"};
                String room = rooms[random.nextInt(30)];
                f5.setText(room);
                f2.setText("2018-01-02");
                f6.setText("2018-01-01");
                bt3.fire();
                bt1.fire();
            }
        });
    }

    public String[] showGuest(){
        Random random = new Random();
        String[] result = new String[2];
        String qu = "SELECT c.name, c.roomId FROM CUSTOMER c";
        ResultSet rs = databaseHandler.execQuery(qu);
        try {
            while (rs.next()) {
                result[0] = rs.getString("name") + String.valueOf(random.nextGaussian());
                result[1] = String.valueOf(rs.getInt("roomId"));
                return result;
            }
        } catch (SQLException ex){
            Logger.getLogger(SetRoomPriceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public boolean findGuest(String name, String room){
        int roomId = Integer.parseInt(room);
        String qu = "SELECT c.name, c.roomId FROM CUSTOMER c WHERE c.name = " +
                "'" + name + "' AND c.roomId = " + roomId + " ";
        ResultSet rs = databaseHandler.execQuery(qu);
        System.out.println(qu);
        try {
            while (rs.next()) {
                return true;
            }
        } catch (SQLException ex){
            Logger.getLogger(SetRoomPriceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}