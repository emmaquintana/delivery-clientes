package com.delivery_clientes.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.delivery_clientes.data.db.entities.Productos;

import java.util.List;

@Dao
public interface ProductosDAO {

    @Query("SELECT * FROM productos")
    List<Productos> obtenerProductos();

    @Query("SELECT * FROM productos WHERE categoria_id = :categoria_id")
    List<Productos> obtenerProductosPorCategoria(int categoria_id);

    @Query("SELECT * FROM productos WHERE nombre = :nombre")
    List<Productos> obtenerProductosPorNombre(String nombre);

    @Query("SELECT * FROM productos")
    LiveData<List<Productos>> obtenerProductosLive();

    //Estos metodos no se suponen que los usen los clientes, pero son necesarios por razones practicas
    @Insert
    void insertarProducto(Productos prod);

    @Update
    void actualizarProducto(Productos prod);
}
