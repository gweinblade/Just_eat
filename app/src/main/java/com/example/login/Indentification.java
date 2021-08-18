package com.example.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Indentification extends AppCompatActivity {
    private TextInputLayout emailTV, passwordTV;

    private Button loginBtn;
    private ProgressBar progressBar;
    //CheckBox mremmember;
    static public SharedPreferences sp;
    //SharedPreferences preferences;
    //private static final String PREFS_NAME = "PrefsFile";
    static private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indentification);
        mAuth = FirebaseAuth.getInstance();
        //preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        initializeUI();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUserAccount();
            }
        });
    }
    public void retour(View view){
        Intent intent= new Intent(this,Activity_principale.class);
        startActivity(intent);
        finish();
    }
    private void loginUserAccount() {
        progressBar.setVisibility(View.VISIBLE);

        /*String email, password;
        password = passwordTV.getEditText().getText().toString().trim();
        email = emailTV.getEditText().getText().toString().trim();*/

        /*if(email.isEmpty())
        {
            emailTV.setError("Field can't be empty");

        }
        else
            {
                emailTV.setError(null);
                //emailTV.setErrorEnabled(false);

            }


        if(password.isEmpty())
        {
            passwordTV.setError("Field can't be empty");

        }
        else
        {
            passwordTV.setError(null);
            //emailTV.setErrorEnabled(false);

        }*/
if (!confirmeemail() | !confirmepassword())
  {
      Toast.makeText(getApplicationContext(), "thers a probleme", Toast.LENGTH_LONG).show();

      return;
  }



        mAuth.signInWithEmailAndPassword(emailTV.getEditText().getText().toString().trim(), passwordTV.getEditText().getText().toString().trim())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                                sp.edit().putBoolean("logged",true).apply();
                                Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);

                                Intent intent = new Intent(Indentification.this, IntroActivity.class);
                                startActivity(intent);
                                finish();
                            Activity_principale.getInstance().finish();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Login failed! Please try again later", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }      }
                });

}

private void initializeUI() {
        emailTV = findViewById(R.id.email);
        passwordTV = findViewById(R.id.password);
        loginBtn = findViewById(R.id.buttonlog);
        progressBar = findViewById(R.id.progressBar);
        //checkbox
        //mremmember = findViewById(R.id.remmember);
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
            passwordTV.setError(null);
return true;
        }
    }



}

