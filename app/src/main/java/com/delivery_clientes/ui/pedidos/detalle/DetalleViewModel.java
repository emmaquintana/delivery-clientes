package com.delivery_clientes.ui.pedidos.detalle;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.delivery_clientes.data.db.entities.Negocios;
import com.delivery_clientes.data.db.entities.PedidoDetalle;
import com.delivery_clientes.data.db.entities.Productos;
import com.delivery_clientes.data.db.entities.Repartidores;
import com.delivery_clientes.data.repository.DetalleRepository;
import com.delivery_clientes.data.repository.NegociosRepository;
import com.delivery_clientes.data.repository.ProductosRepository;
import com.delivery_clientes.data.repository.RepartidoresRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DetalleViewModel extends AndroidViewModel {

    private final DetalleRepository detalleRepository;
    private final NegociosRepository negociosRepository;
    private final RepartidoresRepository repartidoresRepository;
    private final ProductosRepository productosRepository;
    private Double total = 0.0;

    public DetalleViewModel(@NonNull Application application){
        super(application);
        detalleRepository = new DetalleRepository(application);
        negociosRepository = new NegociosRepository(application);
        repartidoresRepository = new RepartidoresRepository(application);
        productosRepository = new ProductosRepository(application);
    }

    public LiveData<List<HashMap<String, Object>>> getDetalleLiveData(long id){
        MediatorLiveData<List<HashMap<String, Object>>> detalleLive = new MediatorLiveData<>();

        LiveData<List<PedidoDetalle>> lista = detalleRepository.obtenerDetalleLive(id);

        detalleLive.addSource(lista, detalleList -> {
            List<HashMap<String, Object>> detalles = new ArrayList<>();
            for (PedidoDetalle pedido : lista.getValue()){
                HashMap<String, Object> detalle = new HashMap<>();
                detalle.put("cant", pedido.getCantidad());
                detalle.put("precio", pedido.getPrecio_unitario());
                detalle.put("productoId", pedido.getProducto_id());
                this.total = this.total + (pedido.getPrecio_unitario() * pedido.getCantidad());

                MediatorLiveData<String> productoLiveData = new MediatorLiveData<>();
                productoLiveData.addSource(getProducto(pedido.getProducto_id()), producto -> {
                    productoLiveData.setValue(producto.getNombre());
                });

                detalleLive.addSource(productoLiveData, nombreProd -> {
                    detalle.put("producto", nombreProd);
                    detalleLive.setValue(detalles);
                });

                detalles.add(detalle);
            }
        });
        return detalleLive;
    }

    public LiveData<Negocios> getNegocio(int id){
        return negociosRepository.getNegocioLive(id);
    }

    public LiveData<Repartidores> getRepartidores(int id){
        return repartidoresRepository.getRepartidorLive(id);
    }

    public LiveData<Productos> getProducto(int id){
        return productosRepository.obtenerProductosLivePorId(id);
    }

    public Double getTotal(){ return this.total;}
}
