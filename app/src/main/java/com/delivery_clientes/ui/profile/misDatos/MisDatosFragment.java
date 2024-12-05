package com.delivery_clientes.ui.profile.misDatos;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.delivery_clientes.R;
import com.delivery_clientes.data.db.entities.Clientes;
import com.delivery_clientes.data.db.entities.Direcciones;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MisDatosFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    private MisDatosViewModel misDatosViewModel;
    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE_LOC_PERM = 1;
    private TextView direccion;
    private Geocoder geocoder;
    private LatLng ubicacion;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mis_datos, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        geocoder = new Geocoder(requireContext(), Locale.getDefault());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        misDatosViewModel = new ViewModelProvider(this).get(MisDatosViewModel.class);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        activity.setSupportActionBar(toolbar);

        // Establece un título para el toolbar
        activity.getSupportActionBar().setTitle(R.string.title_mis_datos);

        // Habilita el botón de navegación
        if (activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Configura la acción del botón de navegación
        toolbar.setNavigationOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_misDatosFragment_to_profileFragment);
        });

        direccion = view.findViewById(R.id.TxtDireccion);


        Button btnEditarDatos = view.findViewById(R.id.BtnEditarDatos);
        btnEditarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_misDatosFragment_to_editarDatosFragment);
            }
        });

        Button btnActualizarDir = view.findViewById(R.id.BtnUpdateDir);
        btnActualizarDir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LiveData<Clientes> cliente = misDatosViewModel.obtenerCliente();
                cliente.observe(getViewLifecycleOwner(), clientes -> {
                    LiveData<Direcciones> dir = misDatosViewModel.obtenerDireccion(clientes.getDireccion_id());
                    dir.observe(getViewLifecycleOwner(), direcciones -> {
                        misDatosViewModel.actualizarDireccion(direcciones, direccion.getText().toString(), ubicacion);
                    });
                });
                mMap.clear();
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap){
        mMap = googleMap;
        getCurrentLocation();
        mMap.setOnMapLongClickListener(this);
    }

    private void getCurrentLocation(){
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        if (ActivityCompat.checkSelfPermission(
                requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ){
            ActivityCompat.requestPermissions(requireActivity() , new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE_LOC_PERM);
            return;
        } else {
            mMap.setMyLocationEnabled(true);
        }

        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null){
                // Ubicación actual
                LatLng pos = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLng(pos));

                // Ubicacion de la base de datos
                //LiveData<Clientes> cliente =
                misDatosViewModel.obtenerCliente().observe(getViewLifecycleOwner(), clientes -> {
                    LiveData<Direcciones> dirGuardada = misDatosViewModel.obtenerDireccion(clientes.getDireccion_id());
                    dirGuardada.observe(getViewLifecycleOwner(), direcciones -> {
                        if (direcciones != null) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(direcciones.getLatitud(), direcciones.getLongitud())).title("Ubicación guardada"));
                            direccion.setText(direcciones.getDireccion());
                        } else {
                            direccion.setText("Establezca una dirección");
                        }
//                    try {
//                        List<Address> direc = geocoder.getFromLocation(direcciones.getLatitud(), direcciones.getLongitud(), 1);
//                        if (direcciones != null && !direc.isEmpty()){
//                            Address address = direc.get(0);
//                            String numero = address.getSubThoroughfare();
//                            String calle = address.getThoroughfare();
//                            String ciudad = address.getLocality();
//                            direccion.setText(ciudad + ", " + calle + " " + numero);
//                        } else {
//                            Log.e("MapError", "No se encontró la direccion");
//                            direccion.setText("Dirección no reconocida");
//                        }
//                    } catch (IOException e){
//                        e.printStackTrace();
//                    }
                    });
                });
            }
        });
    }

    @Override
    public void onMapLongClick(@NonNull LatLng latLng) {
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(latLng).title("Mi ubicación"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        setUbicacion(latLng);

        try {
            List<Address> direcciones = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (direcciones != null && !direcciones.isEmpty()){
                Address address = direcciones.get(0);
                String numero = address.getSubThoroughfare();
                String calle = address.getThoroughfare();
                String ciudad = address.getLocality();
                direccion.setText(ciudad + ", " + calle + " " + numero);
            } else {
                Log.e("MapError", "No se encontró la direccion");
                direccion.setText("Dirección no reconocida");
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void setUbicacion(LatLng latLng){
        this.ubicacion = latLng;
    }
}