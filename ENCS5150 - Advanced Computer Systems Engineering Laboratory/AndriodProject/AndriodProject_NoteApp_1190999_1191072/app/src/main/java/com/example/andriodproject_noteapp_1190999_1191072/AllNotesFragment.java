package com.example.andriodproject_noteapp_1190999_1191072;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

public class AllNotesFragment extends Fragment implements Searchable {

    private ListView listView;
    private DatabaseHelper databaseHelper;
    private List<Note> notesList;
    private NoteAdapter noteAdapter;
    private String loggedInUserEmail;
    private Button refreshButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_all_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = view.findViewById(R.id.notesListView);
        databaseHelper = new DatabaseHelper(getContext());
        refreshButton = view.findViewById(R.id.refreshButton);
        refreshButton.setOnClickListener(v -> {
            refreshNotes();
        });


        // Fetch email from SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        loggedInUserEmail = sharedPreferences.getString("LoggedInUserEmail", null);

        if (loggedInUserEmail != null) {
            notesList = databaseHelper.getAllNotesByEmail(loggedInUserEmail);
            noteAdapter = new NoteAdapter(getContext(), notesList, note -> {
                // Navigate to NoteDetailFragment
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
    }

    @Override
    public void onSearch(String query) {
        if (loggedInUserEmail != null) {
            if (query == null || query.isEmpty()) {
                // If search query is empty, fetch all notes
                notesList = databaseHelper.getAllNotesByEmail(loggedInUserEmail);
//                System.out.println("++++++++"+notesList);
            } else {
                // Otherwise, fetch notes based on the search query
                notesList = databaseHelper.searchNotes(query,loggedInUserEmail);
//                System.out.println(">>>>>>>"+notesList);
            }
            // Update the adapter with the new notesList
            noteAdapter.updateNotes(notesList);
            noteAdapter.notifyDataSetChanged();
        }
    }

    public void refreshNotes() {
        // Here you decide how to refresh.
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
            notesList = databaseHelper.getAllNotesByEmail(loggedInUserEmail);
        }
    }


}
