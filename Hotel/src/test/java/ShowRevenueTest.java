import ManagerRevenue.ShowRevenue;
import org.junit.jupiter.api.Test;

import org.testfx.framework.junit5.ApplicationTest;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import com.jfoenix.controls.JFXButton;

import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.application.Platform;


public class ShowRevenueTest extends ApplicationTest {

    ShowRevenue showRevenue = new ShowRevenue();

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/ShowRevenue.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void date_year() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ComboBox<String> b1 = lookup("#month").query();
                ComboBox<String> b2 = lookup("#year").query();
                TextField f1 = lookup("#revenue").query();
                JFXButton bk = lookup("#revenue_ok").query();
                JFXButton bs = lookup("#show_revenue_reset").query();
                bs.fire();
                b1.setValue("11");
                b2.setValue("2018");
                bk.fire();
                assert (!f1.getText().isEmpty());
            }
        });
    }

    @Test
    public void validonlyyear() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ComboBox<String> b2 = lookup("#year").query();
                TextField f1 = lookup("#revenue").query();
                JFXButton b = lookup("#revenue_ok").query();
                b2.setValue("2018");
                b.fire();
                assert (!f1.getText().isEmpty());
            }
        });
    }

    @Test
    public void invalidmissingyear() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ComboBox<String> b1 = lookup("#month").query();
                TextField f1 = lookup("#revenue").query();
                JFXButton b = lookup("#revenue_ok").query();
                b1.setValue("8");
                b.fire();
                assert (f1.getText().isEmpty());
            }
        });
    }

    @Test
    public void validcornercase() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ComboBox<String> b2 = lookup("#year").query();
                ComboBox<String> b1 = lookup("#month").query();
                TextField f1 = lookup("#revenue").query();
                JFXButton b = lookup("#revenue_ok").query();
                JFXButton bs = lookup("#show_revenue_reset").query();
                bs.fire();
                b2.setValue("2016");
                b1.setValue("08");
                b.fire();
                assert (!f1.getText().isEmpty());
                bs.fire();
                b2.setValue("2016");
                b1.setValue("02");
                b.fire();
            }
        });
    }
}
