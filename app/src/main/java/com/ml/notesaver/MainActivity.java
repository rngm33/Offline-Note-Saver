package com.ml.notesaver;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ml.notesaver.fragment.AboutFragment;
import com.ml.notesaver.fragment.AddFragment;
import com.ml.notesaver.fragment.HomeFragment;
import com.ml.notesaver.model.Notes;
import com.ml.notesaver.utils.BottomNavigationBehaviour;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class MainActivity extends AppCompatActivity {
    private static FragmentManager fm;
    private static FragmentTransaction ft;
    public static BottomNavigationView bottomNavigationView;
    private ActionBar toolbar;
    private BottomNavigationBehaviour bottomNavigationBehaviour;
    //    private NotesViewModel notesViewModel;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottomnavigation);
        toolbar = getSupportActionBar();
        bottomNavigationBehaviour = new BottomNavigationBehaviour();
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnItemSelectedListener);

        // attaching bottom sheet behaviour - hide / show on scroll
//        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
//        layoutParams.setBehavior(bottomNavigationBehaviour);

        NotesViewModel notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);

        notesViewModel.getAllNotes().observe(this, new Observer<List<Notes>>() {
            @Override
            public void onChanged(List<Notes> notes) {
                for (int i = 0; i < notes.size(); i++) {
                    Log.d("array", "title : " + " " + notes.get(i).getTitle() + ""
                            + " notes: " + " " + notes.get(i).getNotestext());
                }

            }
        });

        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.add(R.id.frame, new HomeFragment()).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.itmabout:
                    fragment= new AboutFragment();
                    loadFragment(fragment);
                    return true;

                case R.id.itmadd:
                    fragment=new AddFragment();
                    loadFragment(fragment);
                    return true;

                case R.id.itmhome:
                    fragment=new HomeFragment();
                    loadFragment(fragment);
                    return true;

            }

            return false;
        }
    };

    public static void loadFragment(Fragment fragment) {
        ft = fm.beginTransaction();
        ft.replace(R.id.frame, fragment)
                .addToBackStack(null)
                .commit();
    }
}
