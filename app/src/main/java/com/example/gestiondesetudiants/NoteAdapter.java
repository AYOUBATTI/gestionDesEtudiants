package com.example.gestiondesetudiants;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
public class NoteAdapter extends ArrayAdapter<Note> {

    public NoteAdapter(Context context, ArrayList<Note> notelist) {
        super(context,0,notelist);

    }
    @Override
    public View getView(int position, @Nullable View convertView, ViewGroup parent) {
        View view = convertView; // Reuse the convertView if available
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.note_layout, parent, false);
        }
        Note note=getItem(position);
        TextView titileTextView=view.findViewById(R.id.titileTextView);
        TextView timeTextView=view.findViewById(R.id.timeTextView);
        //titileTextView.setText(note.title);
        titileTextView.setText(note.getTitle());
        timeTextView.setText(note.getTimestamp());
        return view;
    }



}
