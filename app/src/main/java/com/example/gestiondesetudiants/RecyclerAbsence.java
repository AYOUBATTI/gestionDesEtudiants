package com.example.gestiondesetudiants;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RecyclerAbsence extends RecyclerView.Adapter<RecyclerAbsence.AbsenceViewHolder> {
    Context context;
    ArrayList<dataAbsence> absenceArrayList;
    String date;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    public RecyclerAbsence(Context context, ArrayList<dataAbsence> absenceArrayList, String date) {
        this.context = context;
        this.absenceArrayList = absenceArrayList;
        this.date = date;
    }

    @Override
    public AbsenceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_absence,parent,false);
        return new AbsenceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AbsenceViewHolder holder, int position) {
        holder.viewBind(absenceArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return absenceArrayList.size();
    }
    public class AbsenceViewHolder extends RecyclerView.ViewHolder {
        TextView txt_name;
        CheckBox checkbox;

        public AbsenceViewHolder(View itemView) {
            super(itemView);
            txt_name =itemView.findViewById(R.id.txt_name);
            checkbox =itemView.findViewById(R.id.checkbox);
        }

        public void viewBind(dataAbsence dataAbsence) {
            if (dataAbsence.getIsAbsence().equals("yes")){
                checkbox.setChecked(true);
            }else{checkbox.setChecked(false);}

            database.child("Liste d'absence").child(date).child(dataAbsence.getKey()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot datasnapshot) {
                Note note=datasnapshot.getValue(Note.class);
                if (note!=null){
                    note.setId(datasnapshot.getKey());
                    txt_name.setText("name :"+dataAbsence.getClé_étudiant());
                }
                }

                @Override
                public void onCancelled(DatabaseError error) {

                }
            });
        }
    }
}
