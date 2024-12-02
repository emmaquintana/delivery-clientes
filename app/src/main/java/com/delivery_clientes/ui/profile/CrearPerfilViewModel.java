package com.delivery_clientes.ui.profile;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.delivery_clientes.data.db.entities.Clientes;
import com.delivery_clientes.data.db.entities.Usuario;
import com.delivery_clientes.data.repository.ClientesRepository;
import com.delivery_clientes.data.repository.UsuariosRepository;

public class CrearPerfilViewModel extends ViewModel {
    private final ClientesRepository clientesRepository;
    private final MutableLiveData<Clientes> clienteCreado = new MutableLiveData<>();
    private final UsuariosRepository usuariosRepository;

    public CrearPerfilViewModel(Application application) {
        super();
        clientesRepository = new ClientesRepository(application);
        usuariosRepository = new UsuariosRepository(application);
    }

    public LiveData<Clientes> getClienteCreado() {
        return clienteCreado;
    }


    public void crearCliente(String nombre, String apellido, String fechaNacimiento, String telefono, String email) {
        new Thread(() -> {

            // Crear un objeto Cliente
                Clientes cliente = new Clientes();
                cliente.setNombre(nombre);
                cliente.setApellido(apellido);
                cliente.setFecha_nacimiento(fechaNacimiento);
                cliente.setTelefono(telefono);
                cliente.setEmail(email);

                // Insertar en la base de datos
                long id = clientesRepository.insertarCliente(cliente);

                if (id > 0) {
                    cliente.setId((int) id); // Actualiza el ID asignado por Room

                    Usuario usuario = usuariosRepository.findUserByEmail(email);
                    usuariosRepository.actualizarClienteId(usuario.getIdUsuarios(), (int) id);

                    clienteCreado.postValue(cliente);
                } else {
                    clienteCreado.postValue(null);
                }
            }).start();
    }
}