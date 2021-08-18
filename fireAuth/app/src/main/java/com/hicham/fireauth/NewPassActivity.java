package com.hicham.fireauth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class NewPassActivity extends AppCompatActivity {
    EditText email;
    Button btnNewPass;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pass);

        email = (EditText) findViewById(R.id.uyeEmail);
        btnNewPass = (Button) findViewById(R.id.yeniParolaGonder);
        firebaseAuth = FirebaseAuth.getInstance();
        btnNewPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = email.getText().toString();
                if (TextUtils.isEmpty(Email)) {
                    Toast.makeText(getApplicationContext(), "Please fill e-mail", Toast.LENGTH_SHORT).show();
                    return;
                }
                firebaseAuth.sendPasswordResetEmail(Email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Password reset link was sent your email address", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Mail sending error", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }

        });
    }
}
