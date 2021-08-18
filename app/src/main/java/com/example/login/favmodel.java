package com.example.login;

public class favmodel {
    private String name;
    private String rating;
    private String image;
    private String time;
    private String Adress;
    private String phone;
    private String ID;
    private String path;

    public favmodel(String name, String rating, String image, String time, String adress, String phone, String ID, String path) {
        this.name = name;
        this.rating = rating;
        this.image = image;
        this.time = time;
        Adress = adress;
        this.phone = phone;
        this.ID = ID;
        this.path = path;
    }

    public favmodel() {
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAdress() {
        return Adress;
    }

    public void setAdress(String adress) {
        Adress = adress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
