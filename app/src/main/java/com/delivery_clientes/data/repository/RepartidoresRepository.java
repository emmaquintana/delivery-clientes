package com.delivery_clientes.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.delivery_clientes.data.db.AppDatabase;
import com.delivery_clientes.data.db.dao.RepartidoresDAO;
import com.delivery_clientes.data.db.entities.Repartidores;

public class RepartidoresRepository {

    private final RepartidoresDAO repartidoresDAO;

    public RepartidoresRepository(Application application){
        AppDatabase db = AppDatabase.getInstance(application);
        repartidoresDAO = db.repartidoresDAO();
    }

    public LiveData<Repartidores> getRepartidorLive(int id){
        return repartidoresDAO.obtenerRepartidorPorId(id);
    }
}
