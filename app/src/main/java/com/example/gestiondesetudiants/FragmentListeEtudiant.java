package com.example.gestiondesetudiants;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;

import android.app.AlertDialog;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class FragmentListeEtudiant extends Fragment {

    //ArrayList<Note> mNoteList;
    ArrayList<Note> mNoteList = new ArrayList<>();

    FloatingActionButton add_new_note;
    AlertDialog.Builder  alertbuilder,alertbuilderr ;
     AlertDialog alertDialog,alertDialogg;
    View view;
    FirebaseDatabase database;
    DatabaseReference mRef ;
    Button btnSaveNote;
    EditText title_edit_text ;
    EditText note_edit_text ;
    String title;
    String note;
    Note myNote; String id;
    ListView note_list_view;
    TextView titileTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_liste_etudiant, container, false);

        add_new_note = root.findViewById(R.id.add_new_note);
        note_list_view = root.findViewById(R.id.note_list_view);
        database = FirebaseDatabase.getInstance();

        //titileTextView = root.findViewById(R.id.titileTextView);

        mRef = database.getReference("Note");

        add_new_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAddNote();
            }
        });
        note_list_view.setOnItemClickListener((parent, view, position, id) -> {
            // Code à exécuter lorsque l'élément de la liste est cliqué
           Note notee= mNoteList.get(position);

           Intent noteIntent = new Intent(requireContext(), pageNoteActivity.class);
            noteIntent.putExtra("Title_key", notee.getTitle());
            noteIntent.putExtra("Note_key", notee.getNote());
           // noteIntent.putExtra("date_key", notee.getTimestamp());
            startActivity(noteIntent);
        });

        note_list_view.setOnItemLongClickListener((parent, view, position, id) -> {
            alertbuilderr = new AlertDialog.Builder(requireContext());
             view = getLayoutInflater().inflate(R.layout.delete_note, null);
            alertbuilderr.setView(view);
            alertDialogg = alertbuilderr.create();
            alertDialogg.show();
            Button btnDeleteNote = view.findViewById(R.id.btnDeleteNote);
            Button btnUpdateNote=view.findViewById(R.id.btnUpdateNote);
            EditText delete_title = view.findViewById(R.id.delete_title);
            EditText delete_note = view.findViewById(R.id.delete_note);
            Note mxyNote =mNoteList.get(position);

            //pour copie les donner sur les edit text
            delete_title.setText(mxyNote.getTitle());
            delete_note.setText(mxyNote.getNote());
            //pour update
            btnUpdateNote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Note myNote =mNoteList.get(position);
                    String id =myNote.getId();
                    DatabaseReference childRef=mRef.child(id);
                    String title =delete_title.getText().toString();
                    String note = delete_note.getText().toString();

                    Note afterUpdate= new Note(id,title,note,getCurrentDate());
                    childRef.setValue(afterUpdate);
                    alertDialogg.dismiss();
                }
            });
            //suprimer child
            btnDeleteNote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Note myNote =mNoteList.get(position);
                    mRef.child(myNote.getId()).removeValue();
                    alertDialogg.dismiss();
                }
            });





            return false;
        });



        return root;

    }

    @Override
    public void onStart() {
        super.onStart();
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mNoteList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Note note = snapshot.getValue(Note.class);
                    if (note != null) {
                        mNoteList.add(0,note);
                    }
                }

                NoteAdapter noteAdapter = new NoteAdapter(requireContext(), mNoteList);
                note_list_view.setAdapter(noteAdapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }

    public void showDialogAddNote() {
        alertbuilder = new AlertDialog.Builder(requireContext());
        view = getLayoutInflater().inflate(R.layout.add_note, null);
        title_edit_text = view.findViewById(R.id.title_edit_text);
        note_edit_text = view.findViewById(R.id.note_edit_text);
        btnSaveNote = view.findViewById(R.id.btnSaveNote);

        btnSaveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = title_edit_text.getText().toString();
                note = note_edit_text.getText().toString();

                if (!title.isEmpty() && !note.isEmpty()) {
                    id = mRef.push().getKey();
                    myNote = new Note(id, title, note,getCurrentDate());
                    mRef.child(id).setValue(myNote);
                    alertDialog.dismiss();

                    Toast.makeText(requireContext(), "Note added successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireContext(), "The fields are empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        alertbuilder.setView(view);
        alertDialog = alertbuilder.create();
        alertDialog.show();
    }

    public String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("EEE - d MMM yyyy - HH:mm:ss");
        return mdformat.format(calendar.getTime());
    }
}






//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_liste_etudiant, container, false);
//    }

//}