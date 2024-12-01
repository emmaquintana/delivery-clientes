package com.delivery_clientes.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.delivery_clientes.data.db.AppDatabase;
import com.delivery_clientes.data.db.entities.Clientes;
import com.delivery_clientes.data.repository.ClientesRepository;
import com.delivery_clientes.ui.register.SignUpResult;

public class ProfileViewModel extends ViewModel {

    private final ClientesRepository clientesRepository;
    private LiveData<Clientes> cliente;

    public ProfileViewModel(AppDatabase database) {
        this.clientesRepository = new ClientesRepository(database);
    }

    public void cargarClientePorId(int idClientes) {
        cliente = clientesRepository.obtenerClientePorId(idClientes);
    }

    public LiveData<Clientes> getCliente() {
        return cliente;
    }
}
