package com.example.login.model;

public class User {
    private String Name;
    private String Pass;
    private String email;
    private String number;
    private String adresse;
    private String picture;
    private String ID;



    public User(String name, String pass, String email, String number, String adresse,String picture,String ID) {
        Name = name;
        Pass = pass;
        this.email = email;
        this.number = number;
        this.adresse = adresse;
        this.picture=picture;
    }

    public String getPicture() {
        return picture;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public User(){
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String pass) {
        Pass = pass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
}
