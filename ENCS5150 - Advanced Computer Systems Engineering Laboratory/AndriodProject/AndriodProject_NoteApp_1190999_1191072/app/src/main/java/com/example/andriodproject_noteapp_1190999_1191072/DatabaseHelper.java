package com.example.andriodproject_noteapp_1190999_1191072;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 9;  // updated version

    // Database Name
    private static final String DATABASE_NAME = "notes_app";

    // Table Names
    private static final String TABLE_USERS = "users";
    private static final String TABLE_NOTES = "notes";

    // Users Table - column names
    private static final String KEY_USER_EMAIL = "email"; // Primary key now
    private static final String KEY_USER_FIRST_NAME = "first_name";
    private static final String KEY_USER_LAST_NAME = "last_name";
    private static final String KEY_USER_PASSWORD = "password";

    // Notes Table - column names
    private static final String KEY_NOTE_ID = "note_id";
    private static final String KEY_NOTE_USER_EMAIL = "user_email";
    private static final String KEY_NOTE_TITLE = "title";
    private static final String KEY_NOTE_CONTENT = "content";
    private static final String KEY_NOTE_CREATED_AT = "created_at";
    private static final String KEY_NOTE_UPDATED_AT = "updated_at";
    private static final String KEY_NOTE_LABEL = "label";
    private static final String KEY_NOTE_IS_FAVORITE = "is_favorite";


    // Table Create Statements
    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS
            + "(" + KEY_USER_EMAIL + " TEXT PRIMARY KEY,"
            + KEY_USER_FIRST_NAME + " TEXT,"
            + KEY_USER_LAST_NAME + " TEXT,"
            + KEY_USER_PASSWORD + " TEXT" + ")";

    private static final String CREATE_TABLE_NOTES = "CREATE TABLE " + TABLE_NOTES
            + "(" + KEY_NOTE_ID + " INTEGER PRIMARY KEY,"
            + KEY_NOTE_USER_EMAIL + " TEXT,"
            + KEY_NOTE_TITLE + " TEXT,"
            + KEY_NOTE_CONTENT + " TEXT,"
            + KEY_NOTE_LABEL + " TEXT,"  // New label column
            + KEY_NOTE_CREATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
            + KEY_NOTE_UPDATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
            + KEY_NOTE_IS_FAVORITE + " INTEGER DEFAULT 0,"  // New column for favorite
            + "FOREIGN KEY (" + KEY_NOTE_USER_EMAIL + ") REFERENCES " + TABLE_USERS + "(" + KEY_USER_EMAIL + "))";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_NOTES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);  // Drop Notes table first because it references Users table
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // Adding new user
    public long addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USER_EMAIL, user.getEmail());
        values.put(KEY_USER_FIRST_NAME, user.getFirstName());
        values.put(KEY_USER_LAST_NAME, user.getLastName());
        values.put(KEY_USER_PASSWORD, user.getPassword());

        // Insert row
        long userId = db.insert(TABLE_USERS, null, values);

        db.close();
        return userId;
    }

    // Getting single user by email
    @SuppressLint("Range")
    public User getUserByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_USERS + " WHERE "
                + KEY_USER_EMAIL + " = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{email});

        if (cursor != null && cursor.moveToFirst()) {
            User user = new User();
            user.setEmail(cursor.getString(cursor.getColumnIndex(KEY_USER_EMAIL)));
            user.setFirstName(cursor.getString(cursor.getColumnIndex(KEY_USER_FIRST_NAME)));
            user.setLastName(cursor.getString(cursor.getColumnIndex(KEY_USER_LAST_NAME)));
            user.setPassword(cursor.getString(cursor.getColumnIndex(KEY_USER_PASSWORD)));

            cursor.close();
            return user;
        } else {
            return null;
        }

    }


    // Add a new note for a user
    public long addNote(String userEmail, String title, String content, String label, boolean isFavorite) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NOTE_USER_EMAIL, userEmail);
        values.put(KEY_NOTE_TITLE, title);
        values.put(KEY_NOTE_CONTENT, content);
        values.put(KEY_NOTE_LABEL, label);
        values.put(KEY_NOTE_IS_FAVORITE, isFavorite ? 1 : 0);  // Convert boolean to integer

        long noteId = db.insert(TABLE_NOTES, null, values);
        db.close();
        return noteId;
    }


    public int updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

