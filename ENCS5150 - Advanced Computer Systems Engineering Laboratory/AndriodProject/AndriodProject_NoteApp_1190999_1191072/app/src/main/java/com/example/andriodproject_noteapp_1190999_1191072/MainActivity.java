package com.example.andriodproject_noteapp_1190999_1191072;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText edtEmailSignIn, edtPasswordSignIn;
    private CheckBox chkRememberMe;
    private Button btnSignIn, btnSignUp;

     private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtEmailSignIn = findViewById(R.id.edtEmailSignIn);
        edtPasswordSignIn = findViewById(R.id.edtPasswordSignIn);
        chkRememberMe = findViewById(R.id.chkRememberMe);
        btnSignIn = findViewById(R.id.btnSignInMain);
        btnSignUp = findViewById(R.id.btnSignUpMain);

        databaseHelper = new DatabaseHelper(this);

        // Load remembered email
        SharedPreferences prefs = getSharedPreferences("MY_PREFS", MODE_PRIVATE);
        String rememberedEmail = prefs.getString("remembered_email", "");
        edtEmailSignIn.setText(rememberedEmail);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInUser();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignUpActivity.class));
            }
        });
    }

    private void signInUser() {
        // Extract user inputs
        String email = edtEmailSignIn.getText().toString().trim();
        String password = edtPasswordSignIn.getText().toString().trim();

        // Validate inputs
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check credentials in the database
        User user = databaseHelper.getUserByEmail(email);
        if (user != null) {
            if (password.equals(user.getPassword())) {
                // Save email to shared preferences if "Remember Me" is checked
                if (chkRememberMe.isChecked()) {
                    SharedPreferences prefs = getSharedPreferences("MY_PREFS", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("remembered_email", email);
                    editor.apply();
                }
                SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("LoggedInUserEmail", email);  // `userEmail` is the email of the user who just logged in.
                editor.apply();

                Toast.makeText(this, "Sign in successful", Toast.LENGTH_SHORT).show();
                // Navigate to next activity or main dashboard of the app
                startActivity(new Intent(MainActivity.this, DashboardActivity.class));
//                Toast.makeText(this, "--------TODO-------", Toast.LENGTH_SHORT).show();

                finish();
            } else {
                Toast.makeText(this, "Incorrect password", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Email not found", Toast.LENGTH_SHORT).show();
        }
    }
}
