package database;

import CheckIn.addGuestController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import javax.swing.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by jeremyjiang on 10/27/18.
 */
public class DatabaseHandler {

    private static DatabaseHandler handler = null;

    private static final String DB_URL = "jdbc:derby:database;create=true";
    private static Connection conn = null;
    private static Statement stmt = null;

    public DatabaseHandler() {
        createConnection();
        setupTable();
    }

    private static void createConnection() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            conn = DriverManager.getConnection(DB_URL);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Cant load database", "Database Error", JOptionPane.ERROR_MESSAGE);
            //System.exit(0);
        }
    }

    public void setupTable() {
        //dropTable();
        setupRoomTypeTable();
        setupRoomTable();
        setupCustomerTable();
    }

    public void dropTable() {
        String customerTableName = "CUSTOMER";
        String roomTableName = "ROOM";
        String roomTypeTableName = "ROOMTYPE";

        try {
            stmt = conn.createStatement();
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet customerTables =  dbm.getTables(null, null, customerTableName.toUpperCase(), null);
            ResultSet roomTables =  dbm.getTables(null, null, roomTableName.toUpperCase(), null);
            ResultSet roomTypeTables =  dbm.getTables(null, null, roomTypeTableName.toUpperCase(), null);



            if (customerTables.next()) {
                stmt.execute("DROP TABLE " + customerTableName);
                System.out.println(customerTableName + " drop success");
            }

            if (roomTables.next()) {
                stmt.execute("DROP TABLE " + roomTableName);
                System.out.println(roomTableName + " drop success");
            }

            if (roomTypeTables.next()) {
                stmt.execute("DROP TABLE " + roomTypeTableName);
                System.out.println(roomTypeTableName + " drop success");
            }



        } catch (SQLException e){
            System.err.println(e.getMessage() + " --- setupDatabase");

        } finally {
        }
    }

    public void initRoom() {
        int roomNum = 30;


//        + "roomTypeId int,\n"
//                + "roomId int,\n"

        for (int i = 0; i < roomNum; i++) {
            int roomTypeId = i / 10 + 1;
            int roomId = (i / 10 + 1) * 100 + i % 10 + 1;
            String qu = "INSERT INTO ROOM VALUES("
                    + roomTypeId+ ","
                    + roomId
                    + ")";
            if (execAction(qu)) {
                //System.out.println("success");

            }
            else {
                System.out.println("failed");
            }
        }
        System.out.println("Room table inited");


    }


    public void initRoomType() {
        int roomTypeNum = 3;
        String[] names = {"", "Single", "Double", "Suite"};
        int[] prices = {0, 100, 200, 300};

        for (int i = 1; i <= roomTypeNum; i++) {
            String qu = "INSERT INTO ROOMTYPE VALUES("
                    + "'" + names[i] + "',"
                    + i + ","
                    + prices[i]
                    + ")";
            System.out.println(qu);
            if (execAction(qu)) {
                //System.out.println("success");


            }
            else {
                System.out.println("failed");
            }
        }
        System.out.println("RoomType table inited");

    }

    public void setupCustomerTable() {
        String tableName = "CUSTOMER";
        try {
            stmt = conn.createStatement();
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables =  dbm.getTables(null, null, tableName.toUpperCase(), null);

            if (tables.next()) {
                System.out.println("Table " + tableName + " already exists");
            }
            else {
                stmt.execute("CREATE TABLE " + tableName + "("
                        + "name varchar(200),\n"
                        + "roomId int,\n"
                        + "checkInDate date,\n"
                        + "checkOutDate date,\n"
                        + "requirement varchar(200),\n"
                        + "isGone boolean default false,\n"
                        + "totalPrice int,\n"
                        + "PRIMARY KEY (name, checkInDate),\n"
                        + "FOREIGN KEY (roomId) REFERENCES ROOM(roomId)"
                        + ")"

                );
                System.out.println("Table " + tableName + " created");
            }


        } catch (SQLException e){
            System.err.println(e.getMessage() + " --- setupDatabase");

        } finally {
        }
    }
    public void setupRoomTypeTable() {
        String tableName = "ROOMTYPE";
        try {
            stmt = conn.createStatement();
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables =  dbm.getTables(null, null, tableName.toUpperCase(), null);


            if (tables.next()) {
                System.out.println("Table " + tableName + " already exists");

            }
            else {
                stmt.execute("CREATE TABLE " + tableName + "("
                        + "roomTypeName varchar(200),\n"
                        + "roomTypeId int,\n"
                        + "price int,\n"
                        + "PRIMARY KEY (roomTypeId)"
                        + ")"

                );

                System.out.println("Table " + tableName + " created");
                initRoomType();


            }


        } catch (SQLException e){
            System.err.println(e.getMessage() + " --- setupDatabase");

        } finally {
        }
    }
    public void setupRoomTable() {
        String tableName = "ROOM";
        try {
            stmt = conn.createStatement();
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables =  dbm.getTables(null, null, tableName.toUpperCase(), null);

            if (tables.next()) {
                System.out.println("Table " + tableName + " already exists");
            }
            else {
                stmt.execute("CREATE TABLE " + tableName + "("
                        + "roomTypeId int,\n"
                        + "roomId int,\n"
                        + "PRIMARY KEY (roomId),\n"
                        + "FOREIGN KEY (roomTypeId) REFERENCES ROOMTYPE (roomTypeId)"
                        + ")"

                );
                System.out.println("Table " + tableName + " created");
                initRoom();
            }


        } catch (SQLException e){
            System.err.println(e.getMessage() + " --- setupDatabase");

        } finally {
        }
    }

    public void checkData() {
        String qu = "SELECT * FROM CUSTOMER";
        ResultSet rs = execQuery(qu);

        System.out.println("***************** check customer table *****************");
        try {
            while (rs.next()) {
                String name = rs.getString("name");
                int roomId = rs.getInt("roomId");
                String checkInDate = rs.getString("checkInDate");
                String checkOutDate = rs.getString("checkOutDate");
                String requirement = rs.getString("requirement");
                boolean isGone = rs.getBoolean("isGone");
                int totalPrice = rs.getInt("totalPrice");

                System.out.println(name + ","
                        + roomId + ","
                        + checkInDate + ","
                        + checkOutDate + ","
                        + requirement + ","
                        + isGone + ","
                        + totalPrice);
            }

        } catch (SQLException ex){
            Logger.getLogger(addGuestController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

public ResultSet execQuery(String query) {
        ResultSet result;
        try {
            stmt = conn.createStatement();
            result = stmt.executeQuery(query);
        } catch (SQLException ex) {
            System.out.print("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
            return null;
        } finally {

        }
        return result;
    }

    public boolean execAction(String qu) {
        try {
            stmt = conn.createStatement();
            stmt.execute(qu);
            return true;
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(null, "Error" + ex.getMessage(), "Error OCcured", JOptionPane.ERROR_MESSAGE);
            System.out.println("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
            return false;
        } finally {
        }
    }






}