//        if (user.getEmail() != null) {
//            values.put(KEY_USER_EMAIL, user.getEmail());
//        }
        if (user.getFirstName() != null) {
            values.put(KEY_USER_FIRST_NAME, user.getFirstName());
        }
        if (user.getLastName() != null) {
            values.put(KEY_USER_LAST_NAME, user.getLastName());
        }
        if (user.getPassword() != null) {
            values.put(KEY_USER_PASSWORD, user.getPassword());
        }

        // Update row
        return db.update(TABLE_USERS, values, KEY_USER_EMAIL + " = ?", new String[]{String.valueOf(user.getEmail())});
    }


    @SuppressLint("Range")
    public List<Note> getAllNotesByEmail(String userEmail) {
        List<Note> notes = new ArrayList<>();

        // SELECT query
        String selectQuery = "SELECT * FROM " + TABLE_NOTES + " WHERE " + KEY_NOTE_USER_EMAIL + " = ?";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{userEmail});

        // Loop through all rows and add to the list
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setId(cursor.getInt(cursor.getColumnIndex(KEY_NOTE_ID)));
                note.setUserEmail(cursor.getString(cursor.getColumnIndex(KEY_NOTE_USER_EMAIL)));
                note.setTitle(cursor.getString(cursor.getColumnIndex(KEY_NOTE_TITLE)));
                note.setContent(cursor.getString(cursor.getColumnIndex(KEY_NOTE_CONTENT)));
                note.setCreatedAt(cursor.getString(cursor.getColumnIndex(KEY_NOTE_CREATED_AT)));
                note.setUpdatedAt(cursor.getString(cursor.getColumnIndex(KEY_NOTE_UPDATED_AT)));
                note.setLabel(cursor.getString(cursor.getColumnIndex(KEY_NOTE_LABEL)));
                note.setFavorite(cursor.getInt(cursor.getColumnIndex(KEY_NOTE_IS_FAVORITE)) == 1);  // Convert integer to boolean
                // Add note to the notes list
                notes.add(note);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return notes;
    }

    public void deleteNote(int noteId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTES, KEY_NOTE_ID + " = ?", new String[]{String.valueOf(noteId)});
        db.close();
    }

    public int updateNoteFavoriteStatus(int noteId, boolean isFavorite) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NOTE_IS_FAVORITE, isFavorite ? 1 : 0);  // Convert boolean to integer

        // Update row
        return db.update(TABLE_NOTES, values, KEY_NOTE_ID + " = ?", new String[]{String.valueOf(noteId)});
    }

    public int updateNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NOTE_TITLE, note.getTitle());
        values.put(KEY_NOTE_CONTENT, note.getContent());
        values.put(KEY_NOTE_LABEL, note.getLabel());
        values.put(KEY_NOTE_IS_FAVORITE, note.isFavorite() ? 1 : 0);

        // updating row/
        return db.update(TABLE_NOTES, values, KEY_NOTE_ID + " = ?", new String[]{String.valueOf(note.getId())});
    }

    @SuppressLint("Range")
    public Note getNoteById(int noteId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_NOTES + " WHERE " + KEY_NOTE_ID + " = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(noteId)});

        if (cursor != null && cursor.moveToFirst()) {
            Note note = new Note();
            note.setId(cursor.getInt(cursor.getColumnIndex(KEY_NOTE_ID)));
            note.setUserEmail(cursor.getString(cursor.getColumnIndex(KEY_NOTE_USER_EMAIL)));
            note.setTitle(cursor.getString(cursor.getColumnIndex(KEY_NOTE_TITLE)));
            note.setContent(cursor.getString(cursor.getColumnIndex(KEY_NOTE_CONTENT)));
            note.setCreatedAt(cursor.getString(cursor.getColumnIndex(KEY_NOTE_CREATED_AT)));
            note.setUpdatedAt(cursor.getString(cursor.getColumnIndex(KEY_NOTE_UPDATED_AT)));
            note.setLabel(cursor.getString(cursor.getColumnIndex(KEY_NOTE_LABEL)));
            note.setFavorite(cursor.getInt(cursor.getColumnIndex(KEY_NOTE_IS_FAVORITE)) == 1); // Convert integer to boolean

            cursor.close();
            return note;
        } else {
            return null;
        }
    }

    @SuppressLint("Range")
    public List<Note> searchNotes(String searchTerm, String userEmail) {
        List<Note> notesList = new ArrayList<>();


        // SQL query to search for notes by title or content
        String selectQuery = "SELECT * FROM " + TABLE_NOTES
                + " WHERE " + KEY_NOTE_USER_EMAIL + " = ?"
                + " AND (" + KEY_NOTE_TITLE + " LIKE ? OR "
                + KEY_NOTE_CONTENT + " LIKE ?)";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{userEmail, "%" + searchTerm + "%", "%" + searchTerm + "%"});


