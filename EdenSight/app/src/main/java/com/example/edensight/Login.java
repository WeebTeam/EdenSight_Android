package com.example.edensight;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    EditText usernameInput, passwordInput;
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginBtn = findViewById(R.id.loginBtn);

        // Check if login details are correct (Use staff [staff] for now until database is available)
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
}
