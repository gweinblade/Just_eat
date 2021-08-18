package com.example.login;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */

public class ProfilFragment extends Fragment {
    FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener  authStateListener;
    ImageView imageView;
    DatabaseReference reference;

    DatabaseReference reff;
    Button Logout;
    Uri imageUri,img;
    String names,numbers,emails,adresse,pic;
    TextView user_affiche,about;
    TextView phone_affiche;
    RelativeLayout info,visi;
    TextView email;
    ImageButton ed,camera,ga;
    Button tuto;
    public ProfilFragment() {
        // Required empty public constructor

    }


    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        //user_affiche.setText();
        imageView = v.findViewById(R.id.profile_image);
        // affiche changement
    //check();
        user_affiche = v.findViewById(R.id.afficheName);
        phone_affiche = v.findViewById(R.id.yourPhone);
        info = v.findViewById(R.id.l);
        about = v.findViewById(R.id.ifo3);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),aboutus.class);
                startActivity(i);
            }
        });

        visi = v.findViewById(R.id.r2);

        tuto = v.findViewById(R.id.tutorielle);


        ga = v.findViewById(R.id.ga);
        camera = v.findViewById(R.id.camera);
        ed = v.findViewById(R.id.ed);
        Logout= v.findViewById(R.id.logout);
info.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if(visi.getVisibility()==View.GONE){visi.setVisibility(View.VISIBLE);}
        else {visi.setVisibility(View.GONE);}
    }
});

tuto.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i = new Intent(getContext(),IntroActivity.class);
        startActivity(i);
        getActivity().finish();
    }
});
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user == null){
                    Indentification.sp.edit().putBoolean("logged",false).apply();
                    Intent intent = new Intent(getActivity(), Activity_principale.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        };
        email = v.findViewById(R.id.yourEmail);
        final FirebaseUser user  = firebaseAuth.getCurrentUser();
        String users= Objects.requireNonNull(user).getUid();

        reference = FirebaseDatabase.getInstance().getReference("Users").child(users);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    String namess = String.valueOf(Objects.requireNonNull(dataSnapshot.child("name").getValue()));
                String pic = String.valueOf(Objects.requireNonNull(dataSnapshot.child("picture").getValue()));
                numbers = dataSnapshot.child("number").getValue().toString();
                    adresse = dataSnapshot.child("adresse").getValue().toString();
                    emails = dataSnapshot.child("email").getValue().toString();

                imageView.setImageURI(Uri.parse(pic));
                        //Picasso.get().load(pic).into(imageView);

                user_affiche.setText(namess);

                email.setText(emails);
                phone_affiche.setText(numbers);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //go to edit
        ed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),editprofil.class);
                startActivity(intent);
            }
        });
        // open camera


        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

opencamera();


     }


        });
        // open gallery
        ga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent gallery = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, 1);

            }
        });

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Indentification.sp.edit().putBoolean("logged",false).apply();
                firebaseAuth.signOut();
                startActivity(new Intent(getActivity(),Activity_principale.class));
                getActivity().finish();
            }
        });

        return v;




    }
// open the camera methode
    private void opencamera() {

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"new image");
        values.put(MediaStore.Images.Media.DESCRIPTION,"from camera");
        img = Objects.requireNonNull(getActivity()).getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        captureIntent.putExtra(MediaStore.EXTRA_OUTPUT,img);
        startActivityForResult(captureIntent, 1888);
    }


    // set photo in imageView
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1888){
            if (resultCode == Activity.RESULT_OK){


                imageView.setImageURI(img);
                //Toast.makeText(getContext(),img.toString(),Toast.LENGTH_LONG).show();


                final FirebaseUser user  = firebaseAuth.getCurrentUser();
                String users= Objects.requireNonNull(user).getUid();
                reff = FirebaseDatabase.getInstance().getReference("Users").child(users);

                reff.child("picture").setValue(img.toString());

            }
        }
        if (requestCode == 1 ){
            imageUri = data.getData();
            imageView.setImageURI(imageUri);


                final FirebaseUser user  = firebaseAuth.getCurrentUser();
                String users= Objects.requireNonNull(user).getUid();
                reff = FirebaseDatabase.getInstance().getReference("Users").child(users);

                reff.child("picture").setValue(imageUri.toString());



            Toast.makeText(getContext(),imageUri.toString(),Toast.LENGTH_LONG).show();
        }
    }




    //use permision
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 100: {

                if (grantResults.length > 0 && (grantResults[0]+grantResults[1] == PackageManager.PERMISSION_GRANTED))
                {
                    Toast.makeText(getContext(),"was here",Toast.LENGTH_LONG).show();

                   opencamera();
                }
                else Toast.makeText(getContext(),"denied",Toast.LENGTH_LONG).show();
            }



        }

    }

    /*public void check()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    200);


            return;
        }
    }*/
}




