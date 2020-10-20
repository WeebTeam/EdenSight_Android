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
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EditText searchFilter;
    ProgressBar mainProgressBar;
    static boolean alarmIsRunning = false;
    final Handler alarmHandler = new Handler();
    String username, password, caretakerName;
    int caretakerId;
    ArrayList<Resident> residents = new ArrayList<>();
    List<String> deviceAddressList = new ArrayList<>();

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

        // Retrieve mac address details before making resident list
        PreretrieveDataTask firstTask = new PreretrieveDataTask();
        try {
            String test = firstTask.execute().get();
        } catch (ExecutionException e) {
            Log.e("ERROR", e.getMessage(), e);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Ensures that all resident names are updated into the resident names list to be used in the alarm
        RetrieveMainActivityTask secondTask = new RetrieveMainActivityTask(this, recyclerView, searchFilter);
        try {
            List<String> test = secondTask.execute().get();
        } catch (ExecutionException e) {
            Log.e("ERROR", e.getMessage(), e);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Making sure that only 1 instance is running
        if (!alarmIsRunning) {
            alarmRun.run();
        }
        alarmIsRunning = true;

    }

    // Runnable class that checks vital signs data every 10 seconds
    Runnable alarmRun = new Runnable() {
        @Override
        public void run() {
            new RetrieveVitalSignsAlarmTask().execute();
            alarmHandler.postDelayed(this, 10000);
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

    // Async Task that retrieves all required data before creating the resident list (Need to get device address + cross ref user to their name to sort residents by caretaker)
    public class PreretrieveDataTask extends AsyncTask<Void, Void, String>{

        @Override
        protected void onPreExecute() { mainProgressBar.setVisibility(View.VISIBLE); }

        @Override
        protected String doInBackground(Void... voids) {
            String urlText = "https://braserver.mooo.com/edensight/api/residents/all";
            String staffUrlText = "https://braserver.mooo.com/edensight/api/users/all/";
            try {
                // Retrieve current user's name based on username
                URL staffUrl = new URL(staffUrlText);
                HttpsURLConnection staffUrlConnection = (HttpsURLConnection) staffUrl.openConnection();
                staffUrlConnection.setRequestMethod("GET");
                String encoded = java.util.Base64.getEncoder().encodeToString((username + ":" + password).getBytes(StandardCharsets.UTF_8));
                staffUrlConnection.setRequestProperty("Authorization", "Basic " + encoded);
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(staffUrlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String inline;
                    while ((inline = bufferedReader.readLine()) != null){
                        stringBuilder.append(inline).append("\n");
                    }
                    bufferedReader.close();

                    JSONArray jsonStaffData = new JSONArray(stringBuilder.toString());
                    for (int i = 0; i < jsonStaffData.length(); i++){
                        JSONObject staffObject = jsonStaffData.getJSONObject(i);
                        if (username.equals(staffObject.getString("uname"))){
                            caretakerName = staffObject.getString("name");
                            caretakerId = staffObject.getInt("_id");
                        }
                    }
                } finally {
                    staffUrlConnection.disconnect();
                }

                // Retrieve device mac address data
                URL url = new URL(urlText);
                HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Authorization", "Basic " + encoded);

                try{
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String inline;
                    while ((inline = bufferedReader.readLine()) != null){
                        stringBuilder.append(inline).append("\n");
                    }
                    bufferedReader.close();

                    JSONArray jsonRawData = new JSONArray(stringBuilder.toString());
                    for (int i = 0; i < jsonRawData.length(); i++){
                        JSONObject residentObject = jsonRawData.getJSONObject(i);
                        JSONObject caretakerObj = residentObject.getJSONObject("caretaker");
                        if (caretakerObj.getInt("_id") == caretakerId){
                            String macAddress = residentObject.getString("deviceAddr");
                            deviceAddressList.add(macAddress);
                        }
                    }
                } finally {
                    urlConnection.disconnect();
                }

                return null;
            } catch (Exception e){
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }
    }

    // Async Task that gets the residents details and their respective vital signs data
    public class RetrieveMainActivityTask extends AsyncTask<Void, Void, List<String>>{
        Context c;
        RecyclerView rv;
        EditText filterText;

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
        protected List<String> doInBackground(Void... voids) {
            String urlText = "https://braserver.mooo.com/edensight/api/residents/all";
            List<String> vitalSignsUrls = new ArrayList<>();
            for (int i = 0; i < deviceAddressList.size(); i++){
                if (deviceAddressList.get(i).equals("")){
                    continue;
                } else {
                    String urlData = "https://braserver.mooo.com/edensight/api/vitalsigns/" + deviceAddressList.get(i) + "/6";
                    vitalSignsUrls.add(urlData);
                }
            }

            try {
                URL url = new URL(urlText);
                HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                String encoded = java.util.Base64.getEncoder().encodeToString((username + ":" + password).getBytes(StandardCharsets.UTF_8));
                urlConnection.setRequestProperty("Authorization", "Basic " + encoded);

                List<HttpsURLConnection> vitalSignsConnections = new ArrayList<>();
                for (int i = 0; i < vitalSignsUrls.size(); i++){
                    URL vitalSignsUrl = new URL(vitalSignsUrls.get(i));
                    HttpsURLConnection vitalSignsUrlConnection = (HttpsURLConnection) vitalSignsUrl.openConnection();
                    vitalSignsUrlConnection.setRequestMethod("GET");
                    vitalSignsUrlConnection.setRequestProperty("Authorization", "Basic " + encoded);
                    vitalSignsConnections.add(vitalSignsUrlConnection);
                }

                try {
                    List<String> response = new ArrayList<>();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String inline;
                    while ((inline = bufferedReader.readLine()) != null){
                        stringBuilder.append(inline).append("\n");
                    }
                    bufferedReader.close();
                    response.add(stringBuilder.toString());

                    for (int i = 0; i < vitalSignsConnections.size(); i++){
                        BufferedReader vitalSignsReader = new BufferedReader(new InputStreamReader(vitalSignsConnections.get(i).getInputStream()));
                        StringBuilder vitalSignsBuilder = new StringBuilder();
                        String inline2;
                        while ((inline2 = vitalSignsReader.readLine()) != null){
                            vitalSignsBuilder.append(inline2).append("\n");
                        }
                        vitalSignsReader.close();
                        response.add(vitalSignsBuilder.toString());
                    }
                    return response;
                } finally {
                    urlConnection.disconnect();
                    for (int i = 0; i < vitalSignsConnections.size(); i++){
                        vitalSignsConnections.get(i).disconnect();
                    }
                }

            } catch (Exception e){
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(List<String> response){
            if (response.get(0) == null){
                mainProgressBar.setVisibility(View.GONE);
                searchFilter.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Please ensure that device has Internet connection.", Toast.LENGTH_SHORT).show();
            } else {
                mainProgressBar.setVisibility(View.GONE);
                String residentListText = response.get(0);

                try{
                    JSONArray jsonResidents = new JSONArray(residentListText);

                    for (int i = 0; i < jsonResidents.length(); i++) {
                        JSONObject object = jsonResidents.getJSONObject(i);
                        // Display residents assigned to caretaker only
                        JSONObject caretakerObj = object.getJSONObject("caretaker");
                        if (caretakerObj.getInt("_id") == caretakerId){
                            String conditionsLiteral = object.get("healthConditions").toString().replace("[", "").replace("]", "");
                            String[] conditions = conditionsLiteral.split(",");
                            if (conditions[0].equals("")){
                                conditions[0] = "- (No Known Health Conditions)";
                            }
                            String allergiesLiteral = object.get("allergies").toString().replace("[", "").replace("]", "");
                            String[] allergies = allergiesLiteral.split(",");
                            if (allergies[0].equals("")){
                                allergies[0] = "- (No Known Allergies)";
                            }
                            String medicationLiteral = object.get("medication").toString().replace("[", "").replace("]", "");
                            String[] medication = medicationLiteral.split(",");
                            if (medication[0].equals("")){
                                medication[0] = "- (No Known Medications)";
                            }

                            Resident resident = new Resident(object.get("name").toString(), object.get("dob").toString(), object.get("room").toString(), caretakerObj.getString("name"), object.get("gender").toString(), object.get("ic").toString(), object.get("nationality").toString(), object.get("bloodType").toString(), object.get("pNum").toString(), object.get("emergencyPNum").toString(), object.get("guardian").toString(), object.getDouble("weight"), object.getDouble("height"));
                            resident.setStreetAddr1(object.getString("streetAdd"));
                            resident.setStreetAddr2(object.getString("streetAdd2"));
                            resident.setPostal(object.getString("postal"));
                            resident.setCity(object.getString("city"));
                            resident.setState(object.getString("state"));
                            resident.setConditions(conditions);
                            resident.setAllergies(allergies);
                            resident.setMedication(medication);
                            resident.setDeviceAddr(object.getString("deviceAddr"));

                            if ((i + 1) >= response.size()){
                                resident.addBpmList("Nil");
                                resident.addSpo2List("Nil");
                                resident.addUpdateDateList("Nil");
                            } else {
                                JSONArray jsonVitalSignsArray = new JSONArray(response.get(i+1));
                                for (int j = 0; j < jsonVitalSignsArray.length(); j++){
                                    JSONObject jsonVitalSigns = jsonVitalSignsArray.getJSONObject(j);
                                    String bpm = jsonVitalSigns.getString("heartRate");
                                    String spo2 = jsonVitalSigns.getString("spO2");
                                    String updateDate = jsonVitalSigns.getString("dateTime");
                                    resident.addBpmList(bpm);
                                    resident.addSpo2List(spo2);
                                    resident.addUpdateDateList(updateDate);
                                }
                            }
                            residents.add(resident);
                        }

                    }

                    residentAdapter = new ResidentAdapter(residents, getApplicationContext(), username, password);
                    if (residentAdapter.getItemCount() == 0){
                        Toast.makeText(getApplicationContext(), "You appear to not have any residents under your care.", Toast.LENGTH_SHORT).show();
                    }
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

    // Async Task that works with a Runnable class to constantly check the most recent vital signs data of all resident to check if they are in an emergency
    public class RetrieveVitalSignsAlarmTask extends  AsyncTask<Void, Void, List<String>>{

        @Override
        protected List<String> doInBackground(Void... voids) {
            List<String> vitalSignsUrls = new ArrayList<>();
            for (int i = 0; i < deviceAddressList.size(); i++){
                if (deviceAddressList.get(i).equals("")){
                    continue;
                } else {
                    String urlData = "https://braserver.mooo.com/edensight/api/vitalsigns/" + deviceAddressList.get(i) + "/1";
                    vitalSignsUrls.add(urlData);
                }
            }
            try{
                List<HttpsURLConnection> vitalSignsConnections = new ArrayList<>();
                for (int i = 0; i < vitalSignsUrls.size(); i++){
                    URL vitalSignsUrl = new URL(vitalSignsUrls.get(i));
                    HttpsURLConnection vitalSignsUrlConnection = (HttpsURLConnection) vitalSignsUrl.openConnection();
                    vitalSignsUrlConnection.setRequestMethod("GET");
                    String encoded = java.util.Base64.getEncoder().encodeToString((username + ":" + password).getBytes(StandardCharsets.UTF_8));
                    vitalSignsUrlConnection.setRequestProperty("Authorization", "Basic " + encoded);
                    vitalSignsConnections.add(vitalSignsUrlConnection);
                }
                try {
                    for (int i = 0; i < vitalSignsConnections.size(); i++){
                        BufferedReader vitalSignsReader = new BufferedReader(new InputStreamReader(vitalSignsConnections.get(i).getInputStream()));
                        StringBuilder vitalSignsBuilder = new StringBuilder();
                        String inline2;
                        while ((inline2 = vitalSignsReader.readLine()) != null){
                            vitalSignsBuilder.append(inline2).append("\n");
                        }
                        vitalSignsReader.close();
                        JSONArray jsonVitalSignsArray = new JSONArray(vitalSignsBuilder.toString());
                        JSONObject vitalSignsObject = jsonVitalSignsArray.getJSONObject(0);
                        double bpm = vitalSignsObject.getDouble("heartRate");
                        double spo2 = vitalSignsObject.getDouble("spO2");
                        Resident resident = residents.get(i);
                        String name = resident.getName();

                        // Check if resident is in an emergency situation (bpm < 41.0 || bpm > 139.0) || spo2 < 95.0
                        if ((bpm < 41.0 || bpm > 139.0) || spo2 < 90.0){
                            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "aye")
                                    .setSmallIcon(R.drawable.eden_logo)
                                    .setContentTitle("Alert From EdenSight!")
                                    .setContentText("Resident " + name + " is having trouble. Please send help!")
                                    .setAutoCancel(true)
                                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                            Intent notificationIntent = new Intent(getApplicationContext(), ResidentDetailsActivity.class);
                            notificationIntent.putExtra("resident", resident);
                            notificationIntent.putExtra("username", username);
                            notificationIntent.putExtra("password", password);
                            notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                            builder.setContentIntent(pendingIntent);

                            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                            manager.notify(i, builder.build());
                        }
                    }

                } finally {
                    for (int i = 0; i < vitalSignsConnections.size(); i++){
                        vitalSignsConnections.get(i).disconnect();
                    }
                }
            } catch (Exception e){
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
            return vitalSignsUrls;
        }
    }

    // Notification Channel creation for notification box
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
