package Guest;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GuestMenuController {

    @FXML
    private JFXButton guest_menu_checkin;

    @FXML
    private JFXButton guest_menu_roomserv;

    @FXML
    private JFXButton guest_menu_checkout;

    @FXML
    void check_in_page(ActionEvent event) {
        loadWindow("/addGuest.fxml", "Check-In");
    }

    @FXML
    void check_out_page(ActionEvent event) {
        loadWindow("/deleteGuest.fxml", "Check-Out");
    }

    @FXML
    void room_service_page(ActionEvent event) {
        loadWindow("/RoomService.fxml", "Add-Requirements");
    }

    void loadWindow(String loc, String title){
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(loc));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException ex){
            Logger.getLogger(GuestMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

