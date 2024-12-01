package com.delivery_clientes.ui.profile;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.delivery_clientes.data.db.entities.Clientes;
import com.delivery_clientes.data.repository.ClientesRepository;

public class CrearPerfilViewModel extends ViewModel {
    private final ClientesRepository clientesRepository;
    private final MutableLiveData<Clientes> clienteCreado = new MutableLiveData<>();

    public CrearPerfilViewModel(Application application) {
        super();
        clientesRepository = new ClientesRepository(application);
    }

    public LiveData<Clientes> getClienteCreado() {
        return clienteCreado;
    }

    public void crearCliente(String nombre, String apellido, String fechaNacimiento, String telefono) {
        new Thread(() -> {
            // Crear un objeto Cliente
            Clientes cliente = new Clientes();
            cliente.setNombre(nombre);
            cliente.setApellido(apellido);
            cliente.setFecha_nacimiento(fechaNacimiento);
            cliente.setTelefono(telefono);

            // Insertar en la base de datos
            long id = clientesRepository.insertarCliente(cliente);

            if (id > 0) {
                cliente.setId((int) id); // Actualiza el ID asignado por Room
                clienteCreado.postValue(cliente);
            } else {
                clienteCreado.postValue(null);
            }
        }).start();
    }
}
