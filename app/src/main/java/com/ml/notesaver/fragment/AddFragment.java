package com.ml.notesaver.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.ml.notesaver.MainActivity;
import com.ml.notesaver.NotesViewModel;
import com.ml.notesaver.R;
import com.ml.notesaver.model.Notes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class AddFragment extends Fragment {
    private TextInputLayout tiptitle, tipnotes;
    private TextInputEditText ettitile, etnotes;
    private MaterialButton btnsave;
    private NotesViewModel ntViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);
        initUI(view);
        ntViewModel = new ViewModelProvider(this).get(NotesViewModel.class);
        return view;
    }

    private void initUI(View view) {
        tiptitle = view.findViewById(R.id.tip1);
        tipnotes = view.findViewById(R.id.tip2);
        ettitile = view.findViewById(R.id.ettitle);
        etnotes = view.findViewById(R.id.etnotes);
        btnsave = view.findViewById(R.id.btnsave);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnsave.setOnClickListener(onClickListener);
    }

    private MaterialButton.OnClickListener onClickListener = new MaterialButton.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!validateTitle() | !validateNotes()) {
                return;
            }
            Notes notes = new Notes();
            notes.setTitle(ettitile.getText().toString().trim());
            notes.setNotestext(etnotes.getText().toString().trim());
            ntViewModel.insertNotes(notes);

            Toast.makeText(getContext(), "Notes Added Successfully :)", Toast.LENGTH_SHORT).show();

            MainActivity.loadFragment(new HomeFragment());
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Add Notes");
    }

    private boolean validateTitle() {
        String title = ettitile.getText().toString().trim();
        if (title.isEmpty()) {
            tiptitle.setError("Title can't be empty");
            return false;
        } else {
            tiptitle.setError("");
            tiptitle.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateNotes() {
        String notes = etnotes.getText().toString().trim();
        if (notes.isEmpty()) {
            tipnotes.setError("Notes can't be empty");
            return false;
        } else {
            tipnotes.setError("");
            tipnotes.setErrorEnabled(false);
            return true;
        }
    }
}
