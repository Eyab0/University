package com.example.andriodproject_noteapp_1190999_1191072;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditNoteActivity extends Activity {

    private EditText edtTitle, edtContent;
    private Button btnSave;
    private DatabaseHelper dbHelper;
    private Note currentNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        edtTitle = findViewById(R.id.edtTitle);
        edtContent = findViewById(R.id.edtContent);
        btnSave = findViewById(R.id.btnSave);

        dbHelper = new DatabaseHelper(this);

        int noteId = getIntent().getIntExtra("NOTE_ID", -1);
        if (noteId != -1) {
            currentNote = dbHelper.getNoteById(noteId);
            edtTitle.setText(currentNote.getTitle());
            edtContent.setText(currentNote.getContent());
        } else {
            finish(); // exit if there's an error or no note ID is provided
        }

        btnSave.setOnClickListener(v -> {
            String updatedTitle = edtTitle.getText().toString().trim();
            String updatedContent = edtContent.getText().toString().trim();

            if (!TextUtils.isEmpty(updatedTitle) && !TextUtils.isEmpty(updatedContent)) {
                currentNote.setTitle(updatedTitle);
                currentNote.setContent(updatedContent);

                int result = dbHelper.updateNote(currentNote);

                if (result > 0) {
                    Toast.makeText(EditNoteActivity.this, "Note updated successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditNoteActivity.this, "Error updating note!", Toast.LENGTH_SHORT).show();
                }

                finish();
            } else {
                Toast.makeText(EditNoteActivity.this, "Title and content cannot be empty!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
