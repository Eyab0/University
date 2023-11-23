package com.example.andriodproject_noteapp_1190999_1191072;

import java.io.Serializable;

public class Note implements Serializable {
    private int id;
    private String userEmail;  // This is the foreign key pointing to the user who created the note
    private String title;
    private String content;
    private String createdAt;
    private String updatedAt;
    private String label;
    private boolean isFavorite;

    // Constructors
    public Note() {
    }

    public Note(int id, String userEmail, String title, String content, String createdAt, String updatedAt, String label, boolean isFavorite) {
        this.id = id;
        this.userEmail = userEmail;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.label = label;
        this.isFavorite = isFavorite;
    }

    // Getter and Setter methods

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
