package com.delivery_clientes.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.delivery_clientes.data.db.AppDatabase;
import com.delivery_clientes.data.db.dao.ClientesDAO;
import com.delivery_clientes.data.db.entities.Clientes;

import java.util.List;

public class ClientesRepository {

    private ClientesDAO clientesDAO;

    public ClientesRepository(Application application){
        AppDatabase db = AppDatabase.getInstance(application);
        clientesDAO = db.clientesDAO();
    }

    public List<Clientes> obtenerClientes(){
        return clientesDAO.obtenerClientes();
    }

    public Clientes obtenerClientesPorId(int id_clientes){
        return clientesDAO.obtenerClientePorId(id_clientes);
    }

    public Clientes obtenerClientesPorEmail(String email){
        return clientesDAO.obtenerClientePorEmail(email);
    }

    public LiveData<Clientes> obtenerClientesPorEmailLive(String email){
        return clientesDAO.obtenerClientePorEmailLive(email);
    }

    public long insertarCliente(Clientes cliente){
        return clientesDAO.insertarCliente(cliente);
    }

    public void actualizarCliente(Clientes cliente){
        clientesDAO.actualizarCliente(cliente);
    }
}
