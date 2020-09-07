package com.ml.notesaver.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ml.notesaver.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class NotesDetailFragment extends Fragment {
    private TextView tvtitle, tvnotes;
    Bundle bundle = new Bundle();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_detail, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvtitle = view.findViewById(R.id.tvtitle_detail);
        tvnotes = view.findViewById(R.id.tvnotes_detail);
        bundle= getArguments();
        tvtitle.setText(bundle.getString("title", ""));
        tvnotes.setText(bundle.getString("notes", ""));
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Note Details");
    }
}
