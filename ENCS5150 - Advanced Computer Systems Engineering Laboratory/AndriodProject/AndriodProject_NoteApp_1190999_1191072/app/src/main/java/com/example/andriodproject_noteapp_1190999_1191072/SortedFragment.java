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

import java.util.List;

public class SortedFragment extends Fragment implements Searchable {

    private ListView listView;
    private Spinner sortingSpinner;
    private DatabaseHelper databaseHelper;
    private List<Note> notesList;
    private NoteAdapter noteAdapter;
    private String loggedInUserEmail;
    private SharedPreferences sharedPreferences;
    private Button refreshButton;
    String savedSortingPreference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sorted, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = view.findViewById(R.id.notesListView);
        sortingSpinner = view.findViewById(R.id.sortSpinner);
        databaseHelper = new DatabaseHelper(getContext());
        refreshButton = view.findViewById(R.id.refreshButton);
        refreshButton.setOnClickListener(v -> {
            refreshNotes();
        });
        sharedPreferences = getActivity().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        loggedInUserEmail = sharedPreferences.getString("LoggedInUserEmail", null);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.sorting_methods, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortingSpinner.setAdapter(adapter);

        sortingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedMethod = (String) parent.getItemAtPosition(position);

                // Saving the sorting preference
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("SortingPreference", selectedMethod);
                editor.apply();

                if (loggedInUserEmail != null) {
                    if (selectedMethod.equals("Alphabetically")) {
                        notesList = databaseHelper.getAllNotesByEmailSortedAlphabetically(loggedInUserEmail);
                    } else if (selectedMethod.equals("Creation Date")) {
                        notesList = databaseHelper.getAllNotesByEmailSortedByDate(loggedInUserEmail);
                    }
                    updateNotesListView();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
             }
        });

        // Set the spinner's value based on the saved preference
        savedSortingPreference = sharedPreferences.getString("SortingPreference", "Alphabetically");
        sortingSpinner.setSelection(adapter.getPosition(savedSortingPreference));

        if (loggedInUserEmail != null) {
            if (savedSortingPreference.equals("Alphabetically")) {
                notesList = databaseHelper.getAllNotesByEmailSortedAlphabetically(loggedInUserEmail);
            } else {
                notesList = databaseHelper.getAllNotesByEmailSortedByDate(loggedInUserEmail);
            }
            updateNotesListView();
        }
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

    @Override
    public void onSearch(String query) {
        if (loggedInUserEmail != null) {
            if (query == null || query.isEmpty()) {
                notesList = databaseHelper.getAllNotesByEmail(loggedInUserEmail);
            } else {
                notesList = databaseHelper.searchNotes(query, loggedInUserEmail);
            }
            noteAdapter.updateNotes(notesList);
            noteAdapter.notifyDataSetChanged();
        }
    }

    public void refreshNotes() {
        loadNotes();
        updateNotesListView();
    }


    private void loadNotes() {
        if (loggedInUserEmail != null) {
            if (savedSortingPreference.equals("Alphabetically")) {
                notesList = databaseHelper.getAllNotesByEmailSortedAlphabetically(loggedInUserEmail);
            } else {
                notesList = databaseHelper.getAllNotesByEmailSortedByDate(loggedInUserEmail);
            }
        }
    }
}