package com.example.andriodproject_noteapp_1190999_1191072;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NoteDetailFragment extends Fragment {
    private TextView noteContent, noteLabel;
    private Note currentNote;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_detail, container, false);

        TextView noteTitle = view.findViewById(R.id.noteTitle);
        noteContent = view.findViewById(R.id.noteContent);
        noteLabel = view.findViewById(R.id.noteLabel);

        Button btnEdit = view.findViewById(R.id.btn_edit_note);
        Button btnDelete = view.findViewById(R.id.btn_delete_note);
        Button btnSend = view.findViewById(R.id.btn_send_note);
        Button btnAddFavorite = view.findViewById(R.id.btn_add_favorite);
        ImageView imgHeart = view.findViewById(R.id.imgHeart);

        // Extract the Note object
        if (getArguments() != null) {
            currentNote = (Note) getArguments().getSerializable("selectedNote");

            if (currentNote != null) {
                noteTitle.setText(currentNote.getTitle());
                noteContent.setText(currentNote.getContent());
                noteLabel.setText(currentNote.getLabel());
            }
        }

        if (currentNote.isFavorite()) {
            btnAddFavorite.setText("Remove from Favorite");
        } else {
            btnAddFavorite.setText("Add to Favorite");
        }


        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), EditNoteActivity.class);


            intent.putExtra("NOTE_ID", currentNote.getId());

            startActivity(intent);
        });

        btnDelete.setOnClickListener(v -> {
            // Show confirmation dialog
            new AlertDialog.Builder(getContext())
                    .setTitle("Delete Note")
                    .setMessage("Are you sure you want to delete this note?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        // Delete the note from the database
                        DatabaseHelper db = new DatabaseHelper(getContext());
                        db.deleteNote(currentNote.getId());

                        // Close the fragment
                        getActivity().getSupportFragmentManager().popBackStack();
                    })
                    .setNegativeButton("No", null)
                    .show();
        });

        btnSend.setOnClickListener(v -> {
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setType("text/plain");
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, currentNote.getTitle());
            emailIntent.putExtra(Intent.EXTRA_TEXT, currentNote.getContent());
            startActivity(Intent.createChooser(emailIntent, "Send Email"));
        });

        btnAddFavorite.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                // Make the heart image visible
                imgHeart.setVisibility(View.VISIBLE);
                imgHeart.setAlpha(1f);

                // Load the animation
                Animation scaleAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.scale_heart);

                // Add a listener to set the ImageView to invisible again after the animation
                scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                     }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        imgHeart.setVisibility(View.INVISIBLE);  // Hide the heart image after the animation ends
                        imgHeart.invalidate();
                        imgHeart.setAlpha(0f);

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // No action needed on animation repeat
                    }
                });

                // Start the animation
                imgHeart.startAnimation(scaleAnimation);



                if (currentNote.isFavorite()) {
                    currentNote.setFavorite(false);
                    Toast.makeText(getActivity(), "Removed from favorites", Toast.LENGTH_SHORT).show();
                    btnAddFavorite.setText("Add to Favorite");
                } else {
                    currentNote.setFavorite(true);
                    Toast.makeText(getActivity(), "Added to favorites", Toast.LENGTH_SHORT).show();
                    btnAddFavorite.setText("Remove from Favorite");
                }

                // Update the note in the database
                DatabaseHelper db = new DatabaseHelper(getActivity());
                 db.updateNoteFavoriteStatus(currentNote.getId(), currentNote.isFavorite());
            }
        });


        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FloatingActionButton backButton = view.findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            if (getActivity() != null) {
                getActivity().onBackPressed();
            }
        });
    }


}
