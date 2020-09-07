package com.ml.notesaver.adaptor;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ml.notesaver.MainActivity;
import com.ml.notesaver.NotesViewModel;
import com.ml.notesaver.R;
import com.ml.notesaver.fragment.EditNotesFragment;
import com.ml.notesaver.fragment.NotesDetailFragment;
import com.ml.notesaver.model.Notes;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class NoteListAdaptor extends RecyclerView.Adapter<NoteListAdaptor.MyViewHolder> {
    private Context ctx;
    private List<Notes> ntList;
    NotesViewModel notesViewModel;

    public NoteListAdaptor(Context ctxx) {
        this.ctx = ctxx;
        ntList = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.notes_list, parent, false);
        notesViewModel = new ViewModelProvider((ViewModelStoreOwner) ctx).get(NotesViewModel.class);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (ntList != null) {
            Notes notes = ntList.get(position);
            holder.setData(notes);
        } else {
            Toast.makeText(ctx, "NO data in DB :(", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        if (ntList != null) {
            return ntList.size();
        } else {
            return 0;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvtitle, tvnotes;
        private TextView tvid;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvtitle = itemView.findViewById(R.id.tvtitle);
            tvnotes = itemView.findViewById(R.id.tvnotes);
            tvid = new TextView(ctx);
            itemView.setOnClickListener(itemClickListener);
        }

        private View.OnClickListener itemClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotesDetailFragment notesDetailFragment = new NotesDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putString("title", tvtitle.getText().toString());
                bundle.putString("notes", tvnotes.getText().toString());
//                bundle.putInt("id", Integer.parseInt(tvid.getText().toString()));
                notesDetailFragment.setArguments(bundle);
                MainActivity.loadFragment(notesDetailFragment);
                Log.d("lola", tvid.getText().toString());
            }
        };

        public void setData(Notes notes) {
            tvtitle.setText(notes.getTitle());
            tvnotes.setText(notes.getNotestext());
            tvid.setText(notes.getId() + "");
        }
    }

    public void setNotes(List<Notes> newNotes) {
        if (ntList != null) {
            PostDiffCallBack postDiffCallBack = new PostDiffCallBack(ntList, newNotes);
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(postDiffCallBack);

            ntList.clear();
            ntList.addAll(newNotes);
            diffResult.dispatchUpdatesTo(this);
        } else {
            // first initialization
            ntList = newNotes;
        }
//        ntList = newNotes;
//        notifyDataSetChanged();
    }

    //remove notes from recyclerview
    public void delete(int pos) {
//        notesViewModel.deleteNotes(ntList.get(pos));
        delNote(pos);
        ntList.remove(pos);
        notifyItemRemoved(pos);
        notifyItemRangeChanged(pos, ntList.size());


    }

    //remove notes from db
    private void delNote(int pos) {
        notesViewModel.deleteNotes(ntList.get(pos));
    }

    public void editNotes(int pos) {
        EditNotesFragment editNotesFragment = new EditNotesFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", ntList.get(pos).getTitle());
        bundle.putString("notes", ntList.get(pos).getNotestext());
        bundle.putInt("id", ntList.get(pos).getId());
        editNotesFragment.setArguments(bundle);
        MainActivity.loadFragment(editNotesFragment);
    }

    //class to calculate diff between old and new list
    class PostDiffCallBack extends DiffUtil.Callback {
        private final List<Notes> oldNotes, newNotes;

        public PostDiffCallBack(List<Notes> oldNotes, List<Notes> newNotes) {
            this.oldNotes = oldNotes;
            this.newNotes = newNotes;
        }

        @Override
        public int getOldListSize() {
            return oldNotes.size();
        }

        @Override
        public int getNewListSize() {
            return newNotes.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return oldNotes.get(oldItemPosition).getId() == newNotes.get(newItemPosition).getId();
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return oldNotes.get(oldItemPosition).equals(newNotes.get(newItemPosition));
        }
    }
}
