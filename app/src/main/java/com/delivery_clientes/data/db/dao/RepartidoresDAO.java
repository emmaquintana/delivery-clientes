package com.delivery_clientes.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.delivery_clientes.data.db.entities.Repartidores;

import java.util.List;

@Dao
public interface RepartidoresDAO {

    @Query("SELECT * FROM repartidores")
    List<Repartidores> obtenerRepartidores();

    @Query("SELECT * FROM repartidores WHERE id = :repartidor_id")
    LiveData<Repartidores> obtenerRepartidorPorId(int repartidor_id);

    @Query("SELECT * FROM repartidores WHERE apellido = :apellido AND nombre = :nombre")
    List<Repartidores> obtenerRepartidoresPorApellidoAndNombre(String apellido, String nombre);


    @Insert
    void insertarRepartidor(Repartidores repartidor);

    @Update
    void actualizarRepartidor(Repartidores repartidor);
}
