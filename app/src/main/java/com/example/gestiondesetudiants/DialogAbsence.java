package com.example.gestiondesetudiants;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class DialogAbsence extends DialogFragment {
    Context context;
    String date;
    Dialog dialog;
    public DialogAbsence(Context context, String date) {
        this.context = context;
        this.date = date;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(dialog!=null){
            Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }
    RecyclerView recyclerView;
    Button btn_sauvgarder;

    ArrayList<dataAbsence> absenceArrayList=new ArrayList<>();
    ArrayList<Note> noteArrayList=new ArrayList<>();
    AdapterItemAbsence adapterItemAbsence;
    DatabaseReference database= FirebaseDatabase.getInstance().getReference();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.forme_absence,container,false);
        recyclerView=v.findViewById(R.id.recyclerView);
        btn_sauvgarder=v.findViewById(R.id.btn_sauvgarder);
        dialog=getDialog();
        showData();
        

        return v;
    }

    private void showData() {
        database.child("Note").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot datasnapshot) {
              //  noteArrayList.clear();
              //  absenceArrayList.clear();
                for (DataSnapshot item : datasnapshot.getChildren()){
                    Note note=item.getValue(Note.class);
                    if (note !=null){
                        note.setId(item.getKey());
                        noteArrayList.add(note);
                        absenceArrayList.add(new dataAbsence(note.getTitle(),System.currentTimeMillis(),"no" ));
                    }
                }
                    adapterItemAbsence=new AdapterItemAbsence(context,absenceArrayList,noteArrayList,btn_sauvgarder,dialog, date);
                    recyclerView.setAdapter(adapterItemAbsence);
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }
}