//        System.out.println(cursor.toString());
        // loop through all rows and add to the list
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setId(cursor.getInt(cursor.getColumnIndex(KEY_NOTE_ID)));
                note.setUserEmail(cursor.getString(cursor.getColumnIndex(KEY_NOTE_USER_EMAIL)));
                note.setTitle(cursor.getString(cursor.getColumnIndex(KEY_NOTE_TITLE)));
                note.setContent(cursor.getString(cursor.getColumnIndex(KEY_NOTE_CONTENT)));
                note.setLabel(cursor.getString(cursor.getColumnIndex(KEY_NOTE_LABEL)));
                note.setCreatedAt(cursor.getString(cursor.getColumnIndex(KEY_NOTE_CREATED_AT)));
                note.setUpdatedAt(cursor.getString(cursor.getColumnIndex(KEY_NOTE_UPDATED_AT)));
                note.setFavorite(cursor.getInt(cursor.getColumnIndex(KEY_NOTE_IS_FAVORITE)) == 1);
//                System.out.println(note.toString() + "=============");

                // adding note to notes list
                notesList.add(note);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        // return notes list
        return notesList;
    }

    @SuppressLint("Range")
    public List<Note> getFavoriteNotes(String userEmail) {
        List<Note> favoriteNotes = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_NOTES + " WHERE " + KEY_NOTE_USER_EMAIL + " = ? AND " + KEY_NOTE_IS_FAVORITE + " = 1";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{userEmail});

        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setId(cursor.getInt(cursor.getColumnIndex(KEY_NOTE_ID)));
                note.setUserEmail(cursor.getString(cursor.getColumnIndex(KEY_NOTE_USER_EMAIL)));
                note.setTitle(cursor.getString(cursor.getColumnIndex(KEY_NOTE_TITLE)));
                note.setContent(cursor.getString(cursor.getColumnIndex(KEY_NOTE_CONTENT)));
                note.setLabel(cursor.getString(cursor.getColumnIndex(KEY_NOTE_LABEL)));
                note.setCreatedAt(cursor.getString(cursor.getColumnIndex(KEY_NOTE_CREATED_AT)));
                note.setUpdatedAt(cursor.getString(cursor.getColumnIndex(KEY_NOTE_UPDATED_AT)));
                note.setFavorite(cursor.getInt(cursor.getColumnIndex(KEY_NOTE_IS_FAVORITE)) == 1);
                favoriteNotes.add(note);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return favoriteNotes;
    }


    @SuppressLint("Range")
    public List<Note> getAllNotesByEmailSortedAlphabetically(String userEmail) {
        List<Note> notes = new ArrayList<>();

        // SELECT query with ORDER BY clause for alphabetical sorting based on the title
        String selectQuery = "SELECT * FROM " + TABLE_NOTES + " WHERE " + KEY_NOTE_USER_EMAIL + " = ? ORDER BY " + KEY_NOTE_TITLE + " ASC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{userEmail});

        // Loop through all rows and add to the list
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setId(cursor.getInt(cursor.getColumnIndex(KEY_NOTE_ID)));
                note.setUserEmail(cursor.getString(cursor.getColumnIndex(KEY_NOTE_USER_EMAIL)));
                note.setTitle(cursor.getString(cursor.getColumnIndex(KEY_NOTE_TITLE)));
                note.setContent(cursor.getString(cursor.getColumnIndex(KEY_NOTE_CONTENT)));
                note.setCreatedAt(cursor.getString(cursor.getColumnIndex(KEY_NOTE_CREATED_AT)));
                note.setUpdatedAt(cursor.getString(cursor.getColumnIndex(KEY_NOTE_UPDATED_AT)));
                note.setLabel(cursor.getString(cursor.getColumnIndex(KEY_NOTE_LABEL)));
                note.setFavorite(cursor.getInt(cursor.getColumnIndex(KEY_NOTE_IS_FAVORITE)) == 1);  // Convert integer to boolean

                // Add note to the notes list
                notes.add(note);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return notes;
    }

    @SuppressLint("Range")
    public List<Note> getAllNotesByEmailSortedByDate(String userEmail) {
        List<Note> notes = new ArrayList<>();

        // SELECT query with ORDER BY clause for sorting based on creation date and time
        String selectQuery = "SELECT * FROM " + TABLE_NOTES + " WHERE " + KEY_NOTE_USER_EMAIL + " = ? ORDER BY " + KEY_NOTE_CREATED_AT + " DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{userEmail});

        // Loop through all rows and add to the list
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setId(cursor.getInt(cursor.getColumnIndex(KEY_NOTE_ID)));
                note.setUserEmail(cursor.getString(cursor.getColumnIndex(KEY_NOTE_USER_EMAIL)));
                note.setTitle(cursor.getString(cursor.getColumnIndex(KEY_NOTE_TITLE)));
                note.setContent(cursor.getString(cursor.getColumnIndex(KEY_NOTE_CONTENT)));
                note.setCreatedAt(cursor.getString(cursor.getColumnIndex(KEY_NOTE_CREATED_AT)));
                note.setUpdatedAt(cursor.getString(cursor.getColumnIndex(KEY_NOTE_UPDATED_AT)));
                note.setLabel(cursor.getString(cursor.getColumnIndex(KEY_NOTE_LABEL)));
                note.setFavorite(cursor.getInt(cursor.getColumnIndex(KEY_NOTE_IS_FAVORITE)) == 1);  // Convert integer to boolean

                // Add note to the notes list
                notes.add(note);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return notes;
    }


    public List<String> getUniqueLabels(String userEmail) {
        List<String> labels = new ArrayList<>();

        String selectQuery = "SELECT DISTINCT " + KEY_NOTE_LABEL + " FROM " + TABLE_NOTES
                + " WHERE " + KEY_NOTE_USER_EMAIL + " = ?";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{userEmail});

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String label = cursor.getString(cursor.getColumnIndex(KEY_NOTE_LABEL));
                labels.add(label);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return labels;
    }

    @SuppressLint("Range")
    public List<Note> getNotesByLabelAndEmail(String label, String email) {
        List<Note> notesList = new ArrayList<>();

        // SQL query to get notes by label and email
        String selectQuery = "SELECT * FROM " + TABLE_NOTES
                + " WHERE " + KEY_NOTE_USER_EMAIL + " = ?"
                + " AND " + KEY_NOTE_LABEL + " = ?";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{email, label});

        // Loop through all rows and add to the list
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setId(cursor.getInt(cursor.getColumnIndex(KEY_NOTE_ID)));
                note.setUserEmail(cursor.getString(cursor.getColumnIndex(KEY_NOTE_USER_EMAIL)));
                note.setTitle(cursor.getString(cursor.getColumnIndex(KEY_NOTE_TITLE)));
                note.setContent(cursor.getString(cursor.getColumnIndex(KEY_NOTE_CONTENT)));
                note.setLabel(cursor.getString(cursor.getColumnIndex(KEY_NOTE_LABEL)));
                note.setCreatedAt(cursor.getString(cursor.getColumnIndex(KEY_NOTE_CREATED_AT)));
                note.setUpdatedAt(cursor.getString(cursor.getColumnIndex(KEY_NOTE_UPDATED_AT)));
                note.setFavorite(cursor.getInt(cursor.getColumnIndex(KEY_NOTE_IS_FAVORITE)) == 1);

                // Adding note to notes list
                notesList.add(note);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        // Return notes list
        return notesList;
    }


}