package com.example.edensight;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;


import android.content.Intent;
import android.os.Bundle;


public class ResidentDetailsActivity extends FragmentActivity {

    Resident selectedResident;
    String username, password;
    ViewPager pager;
    PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resident_details);

        // Retrieve resident info from previous activity
        Intent intent = getIntent();
        selectedResident = intent.getParcelableExtra("resident");
        username = intent.getStringExtra("username");
        password = intent.getStringExtra("password");

        pager = findViewById(R.id.resident_detail_viewpager);
        pagerAdapter = new ResidentPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        pager.setPageTransformer(true, new ZoomOutPageTransformer());
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("password", password);
        startActivity(intent);
        finish();
    }

    private class ResidentPagerAdapter extends FragmentStatePagerAdapter {

        public ResidentPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0: return ResidentGeneralFragment.newInstance(selectedResident);
                case 1: return ResidentDetailedFragment.newInstance(selectedResident);
                case 2: return ResidentHealthFragment.newInstance(selectedResident);
                case 3: return ResidentHistoryFragment.newInstance(selectedResident);
                default: return ResidentGeneralFragment.newInstance(selectedResident);
            }
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0: return "Status"; // Class is ResidentGeneralFragment
                case 1: return "Details"; // Class is ResidentDetailedFragment
                case 2: return "Health";
                case 3: return "History";
                default: return "none";
            }
        }
    }
}
