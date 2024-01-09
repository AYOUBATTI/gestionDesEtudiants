package com.example.gestiondesetudiants;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class pageNoteActivity extends AppCompatActivity {
TextView title_text_view;
TextView note_text_view ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_note);
        title_text_view=findViewById(R.id.title_text_view);
        note_text_view =findViewById(R.id.note_text_view);
        String title= getIntent().getExtras().getString("Title_key");
        String note = getIntent().getExtras().getString("Note_key");
        title_text_view.setText(title);
        note_text_view.setText(note);


    }
}