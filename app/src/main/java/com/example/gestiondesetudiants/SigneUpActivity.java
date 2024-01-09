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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SigneUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signe_up);
        Button inscrire =findViewById(R.id.inscrire);
        EditText edt_nom_user =findViewById(R.id.edt_nom_user);
        EditText edt_email =findViewById(R.id.edt_emailLogin);
        EditText edt_password =findViewById(R.id.edt_passwordLogin);
        EditText edt_passwordconfirmation =findViewById(R.id.edt_passwordLoginconfirmation);
        // ...
        FirebaseAuth mAuth;
// Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        ProgressBar progressBar =findViewById(R.id.progressBar);
        inscrire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth mAuth;
                String email = edt_email.getText().toString();
                String password = edt_password.getText().toString();
                String passwordconfirmation = edt_passwordconfirmation.getText().toString();
                String name = edt_nom_user.getText().toString();
                if (!email.isEmpty() || !password.isEmpty() || !name.isEmpty() || !passwordconfirmation.isEmpty()) {
                    progressBar.setVisibility(View.VISIBLE);
                }

//                String email, password,name;
//                email = editTextEmail.getText().toString();
//                password = editTextPassword.getText().toString();
//                name = editTextFullname.getText().toString();
//                number = editTextNumber.getText().toString();
                if (name.isEmpty() || email.isEmpty() || password.isEmpty() || passwordconfirmation.isEmpty()) {
                    edt_nom_user.setError("Please enter your Full Name");
                    edt_email.setError("Please enter your Email");
                    edt_password.setError("Please enter your Password");
                    edt_passwordconfirmation.setError("Please verifier your Password");
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    edt_email.setError("Use email like L13*******@usms.ac.ma");
                } else if (name.length() > 20) {
                    edt_email.setError("Invalid Name");
                } else if (password.length() < 4) {
                    edt_password.setError("Invalid Password");
//                } else if (password !=passwordconfirmation) {
//                    edt_password.setError("vérifie Password");
//                    edt_passwordconfirmation.setError("vérifie Password");

            } else {
                    mAuth = FirebaseAuth.getInstance();

                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(SigneUpActivity.this, "createUserWithEmail:success.",
                                                Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);

                                        Intent intent = new Intent(SigneUpActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        finish();

                                        //  Sign in success, update UI with the signed-in user's information
                                        //FirebaseUser user = mAuth.getCurrentUser();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(SigneUpActivity.this, task.getException().toString(),
                                                Toast.LENGTH_LONG).show();

                                    }
                                }
                            });
                }
//                else{
//                    Toast.makeText(SigneUpActivity.this, "SVP Entrer email et le mot de passe",
//                            Toast.LENGTH_LONG).show();
//
//                }


            }

        });
    }

    public static class PDFs {
        String title, url, id;

        public PDFs(String title, String url) {
            this.title = title;
            this.url = url;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
