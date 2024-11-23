package com.delivery_clientes.data.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.delivery_clientes.data.db.entities.Direcciones;

import java.util.List;

@Dao
public interface DireccionesDAO {

    @Query("SELECT * FROM direcciones")
    List<Direcciones> obtenerDirecciones();

    @Query("SELECT * FROM direcciones WHERE id = :direccion_id")
    Direcciones obtenerDireccionPorId(int direccion_id);

    @Query("SELECT * FROM direcciones WHERE direccion = :direccion")
    Direcciones obtenerDireccionPorDireccion(String direccion);

    @Query("SELECT * FROM direcciones WHERE latitud = :latitud AND longitud = :longitud")
    Direcciones obtenerDireccionPorCoord(double latitud, double longitud);


    @Insert
    void insertarDireccion(Direcciones direccion);

    @Update
    void actualizarDireccion(Direcciones direccion);
}
