package entity;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by jeremyjiang on 11/6/18.
 */
public class Room {
    private final SimpleIntegerProperty roomId;
    private final SimpleIntegerProperty roomTypeId;
    private final SimpleIntegerProperty price;
    private final SimpleStringProperty roomTypeName;

    public Room(Integer roomId, Integer roomTypeId, Integer price, String roomTypeName) {
        this.roomId = new SimpleIntegerProperty(roomId);
        this.roomTypeId = new SimpleIntegerProperty(roomTypeId);
        this.price = new SimpleIntegerProperty(price);
        this.roomTypeName = new SimpleStringProperty(roomTypeName);
    }
    /*
    public int getRoomId() {
        return roomId.get();
    }
    */
    public SimpleIntegerProperty roomIdProperty() {
        return roomId;
    }
    /*
    public void setRoomId(int roomId) {
        this.roomId.set(roomId);
    }

    public int getRoomTypeId() {
        return roomTypeId.get();
    }

    public SimpleIntegerProperty roomTypeIdProperty() {
        return roomTypeId;
    }

    public void setRoomTypeId(int roomTypeId) {
        this.roomTypeId.set(roomTypeId);
    }

    public int getPrice() {
        return price.get();
    }

    public SimpleIntegerProperty priceProperty() {
        return price;
    }

    public void setPrice(int price) {
        this.price.set(price);
    }

    public String getRoomTypeName() {
        return roomTypeName.get();
    }
    */
    public SimpleStringProperty roomTypeNameProperty() {
        return roomTypeName;
    }
    /*
    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName.set(roomTypeName);
    }
    */
}
