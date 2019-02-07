package entity;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.ImageView;

import java.awt.*;

public class Restaurant {
    //private final SimpleStringProperty img_url;
    private final ImageView image;
    private final SimpleStringProperty name;
    private final SimpleStringProperty location;
    private final SimpleStringProperty rating;

    public Restaurant(ImageView image, String name, String location, String rating) {
        //this.img_url = new SimpleStringProperty(img_url);
        this.image = image;
        this.name = new SimpleStringProperty(name);
        this.location = new SimpleStringProperty(location);
        this.rating = new SimpleStringProperty(rating);
    }

//    public String getImg_url() {
//        return img_url.get();
//    }
//
//    public SimpleStringProperty img_urlProperty() {
//        return img_url;
//    }
//
//    public void setImg_url(String img_url) {
//        this.img_url.set(img_url);
//    }


    public ImageView getImage() {
        return image;
    }
    /*
    public String getName() { return name.get(); }
*/
    public SimpleStringProperty nameProperty() {
        return name;
    }
/*
    public void setName(String name) {
        this.name.set(name);
    }

    public String getLocation() {
        return location.get();
    }
*/
    public SimpleStringProperty locationProperty() {
        return location;
    }
/*
    public void setLocation(String location) {
        this.location.set(location);
    }

    public String getRating() {
        return rating.get();
    }
*/
    public SimpleStringProperty ratingProperty() {
        return rating;
    }
/*
    public void setRating(String rating) {
        this.rating.set(rating);
    }
    */
}
