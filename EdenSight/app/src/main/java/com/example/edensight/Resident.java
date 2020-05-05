package com.example.edensight;

import java.util.ArrayList;

public class Resident {
    private String name, age, imageURL;

    public Resident() { }

    public Resident(String name, String age, String imageURL) {
        this.name = name;
        this.age = age;
        this.imageURL = imageURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public static ArrayList<Resident> dummyResidentList(){;
        ArrayList<Resident> residentArrayList = new ArrayList<Resident>();
        residentArrayList.add(new Resident("Plucky", "10", "https://i.redd.it/765frlqxtuw41.png"));
        residentArrayList.add(new Resident("Timmy", "5", "https://i.redd.it/765frlqxtuw41.png"));
        residentArrayList.add(new Resident("Tommy", "5", "https://i.redd.it/765frlqxtuw41.png"));
        residentArrayList.add(new Resident("Rory", "11", "https://i.redd.it/765frlqxtuw41.png"));
        residentArrayList.add(new Resident("Sydney", "9", "https://i.redd.it/765frlqxtuw41.png"));
        residentArrayList.add(new Resident("Yuka", "9", "https://i.redd.it/765frlqxtuw41.png"));
        residentArrayList.add(new Resident("Tom Nook", "20", "https://i.redd.it/765frlqxtuw41.png"));

        return residentArrayList;
    }
}
