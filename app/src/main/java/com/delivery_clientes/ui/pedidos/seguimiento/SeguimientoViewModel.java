package com.delivery_clientes.ui.pedidos.seguimiento;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.delivery_clientes.data.db.entities.Seguimiento;
import com.delivery_clientes.data.repository.SeguimientoRepository;

import java.util.List;

public class SeguimientoViewModel extends AndroidViewModel {

    private final SeguimientoRepository seguimientoRepository;

    public SeguimientoViewModel(@NonNull Application application){
        super(application);
        seguimientoRepository = new SeguimientoRepository(application);
    }

    public LiveData<List<Seguimiento>> getSeguimientoLiveData(int id){
        return seguimientoRepository.obtenerSeguimientoLive(id);
    }
}
