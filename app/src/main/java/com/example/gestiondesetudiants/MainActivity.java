package com.example.gestiondesetudiants;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    ImageView logout,note,absence,home,cours;


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logout = findViewById(R.id.logout);
         note =findViewById(R.id.note);
        absence=findViewById(R.id.absence);
        home = findViewById(R.id.home);
        cours=findViewById(R.id.cours);


    note.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
         // Intent intent = new Intent(MainActivity.this, NoteActivity.class);
            Intent intent = new Intent(MainActivity.this, TabNavigation.class);
            startActivity(intent);
        }
    });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intent intent = new Intent(MainActivity.this, NoteActivity.class);
                Intent intent = new Intent(MainActivity.this, Home_activity.class);
                startActivity(intent);
            }
        });
        cours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intent intent = new Intent(MainActivity.this, NoteActivity.class);
                Intent intent = new Intent(MainActivity.this, coursActivity2.class);
                startActivity(intent);
            }
        });



    absence.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        // Intent intent = new Intent(MainActivity.this, NoteActivity.class);
        Intent intent2 = new Intent(MainActivity.this, AbsenceActivity.class);
        startActivity(intent2);
    }
});

    logout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {


            // Créez la boîte de dialogue d'alerte
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);

            // Définissez le message de la boîte de dialogue d'alerte
            dialogBuilder.setMessage("Do you want to close this application?")
                    // Si la boîte de dialogue est non-annulable
                    .setCancelable(false)
                    // Texte et action du bouton positif
                    .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            mAuth.signOut();
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);

                            finish();
                        }
                    })
                    // Texte et action du bouton négatif
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            // Créez la boîte de dialogue
            AlertDialog alert = dialogBuilder.create();
            // Définissez le titre de la boîte de dialogue d'alerte
            alert.setTitle("Alert Logout");
            // Affichez la boîte de dialogue d'alerte
            alert.show();












        }
    });



}

//    @Override
//    protected void onStart() {
//        super.onStart();
//        FirebaseAuth mAuth;
//// Initialize Firebase Auth
//        mAuth = FirebaseAuth.getInstance();
//        if(mAuth.getCurrentUser() != null){
//            Intent intentToLogin =new Intent(MainActivity.this, LoginActivity.class);
//            startActivity(intentToLogin);
//
//    }
//}



}