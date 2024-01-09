package com.example.gestiondesetudiants;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AbsenceActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton btn_add;
    Context context;
    Button btn_view_listEtudiant,btn_date;
    TextView txt_date;
    Calendar calendar=Calendar.getInstance();
    String date;
    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MMMM-yyyy",new Locale("fr","MA"));
    DatabaseReference database= FirebaseDatabase.getInstance().getReference();
    ArrayList<dataAbsence> absenceArrayList=new ArrayList<>();
    RecyclerAbsence recyclerAbsence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absence);
        context=this;
        recyclerView=findViewById(R.id.recyclerView);
        btn_add=findViewById(R.id.btn_add);
        btn_date=findViewById(R.id.btn_date);
        txt_date=findViewById(R.id.txt_date);
        btn_view_listEtudiant=findViewById(R.id.btn_view_listEtudiant);

        date = simpleDateFormat.format(calendar.getTime()).toString();
        txt_date.setText(date);
        showData();

//        btn_view_listEtudiant.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(context,MainActivity.class);
//                startActivity(intent);
//
//            }
//        });


        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogAbsence absence =new DialogAbsence(context, date);
                absence.show(getSupportFragmentManager(),"absence");

            }
        });
        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year,month,dayOfMonth);
                        date = simpleDateFormat.format(calendar.getTime()).toString();
                        txt_date.setText(date);

                        showData();
                    }
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();

            }
        });

    }
////partie d affichage d'absence
    private void showData() {

        database.child("Liste d'absence").child(date).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                absenceArrayList.clear();
                for (DataSnapshot item:dataSnapshot.getChildren()){
                    dataAbsence absen =item.getValue(dataAbsence.class);
                        absen.setKey(item.getKey());
                        absenceArrayList.add(absen);
//                        if (simpleDateFormat.format(absen.getDate()).equals(simpleDateFormat.format(time))){
//                         absenceArrayList.add(absen);
//                        }
                }
                recyclerAbsence=new RecyclerAbsence(context,absenceArrayList, date);
                recyclerView.setAdapter(recyclerAbsence);
            }

            @Override
            public void onCancelled(DatabaseError error) {


            }
        });
    }
}