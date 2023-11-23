package com.example.andriodproject_noteapp_1190999_1191072;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class CategorizationFragment extends Fragment implements Searchable {

    private ListView listView;
    private DatabaseHelper databaseHelper;
    private List<Note> notesList;
    private NoteAdapter noteAdapter;
    private String loggedInUserEmail;
    private Button refreshButton;
    private Spinner labelSpinner;
    String selectedLabel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_categorization, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = view.findViewById(R.id.notesListView);
        refreshButton = view.findViewById(R.id.refreshButton);
        labelSpinner = view.findViewById(R.id.labelSpinner);

        databaseHelper = new DatabaseHelper(getContext());

        refreshButton.setOnClickListener(v -> {
            refreshNotes();
        });

        // Fetch email from SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        loggedInUserEmail = sharedPreferences.getString("LoggedInUserEmail", null);

        if (loggedInUserEmail != null) {
            notesList = databaseHelper.getAllNotesByEmail(loggedInUserEmail);

            // Load unique labels into the spinner
            HashSet<String> labels = new HashSet<>();
            for (Note note : notesList) {
                labels.add(note.getLabel());
            }

            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, new ArrayList<>(labels));
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            labelSpinner.setAdapter(spinnerAdapter);

            // Handle spinner item selection
            labelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selectedLabel = (String) parent.getItemAtPosition(position);
                    // Refresh your note list based on the selected label
                    notesList = databaseHelper.getNotesByLabelAndEmail(selectedLabel, loggedInUserEmail);
                    updateNotesListView();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            updateNotesListView();
        }
    }

    @Override
    public void onSearch(String query) {
        if (loggedInUserEmail != null) {
            if (query == null || query.isEmpty()) {
                notesList = databaseHelper.getAllNotesByEmail(loggedInUserEmail);
            } else {
                notesList = databaseHelper.searchNotes(query, loggedInUserEmail);
            }
            updateNotesListView();
        }
    }

    public void refreshNotes() {
        loadNotes();
        updateNotesListView();
    }

    private void updateNotesListView() {
        noteAdapter = new NoteAdapter(getContext(), notesList, note -> {
            NoteDetailFragment noteDetailFragment = new NoteDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("selectedNote", note);
            noteDetailFragment.setArguments(bundle);

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, noteDetailFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        listView.setAdapter(noteAdapter);
    }

    private void loadNotes() {
        if (loggedInUserEmail != null) {
            notesList = databaseHelper.getNotesByLabelAndEmail(selectedLabel, loggedInUserEmail);
        }
    }
}
