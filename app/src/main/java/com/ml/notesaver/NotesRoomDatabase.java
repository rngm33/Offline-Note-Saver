package com.ml.notesaver;

import android.content.Context;
import android.os.AsyncTask;

import com.ml.notesaver.model.Notes;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Notes.class}, version = 1)
public abstract class NotesRoomDatabase extends RoomDatabase {

    public abstract NotesDao notesDao();

    private static volatile NotesRoomDatabase INSTANCE;

    static NotesRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (NotesRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            NotesRoomDatabase.class, "note_db")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

//            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final NotesDao mDao;

        PopulateDbAsync(NotesRoomDatabase db) {
            mDao = db.notesDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {

            mDao.deleteAll();

            Notes notes = new Notes();
            notes.setTitle("title");
//            notes.setNotestext("notetext");
            mDao.addNotes(notes);

            return null;
        }
    }
}
