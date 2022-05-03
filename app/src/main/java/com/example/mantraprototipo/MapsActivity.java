package com.example.mantraprototipo;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.mantraprototipo.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng hospitaldesaludmental = new LatLng(32.551691,-116.910039);
        LatLng psicologoinfantil = new LatLng(32.537729,-116.935695);
        LatLng centroterapeutico = new LatLng(32.535598,-116.933242);
        LatLng psicosaludintegral = new LatLng(32.512016,-116.985873);
        mMap.addMarker(new MarkerOptions().position(hospitaldesaludmental).title("Hospital de salud mental de Tijuana"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(hospitaldesaludmental));
        mMap.addMarker(new MarkerOptions().position(psicologoinfantil).title("Psicologia integral, psicologo infantil"));
        mMap.addMarker(new MarkerOptions().position(centroterapeutico).title("Centro terapeutico Avanza"));
        mMap.addMarker(new MarkerOptions().position(psicosaludintegral).title("Centro terapeutico Avanza"));

    }
}