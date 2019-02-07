package ManagerRevenue;

import CheckIn.addGuestController;
import com.jfoenix.controls.JFXButton;
import database.DatabaseHandler;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.Initializable;

import javafx.scene.control.TextField;

public class ShowRevenueController implements Initializable{
    DatabaseHandler databaseHandler;

    public void initialize(URL url, ResourceBundle rb){
        databaseHandler = new DatabaseHandler();
        month.getItems().addAll("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12");
        year.getItems().addAll("2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019");
    }

    @FXML
    private JFXButton revenue_ok;

    @FXML
    private JFXButton show_revenue_reset;

    @FXML
    private TextField revenue;

    @FXML
    private ComboBox<String> month;

    @FXML
    private ComboBox<String> year;

    @FXML
    void show_revenue(ActionEvent event) {
        String selectedMonth = month.getValue();
        String selectedYear = year.getValue();
        System.out.println(selectedMonth);
        System.out.println(selectedYear);
        if(selectedMonth == null){
            String startDate = selectedYear + "-01-01";
            String endDate = selectedYear+"-12-31";
            String qu = "SELECT SUM(c.totalPrice) AS Total FROM CUSTOMER c WHERE c.checkOutDate BETWEEN  '"+ startDate +"'  AND  '"+ endDate +"' ";
            ResultSet rs = databaseHandler.execQuery(qu);
            try{
                while(rs.next()){
                    int totalRevenue = rs.getInt("Total");
                    revenue.setText(Integer.toString(totalRevenue));
                }
            }catch (SQLException ex) {
                Logger.getLogger(addGuestController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    else{
        String startDate = selectedYear + "-" + selectedMonth + "-01";
        String endDate = "";
        System.out.print(selectedMonth);
        if (selectedMonth.equals("1") || selectedMonth.equals("3") || selectedMonth.equals("5") || selectedMonth.equals("7") || selectedMonth.equals("8") || selectedMonth.equals("10") || selectedMonth.equals("12")) {
            System.out.println("big");
            endDate = selectedYear + "-" + selectedMonth + "-31";
        } else if (selectedMonth.equals("2")) {
            System.out.println("2");
            endDate = selectedYear + "-" + selectedMonth + "-28";
        } else {
            System.out.println("small");
            endDate = selectedYear + "-" + selectedMonth + "-30";
        }

        String qu = "SELECT SUM(c.totalPrice) AS Total FROM CUSTOMER c WHERE c.checkOutDate BETWEEN  '"+ startDate +"'  AND  '"+ endDate +"' ";
        ResultSet rs = databaseHandler.execQuery(qu);
        try {
            while (rs.next()) {
                int totalRevenue = rs.getInt("Total");
                revenue.setText(Integer.toString(totalRevenue));
            }
        } catch (SQLException ex) {
            Logger.getLogger(addGuestController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    }

    @FXML
    void reset_all(ActionEvent event) {
        month.setValue(null);
        year.setValue(null);
        revenue.clear();
    }
}
