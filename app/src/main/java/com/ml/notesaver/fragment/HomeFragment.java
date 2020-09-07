package com.ml.notesaver.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ml.notesaver.MainActivity;
import com.ml.notesaver.NotesViewModel;
import com.ml.notesaver.R;
import com.ml.notesaver.adaptor.NoteListAdaptor;
import com.ml.notesaver.model.Notes;
import com.ml.notesaver.utils.SwipeHelper;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeFragment extends Fragment {
    private RecyclerView rview;
    private NotesViewModel notesViewModel;
    private NoteListAdaptor ntAdaptor;
    private SwipeHelper swipeHelper;
    private List<Notes> ntlist = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rview = view.findViewById(R.id.rview);
        rview.setItemAnimator(new DefaultItemAnimator());
        rview.setLayoutManager(new LinearLayoutManager(getActivity()));
        rview.setNestedScrollingEnabled(false);
        ntAdaptor = new NoteListAdaptor(getContext());
        rview.setAdapter(ntAdaptor);

        rview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && MainActivity.bottomNavigationView.isShown()) {
                    MainActivity.bottomNavigationView.setVisibility(View.GONE);
                } else if (dy < 0) {
                    MainActivity.bottomNavigationView.setVisibility(View.VISIBLE);

                }
            }
        });

        swipeHelper = new SwipeHelper(getContext(), rview) {
            @Override
            public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
                underlayButtons.add(new SwipeHelper.UnderlayButton(
                        "Delete",
                        0,
                        Color.parseColor("#FF3C30"),
                        new UnderlayButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                ntAdaptor.delete(pos);
                            }
                        }));

                underlayButtons.add(new SwipeHelper.UnderlayButton(
                        "Edit",
                        0,
                        Color.parseColor("#FF9502"),
                        new UnderlayButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                ntAdaptor.editNotes(pos);
                            }
                        }));
            }
        };

        try {
            notesViewModel.getAllNotes().observe(this, new Observer<List<Notes>>() {
                @Override
                public void onChanged(List<Notes> notes) {
                    ntAdaptor.setNotes(notes);
                    ntlist=notes;
                }
            });
        } catch (Exception e) {

        }

    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Home");
    }
}
