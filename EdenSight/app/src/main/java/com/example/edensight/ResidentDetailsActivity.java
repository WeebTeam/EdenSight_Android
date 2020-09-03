package com.example.edensight;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;


public class ResidentDetailsActivity extends FragmentActivity {

    Resident selectedResident;
    TextView residentName, residentAge, residentAllocateDate, residentRoomNumber, residentStatus, residentCaretaker;
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resident_details);

        // Retrieve resident info from previous activity
        Intent intent = getIntent();
        selectedResident = intent.getParcelableExtra("resident");

        residentName = findViewById(R.id.resident_detail_name);
        residentAge = findViewById(R.id.resident_detail_age);
        residentAllocateDate = findViewById(R.id.resident_detail_allocateDate);
        residentRoomNumber = findViewById(R.id.resident_detail_roomNumber);
        residentStatus = findViewById(R.id.resident_detail_status);
        residentCaretaker = findViewById(R.id.resident_detail_caretaker);
        pager = findViewById(R.id.resident_detail_viewpager);

        String name = getString(R.string.name) + " " + selectedResident.getName();
        String age = getString(R.string.age) + " " + selectedResident.getAge();
        String allocDate = getString(R.string.alloc_date) + " " + selectedResident.getAllocationDate();
        String roomNum = getString(R.string.room_number) + " " + selectedResident.getRoomNumber();
        String status = getString(R.string.status) + " " + selectedResident.getStatus();
        String caretaker = getString(R.string.caretaker) + " " + selectedResident.getCaretaker();

        residentName.setText(name);
        residentAge.setText(age);
        residentAllocateDate.setText(allocDate);
        residentRoomNumber.setText(roomNum);
        residentStatus.setText(status);
        residentCaretaker.setText(caretaker);

        pager.setAdapter(new DetailsPagerAdapter(getSupportFragmentManager()));
        pager.setPageTransformer(true, new ZoomOutPageTransformer());
    }

    // If back button is pressed when viewpager is on history page, will go to dashboard page instead
    @Override
    public void onBackPressed() {
        if (pager.getCurrentItem() == 0){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            pager.setCurrentItem(pager.getCurrentItem() - 1);
        }
    }

   public class DetailsPagerAdapter extends FragmentStatePagerAdapter{

       DetailsPagerAdapter(@NonNull FragmentManager fm) {
           super(fm);
       }

       @NonNull
       @Override
       public Fragment getItem(int position) {
           switch (position){
               case 0:
                   return DashboardFragment.newInstance(selectedResident);
               case 1:
                   return HistoryFragment.newInstance(selectedResident);
           }
           return null;
       }

       @Override
       public int getCount() {
           return 2;
       }

       @Nullable
       @Override
       public CharSequence getPageTitle(int position) {
           switch (position){
               case 0:
                   return "Dashboard";
               case 1:
                   return "History";
           }
           return null;
       }
   }
}
