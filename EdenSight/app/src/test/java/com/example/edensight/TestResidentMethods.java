package com.example.edensight;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

// Test Unit for Resident Class to ensure object returns correct values
public class TestResidentMethods {
    private Resident testResident;

    @Before
    public void createResident(){
        testResident = new Resident("Jeff", "1/6/1990", "A705", "Emily", "Male", "900601134567", "Malaysian", "A", "0197654321", "0123456789", "King", 86.7, 190.4);
    }

    @Test
    public void getName() {
        assertEquals("jeff", testResident.getName());
    }

    @Test
    public void getDob() {
        assertEquals("1/6/1990", testResident.getDob());
    }

    @Test
    public void getRoomNumber() {
        assertEquals("A705", testResident.getRoomNumber());
    }

    @Test
    public void getCaretaker() {
        assertEquals("Emily", testResident.getCaretaker());
    }

    @Test
    public void getGender() {
        assertEquals("Male", testResident.getGender());
    }

    @Test
    public void getIc() {
        assertEquals("900601134567", testResident.getIc());
    }

    @Test
    public void getNationality() {
        assertEquals("Malaysian", testResident.getNationality());
    }

    @Test
    public void getBloodType() {
        assertEquals("A", testResident.getBloodType());
    }

    @Test
    public void getTelNum() {
        assertEquals("0197654321", testResident.getTelNum());
    }

    @Test
    public void getEmergencyTel() {
        assertEquals("0123456789", testResident.getEmergencyTel());
    }

    @Test
    public void getGuardian() {
        assertEquals("King", testResident.getGuardian());
    }

    @Test
    public void setName() {
        testResident.setName("Bob");
        assertEquals("Bob", testResident.getName());
    }

    @Test
    public void getWeight() {
        assertEquals(86.7, testResident.getWeight(), 0);
    }

    @Test
    public void getHeight() {
        assertEquals(190.4, testResident.getHeight(), 0);
    }
}