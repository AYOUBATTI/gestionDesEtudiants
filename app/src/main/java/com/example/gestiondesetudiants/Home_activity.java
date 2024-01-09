package com.example.gestiondesetudiants;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;

public class Home_activity extends AppCompatActivity {

    private EditText editTextNews;
    private ListView listViewNews;
    private Button addButton;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        editTextNews = findViewById(R.id.editTextNews);
        listViewNews = findViewById(R.id.listViewNews);
        addButton = findViewById(R.id.addButton);




        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user.getEmail().toString().equals("admin@admin.ma")){
            editTextNews.setVisibility(View.VISIBLE);
            addButton.setVisibility(View.VISIBLE);
        }

        // Récupérer les références des vues



        myRef = FirebaseDatabase.getInstance().getReference("Remarques");




        // Gérer le clic sur le bouton
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ajouter le texte saisi à la liste des actualités
                String newsText = editTextNews.getText().toString();
                UUID randomId = UUID.randomUUID();
                String idString = randomId.toString();

                myRef.child(idString).setValue(newsText).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Home_activity.this, "successful", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Home_activity.this, "unsuccessful", Toast.LENGTH_SHORT).show();
                        }
                        ;
                    }
                });

            Intent notificationIntent = new Intent(Home_activity.this, notifActivity2.class);
            startService(notificationIntent);
            }
        });




        final ArrayList<String> newsList = new ArrayList<>();
        final ArrayAdapter<String> newsAdapter = new ArrayAdapter<String>(this ,R.layout.list_ithem, newsList);
        listViewNews.setAdapter(newsAdapter);


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                    newsList.clear();
                    for (DataSnapshot data: snapshot.getChildren()){
                        newsList.add(data.getValue().toString());
                    }
                    newsAdapter.notifyDataSetChanged();

            }


            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(Home_activity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}


