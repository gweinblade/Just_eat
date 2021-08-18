package com.example.login;

public class cartmodel {
    String name,price;
    String image,ID,quantite,idresto;

    public cartmodel() {
    }

    public cartmodel(String name, String price, String image, String ID, String quantite, String idresto) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.ID = ID;
        this.quantite = quantite;
        this.idresto = idresto;
    }

    public String getidresto() {
        return idresto;
    }

    public void setidresto(String idresto) {
        this.idresto = idresto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }



    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getQuantite() {
        return quantite;
    }

    public void setQuantite(String quantite) {
        this.quantite = quantite;
    }

}
