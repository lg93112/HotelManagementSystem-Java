package FrontDesk;

import API.API;
import CheckIn.addGuestController;
import Guest.GuestMenuController;
import database.DatabaseHandler;
import entity.Restaurant;
import entity.Room;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.image.ImageViewBuilder;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.image.Image;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTextArea;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class frontDeskMenuController implements Initializable {

    DatabaseHandler databaseHandler;
    ObservableList<Room> list = FXCollections.observableArrayList();
    ObservableList<Restaurant> yelplist = FXCollections.observableArrayList();
    private Set<String> rooms;
    private String[] roomnumbers;

    public void initialize(URL url, ResourceBundle rb){
        databaseHandler = new DatabaseHandler();
        initCol();
        recommend_term.getItems().addAll(
                "restaurants", "arts", "food", "nightlife",
                "shopping"
        );
        rooms = new HashSet<String>();
        roomnumbers = new String[] {"101", "102", "103", "104", "105", "106", "107", "108", "109", "110", "201", "202", "203", "204", "205", "206", "207", "208", "209", "210", "301", "302", "303", "304", "305", "306", "307", "308", "309", "310"};
        Collections.addAll(rooms, roomnumbers);
    }

    @FXML
    void choose_type(ActionEvent event) {
        if(!recommend_term.getValue().isEmpty()){
            if(recommend_term.getValue().equals("restaurants")){
                recommend_type.getItems().setAll(
                        "newamerican", "buffets", "cafes", "chinese", "french", "indpak", "italian",
                        "japanese", "korean", "mexican", "pizza"
                );
            }
            if(recommend_term.getValue().equals("arts")){
                recommend_type.getItems().setAll(
                        "galleries", "movietheaters",
                        "museums", "opera", "theater"
                );
            }
            if(recommend_term.getValue().equals("food")){
                recommend_type.getItems().setAll(
                      "bubbletea", "coffee", "cupcakes", "farmersmarket", "icecream",
                       "juicebars", "poke", "gourmet", "tea", "wineries"
                );
            }
            if(recommend_term.getValue().equals("nightlife")){
                recommend_type.getItems().setAll(
                        "adultentertainment", "bars", "karaoke", "musicvenues"
                );
            }
            if(recommend_term.getValue().equals("shopping")){
                recommend_type.getItems().setAll(
                        "fashion", "flowers", "drugstores", "cosmetics", "electronics"
                );
            }
        }
    }

    @FXML
    private ImageView imageTest;

    @FXML
    private Tab recommendation_tab;

    @FXML
    private Button front_desk_guest;

    @FXML
    private JFXButton resetBtn;

    @FXML
    private JFXButton okBtn;

    @FXML
    private JFXButton guest_info_Reset;

    @FXML
    private JFXButton guest_info_OK;

    @FXML
    private JFXTextField startDateText;

    @FXML
    private JFXTextField endDateText;

    @FXML
    private TableView<Room>  tableView;

    @FXML
    private TableColumn<Room, Integer> roomIdCol;

    @FXML
    private TableColumn<Room, String> typeCol;

    @FXML
    private JFXTextField Guest_info_name;

    @FXML
    private JFXTextField Guest_info_room;

    @FXML
    private TextField guest_name;

    @FXML
    private TextArea guest_requirement;

    @FXML
    private TextField guest_room;

    @FXML
    private TextField guest_check_in;

    @FXML
    private TextField guest_check_out;

    @FXML
    private TextField guest_total_price;

    @FXML
    private TableView<Restaurant> yelptable;

    @FXML
    private TableColumn<Restaurant, ImageView> thumbnail_col;

    @FXML
    private TableColumn<Restaurant,String> name_col;

    @FXML
    private TableColumn<Restaurant,String> rating_col;

    @FXML
    private TableColumn<Restaurant,String> location_col;

    @FXML
    private ComboBox<String> recommend_term;

    @FXML
    private ComboBox<String> recommend_type;

    @FXML
    private JFXButton search;

    @FXML
    private JFXButton recommend_reset;



    @FXML
    void to_guestmenu(ActionEvent event) {
        loadWindow("/GuestMenu.fxml", "GuestMenu");
    }


    void loadWindow(String loc, String title){
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(loc));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException ex){
            Logger.getLogger(frontDeskMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void initCol() {
        roomIdCol.setCellValueFactory(new PropertyValueFactory<>("roomId"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("roomTypeName"));
        thumbnail_col.setCellValueFactory(new PropertyValueFactory<>("image"));
        name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        location_col.setCellValueFactory(new PropertyValueFactory<>("location"));
        rating_col.setCellValueFactory(new PropertyValueFactory<>("rating"));
        tableView.getItems().clear();
        yelptable.getItems().clear();
    }


    @FXML
    void show_availability(ActionEvent event) {
        tableView.getItems().clear();
        String fromDate = startDateText.getText();
        String toDate = endDateText.getText();
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date1 = formatter1.parse(fromDate);
            Date date2 = formatter1.parse(toDate);
            if(date2.compareTo(date1) < 0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Invalid Date Format");
                alert.showAndWait();
                return;
            }
        }
        catch(Exception ex) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Invalid Date Format");
            alert.showAndWait();
            return;
        }
        load_room();
    }


    void load_room() {
        list.clear();
        String fromDate = startDateText.getText();
        String toDate = endDateText.getText();
        String qu = "SELECT r.roomId, t.roomTypeName, t.price, t.roomTypeId FROM ROOM r, ROOMTYPE t " +
                "WHERE r.roomTypeId = t.roomTypeId " +
                "EXCEPT " +
                "SELECT r.roomId, t.roomTypeName, t.price, t.roomTypeId FROM CUSTOMER c, ROOM r, ROOMTYPE t " +
                "WHERE c.isGone = false AND " +
                "c.roomId = r.roomId AND " +
                "r.roomTypeId = t.roomTypeId AND (('"
                + fromDate + "' BETWEEN c.checkInDate AND c.checkOutDate) OR ('"
                + toDate + "' BETWEEN c.checkInDate AND c.checkOutDate))";
        System.out.println("here");
        System.out.println(qu);
        ResultSet rs = databaseHandler.execQuery(qu);

        try {
            while (rs.next()) {

                int roomId = rs.getInt("roomId");
                int price = rs.getInt("price");
                int roomTypeId = rs.getInt("roomTypeId");
                String roomTypeName = rs.getString("roomTypeName");

                list.add(new Room(roomId, roomTypeId, price, roomTypeName));
                System.out.println(roomId + ":" + roomTypeName);
            }

        } catch (SQLException ex){
            Logger.getLogger(addGuestController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.println("here:  " + list.size());

        tableView.getItems().addAll(list);
    }


    @FXML
    void Guest_info_initCol(ActionEvent event) {
        guest_name.clear();
        guest_room.clear();
        guest_check_in.clear();
        guest_check_out.clear();
        guest_total_price.clear();
        guest_requirement.clear();
    }

    @FXML
    void show_guest(ActionEvent event) {
        String name = Guest_info_name.getText();
        if(name.isEmpty() || Guest_info_room.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Missing Information");
            alert.showAndWait();
            return;
        }
        if(!rooms.contains(Guest_info_room.getText())){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Wrong room number");
            alert.showAndWait();
            return;
        }
        int room = Integer.parseInt(Guest_info_room.getText());

        String qu = "SELECT c.name, c.roomId, c.CheckInDate, c.CheckOutDate, c.requirement, c.totalPrice " +
                "FROM CUSTOMER c " +
                "WHERE c.name = '" + name + "' AND c.roomId = " + room + " " +
                "Order By c.CheckInDate";
        System.out.println(qu);
        ResultSet rs = databaseHandler.execQuery(qu);

        try {
            while (rs.next()){
                String g_name = rs.getString("name");
                String g_room = rs.getString("roomId");
                String g_checkin = rs.getString("CheckInDate");
                String g_checkout = rs.getString("CheckOutDate");
                String g_req = rs.getString("requirement");
                int g_price = rs.getInt("totalPrice");
                System.out.println(g_name + " " + g_room + " " + g_checkin);
                guest_name.setText(g_name);
                guest_room.setText(g_room);
                guest_check_in.setText(g_checkin);
                guest_check_out.setText(g_checkout);
                guest_total_price.setText(String.valueOf(g_price));
                guest_requirement.setText(g_req);
            }
        } catch (SQLException ex){
            Logger.getLogger(addGuestController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public void loadyelp() {
        yelptable.getItems().clear();
        yelplist.clear();
        API yelpApi = new API();
        String term = recommend_term.getValue();
        String location = "NYC";
        String categories = recommend_type.getValue();
        int limit = 10;
        try{
            List<String[]> restaurantList = yelpApi.Get_yelp(term,location,categories,limit);
            String[] img_urls = restaurantList.get(0);
            String[] names = restaurantList.get(1);
            String[] address = restaurantList.get(2);
            String[] rating = restaurantList.get(3);



            for (int i = 0; i < limit; i++) {
                String imageSource = img_urls[i];

                Image thumbnail = new Image(imageSource);
                ImageView imageView = new ImageView();
                imageView.setImage(thumbnail);
                imageView.setFitHeight(95);
                imageView.setFitWidth(95);
                yelplist.add(new Restaurant(imageView, names[i], address[i], rating[i]));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        yelptable.getItems().addAll(yelplist);

    }

    @FXML
    void reset_yelp_table(ActionEvent event) {
        yelptable.getItems().clear();
    }

}



