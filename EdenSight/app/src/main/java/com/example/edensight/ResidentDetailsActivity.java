package com.example.edensight;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ResidentDetailsActivity extends FragmentActivity {

    Resident selectedResident;
    ImageView residentImage;
    TextView residentName, residentAge, residentAllocateDate, residentRoomNumber;
    ViewPager pager;

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
        pager = findViewById(R.id.resident_detail_viewpager);

        PicassoClient.downloadImage(this, selectedResident.getImageURL(), residentImage);
        residentName.setText("Name: " + selectedResident.getName());
        residentAge.setText("Age: " + selectedResident.getAge());
        residentAllocateDate.setText("Date Allocated: " + selectedResident.getAllocationDate());
        residentRoomNumber.setText("Room Number: " + selectedResident.getRoomNumber());

        pager.setAdapter(new DetailsPagerAdapter(getSupportFragmentManager()));
        pager.setPageTransformer(true, new ZoomOutPageTransformer());
    }

    // If back button is pressed when viewpager is on history page, will go to dashboard page instead
    @Override
    public void onBackPressed() {
        if (pager.getCurrentItem() == 0){
            super.onBackPressed();
        } else {
            pager.setCurrentItem(pager.getCurrentItem() - 1);
        }
    }

   public class DetailsPagerAdapter extends FragmentStatePagerAdapter{

       public DetailsPagerAdapter(@NonNull FragmentManager fm) {
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
