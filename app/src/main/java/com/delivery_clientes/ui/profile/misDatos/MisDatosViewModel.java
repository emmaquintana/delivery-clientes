package com.delivery_clientes.ui.profile.misDatos;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.delivery_clientes.data.db.entities.Clientes;
import com.delivery_clientes.data.db.entities.Direcciones;
import com.delivery_clientes.data.repository.ClientesRepository;
import com.delivery_clientes.data.repository.DireccionesRepository;
import com.google.android.gms.maps.model.LatLng;

import java.util.concurrent.Executors;

public class MisDatosViewModel extends AndroidViewModel {

    private final DireccionesRepository direccionesRepository;
    private final ClientesRepository clientesRepository;

    public MisDatosViewModel(@NonNull Application application){
        super(application);
        direccionesRepository = new DireccionesRepository(application);
        clientesRepository = new ClientesRepository(application);
    }

    public void actualizarDireccion(Direcciones dir, String direccion, LatLng latLng){
        Executors.newSingleThreadExecutor().execute(() -> {
            dir.setLatitud(latLng.latitude);
            dir.setLongitud(latLng.longitude);
            dir.setDireccion(direccion);
            direccionesRepository.actualizarDireccion(dir);
        });
    }


    public LiveData<Direcciones> obtenerDireccion(int id){
        return direccionesRepository.obtenerDireccionPorIdLive(id);
    }

    public LiveData<Clientes> obtenerCliente(){
        String email = getEmail();
        return clientesRepository.obtenerClientesPorEmailLive(email);
    }

    private String getEmail() {
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE);
        return sharedPreferences.getString("email", null);  // Retorna el email almacenado, o null si no existe
    }
}
