package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class editprofil extends AppCompatActivity {
    String EXTRA_USER = "user_name";
    String EXTRA_PHONE = "user_phone";
    String EXTRA_ADDRESS = "user_address";
    FirebaseAuth firebaseAuth;
    ImageButton goback;
    DatabaseReference reff,refff,ref;
    EditText user;
    EditText phone;
    EditText address;
    Button validate;
    PlacesClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofil);
        user=  findViewById(R.id.enterUser);
        phone=  findViewById(R.id.mbr);
        validate=  findViewById(R.id.Validate);
        goback = findViewById(R.id.gobackpro);

        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //initialize places
        if(!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), "AIzaSyAmufG4GY4XoOGnXHQAX95DQhbnGwaJAdM");
        }
    //initialize editview
        /* address.setFocusable(false);
        address.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
            //initialize place filed
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS,

                        Place.Field.LAT_LNG,Place.Field.NAME);

                Intent i = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN,

                        fieldList).build(editprofil.this);
                //start activity result
                startActivityForResult(i,100);

            }
        });*/
      /* client= Places.createClient(this);

       final AutocompleteSupportFragment autocompleteSupportFragment =
               (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

       autocompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.ADDRESS,

               Place.Field.LAT_LNG,Place.Field.NAME));

       autocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
           @Override
           public void onPlaceSelected(@NonNull Place place) {
               final String name = place.getName();
               Toast.makeText(getApplicationContext(),name,Toast.LENGTH_LONG).show();
           }

           @Override
           public void onError(@NonNull Status status) {

           }
       });*/

        validate.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {

                firebaseAuth = FirebaseAuth.getInstance();
                final FirebaseUser userss  = firebaseAuth.getCurrentUser();
                String users= Objects.requireNonNull(userss).getUid();
                reff = FirebaseDatabase.getInstance().getReference("Users").child(users);
                if(!TextUtils.isEmpty(user.getText().toString()))reff.child("name").setValue(user.getText().toString());;



                refff = FirebaseDatabase.getInstance().getReference("Users").child(users);
                if(!TextUtils.isEmpty(phone.getText().toString()))refff.child("number").setValue(phone.getText().toString());




                finish();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==100 && resultCode == RESULT_OK)
        {
            //ON SUCCES
            Place place = Autocomplete.getPlaceFromIntent(data);
            //setadresse on edit text

        }else if (resultCode == AutocompleteActivity.RESULT_ERROR)
        {
            Status status =Autocomplete.getStatusFromIntent(data);
            Toast.makeText(getApplicationContext(),status.getStatusMessage(),Toast.LENGTH_LONG).show();

         }
    }
}
