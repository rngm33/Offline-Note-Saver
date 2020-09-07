package com.ml.notesaver;

import android.app.Application;

import com.ml.notesaver.model.Notes;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class NotesViewModel extends AndroidViewModel {

    private LiveData<List<Notes>> mAllNotes;
    private NotesRepository notesRepository;

    public NotesViewModel(Application application) {
        super(application);
        notesRepository = new NotesRepository(application);
        mAllNotes = notesRepository.getallNotes();
    }

    public LiveData<List<Notes>> getAllNotes() {
        return mAllNotes;
    }

    public void insertNotes(Notes notes) {
        notesRepository.insertNotes(notes);
    }

    public void updateNotes(Notes notes){
        notesRepository.updateNotes(notes);
    }

    public void deleteNotes(Notes notes){
        notesRepository.deleteNote(notes);
    }
}
