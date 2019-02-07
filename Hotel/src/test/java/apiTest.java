import API.API;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import javafx.application.Platform;
public class apiTest {
    @Test
    public void testget(){
        API testapi = new API();
        try {
            testapi.Get_yelp("restaurants","NYC","buffets",1);
            assertEquals(200,testapi.responseCode);
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
