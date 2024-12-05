package com.delivery_clientes.ui.profile.misDatos;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.delivery_clientes.data.db.entities.Clientes;
import com.delivery_clientes.data.db.entities.Usuario;
import com.delivery_clientes.data.repository.ClientesRepository;
import com.delivery_clientes.data.repository.DireccionesRepository;
import com.delivery_clientes.data.repository.UsuariosRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class EditarDatosViewModel extends AndroidViewModel {
    private ClientesRepository clientesRepository;
    private UsuariosRepository usuariosRepository;

    public EditarDatosViewModel(@NonNull Application application) {
        super(application);
        clientesRepository = new ClientesRepository(application);
        usuariosRepository = new UsuariosRepository(application);
    }

    public LiveData<Clientes> obtenerCliente(){
        String email = getEmail();
        return clientesRepository.obtenerClientesPorEmailLive(email);
    }

    public void actualizarCliente(Clientes cliente) {
        new Thread(() -> {
            clientesRepository.actualizarCliente(cliente);
        }).start();
    }

    public LiveData<Usuario> findUserByEmail(String email) {
        return usuariosRepository.findUserByEmailLive(email);
    }

    public void actualizarUsuario(Usuario usuario) {
        new Thread(() -> {
            usuariosRepository.actualizarUsuario(usuario);
            updateEmailInSharedPreferences(usuario.getEmail());
        }).start();
    }

    private String getEmail() {
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE);
        return sharedPreferences.getString("email", null);  // Retorna el email almacenado, o null si no existe
    }

    public void updateEmailInSharedPreferences(String email) {
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", true);
        editor.putString("email", email);
        editor.apply();
    }
}
