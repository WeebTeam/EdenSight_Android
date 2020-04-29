package com.example.edensight;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ResidentAdapter.AdapterCallBack {

    private RecyclerView recyclerView;
    private ArrayList<Resident> residents;
    private ResidentAdapter residentAdapter;
    private Resident selectedResident;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        residents = Resident.dummyResidentList();
        recyclerView = findViewById(R.id.resident_recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        residentAdapter = new ResidentAdapter(residents, this);
        recyclerView.setAdapter(residentAdapter);
    }

    @Override
    public void onMethodCallBack(int position) {
        selectedResident = residents.get(position);
        String text = "Name: " + selectedResident.getName() + " Age: " + selectedResident.getAge();
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.show();
    }
}
