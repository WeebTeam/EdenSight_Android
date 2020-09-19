package com.example.edensight;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.HttpsURLConnection;

public class Login extends AppCompatActivity {

    EditText usernameInput, passwordInput;
    Button loginBtn;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginBtn = findViewById(R.id.loginBtn);
        progressBar = findViewById(R.id.login_progressBar);

        // Check if login details are correct
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Establishes HTTPS connection to API to retrieve user data
                new RetrieveLoginTask(getApplicationContext(), usernameInput.getText().toString(), passwordInput.getText().toString()).execute();
            }
        });
    }

    // Override Back Button Result When User Log Out
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Closing App Confirmation");
        builder.setMessage("Are you sure you want to exit the application?");
        builder.setCancelable(true);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Close Application
                finish();
                System.exit(0);
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

    // Internal class used to retrieve data
    class RetrieveLoginTask extends AsyncTask<Void, Void, String>{
        private Context c;
        private String username, password;

        public RetrieveLoginTask(Context c, String username, String password){
            this.c = c;
            this.username = username;
            this.password = password;
        }

        protected void onPreExecute(){
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Void... voids) {
            String urlText = "https://braserver.mooo.com/edensight/api/auth/login";
            try {
                URL url = new URL(urlText);
                HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                String encoded = java.util.Base64.getEncoder().encodeToString((username + ":" + password).getBytes(StandardCharsets.UTF_8));
                urlConnection.setRequestProperty("Authorization", "Basic " + encoded);
                int responseCode = urlConnection.getResponseCode();
                return String.valueOf(responseCode);
            } catch (Exception e){
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(String response){
            progressBar.setVisibility(View.GONE);
            if (response.equals("200")){
                Toast.makeText(c, "Login Successful!", Toast.LENGTH_SHORT).show();
                // Moves to Main Activity
                Intent loginIntent = new Intent (getApplicationContext(), MainActivity.class);
                loginIntent.putExtra("username", username);
                loginIntent.putExtra("password", password);
                startActivity(loginIntent);
                finish();
            } else if(response.equals("401")){
                Toast.makeText(c, "Please provide the correct user details.", Toast.LENGTH_SHORT).show();
            } else { // Incorrect Login
                Toast.makeText(c, "Connection error. Server might be down or device does not have Internet connection.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
