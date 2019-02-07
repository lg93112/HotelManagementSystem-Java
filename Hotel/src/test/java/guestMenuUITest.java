import Guest.GuestMenu;
import javafx.application.Platform;
import javafx.scene.control.Button;
import org.junit.jupiter.api.Test;

import org.testfx.framework.junit5.ApplicationTest;
import static org.testfx.assertions.api.Assertions.assertThat;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import com.jfoenix.controls.JFXButton;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

public class guestMenuUITest extends ApplicationTest {
    GuestMenu guestmenu = new GuestMenu();
    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/GuestMenu.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void nametest() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                JFXButton bt1 = lookup("#guest_menu_checkin").query();
                JFXButton bt2 = lookup("#guest_menu_checkout").query();
                JFXButton bt3 = lookup("#guest_menu_roomserv").query();
                assertThat(bt1).hasText("Check-In");
                assertThat(bt2).hasText("Check-Out");
                assertThat(bt3).hasText("Room Service");
                bt1.fire();
                bt2.fire();
                bt3.fire();
            }
        });
    }
}