package com.example.edensight;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ResidentAdapter.AdapterCallBack {

    private RecyclerView recyclerView;
    private ArrayList<Resident> residents;
    private ResidentAdapter residentAdapter;
    private EditText searchFilter;

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

        searchFilter = findViewById(R.id.search_filter);
        searchFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }

    @Override
    public void onMethodCallBack(Resident resident) {
        String text = "Name: " + resident.getName() + " Age: " + resident.getAge();
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void filter(String text){
        ArrayList<Resident> filteredList = new ArrayList<>();

        for (Resident item : residents){
            if (item.getName().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }
        residentAdapter.filterList(filteredList);
    }
}
