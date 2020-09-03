package com.example.edensight;

import android.os.Parcel;
import android.os.Parcelable;

public class Resident implements Parcelable {
    private String name, age, allocationDate, roomNumber, status, caretaker;

    public Resident() { }

    public Resident(String name, String age, String allocationDate, String roomNumber, String status, String caretaker) {
        this.name = name;
        this.age = age;
        this.allocationDate = allocationDate;
        this.roomNumber = roomNumber;
        this.status = status;
        this.caretaker = caretaker;
    }

    protected Resident(Parcel in) {
        name = in.readString();
        age = in.readString();
        allocationDate = in.readString();
        roomNumber = in.readString();
        status = in.readString();
        caretaker = in.readString();
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

    public String getAge() { return age; }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAllocationDate() { return allocationDate; }

    public void setAllocationDate(String allocationDate) { this.allocationDate = allocationDate; }

    public String getRoomNumber() { return roomNumber; }

    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public String getCaretaker() { return caretaker; }

    public void setCaretaker(String caretaker) { this.caretaker = caretaker; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(age);
        dest.writeString(allocationDate);
        dest.writeString(roomNumber);
        dest.writeString(status);
        dest.writeString(caretaker);
    }
}
