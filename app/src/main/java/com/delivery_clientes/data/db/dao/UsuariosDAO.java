package com.delivery_clientes.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.delivery_clientes.data.db.entities.Usuario;

import java.util.List;

@Dao
public interface UsuariosDAO {

    @Query("SELECT * FROM usuarios WHERE email = :email AND password = :password LIMIT 1")
    Usuario login(String email, String password);

    @Insert
    long insertUsuario(Usuario usuario);

    @Query("SELECT * FROM usuarios WHERE email = :email")
    Usuario findUserByUsername(String email);

    @Query("SELECT * FROM usuarios")
    List<Usuario> obtenerUsuarios();

    @Query("SELECT * FROM usuarios WHERE email = :email")
    LiveData<Usuario> findeUserByEmailLive(String email);

    @Query("UPDATE usuarios SET clientes_id = :clienteId WHERE idUsuarios = :usuarioId")
    void actualizarClienteId(int usuarioId, int clienteId);

    @Update
    void actualizarUsuario(Usuario usuario);
}
