package com.example.edensight;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Resident implements Parcelable {
    private String name, dob, allocationDate, roomNumber, status, caretaker, gender, ic, nationality, bloodType, telNum, emergencyTel, guardian, address, deviceAddr;
    private int id;
    private double weight, height;
    private String[] conditions, allergies, medication;
    private List<String> bpm = new ArrayList<String>(), spo2 = new ArrayList<String>(), updateDate = new ArrayList<String>();

    public Resident() { }

    public Resident(String name, String dob, String allocationDate, String roomNumber, String status, String caretaker, String gender, String ic, String nationality, String bloodType, String telNum, String emergencyTel, String guardian, String address, int id, double weight, double height) {
        this.name = name;
        this.dob = dob;
        this.allocationDate = allocationDate;
        this.roomNumber = roomNumber;
        this.status = status;
        this.caretaker = caretaker;
        this.gender = gender;
        this.ic = ic;
        this.nationality = nationality;
        this.bloodType = bloodType;
        this.telNum = telNum;
        this.emergencyTel = emergencyTel;
        this.guardian = guardian;
        this.address = address;
        this.id = id;
        this.weight = weight;
        this.height = height;
    }

    protected Resident(Parcel in) {
        name = in.readString();
        dob = in.readString();
        allocationDate = in.readString();
        roomNumber = in.readString();
        status = in.readString();
        caretaker = in.readString();
        gender = in.readString();
        ic = in.readString();
        nationality = in.readString();
        bloodType = in.readString();
        telNum = in.readString();
        emergencyTel = in.readString();
        guardian = in.readString();
        address = in.readString();
        id = in.readInt();
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

    public void setName(String name) { this.name = name; }

    public String getDob() { return dob; }

    public void setDob(String dob) { this.dob = dob; }

    public String getAllocationDate() { return allocationDate; }

    public void setAllocationDate(String allocationDate) { this.allocationDate = allocationDate; }

    public String getRoomNumber() { return roomNumber; }

    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public String getCaretaker() { return caretaker; }

    public void setCaretaker(String caretaker) { this.caretaker = caretaker; }

    public String getGender() { return gender; }

    public void setGender(String gender) { this.gender = gender; }

    public String getIc() { return ic; }

    public void setIc(String ic) { this.ic = ic; }

    public String getNationality() { return nationality; }

    public void setNationality(String nationality) { this.nationality = nationality; }

    public String getBloodType() { return bloodType; }

    public void setBloodType(String bloodType) { this.bloodType = bloodType; }

    public String getTelNum() { return telNum; }

    public void setTelNum(String telNum) { this.telNum = telNum; }

    public String getEmergencyTel() { return emergencyTel; }

    public void setEmergencyTel(String emergencyTel) { this.emergencyTel = emergencyTel; }

    public String getGuardian() { return guardian; }

    public void setGuardian(String guardian) { this.guardian = guardian; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public int getResidentId() { return id; }

    public void setResidentId(int id) { this.id = id; }

    public double getWeight() { return weight; }

    public void setWeight(float weight) { this.weight = weight; }

    public double getHeight() { return height; }

    public void setHeight(float height) { this.height = height; }

    public String[] getConditions() { return conditions; }

    public void setConditions(String[] conditions) { this.conditions = conditions; }

    public String[] getAllergies() { return allergies; }

    public void setAllergies(String[] allergies) { this.allergies = allergies; }

    public String[] getMedication() { return medication; }

    public void setMedication(String[] medication) { this.medication = medication; }

    public String getDeviceAddr() { return deviceAddr; }

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
        dest.writeString(allocationDate);
        dest.writeString(roomNumber);
        dest.writeString(status);
        dest.writeString(caretaker);
        dest.writeString(gender);
        dest.writeString(ic);
        dest.writeString(nationality);
        dest.writeString(bloodType);
        dest.writeString(telNum);
        dest.writeString(emergencyTel);
        dest.writeString(guardian);
        dest.writeString(address);
        dest.writeInt(id);
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
