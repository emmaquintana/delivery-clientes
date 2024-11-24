package com.delivery_clientes.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.delivery_clientes.data.db.AppDatabase;
import com.delivery_clientes.data.db.dao.ProductosDAO;
import com.delivery_clientes.data.db.entities.Productos;

import java.util.List;

public class ProductosRepository {

    private final ProductosDAO productosDAO;

    public ProductosRepository(Application application){
        AppDatabase db = AppDatabase.getInstance(application);
        productosDAO = db.productosDAO();
    }

    public List<Productos> obtenerProductos(){
        return productosDAO.obtenerProductos();
    }

    public List<Productos> obtenerProductosPorCategoria(int categoria_id){
        return productosDAO.obtenerProductosPorCategoria(categoria_id);
    }

    public LiveData<List<Productos>> obtenerProductosLivePorCategoria(int categoria_id){
        return productosDAO.obtenerProductosLivePorCategoria(categoria_id);
    }

    public List<Productos> obtenerProductosPorNombre(String nombre){
        return productosDAO.obtenerProductosPorNombre(nombre);
    }

    public LiveData<List<Productos>> obtenerProductosLive() {
        return productosDAO.obtenerProductosLive();
    }

    public void insertarProducto(Productos prod){
        productosDAO.insertarProducto(prod);
    }

    public void actualizarProducto(Productos prod){
        productosDAO.actualizarProducto(prod);
    }
}
