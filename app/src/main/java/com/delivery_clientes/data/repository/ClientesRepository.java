package com.delivery_clientes.data.repository;

<<<<<<< HEAD
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
=======
import android.app.Application;

import androidx.lifecycle.LiveData;
>>>>>>> ba1bbd8f38f1d0d05517c096e86ff68ab8ec2828

import com.delivery_clientes.data.db.AppDatabase;
import com.delivery_clientes.data.db.dao.ClientesDAO;
import com.delivery_clientes.data.db.entities.Clientes;

<<<<<<< HEAD
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
=======
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
>>>>>>> ba1bbd8f38f1d0d05517c096e86ff68ab8ec2828
