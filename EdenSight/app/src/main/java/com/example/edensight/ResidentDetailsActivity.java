package com.example.edensight;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ResidentDetailsActivity extends AppCompatActivity {

    Resident selectedResident;
    ImageView residentImage;
    TextView residentName, residentAge, residentAllocateDate, residentRoomNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resident_details);

        // Retrieve resident info from previous activity
        Intent intent = getIntent();
        selectedResident = intent.getParcelableExtra("resident");

        residentImage = findViewById(R.id.resident_detail_picture);
        residentName = findViewById(R.id.resident_detail_name);
        residentAge = findViewById(R.id.resident_detail_age);
        residentAllocateDate = findViewById(R.id.resident_detail_allocateDate);
        residentRoomNumber = findViewById(R.id.resident_detail_roomNumber);

        PicassoClient.downloadImage(this, selectedResident.getImageURL(), residentImage);
        residentName.setText("Name: " + selectedResident.getName());
        residentAge.setText("Age: " + selectedResident.getAge());
        residentAllocateDate.setText("Date Allocated: " + selectedResident.getAllocationDate());
        residentRoomNumber.setText("Room Number: " + selectedResident.getRoomNumber());
    }
}
