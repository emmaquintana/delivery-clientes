package com.delivery_clientes.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.delivery_clientes.data.db.AppDatabase;
import com.delivery_clientes.data.db.entities.Clientes;
import com.delivery_clientes.data.repository.ClientesRepository;
import com.delivery_clientes.ui.register.SignUpResult;

public class ProfileViewModel extends ViewModel {

    private LiveData<Clientes> cliente;

    public LiveData<Clientes> getCliente() {
        return cliente;
    }
}
