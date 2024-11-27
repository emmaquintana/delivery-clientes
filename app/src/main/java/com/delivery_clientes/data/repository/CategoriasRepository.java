package com.delivery_clientes.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.delivery_clientes.data.db.AppDatabase;
import com.delivery_clientes.data.db.dao.CategoriasDAO;
import com.delivery_clientes.data.db.entities.Categorias;

import java.util.List;

public class CategoriasRepository {

    private final CategoriasDAO categoriasDAO;

    public CategoriasRepository(Application application){
        AppDatabase db = AppDatabase.getInstance(application);
        categoriasDAO = db.categoriasDAO();
    }

    public List<Categorias> obtenerCategorias() { return categoriasDAO.obtenerCategorias(); }

    public Categorias obtenerCategoriasPorNombre(String nombre){
        return categoriasDAO.obtenerCategoriaPorNombre(nombre);
    }

    public Categorias obtenerCategoriaPorId(int id_categorias){
        return categoriasDAO.obtenerCategoriaPorId(id_categorias);
    }

    public LiveData<List<Categorias>> obtenerCategoriasLive(){
        return categoriasDAO.obtenerCategoriasLive();
    }



    public void insertarCategoria(Categorias categoria){
        categoriasDAO.insertarCategoria(categoria);
    }
}
