package com.example.edensight;

import androidx.appcompat.app.AppCompatActivity;

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

                } else { // Incorrect Login
                    Toast toast = Toast.makeText(getApplicationContext(),"Please provide the correct login details.", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }
}
