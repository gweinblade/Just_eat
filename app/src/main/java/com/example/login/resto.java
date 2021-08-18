package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class resto extends AppCompatActivity {


    DatabaseReference ref;
    RecyclerView recyclerView;
    ArrayList<restomodel> list;
    restoadapter adap;
    ImageButton goback2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categoriefrag);
        goback2 = findViewById(R.id.goback2);

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));
        list = new ArrayList<restomodel>();
        Intent intent = getIntent();
        String path = intent.getExtras().getString("path");
        goback2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        ref = FirebaseDatabase.getInstance().getReference().child(path);

        /*list.add(new resto("pizzeria","4.5/5",R.drawable.d));
        adap = new restoadapter(restaurent.this,list);
        recyclerView.setAdapter(adap);*/
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    restomodel p = dataSnapshot1.getValue(restomodel.class);

                    list.add(p);
                }

                adap = new restoadapter(resto.this,list);
                recyclerView.setAdapter(adap);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(resto.this,"something wrong ",Toast.LENGTH_LONG).show();
            }
        });

    }

}