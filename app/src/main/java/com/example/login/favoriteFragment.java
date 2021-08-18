package com.example.login;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class favoriteFragment extends Fragment {
    DatabaseReference reff;
    RecyclerView recyclerView;
    ArrayList<favmodel> model;
    favadapter adap;
    FirebaseAuth firebaseAuth;


    public favoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_place, container, false);
        recyclerView = v.findViewById(R.id.favrecycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        model = new ArrayList<favmodel>();
        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser userss  = firebaseAuth.getCurrentUser();
        String users= Objects.requireNonNull(userss).getUid();
        reff = FirebaseDatabase.getInstance().getReference("favoris").child(users);
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                model.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {


                    favmodel p = dataSnapshot1.getValue(favmodel.class);

                    model.add(p);


                }

                adap = new favadapter(getContext(),model);
                recyclerView.setAdapter(adap);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(),"something wrong ",Toast.LENGTH_LONG).show();
            }
        });

        return v;
    }
}
