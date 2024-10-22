package com.delivery_clientes.data.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.delivery_clientes.data.db.entities.Usuario;

@Dao
public interface UsuariosDAO {

    @Query("SELECT * FROM usuarios WHERE email = :email AND password = :password LIMIT 1")
    Usuario login(String email, String password);

    @Insert
    void insertUsuario(Usuario usuario);
}
