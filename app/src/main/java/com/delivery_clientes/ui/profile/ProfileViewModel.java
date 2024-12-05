package com.delivery_clientes.ui.profile;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.delivery_clientes.data.db.AppDatabase;
import com.delivery_clientes.data.db.entities.Clientes;
import com.delivery_clientes.data.repository.ClientesRepository;
import com.delivery_clientes.ui.register.SignUpResult;

public class ProfileViewModel extends AndroidViewModel {

    private final ClientesRepository clientesRepository;

    public ProfileViewModel(Application application){
        super(application);
        clientesRepository = new ClientesRepository(application);
    }

    public LiveData<Clientes> getCliente() {
        return clientesRepository.obtenerClientesPorEmailLive(getEmail());
    }

    private String getEmail() {
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE);
        return sharedPreferences.getString("email", null);  // Retorna el email almacenado, o null si no existe
    }
}
