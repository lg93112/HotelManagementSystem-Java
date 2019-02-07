import database.DatabaseHandler;
import javafx.application.Platform;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by jeremyjiang on 10/29/18.
 */
public class updateDatabaseTest {


    @Test
    public void testUpdate() {
        DatabaseHandler databaseHandler;
        databaseHandler = new DatabaseHandler();


        String name = "test-name1";
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
        String qu_update = "UPDATE Customer "
                + "SET isGone = true "
                + "WHERE checkInDate = '" + checkInDate +"'"
                + " AND name = '" + name + "'";

        assertEquals(true, databaseHandler.execAction(qu_update));
        String qu_delete = "DELETE from CUSTOMER WHERE " +
                "name = '" + name + "' AND checkInDate = '" + checkInDate+"'";
        databaseHandler.execAction(qu_delete);

    }

}
