//package com.example.gestiondesetudiants;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.annotation.SuppressLint;
//import android.app.AlertDialog;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//
//public class NoteActivity extends AppCompatActivity {
//
//    //ArrayList<Note> mNoteList;
//    ArrayList<Note> mNoteList = new ArrayList<>();
//
//    FloatingActionButton add_new_note;
//    AlertDialog.Builder  alertbuilder ;
//    AlertDialog alertDialog;
//    View view;
//    FirebaseDatabase database;
//    DatabaseReference mRef ;
//    Button btnSaveNote;
//    EditText title_edit_text ;
//    EditText note_edit_text ;
//    String title;
//    String note;
//    Note myNote; String id;
//    ListView note_list_view;
//    TextView titileTextView;
//
//    @SuppressLint("MissingInflatedId")
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_note);
//        add_new_note =findViewById(R.id.add_new_note);
//        note_list_view=findViewById(R.id.note_list_view);
//        database =FirebaseDatabase.getInstance();
//        btnSaveNote=findViewById(R.id.btnSaveNote);
//        titileTextView=findViewById(R.id.titileTextView);
//
//
//        mRef =database.getReference("Note");
//
//        add_new_note.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //     Toast.makeText(MainActivity.this, "action bar is clicked", Toast.LENGTH_SHORT).show();
//                showDialogAddNote();
//
//            }
//        });
//
//    }
//
//    //list view lire
//
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        mRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                mNoteList.clear(); // Effacer la liste existante
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
//
//                    Note note =snapshot.getValue(Note.class);
//                    if (note != null) {
//                        mNoteList.add(note);}
//                }
//
//                //mNoteList.addAll(mNoteList); // Ajouter les nouvelles données
//                NoteAdapter noteAdapter=new NoteAdapter(getApplicationContext(),mNoteList);
//                note_list_view.setAdapter(noteAdapter);
//
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });
//    }
//
//    // add button ecrire
//    public void showDialogAddNote(){
//        alertbuilder = new AlertDialog.Builder(NoteActivity.this);
//        view = getLayoutInflater().inflate(R.layout.add_note, null);
//        title_edit_text =view.findViewById(R.id.title_edit_text);
//        note_edit_text =view.findViewById(R.id.note_edit_text);
//        btnSaveNote=view.findViewById(R.id.btnSaveNote);
//        btnSaveNote.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                title = title_edit_text.getText().toString();
//                note = note_edit_text.getText().toString();
//
//                if (!title.isEmpty() && !note.isEmpty()){
//                    id=mRef.push().getKey();
//                    myNote = new Note(id,title,note);
//                    mRef.child(id).setValue(myNote);
//                    alertDialog.dismiss();
//
//
//
//                    Toast.makeText(NoteActivity.this, "Note add successful", Toast.LENGTH_SHORT).show();
//                }else {
//                    Toast.makeText(NoteActivity.this, "les case est vide", Toast.LENGTH_SHORT).show();
//
//                }
//
//
//            }
//        });
//
//        alertbuilder.setView(view);
//        alertDialog = alertbuilder.create();
//        alertDialog.show();
//    }
//    public String getCurrentDate(){
//        Calendar calendar=Calendar.getInstance();
//        SimpleDateFormat mdformat= new SimpleDateFormat("EEEE hh:mm a");
//        //String strDate= mdformat.format(calendar.getTime());
//
//        return mdformat.format(calendar.getTime());
//    }
//}
