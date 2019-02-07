import CheckIn.addGuestController;
import database.DatabaseHandler;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by jeremyjiang on 10/29/18.
 */
public class selectDatabaseTest {
    @Test
    public void testUpdate() {
        DatabaseHandler databaseHandler;
        databaseHandler = new DatabaseHandler();


        String name = "test-name2";
        String roomId = "101";
        String checkInDate = "2012-12-12";
        String checkOutDate = "2012-12-13";
        String requirement = "burger";
        String totalPrice = "100";

        String qu = "INSERT INTO CUSTOMER VALUES("
                + "'" + name + "',"
                + roomId + ","
                + "'" + checkInDate + "',"
                + "'" + checkOutDate + "',"
                + "'" + requirement + "',"
                + "'" + "false" + "',"
                + totalPrice
                + ")";
        databaseHandler.execAction(qu);


        String qu_select = "SELECT * FROM CUSTOMER  WHERE " +
                "name = '" + name + "' AND checkInDate = '" + checkInDate + "'";
        ResultSet rs = databaseHandler.execQuery(qu_select);
        try {

            while (rs.next()) {
                assertEquals(name, rs.getString("name"));
                assertEquals(Integer.parseInt(roomId), rs.getInt("roomId"));
                assertEquals(checkInDate, rs.getString("checkInDate"));
                assertEquals(checkOutDate, rs.getString("checkOutDate"));
                assertEquals(false, rs.getBoolean("isGone"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(addGuestController.class.getName()).log(Level.SEVERE, null, ex);
        }


        String qu_delete = "DELETE from CUSTOMER WHERE " +
                "name = '" + name + "' AND checkInDate = '" + checkInDate + "'";
        databaseHandler.execAction(qu_delete);
    }
}
