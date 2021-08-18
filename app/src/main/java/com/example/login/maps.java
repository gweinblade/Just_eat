package com.example.login;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class maps extends AppCompatActivity implements OnMapReadyCallback {
    static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS=23;
    private GoogleMap mMap;
    ImageButton btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

btn = findViewById(R.id.gobackkkk);
btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        finish();
    }
});
        checkPermission();
    }

    private void checkPermission() {

        if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION )
                != PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  },
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS );
        }
        else{
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
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
                LatLng latlng2=new LatLng(35.696788, -0.631452);

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latlng);
                MarkerOptions resto = new MarkerOptions();
                resto.position(latlng2);
                markerOptions.title("My Marker");
                String name= getIntent().getStringExtra("name");
                resto.title(name);


                mMap.clear();
                CameraUpdate cameraUpdate= CameraUpdateFactory.newLatLngZoom(
                        latlng, 15);
                mMap.animateCamera(cameraUpdate);
                mMap.addMarker(markerOptions).showInfoWindow();
                mMap.addMarker(resto).showInfoWindow();
                //Polyline line =mMap.addPolyline( new PolylineOptions().add(latlng,latlng2).width(5).color(Color.RED));

            }
        });
    }
}
