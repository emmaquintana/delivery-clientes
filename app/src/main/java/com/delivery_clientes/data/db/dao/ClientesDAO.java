package com.delivery_clientes.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.delivery_clientes.data.db.entities.Clientes;

import java.util.List;

@Dao
public interface ClientesDAO {

    @Query("SELECT * FROM clientes")
    List<Clientes> obtenerClientes();

    @Query("SELECT * FROM clientes WHERE id = :id_clientes")
    Clientes obtenerClientePorId(int id_clientes);

    @Query("SELECT * FROM clientes WHERE email = :email")
    Clientes obtenerClientePorEmail(String email);

    @Query("SELECT * FROM clientes WHERE email = :email")
    LiveData<Clientes> obtenerClientePorEmailLive(String email);

    @Insert
    long insertarCliente(Clientes cliente);

    @Update
    void actualizarCliente(Clientes cliente);
}
