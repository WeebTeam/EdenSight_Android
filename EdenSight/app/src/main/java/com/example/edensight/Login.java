package com.example.edensight;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Login extends AppCompatActivity {

    TextView testTextView;
    EditText usernameInput, passwordInput;
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        testTextView = findViewById(R.id.test_textView);
        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginBtn = findViewById(R.id.loginBtn);

        // Check if login details are correct (Use staff [staff] for now until database is available)
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                // Login Correct
                if (usernameInput.getText().toString().equals("staff") && passwordInput.getText().toString().equals("staff")){
                    Toast toast = Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_SHORT);
                    toast.show();
                    // Moves to Main Activity
                    Intent loginIntent = new Intent (getApplicationContext(), MainActivity.class);
                    startActivity(loginIntent);
                    finish();

                } else { // Incorrect Login
                    Toast toast = Toast.makeText(getApplicationContext(),"Please provide the correct login details.", Toast.LENGTH_SHORT);
                    toast.show();
                }

                 */
                // Testing HTTP Connection with API
                new RetrieveLoginTask().execute();
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
        private Exception exception;

        protected void onPreExecute(){
            testTextView.setText("Not Sent Yet.");
        }

        @Override
        protected String doInBackground(Void... voids) {
            String urlText = "http://dataservice.accuweather.com/locations/v1/cities/search?apikey=n6D2aIqpQYd6trDQHw7qesXY1cppKgz5&q=kuching";

            try {
                URL url = new URL(urlText);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null){
                        stringBuilder.append(line).append("\n");
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
            // Cut to 50 characters
            response = response.substring(0, Math.min(response.length(), 50));
            testTextView.setText(response);
        }
    }
}
