package ManagerRoomPrice;

import database.DatabaseHandler;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.fxml.Initializable;
import java.sql.SQLException;

public class SetRoomPriceController implements Initializable {

    DatabaseHandler databaseHandler;

    @FXML
    private TextField single_price;

    @FXML
    private TextField double_price;

    @FXML
    private TextField suite_price;

    @FXML
    private Button suite_button;

    @FXML
    private Button single_button;

    @FXML
    private Button double_button;

    public void showsingle(){
        String qu1 = "SELECT t.price FROM ROOMTYPE t " +
                "WHERE t.roomTypeName = 'Single' ";
        ResultSet rs1 = databaseHandler.execQuery(qu1);
        try {
            while (rs1.next()) {
                String single = String.valueOf(rs1.getInt("price"));
                single_price.setPromptText(single);
            }
        } catch (SQLException ex){
            Logger.getLogger(SetRoomPriceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void showdouble(){
        String qu2 = "SELECT t.price FROM ROOMTYPE t " +
                "WHERE t.roomTypeName = 'Double' ";
        ResultSet rs2 = databaseHandler.execQuery(qu2);
        try {
            while (rs2.next()) {
                String double_p = String.valueOf(rs2.getInt("price"));
                double_price.setPromptText(double_p);
            }
        } catch (SQLException ex){
            Logger.getLogger(SetRoomPriceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void showsuite(){
        String qu3 = "SELECT t.price FROM ROOMTYPE t " +
                "WHERE t.roomTypeName = 'Suite' ";
        ResultSet rs3 = databaseHandler.execQuery(qu3);
        try {
            while (rs3.next()) {
                String suite = String.valueOf(rs3.getInt("price"));
                suite_price.setPromptText(suite);
            }
        } catch (SQLException ex){
            Logger.getLogger(SetRoomPriceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void initialize(URL url, ResourceBundle rb){
        databaseHandler = new DatabaseHandler();

        showsingle();
        showdouble();
        showsuite();
    }

    @FXML
    void change_single(ActionEvent event) {
        if(!single_price.getText().matches("[1-9][0-9]*")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Wrong price input");
            alert.showAndWait();
            single_price.clear();
            showsingle();
            return;
        }
        int single = Integer.parseInt(single_price.getText());

        String newqu = "UPDATE ROOMTYPE " +
                "SET price = " + single + " WHERE roomTypeName = 'Single' ";
        System.out.println(newqu);
        databaseHandler.execAction(newqu);
        if (databaseHandler.execAction(newqu)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Success");
            alert.showAndWait();
            single_price.clear();
            showsingle();
        }
        databaseHandler.checkData();
    }

    @FXML
    void change_double(ActionEvent event) {
        if(!double_price.getText().matches("[1-9][0-9]*")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Wrong price input");
            alert.showAndWait();
            double_price.clear();
            showdouble();
            return;
        }
        int double_p = Integer.parseInt(double_price.getText());
        String newqu = "UPDATE ROOMTYPE " +
                "SET price = " + double_p + " WHERE roomTypeName = 'Double' ";
        System.out.println(newqu);
        if (databaseHandler.execAction(newqu)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Success");
            alert.showAndWait();
            double_price.clear();
            showdouble();
        }
        databaseHandler.checkData();
    }

    @FXML
    void change_suite(ActionEvent event) {
        if(!suite_price.getText().matches("[1-9][0-9]*")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Wrong price input");
            alert.showAndWait();
            suite_price.clear();
            showsuite();
            return;
        }
        int suite = Integer.parseInt(suite_price.getText());
        String newqu = "UPDATE ROOMTYPE " +
                "SET price = " + suite + " WHERE roomTypeName = 'Suite' ";
        System.out.println(newqu);
        if (databaseHandler.execAction(newqu)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Success");
            alert.showAndWait();
            suite_price.clear();
            showsuite();
        }
        databaseHandler.checkData();
    }

}

