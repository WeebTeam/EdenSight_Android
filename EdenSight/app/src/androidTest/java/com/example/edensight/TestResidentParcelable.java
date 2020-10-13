package com.example.edensight;

import android.os.Parcel;
import androidx.test.filters.SmallTest;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

// Test Unit for Parcelable Resident Class
@SmallTest
public class TestResidentParcelable {

    private Resident resident;

    @Before
    public void createResident(){
        resident = new Resident("Jeff", "1/6/1990", "A705", "Emily", "Male", "900601134567", "Malaysian", "A", "0197654321", "0123456789", "King", 86.7, 190.4);
    }

    @Test
    public void resident_ParcelableWriteRead(){
        // Write the data
        Parcel parcel = Parcel.obtain();
        resident.writeToParcel(parcel, resident.describeContents());

        // After writing, reset parcel for reading
        parcel.setDataPosition(0);

        // Read the data
        Resident createdFromParcel = Resident.CREATOR.createFromParcel(parcel);

        // Verifying received data
        assertEquals("Jeff", createdFromParcel.getName());
        assertEquals("1/6/1990", createdFromParcel.getDob());
        assertEquals("A705", createdFromParcel.getRoomNumber());
        assertEquals("Emily", createdFromParcel.getCaretaker());
        assertEquals("Male", createdFromParcel.getGender());
        assertEquals("900601134567", createdFromParcel.getIc());
        assertEquals("Malaysian", createdFromParcel.getNationality());
        assertEquals("A", createdFromParcel.getBloodType());
        assertEquals("0197654321", createdFromParcel.getTelNum());
        assertEquals("0123456789", createdFromParcel.getEmergencyTel());
        assertEquals("King", createdFromParcel.getGuardian());
        assertEquals(86.7, createdFromParcel.getWeight(), 0);
        assertEquals(190.4, createdFromParcel.getHeight(), 0);
    }
}
