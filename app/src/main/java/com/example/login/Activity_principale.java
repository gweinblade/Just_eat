package com.example.login;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Activity_principale extends AppCompatActivity {
    private Button buttonup,buttonin;
    static Activity_principale activityA;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityA = this;


        check();

        setContentView(R.layout.activity_principale);
        Indentification.sp = getSharedPreferences("login",MODE_PRIVATE);
        if(Indentification.sp.getBoolean("logged",false)){
            Intent intent = new Intent(Activity_principale.this, home.class);
            startActivity(intent);
            finish();
        }



        buttonup = (Button) findViewById(R.id.prinup);
        buttonin = (Button) findViewById(R.id.prinin);

        buttonup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openactivity_inscription();
            }
        });
        buttonin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openactivity_indentification();
            }
        } );



    }


    public void openactivity_inscription(){
        Intent intent= new Intent(this,Inscription.class );
        startActivity(intent);


    }

    public static Activity_principale getInstance(){
        return   activityA;
    }
    public void openactivity_indentification(){
        Intent intent= new Intent(this,Indentification.class );
        startActivity(intent);


    }

    public  void check()
    {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) + ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},
                        100);

            }


        }
    }

    }


