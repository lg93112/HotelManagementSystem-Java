import ManagerRoomPrice.SetRoomPrice;
import ManagerRoomPrice.SetRoomPriceController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import org.junit.jupiter.api.Test;

import org.testfx.framework.junit5.ApplicationTest;
import static org.testfx.assertions.api.Assertions.assertThat;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import static org.junit.Assert.*;
import database.DatabaseHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TextField;
import java.sql.SQLException;
import javafx.scene.control.Button;

public class SetRoomPriceTest extends ApplicationTest{

    SetRoomPrice setRoomPrice = new SetRoomPrice();

    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/SetRoomPrice.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void test1(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Button b1 = lookup("#single_button").query();
                TextField f1 = lookup("#single_price").query();
                Button b2 = lookup("#double_button").query();
                TextField f2 = lookup("#double_price").query();
                f1.setText("100");
                b1.fire();
                assertEquals(f1.getPromptText(), "100");
                f2.setText("200");
                b2.fire();
                assertEquals(f2.getPromptText(), "200");
            }
        });
    }

    @Test
    public void test2(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Button b3 = lookup("#suite_button").query();
                TextField f3 = lookup("#suite_price").query();
                f3.setText("300");
                b3.fire();
                assertEquals(f3.getPromptText(), "300");
            }
        });
    }

    @Test
    public void invalidTest(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Button b1 = lookup("#single_button").query();
                TextField f1 = lookup("#single_price").query();
                Button b2 = lookup("#double_button").query();
                TextField f2 = lookup("#double_price").query();
                Button b3 = lookup("#suite_button").query();
                TextField f3 = lookup("#suite_price").query();
                f1.setText("aaa");
                b1.fire();
                f2.setText("011");
                b2.fire();
                f3.setText("-sdhahd");
                b3.fire();
            }
        });
    }

}
