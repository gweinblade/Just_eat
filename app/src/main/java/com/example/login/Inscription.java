package com.example.login;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.login.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class Inscription extends AppCompatActivity {
    private TextInputLayout emailTV, passwordTV,nameTV;
    private Button regBtn;
    private ProgressBar progressBar;
User user;
    private FirebaseAuth mAuth;
    FirebaseAuth firebaseAuth;

    private DatabaseReference reff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);
        mAuth = FirebaseAuth.getInstance();

        initializeUI();

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewUser();
            }
        });
    }

    public void retour(View view){
        Intent intent= new Intent(this,Activity_principale.class);
        startActivity(intent);
        finish();

    }
    private void registerNewUser() {
        progressBar.setVisibility(View.VISIBLE);

        final String email, password,name;
         name = nameTV.getEditText().getText().toString().trim();
        email = emailTV.getEditText().getText().toString().trim();
        password = passwordTV.getEditText().getText().toString().trim();


        if (!confirmeemail() | !confirmepassword()| !confirmename())
        {
            Toast.makeText(getApplicationContext(), "There is a probleme", Toast.LENGTH_LONG).show();

            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                             FirebaseUser ids  = FirebaseAuth.getInstance().getCurrentUser();
                            String id= Objects.requireNonNull(ids).getUid();
                            reff = FirebaseDatabase.getInstance().getReference().child("Users");
                            user = new User();
                            user.setNumber("");
                            user.setAdresse("");
                            user.setEmail(email);
                            user.setPass(password);
                            user.setName(name);
                            user.setPicture("");
                            user.setID(id);

                            reff.child(id).setValue(user);


                            Intent intent = new Intent(Inscription.this, IntroActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Registration failed! Please try again later", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }

    private void initializeUI() {
        emailTV = findViewById(R.id.emailup);
        passwordTV = findViewById(R.id.passwordup);
        nameTV = findViewById(R.id.name);
        regBtn = findViewById(R.id.buttonreg);
        progressBar = findViewById(R.id.progressBar);
    }
    private boolean confirmeemail()
    {
        String email = emailTV.getEditText().getText().toString().trim();


        if(email.isEmpty())
        {
            emailTV.setError("Field can't be empty");
            return false;
        }
        else
        {
            emailTV.setError(null);
            return true;
        }
    }
    private boolean confirmename()
    {
        String name = nameTV.getEditText().getText().toString().trim();


        if(name.isEmpty())
        {
            nameTV.setError("Field can't be empty");
            return false;
        }
        else
        {
            nameTV.setError(null);
            return true;
        }
    }
    private boolean confirmepassword()
    {
        String password = passwordTV.getEditText().getText().toString().trim();

        if(password.isEmpty())
        {
            passwordTV.setError("Field can't be empty");
            return false;
        }
        else
        {
            if(passwordTV.getEditText().getText().length()>=6)
            {
            passwordTV.setError(null);
            return true;}
            else {passwordTV.setError("Enter a combination of at least six numbers, letters and punctuation marks !!");
                return false;}
        }
    }
}
