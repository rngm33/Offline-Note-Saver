package com.ml.notesaver.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes_table")
public class Notes {

    public Notes() {
//        //Empty Constructor
    }

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ColumnInfo(name = "title")
//    @NonNull
    private String title;

    @ColumnInfo(name = "notestext")
//    @NonNull
    private String notestext;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setNotestext(String notestext) {
        this.notestext = notestext;
    }

    public String getNotestext() {
        return notestext;
    }
}
