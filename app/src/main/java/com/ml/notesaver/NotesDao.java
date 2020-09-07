package com.ml.notesaver;

import com.ml.notesaver.model.Notes;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface NotesDao {

    @Query("Select * from notes_table ")
    LiveData<List<Notes>> getAllData();

    @Insert
    void addNotes(Notes notes);

    @Query("DELETE FROM notes_table")
    void deleteAll();

    @Delete
    void delete(Notes notes);

    @Update
    void updateNotes(Notes notes);
}
