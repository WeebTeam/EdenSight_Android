package com.example.edensight;

import java.util.ArrayList;

public class Resident {
    private String name, age;

    public Resident() { }

    public Resident(String name, String age) {
        this.name = name;
        this.age = age;
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

    public static ArrayList<Resident> dummyResidentList(){;
        ArrayList<Resident> residentArrayList = new ArrayList<Resident>();
        residentArrayList.add(new Resident("Plucky", "10"));
        residentArrayList.add(new Resident("Timmy", "5"));
        residentArrayList.add(new Resident("Tommy", "5"));
        residentArrayList.add(new Resident("Rory", "11"));
        residentArrayList.add(new Resident("Sydney", "9"));
        residentArrayList.add(new Resident("Yuka", "9"));
        residentArrayList.add(new Resident("Tom Nook", "20"));

        return residentArrayList;
    }
}
