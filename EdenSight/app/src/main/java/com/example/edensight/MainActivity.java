package com.example.edensight;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EditText searchFilter;
    ProgressBar mainProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.resident_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        searchFilter = findViewById(R.id.search_filter);
        mainProgressBar = findViewById(R.id.main_progressBar);

        new RetrieveMainActivityTask(this, recyclerView, searchFilter).execute();

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
    public void onBackPressed() {
        logout();
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
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
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

    public class RetrieveMainActivityTask extends AsyncTask<Void, Void, String>{
        Context c;
        RecyclerView rv;
        EditText filterText;
        ArrayList<Resident> residents = new ArrayList<>();
        ResidentAdapter residentAdapter;

        public RetrieveMainActivityTask(Context c, RecyclerView rv, EditText filterText){
            this.c = c;
            this.rv = rv;
            this.filterText = filterText;
        }

        protected void onPreExecute(){
            mainProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Void... voids) {
            String urlText = "https://braserver.mooo.com/edensight/api/residents/all";
            try {
                URL url = new URL(urlText);
                HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Authorization", "Basic cG9nZ2Vyczp0dXR1cnU=");
                int responseCode = urlConnection.getResponseCode();

                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String inline;
                    while ((inline = bufferedReader.readLine()) != null){
                        stringBuilder.append(inline).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                } finally {
                    urlConnection.disconnect();
                }

            } catch (Exception e){
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(String response){
            if (response == null){
                response = "THERE WAS AN ERROR";
            }

            mainProgressBar.setVisibility(View.GONE);

            try{
                JSONArray jsonResidents = new JSONArray(response);
                Log.i("INFO", jsonResidents.toString());
                Log.i("INFO", jsonResidents.get(0).toString());
                for (int i = 0; i < jsonResidents.length(); i++) {
                    JSONObject object = jsonResidents.getJSONObject(i);
                    Resident resident = new Resident(object.get("name").toString(), "0", "sampleDate", object.get("room").toString(), object.get("status").toString(), object.get("caretaker").toString());
                    Log.i("INFO", "Name: " + resident.getName());
                    residents.add(resident);
                }
                residentAdapter = new ResidentAdapter(residents, getApplicationContext());
                rv.setAdapter(residentAdapter);
                searchFilter.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) { }

                    @Override
                    public void afterTextChanged(Editable s) {
                        filter(s.toString());
                    }
                });
            } catch (Exception e){
                Log.e("ERROR", e.getMessage(), e);
            }

        }

        private void filter(String text) {
            ArrayList<Resident> filteredList = new ArrayList<>();

            for (Resident item : residents) {
                if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(item);
                }
            }
            residentAdapter.filterList(filteredList);
        }
    }
}
