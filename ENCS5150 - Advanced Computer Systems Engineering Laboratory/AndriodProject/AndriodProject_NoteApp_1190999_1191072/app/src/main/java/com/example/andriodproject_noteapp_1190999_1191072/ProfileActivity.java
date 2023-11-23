package com.example.andriodproject_noteapp_1190999_1191072;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText passwordEditText;
    private Button saveChangesButton;

    private DatabaseHelper db;
    private User currentUser;
    private String originalEmail, originalFirstName, originalLastName, originalPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        emailEditText = findViewById(R.id.emailEditText);
        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        saveChangesButton = findViewById(R.id.saveChangesButton);

        db = new DatabaseHelper(this);

        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        String loggedInUserEmail = sharedPreferences.getString("LoggedInUserEmail", null);
//        System.out.println(loggedInUserEmail);

        if (loggedInUserEmail == null) {
            Toast.makeText(this, "Error fetching user details", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        DatabaseHelper db = new DatabaseHelper(this);
        currentUser = db.getUserByEmail(loggedInUserEmail);
        System.out.println(loggedInUserEmail);
        System.out.println("Current user :"+currentUser.toString());

        // Set the current user details
        if (currentUser != null) {
            emailEditText.setText(currentUser.getEmail());
            firstNameEditText.setText(currentUser.getFirstName());
            lastNameEditText.setText(currentUser.getLastName());
            // Don't set the password for security purposes

            originalEmail = currentUser.getEmail();
            originalFirstName = currentUser.getFirstName();
            originalLastName = currentUser.getLastName();
            originalPassword = currentUser.getPassword();
//            System.out.println("Current user :"+currentUser.toString());
        }

        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserProfile();
            }
        });
    }

    private void updateUserProfile() {
        String newEmail = emailEditText.getText().toString();
        String newFirstName = firstNameEditText.getText().toString().trim();
        String newLastName = lastNameEditText.getText().toString().trim();
        String newPassword = passwordEditText.getText().toString().trim();

        if (newFirstName.isEmpty() || newLastName.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }


        User updatedUser = new User();
        updatedUser.setEmail(originalEmail);

        // Ensure the email hasn't changed
        if (!newEmail.equals(updatedUser.getEmail())) {
            Toast.makeText(this, "Email cannot be changed!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!originalFirstName.equals(newFirstName)) {
            updatedUser.setFirstName(newFirstName);
            System.out.println(updatedUser.toString());
        }
        if (!originalLastName.equals(newLastName)) {
            updatedUser.setLastName(newLastName);
        }
        if (!originalPassword.equals(newPassword) && !newPassword.isEmpty()) {
            updatedUser.setPassword(newPassword);
//            System.out.println("++++++"+updatedUser.toString());

        }else {
            updatedUser.setPassword(originalPassword);
//            System.out.println("====="+updatedUser.toString());

        }

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.updateUser(updatedUser);
//        User usr = dbHelper.getUserByEmail(originalEmail);
//        System.out.println(">>>>>>>>>>>>"+usr.toString());
//        System.out.println("<<<<<<<<<<<<"+updatedUser.toString());

        Toast.makeText(this, "Profile updated successfully!", Toast.LENGTH_SHORT).show();
    }
}
