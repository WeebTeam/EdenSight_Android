package com.example.edensight;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_logout:
                logout();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMethodCallBack(Resident resident) {
        String text = "Name: " + resident.getName() + " Age: " + resident.getAge();
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onBackPressed() {
        logout();
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

    public void logout(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Logout Confirmation");
        builder.setMessage("Do you want to logout?");
        builder.setCancelable(true);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Logout
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do Nothing
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
