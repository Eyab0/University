package com.example.andriodproject_noteapp_1190999_1191072;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CreateNoteActivity extends AppCompatActivity {

    EditText editTextTitle, editTextContent, editTextLabel;
    Button btnCreateNote;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextContent = findViewById(R.id.editTextContent);
        editTextLabel = findViewById(R.id.editTextLabel);
        btnCreateNote = findViewById(R.id.btnCreateNote);

        databaseHelper = new DatabaseHelper(this);

        // Retrieve email from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        String loggedInEmail = sharedPreferences.getString("LoggedInUserEmail", "");

        btnCreateNote.setOnClickListener(v -> {
            String title = editTextTitle.getText().toString();
            String content = editTextContent.getText().toString();
            String label = editTextLabel.getText().toString();

            long noteId = databaseHelper.addNote(loggedInEmail, title, content, label, false);

            if (noteId != -1) {
                Toast.makeText(CreateNoteActivity.this, "Note Created Successfully!", Toast.LENGTH_SHORT).show();
                finish();  // to close the activity and return to the previous one
            } else {
                Toast.makeText(CreateNoteActivity.this, "Error while creating note!", Toast.LENGTH_SHORT).show();
            }

        });
    }
}

