package com.delivery_clientes.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.delivery_clientes.data.db.entities.Categorias;

import java.util.List;

@Dao
public interface CategoriasDAO {

    @Query("SELECT * FROM categorias")
    List<Categorias> obtenerCategorias();

    @Query("SELECT * FROM categorias")
    LiveData<List<Categorias>> obtenerCategoriasLive();

    @Query("SELECT * FROM categorias WHERE nombre = :nombre")
    Categorias obtenerCategoriaPorNombre(String nombre);

    @Query("SELECT * FROM categorias WHERE id = :id_categorias")
    Categorias obtenerCategoriaPorId(int id_categorias);

    @Insert
    void insertarCategoria(Categorias categoria);
}
