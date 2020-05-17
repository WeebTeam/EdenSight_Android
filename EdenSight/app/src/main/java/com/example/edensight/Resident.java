package com.example.edensight;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Resident implements Parcelable {
    private String name, age, imageURL, allocationDate, roomNumber;

    public Resident() { }

    public Resident(String name, String age, String imageURL, String allocationDate, String roomNumber) {
        this.name = name;
        this.age = age;
        this.imageURL = imageURL;
        this.allocationDate = allocationDate;
        this.roomNumber = roomNumber;
    }

    protected Resident(Parcel in) {
        name = in.readString();
        age = in.readString();
        imageURL = in.readString();
        allocationDate = in.readString();
        roomNumber = in.readString();
    }

    public static final Creator<Resident> CREATOR = new Creator<Resident>() {
        @Override
        public Resident createFromParcel(Parcel in) {
            return new Resident(in);
        }

        @Override
        public Resident[] newArray(int size) {
            return new Resident[size];
        }
    };

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

    public String getAllocationDate() { return allocationDate; }

    public void setAllocationDate(String allocationDate) { this.allocationDate = allocationDate; }

    public String getRoomNumber() { return roomNumber; }

    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }

    public static ArrayList<Resident> dummyResidentList(){;
        ArrayList<Resident> residentArrayList = new ArrayList<Resident>();
        residentArrayList.add(new Resident("Plucky", "10", "https://stockfresh.com/zooms/stockfresh_id894367_255ee1.jpg", "12/4/2010", "A304"));
        residentArrayList.add(new Resident("Timmy", "5", "https://stockfresh.com/zooms/stockfresh_id894367_255ee1.jpg", "3/12/2015", "B202"));
        residentArrayList.add(new Resident("Tommy", "5", "https://i.kym-cdn.com/photos/images/original/001/316/888/f81.jpeg", "17/8/2004", "A101"));
        residentArrayList.add(new Resident("Rory", "11", "https://i.kym-cdn.com/photos/images/original/001/316/888/f81.jpeg", "5/1/2010", "A303"));
        residentArrayList.add(new Resident("Sydney", "9", "https://www.apimages.com/Images/Ap_Creative_Stock_Header.jpg", "24/6/2019", "B504"));
        residentArrayList.add(new Resident("Yuka", "9", "https://www.apimages.com/Images/Ap_Creative_Stock_Header.jpg", "6/2/2009", "B605"));
        residentArrayList.add(new Resident("Tom Nook", "20", "https://www.apimages.com/Images/Ap_Creative_Stock_Header.jpg", "5/7/2010", "A102"));

        return residentArrayList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(age);
        dest.writeString(imageURL);
        dest.writeString(allocationDate);
        dest.writeString(roomNumber);
    }
}
