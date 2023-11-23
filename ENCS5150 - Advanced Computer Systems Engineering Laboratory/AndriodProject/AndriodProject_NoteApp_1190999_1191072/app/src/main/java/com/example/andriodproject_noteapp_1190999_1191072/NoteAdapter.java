package com.example.andriodproject_noteapp_1190999_1191072;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NoteAdapter extends BaseAdapter {

    private Context context;
    private List<Note> notesList;
    private OnNoteClickListener onNoteClickListener;

    // Constructor
    public NoteAdapter(Context context, List<Note> notesList, OnNoteClickListener listener) {
        this.context = context;
        this.notesList = notesList;
        this.onNoteClickListener = listener;
    }

    @Override
    public int getCount() {
        return notesList.size();
    }

    @Override
    public Object getItem(int position) {
        return notesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_note, parent, false);
        }

        TextView noteTitle = convertView.findViewById(R.id.tvNoteTitle);
        Note note = notesList.get(position);
        noteTitle.setText(note.getTitle());

        convertView.setOnClickListener(v -> onNoteClickListener.onNoteClick(note));

        return convertView;
    }

    public interface OnNoteClickListener {
        void onNoteClick(Note note);
    }

    public void updateNotes(List<Note> newNotes) {
        this.notesList.clear();
        this.notesList.addAll(newNotes);
        this.notifyDataSetChanged();
    }

}
