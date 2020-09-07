package com.ml.notesaver;

import android.app.Application;
import android.os.AsyncTask;

import com.ml.notesaver.model.Notes;

import java.util.List;

import androidx.lifecycle.LiveData;

class NotesRepository {

    private NotesDao notesDao;
    private LiveData<List<Notes>> mallNotes;

    NotesRepository(Application application) {
        NotesRoomDatabase db = NotesRoomDatabase.getDatabase(application);
        notesDao = db.notesDao();
        mallNotes = notesDao.getAllData();
    }

    LiveData<List<Notes>> getallNotes() {
        return mallNotes;
    }

    void insertNotes(Notes notes) {
        new insertAsyncTask(notesDao).execute(notes);
    }


    private static class insertAsyncTask extends AsyncTask<Notes, Void, Void> {
        private NotesDao nDao;

        public insertAsyncTask(NotesDao notesDao) {
            this.nDao = notesDao;
        }

        @Override
        protected Void doInBackground(final Notes... params) {
            nDao.addNotes(params[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<Notes, Void, Void> {
        private NotesDao notesDao;

        public updateAsyncTask(NotesDao ntDao) {
            this.notesDao = ntDao;
        }

        @Override
        protected Void doInBackground(Notes... params) {
            notesDao.updateNotes(params[0]);
            return null;
        }

    }

    private static class deleteAsyncTask extends AsyncTask<Notes, Void, Void> {
        private NotesDao ntDao;

        public deleteAsyncTask(NotesDao notesDao) {
            this.ntDao= notesDao;
        }

        @Override
        protected Void doInBackground(Notes... notes) {
            ntDao.delete(notes[0]);
            return null;
        }
    }

    void updateNotes(Notes notes) {
        new updateAsyncTask(notesDao).execute(notes);
    }

    public void deleteNote(Notes notes) {
        new deleteAsyncTask(notesDao).execute(notes);
    }
}
