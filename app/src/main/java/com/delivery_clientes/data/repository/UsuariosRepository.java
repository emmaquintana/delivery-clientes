package com.delivery_clientes.data.repository;

import android.app.Application;

import com.delivery_clientes.data.db.AppDatabase;
import com.delivery_clientes.data.db.dao.UsuariosDAO;
import com.delivery_clientes.data.db.entities.Usuario;

public class UsuariosRepository {

    private UsuariosDAO usuariosDAO;

    public UsuariosRepository(Application application){
        AppDatabase db = AppDatabase.getInstance(application);
        usuariosDAO = db.usuariosDAO();
    }

    public Usuario login(String email, String password){
        return usuariosDAO.login(email,password);
    }

    public Usuario findUserByEmail(String email){
        return usuariosDAO.findUserByUsername(email);
    }

    public long insertarUsuario(Usuario usuario){
        return usuariosDAO.insertUsuario(usuario);
    }

    // Método para actualizar el clientes_id en Usuario
    public void actualizarClienteId(int usuarioId, int clienteId) {
        new Thread(() -> {
            usuariosDAO.actualizarClienteId(usuarioId, clienteId);
        }).start();
    }
}
