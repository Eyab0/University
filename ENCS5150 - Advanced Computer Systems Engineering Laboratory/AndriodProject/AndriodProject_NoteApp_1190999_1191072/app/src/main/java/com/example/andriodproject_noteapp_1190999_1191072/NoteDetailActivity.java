package com.example.andriodproject_noteapp_1190999_1191072;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class NoteDetailActivity extends AppCompatActivity {

    public static final String EXTRA_NOTE = "extra_note";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        TextView noteTitle = findViewById(R.id.noteTitle);
        TextView noteContent = findViewById(R.id.noteContent);

        Note note = (Note) getIntent().getSerializableExtra(EXTRA_NOTE);
        if (note != null) {
            noteTitle.setText(note.getTitle());
            noteContent.setText(note.getContent());
        }
    }
}
