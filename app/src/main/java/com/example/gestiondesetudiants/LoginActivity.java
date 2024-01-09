package com.example.gestiondesetudiants;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button seconnecter =findViewById(R.id.se_connecter);
        TextView inscrire_vous =findViewById(R.id.inscrire_vous);
        EditText edt_emailLogin =findViewById(R.id.edt_emailLogin);
        EditText edt_passwordLogin =findViewById(R.id.edt_passwordLogin);

        FirebaseAuth mAuth;
// Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        ProgressBar progressBar =findViewById(R.id.progressBar);
        seconnecter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edt_emailLogin.getText().toString();
                String password =edt_passwordLogin.getText().toString();
                FirebaseAuth mAuth;
// Initialize Firebase Auth
                mAuth = FirebaseAuth.getInstance();
                if (!email.isEmpty() && !password.isEmpty()){

                    progressBar.setVisibility(View.VISIBLE);
                    }

                if ( email.isEmpty() || password.isEmpty()){

                    edt_emailLogin.setError("Please enter your Email");
                    edt_passwordLogin.setError("Please enter your Password");

                } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    edt_emailLogin.setError("Invalid Email");
                } else if (password.length() < 4) {
                    edt_passwordLogin.setError("Invalid Password");
                }else {
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            Intent intentToMain = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intentToMain);
                            finish();


                        }else{
                            Toast.makeText(LoginActivity.this, "Ther is no account with thin email", Toast.LENGTH_LONG).show();


                        }
                    }
                });
            }}});



        inscrire_vous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SigneUpActivity.class);
                startActivity(intent);
                finish();
            }});
    }
}