package Manager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ManagerMenuController {

    @FXML
    private JFXButton manager_menu_room_price;

    @FXML
    private JFXButton manager_menu_revenue;

    @FXML
    void set_room_price(ActionEvent event) {
        loadWindow("/SetRoomPrice.fxml", "SetRoomPrice");
    }

    @FXML
    void show_revenue(ActionEvent event) {loadWindow("/ShowRevenue.fxml", "ShowRevenue");}

    void loadWindow(String loc, String title){
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(loc));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException ex){
            Logger.getLogger(ManagerMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}


