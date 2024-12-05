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
import com.delivery_clientes.data.db.entities.Direcciones;
import com.delivery_clientes.data.db.entities.Usuario;
import com.delivery_clientes.data.repository.ClientesRepository;
import com.delivery_clientes.data.repository.DireccionesRepository;
import com.delivery_clientes.data.repository.UsuariosRepository;

public class CrearPerfilViewModel extends ViewModel {
    private final ClientesRepository clientesRepository;
    private final MutableLiveData<Clientes> clienteCreado = new MutableLiveData<>();
    private final UsuariosRepository usuariosRepository;
    private final DireccionesRepository direccionesRepository;

    public CrearPerfilViewModel(Application application) {
        super();
        clientesRepository = new ClientesRepository(application);
        usuariosRepository = new UsuariosRepository(application);
        direccionesRepository = new DireccionesRepository(application);
    }

    public LiveData<Clientes> getClienteCreado() {
        return clienteCreado;
    }

    public LiveData<Direcciones> getDireccionCliente(int dirId){
        return direccionesRepository.obtenerDireccionPorIdLive(dirId);
    }

    public void crearCliente(String nombre, String apellido, String fechaNacimiento, String telefono, String email) {
        new Thread(() -> {

            // Crear un objeto Direcciones
            Direcciones direccion = new Direcciones();
            direccion.setDireccion(null);
            direccion.setLatitud(0);
            direccion.setLongitud(0);
            long dirId = direccionesRepository.insertarDireccion(direccion);

            // Crear un objeto Cliente
            Clientes cliente = new Clientes();
            cliente.setNombre(nombre);
            cliente.setApellido(apellido);
            cliente.setFecha_nacimiento(fechaNacimiento);
            cliente.setTelefono(telefono);
            cliente.setEmail(email);
            cliente.setDireccion_id((int) dirId);

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