package com.ml.notesaver.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ml.notesaver.MainActivity;
import com.ml.notesaver.NotesViewModel;
import com.ml.notesaver.R;
import com.ml.notesaver.model.Notes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class EditNotesFragment extends Fragment implements Button.OnClickListener {
    private Button btnSave;
    private EditText etTitle, etNotes;
    private Bundle bundle;
    private NotesViewModel notesViewModel;
    private int id;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etTitle = view.findViewById(R.id.edit_title);
        etNotes = view.findViewById(R.id.edit_notes);
        view.findViewById(R.id.edit_btnSave).setOnClickListener(this);

        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);
        bundle = new Bundle();
        bundle = this.getArguments();

        etTitle.setText(bundle.getString("title", ""));
        etNotes.setText(bundle.getString("notes", ""));
        id = bundle.getInt("id");
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Edit Notes");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.edit_btnSave) {
            String title = etTitle.getText().toString().trim();
            String notestext = etNotes.getText().toString().trim();

            Notes notes = new Notes();

            notes.setTitle(title);
            notes.setNotestext(notestext);
            notesViewModel.updateNotes(notes);

            Toast.makeText(getContext(), "updated", Toast.LENGTH_SHORT).show();
            MainActivity.loadFragment(new HomeFragment());
        }
    }
}
