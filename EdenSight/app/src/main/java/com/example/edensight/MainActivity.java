package com.example.edensight;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
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
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EditText searchFilter;
    ProgressBar mainProgressBar;
    static boolean alarmIsRunning = false;
    final Handler alarmHandler = new Handler();
    String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            username = extras.getString("username");
            password = extras.getString("password");
        }

        recyclerView = findViewById(R.id.resident_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        searchFilter = findViewById(R.id.search_filter);
        mainProgressBar = findViewById(R.id.main_progressBar);

        // Comment this if server is not on
        new RetrieveMainActivityTask(this, recyclerView, searchFilter).execute();

        // Making sure that only 1 instance is running
        if (!alarmIsRunning) {
            alarmRun.run();
        }
        alarmIsRunning = true;

    }

    Runnable alarmRun = new Runnable() {
        int timer = 0;
        @Override
        public void run() {
            timer += 5;
            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "aye")
                    .setSmallIcon(R.drawable.eden_logo)
                    .setContentTitle("Message from EdenSight!")
                    .setContentText(timer + " seconds have passed.")
                    .setAutoCancel(true)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
            Intent notificationIntent = new Intent(getApplicationContext(), ResidentDetailsActivity.class);
            notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);

            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(0, builder.build());
            alarmHandler.postDelayed(this, 5000);
        }
    };

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        alarmHandler.removeCallbacks(alarmRun);
        alarmIsRunning = false;
        Log.d("INFO", "Alarm handler remove call back executed.");
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
                String encoded = java.util.Base64.getEncoder().encodeToString((username + ":" + password).getBytes(StandardCharsets.UTF_8));
                urlConnection.setRequestProperty("Authorization", "Basic " + encoded);
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
                mainProgressBar.setVisibility(View.GONE);
                searchFilter.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Please ensure that device has Internet connection.", Toast.LENGTH_SHORT).show();
            } else {
                mainProgressBar.setVisibility(View.GONE);
                try{
                    JSONArray jsonResidents = new JSONArray(response);
                    for (int i = 0; i < jsonResidents.length(); i++) {
                        JSONObject object = jsonResidents.getJSONObject(i);
                        Resident resident = new Resident(object.get("name").toString(), "0", "sampleDate", object.get("room").toString(), object.get("status").toString(), object.get("caretaker").toString());
                        residents.add(resident);
                    }
                    residentAdapter = new ResidentAdapter(residents, getApplicationContext(), username, password);
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

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Notifications";
            String description = "Channel for notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("aye", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
