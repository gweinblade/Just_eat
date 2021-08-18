package com.example.login;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

public class foodpage extends AppCompatActivity {

    ImageView pic,fave;

    TextView numero,heure,adresse,name,rating;
    DatabaseReference ref;
    RecyclerView recyclerView;
    ArrayList<food> list;
    foodadapter adap;
    ImageButton goback;
    FirebaseAuth firebaseAuth;
    fav cartmodels;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foodpage);
        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser userss  = firebaseAuth.getCurrentUser();
        final String use= Objects.requireNonNull(userss).getUid();
        goback = findViewById(R.id.goback);

        recyclerView = findViewById(R.id.fooditemrecycler);
        numero = findViewById(R.id.phone);
        heure = findViewById(R.id.heure);
        name = findViewById(R.id.restonamepage);
        fave = findViewById(R.id.favebutton);

        rating = findViewById(R.id.restorating);
        adresse = findViewById(R.id.adresseresto);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));
        list = new ArrayList<food>();
        pic = findViewById(R.id.img);



adresse.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i = new Intent(foodpage.this,maps.class);
        startActivity(i);
    }
});

goback.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        finish();
    }
});
        Intent intent = getIntent();
        final String img = intent.getExtras().getString("image");
        final String adress = intent.getExtras().getString("adresse");
        final String number   = intent.getExtras().getString("numero");
        final String date = intent.getExtras().getString("heure");
        final String names = intent.getExtras().getString("name");
        final String id = intent.getExtras().getString("id");
        final String rat = intent.getExtras().getString("rating");
        final String path = intent.getExtras().getString("pathe");

        name.setText(names);
        rating.setText(rat);
        Picasso.get().load(img).into(pic);
        numero.setText(number);
        adresse.setText(adress);
        heure.setText(date);


        final DatabaseReference favcolor = FirebaseDatabase.getInstance().getReference().child("favoris");
        favcolor.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(use).child(id).exists())
                {
                    fave.setColorFilter(ContextCompat.getColor(getApplicationContext(),R.color.red));

                }
                else fave.setColorFilter(ContextCompat.getColor(getApplicationContext(),R.color.grey));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        adresse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(foodpage.this,maps.class);
                i.putExtra("name",names);
                startActivity(i);
            }
        });
        fave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final DatabaseReference favcolor2 = FirebaseDatabase.getInstance().getReference().child("favoris");
                favcolor2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(use).child(id).exists())
                        {

                            favcolor2.child(use).child(id).removeValue();
                            fave.setColorFilter(ContextCompat.getColor(getApplicationContext(),R.color.grey));
                        }
                        else {


                            cartmodels = new fav();
                            cartmodels.setImage(img);
                            cartmodels.setName(names);
                            cartmodels.setAdress(date);
                            cartmodels.setPhone(number);
                            cartmodels.setTime(date);
                            cartmodels.setID(id);
                            cartmodels.setPath(path);
                            cartmodels.setRating(rat);
                            Toast.makeText(getApplicationContext(),names+"  have been added to the favoris pages ",Toast.LENGTH_LONG).show();
                            favcolor2.child(use).child(id).setValue(cartmodels);
                            fave.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.red));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        /*SharedPreferences preferences=getSharedPreferences("MYPREFERENCENAME",MODE_PRIVATE);
        String path = preferences.getString("path", "");*/
        Toast.makeText(this,path,Toast.LENGTH_LONG).show();

        numero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent I=new Intent(Intent.ACTION_DIAL);
                I.setData(Uri.parse("tel:"+number));
                startActivity(I);
            }
        });

        ref = FirebaseDatabase.getInstance().getReference(path).child(id).child("food");


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    food p = dataSnapshot1.getValue(food.class);

                    list.add(p);
                }

                adap = new foodadapter(foodpage.this,list);
                recyclerView.setAdapter(adap);
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(foodpage.this,"something wrong ",Toast.LENGTH_LONG).show();
            }
        });







    }


}