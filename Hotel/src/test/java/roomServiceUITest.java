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
import static org.testfx.matcher.control.LabeledMatchers.hasText;

public class roomServiceUITest extends ApplicationTest {
    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/RoomService.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void nametest() {
        JFXTextField f1 = lookup("#room_service_name").query();
        JFXTextField f2 = lookup("#room_service_num").query();
        JFXTextField f3 = lookup("#room_service_req").query();
        JFXButton bt1 = lookup("#room_service_save").query();
        JFXButton bt2 = lookup("#room_service_cancel").query();
        assertEquals("Guest Name", f1.getPromptText() );
        assertEquals("Room Number", f2.getPromptText() );
        assertEquals("Requirements", f3.getPromptText() );
        assertThat(bt1).hasText("Save");
        assertThat(bt2).hasText("Cancel");
    }


}
