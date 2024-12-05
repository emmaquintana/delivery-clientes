package com.delivery_clientes.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.delivery_clientes.data.db.AppDatabase;
import com.delivery_clientes.data.db.dao.DireccionesDAO;
import com.delivery_clientes.data.db.entities.Direcciones;

public class DireccionesRepository {
    private final DireccionesDAO direccionesDAO;

    public DireccionesRepository(Application application){
        AppDatabase db = AppDatabase.getInstance(application);
        direccionesDAO = db.direccionesDAO();
    }

    public Direcciones obtenerDireccionPorId(int id){
        return direccionesDAO.obtenerDireccionPorId(id);
    }

    public void actualizarDireccion(Direcciones dir){
        direccionesDAO.actualizarDireccion(dir);
    }

    public LiveData<Direcciones> obtenerDireccionPorIdLive(int id){
        return direccionesDAO.obtenerDireccionPorIdLive(id);
    }

    public long insertarDireccion(Direcciones dir){
        return direccionesDAO.insertarDireccion(dir);
    }
}
