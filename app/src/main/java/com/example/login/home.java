package com.example.login;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class home extends AppCompatActivity {
    private BottomAppBar bottomAppBar;
    private FloatingActionButton fcate,fhome,fplace,fcart,fprofil;
    private boolean isFabTapped = false;
    static ViewPager viewPager;
    List<Model> models;

    public static slider_ada slider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        bottomAppBar=findViewById(R.id.bar);

        fhome=findViewById(R.id.bhome);
        fhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleFrame(new homefragment());
            }
        });
        fcate=findViewById(R.id.bcategorie);
        fcate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleFrame(new CategorieFragment());
            }
        });
        fcart=findViewById(R.id.bcart);
        fcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleFrame(new CartFragment());
            }
        });
        fplace=findViewById(R.id.bplace);
        fplace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleFrame(new favoriteFragment());
            }
        });
        fprofil=findViewById(R.id.bprofil);
        fprofil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleFrame(new ProfilFragment());
            }
        });
        handleFrame(new homefragment());

    }



    private   void handleFrame(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.fram1, fragment);

        fragmentTransaction.commit();
    }







}