package com.delivery_clientes.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.delivery_clientes.data.db.entities.Negocios;

import java.util.List;

@Dao
public interface NegociosDAO {

    @Query("SELECT * FROM negocios")
    List<Negocios> obtenerNegocios();

    @Query("SELECT * FROM negocios WHERE id = :negocio_id")
    LiveData<Negocios> obtenerNegocioPorId(int negocio_id);

    @Query("SELECT * FROM negocios WHERE email = :email")
    Negocios obtenerNegocioPorEmail(String email);

    @Query("SELECT * FROM negocios WHERE cuit = :cuit")
    Negocios obtenerNegocioPorCUIT(String cuit);

    @Query("SELECT * FROM negocios WHERE telefono = :telefono")
    Negocios obtenerNegocioPorTelefono(String telefono);

    //Devuelve un listado en caso de que existan multiples negocios con el mismo nombre
    @Query("SELECT * FROM negocios WHERE nombre = :nombre")
    List<Negocios> obtenerNegociosPorNombre(String nombre);

    @Query("SELECT * FROM negocios WHERE direccion_id = :direccion_id")
    Negocios obtenerNegocioPorDireccionId(int direccion_id);


    @Insert
    void insertarNegocio(Negocios negocio);

    @Update
    void actualizarNegocio(Negocios negocio);
}
