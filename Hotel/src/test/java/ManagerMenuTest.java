import Manager.ManagerMenu;
import javafx.application.Platform;
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

public class ManagerMenuTest extends ApplicationTest{

    ManagerMenu managerMenu = new ManagerMenu();
    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/ManagerMenu.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void test() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                JFXButton bt1 = lookup("#manager_menu_room_price").query();
                JFXButton bt2 = lookup("#manager_menu_revenue").query();
                assertThat(bt1).hasText("Room Price");
                assertThat(bt2).hasText("Hotel Revenue");
                bt1.fire();
                bt2.fire();
            }
        });
    }

}
