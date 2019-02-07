package LogIn;


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

public class logInMenuController {

    @FXML
    private JFXButton main_front_desk;

    @FXML
    private JFXButton main_manager;

    @FXML
    void front_desk_menu(ActionEvent event) {
        loadWindow("/frontDeskMenu.fxml", "FrontDeskMenu");
    }

    @FXML
    void manager_menu(ActionEvent event) {loadWindow("/ManagerMenu.fxml", "ManagerMenu");}

    void loadWindow(String loc, String title){
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(loc));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException ex){
            Logger.getLogger(logInMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

