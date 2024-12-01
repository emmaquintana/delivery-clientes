package com.delivery_clientes.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.delivery_clientes.data.db.AppDatabase;
import com.delivery_clientes.data.db.dao.ClientesDAO;
import com.delivery_clientes.data.db.entities.Clientes;

import java.util.concurrent.Executors;

public class ClientesRepository {
    private final ClientesDAO clientesDAO;

    public ClientesRepository(AppDatabase db) {
        clientesDAO = db.clientesDAO();
    }

    public LiveData<Clientes> obtenerClientePorId(int idClientes) {
        MutableLiveData<Clientes> clienteLiveData = new MutableLiveData<>();
        Executors.newSingleThreadExecutor().execute(() -> {
            clienteLiveData.postValue(clientesDAO.obtenerClientePorId(idClientes));
        });
        return clienteLiveData;
    }
}