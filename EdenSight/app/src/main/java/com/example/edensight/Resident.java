package com.example.edensight;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Resident implements Parcelable {
    private String name, dob, roomNumber, caretaker, gender, ic, nationality, bloodType, telNum, emergencyTel, guardian, deviceAddr, streetAddr1, streetAddr2, postal, city, state;
    private double weight, height;
    private String[] conditions, allergies, medication;
    private List<String> bpm = new ArrayList<String>(), spo2 = new ArrayList<String>(), updateDate = new ArrayList<String>();

    public Resident() { }

    public Resident(String name, String dob, String roomNumber, String caretaker, String gender, String ic, String nationality, String bloodType, String telNum, String emergencyTel, String guardian, double weight, double height) {
        this.name = name;
        this.dob = dob;
        this.roomNumber = roomNumber;
        this.caretaker = caretaker;
        this.gender = gender;
        this.ic = ic;
        this.nationality = nationality;
        this.bloodType = bloodType;
        this.telNum = telNum;
        this.emergencyTel = emergencyTel;
        this.guardian = guardian;
        this.weight = weight;
        this.height = height;
    }

    protected Resident(Parcel in) {
        name = in.readString();
        dob = in.readString();
        roomNumber = in.readString();
        caretaker = in.readString();
        gender = in.readString();
        ic = in.readString();
        nationality = in.readString();
        bloodType = in.readString();
        telNum = in.readString();
        emergencyTel = in.readString();
        guardian = in.readString();
        streetAddr1 = in.readString();
        streetAddr2 = in.readString();
        postal = in.readString();
        city = in.readString();
        state = in.readString();
        weight = in.readDouble();
        height = in.readDouble();
        conditions = in.createStringArray();
        allergies = in.createStringArray();
        medication = in.createStringArray();
        deviceAddr = in.readString();
        bpm = in.createStringArrayList();
        spo2 = in.createStringArrayList();
        updateDate = in.createStringArrayList();
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

    public String getName() { return name; }

    public String getDob() { return dob; }

    public String getRoomNumber() { return roomNumber; }

    public String getCaretaker() { return caretaker; }

    public String getGender() { return gender; }

    public String getIc() { return ic; }

    public String getNationality() { return nationality; }

    public String getBloodType() { return bloodType; }

    public String getTelNum() { return telNum; }

    public String getEmergencyTel() { return emergencyTel; }

    public String getGuardian() { return guardian; }

    public void setName(String name) { this.name = name; }

    public String getStreetAddr1() { return streetAddr1; }

    public void setStreetAddr1(String streetAddr1) { this.streetAddr1 = streetAddr1; }

    public String getStreetAddr2() { return streetAddr2; }

    public void setStreetAddr2(String streetAddr2) { this.streetAddr2 = streetAddr2; }

    public String getPostal() { return postal; }

    public void setPostal(String postal) { this.postal = postal; }

    public String getCity() { return city; }

    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }

    public void setState(String state) { this.state = state; }

    public double getWeight() { return weight; }

    public double getHeight() { return height; }

    public String[] getConditions() { return conditions; }

    public void setConditions(String[] conditions) { this.conditions = conditions; }

    public String[] getAllergies() { return allergies; }

    public void setAllergies(String[] allergies) { this.allergies = allergies; }

    public String[] getMedication() { return medication; }

    public void setMedication(String[] medication) { this.medication = medication; }

    public void setDeviceAddr(String deviceAddr) { this.deviceAddr = deviceAddr; }

    public List<String> getBpmList() { return bpm; }

    public String getFirstBpmList() { return bpm.get(0); }

    public void addBpmList(String value){ bpm.add(value); }

    public List<String> getSpo2List() { return spo2; }

    public String getFirstSpo2List() { return spo2.get(0); }

    public void addSpo2List(String value){ spo2.add(value); }

    public List<String> getUpdateDateList() { return updateDate; }

    public String getFirstUpdateDate(){ return updateDate.get(0); }

    public void addUpdateDateList(String value){ updateDate.add(value); }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(dob);
        dest.writeString(roomNumber);
        dest.writeString(caretaker);
        dest.writeString(gender);
        dest.writeString(ic);
        dest.writeString(nationality);
        dest.writeString(bloodType);
        dest.writeString(telNum);
        dest.writeString(emergencyTel);
        dest.writeString(guardian);
        dest.writeString(streetAddr1);
        dest.writeString(streetAddr2);
        dest.writeString(postal);
        dest.writeString(city);
        dest.writeString(state);
        dest.writeDouble(weight);
        dest.writeDouble(height);
        dest.writeStringArray(conditions);
        dest.writeStringArray(allergies);
        dest.writeStringArray(medication);
        dest.writeString(deviceAddr);
        dest.writeStringList(bpm);
        dest.writeStringList(spo2);
        dest.writeStringList(updateDate);
    }
}
