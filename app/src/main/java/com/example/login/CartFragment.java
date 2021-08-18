package com.example.login;


import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
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
public class CartFragment extends Fragment implements OnMapReadyCallback {
    DatabaseReference reff;
    RecyclerView recyclerView;
    ArrayList<cartmodel> model;
    cartadapter adap;
    TextView total,totalall,adresse;
    DatabaseReference reference,reffs;
    FirebaseAuth firebaseAuth;
    ImageButton del;
    static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS=23;
    private GoogleMap mMap;
    ProfilFragment profilFragment;
Button confirme;
LinearLayout layout,imageView;

    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_cart, container, false);

        checkPermission();
        recyclerView = v.findViewById(R.id.cartrecycler);
        total = v.findViewById(R.id.totalprice);
        totalall = v.findViewById(R.id.totalpriceall);
        //adresse = v.findViewById(R.id.adressecart);

        del = v.findViewById(R.id.deletecart);
        confirme = v.findViewById(R.id.confirme);
        imageView = v.findViewById(R.id.cartevide);
        layout = v.findViewById(R.id.cartepage);

        recyclerView.setLayoutManager( new LinearLayoutManager(getContext()));

        model = new ArrayList<cartmodel>();
        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser userss  = firebaseAuth.getCurrentUser();
        final String users= Objects.requireNonNull(userss).getUid();

        reff = FirebaseDatabase.getInstance().getReference("cart").child(users);
        reffs = FirebaseDatabase.getInstance().getReference("Users").child(users);

        reffs.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String adr = dataSnapshot.child("adresse").getValue().toString();

                //adresse.setText(adr);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        confirme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*if (adresse.getText().equals(""))
                {
                    Toast.makeText(getContext(),"please enter  a delivery adresse in your profile ",Toast.LENGTH_LONG).show();
                }

                else {*/

                    new AlertDialog.Builder(getContext())
                            .setTitle("Confirm Order")
                            .setMessage("Do you want to confirm your order ?")
                            .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                    DatabaseReference names = FirebaseDatabase.getInstance().getReference("cart").child(users);
                                    names.removeValue();
                                    DatabaseReference resto = FirebaseDatabase.getInstance().getReference("rest").child(users);
                                    resto.removeValue();
                                    deletecart(users);
                                    model.clear();
                                    adap.notifyDataSetChanged();
                                    Notification();
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .show();
                //}
            }
        });

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    cartmodel p = dataSnapshot1.getValue(cartmodel.class);

                    model.add(p);


                }
                int nu= model.size();
                if(nu==0)
                {
                    DatabaseReference resto = FirebaseDatabase.getInstance().getReference("rest").child(users);
                    resto.removeValue();
                    layout.setVisibility(View.GONE);
                    imageView.setVisibility(View.VISIBLE);


                }
                else {
                    layout.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.GONE);
                }
                setprice();
                //total.setText(String.valueOf(count));
                adap = new cartadapter(getContext(),model);
                recyclerView.setAdapter(adap);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(),"something wrong ",Toast.LENGTH_LONG).show();
            }
        });


        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT|ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                int position =  viewHolder.getAdapterPosition();
                String name = model.get(position).getID();
                setprice();
                delete(name,users);

                model.clear();
                Toast.makeText(getContext(),name +" was deleted ",Toast.LENGTH_LONG).show();
                adap.notifyDataSetChanged();



            }
        });
        helper.attachToRecyclerView(recyclerView);

//delete carte
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(getContext())
                        .setTitle("Delete Order")
                        .setMessage("Do you want to delete your order ?")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                DatabaseReference names = FirebaseDatabase.getInstance().getReference("cart").child(users);
                                names.removeValue();
                                DatabaseReference resto = FirebaseDatabase.getInstance().getReference("rest").child(users);
                                resto.removeValue();
                                deletecart(users);
                                model.clear();
                                adap.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .show();



            }
        });

        //String adresse = ;


        return v;
    }

    private void Notification() {
        NotificationCompat.Builder builder
                = new NotificationCompat.Builder(getContext(),"lemubitA")
                .setSmallIcon(R.drawable.icone)
                .setContentText("Your order was accepted and will  arrive in approximately 40 minute ")
                .setContentTitle("JUST EAT")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Intent notification = new Intent(getContext(),home.class);
        PendingIntent content = PendingIntent.getActivity(getContext(),0,notification,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(content);
        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(getContext().NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
    }

    private void setprice() {
        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser userss  = firebaseAuth.getCurrentUser();
        String users= Objects.requireNonNull(userss).getUid();
        final int[] counter = {0};
        reference = FirebaseDatabase.getInstance().getReference("cart").child(users);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    String quant = Objects.requireNonNull(dataSnapshot1.child("quantite").getValue().toString());
                    String price = Objects.requireNonNull(dataSnapshot1.child("price").getValue()).toString();
                    counter[0] = counter[0] + (Integer.parseInt(price)*Integer.parseInt(quant));

                }
                total.setText(String.valueOf(counter[0])+" DA");
                int prouce;
                if(counter[0]==0)
                { totalall.setText( "0 DA");}
                else {
                    prouce = counter[0] + 50 + 250;

                    totalall.setText(String.valueOf(prouce) + " DA");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //int all=  total.getText();


    }

    public void delete(String name,String users) {
        DatabaseReference names = FirebaseDatabase.getInstance().getReference("cart").child(users).child(name);
        names.removeValue();



    }
    public void deletecart(String users) {
        DatabaseReference names = FirebaseDatabase.getInstance().getReference("cart").child(users);
        names.removeValue();



    }
    private void checkPermission() {

        if ( ContextCompat.checkSelfPermission( getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION )
                != PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions( getActivity(), new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  },
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS );
        }
        else{
            SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map2);
            mapFragment.getMapAsync(this);
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map2);
                    mapFragment.getMapAsync(this);
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                LatLng latlng=new LatLng(location.getLatitude(),location.getLongitude());
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latlng);
                markerOptions.title(" Im here ");


                mMap.clear();
                CameraUpdate cameraUpdate= CameraUpdateFactory.newLatLngZoom(
                        latlng, 15);
                mMap.animateCamera(cameraUpdate);
                mMap.addMarker(markerOptions).showInfoWindow();
                

            }
        });
    }

}
