package com.example.andriodproject_noteapp_1190999_1191072;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    private EditText edtEmailSignUp, edtFirstNameSignUp, edtLastNameSignUp, edtPasswordSignUp, edtConfirmPasswordSignUp;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initializing views
        edtEmailSignUp = findViewById(R.id.edtEmailSignUp);
        edtFirstNameSignUp = findViewById(R.id.edtFirstNameSignUp);
        edtLastNameSignUp = findViewById(R.id.edtLastNameSignUp);
        edtPasswordSignUp = findViewById(R.id.edtPasswordSignUp);
        edtConfirmPasswordSignUp = findViewById(R.id.edtConfirmPasswordSignUp);
        Button btnCompleteSignUp = findViewById(R.id.btnCompleteSignUp);

        db = new DatabaseHelper(this);

        btnCompleteSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmailSignUp.getText().toString().trim();
                String firstName = edtFirstNameSignUp.getText().toString().trim();
                String lastName = edtLastNameSignUp.getText().toString().trim();
                String password = edtPasswordSignUp.getText().toString().trim();
                String confirmPassword = edtConfirmPasswordSignUp.getText().toString().trim();

                if (isValid(email, firstName, lastName, password, confirmPassword)) {
                    User user = new User();
                    user.setEmail(email);
                    user.setFirstName(firstName);
                    user.setLastName(lastName);
                    user.setPassword(password); // we should to hash it , but this is just an app for University so its OK Dr.Yazan :D

                    long userId = db.addUser(user);
                    if (userId != -1) {
                        Toast.makeText(SignUpActivity.this, "User Registered Successfully!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(SignUpActivity.this, "Error during registration. Try again.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private boolean isValid(String email, String firstName, String lastName, String password, String confirmPassword) {
        boolean valid = true;

        // Check if email is valid
        if (TextUtils.isEmpty(email) || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmailSignUp.setError("Enter a valid email address");
            valid = false;
        }

        // Check for valid first name
        if (firstName.length() < 3 || firstName.length() > 10) {
            edtFirstNameSignUp.setError("First name should be between 3 and 10 characters");
            valid = false;
        }

        // Check for valid last name
        if (lastName.length() < 3 || lastName.length() > 10) {
            edtLastNameSignUp.setError("Last name should be between 3 and 10 characters");
            valid = false;
        }

        // Password validation
        Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,12}$");
        if (!pattern.matcher(password).matches()) {
            edtPasswordSignUp.setError("Password must be 6-12 characters, and include a number, lowercase and uppercase letter.");
            valid = false;
        }

        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            edtConfirmPasswordSignUp.setError("Passwords do not match");
            valid = false;
        }

        return valid;
    }
}
