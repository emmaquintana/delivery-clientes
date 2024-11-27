package com.delivery_clientes.ui.carrito;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.delivery_clientes.data.db.entities.Productos;
import com.delivery_clientes.data.repository.ProductosRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarritoViewModel extends ViewModel {

    private ProductosRepository productosRepository;
    private MutableLiveData<List<CarritoItem>> carritoItems = new MutableLiveData<>(new ArrayList<>());
    private MutableLiveData<Map<Integer, Productos>> productosCache = new MutableLiveData<>(new HashMap<>());

    public CarritoViewModel() {
        // Constructor vacío, sin parámetros
    }

//    public CarritoViewModel(ProductosRepository productosRepository){
//        this.productosRepository = productosRepository;
//    }

    public void setProductosRepository(ProductosRepository productosRepository) {
        this.productosRepository = productosRepository;
    }

    public LiveData<List<CarritoItem>> getCarritoItems(){
        return carritoItems;
    }

    public LiveData<Map<Integer, Productos>> getProductosCache() {
        return productosCache;
    }

    public void addCarritoItem(int idProducto){
        List<CarritoItem> productosActuales = new ArrayList<>(carritoItems.getValue());

        boolean existe = false;
        for (CarritoItem item : productosActuales) {
            if(item.getId_producto() == idProducto){
                item.setCantidad(item.getCantidad()+1);
                existe = true;
                break;
            }
        }

        if(!existe){
            productosActuales.add(new CarritoItem(idProducto,1));
        }

        carritoItems.setValue(productosActuales);
    }

    public void observarProductos(List<CarritoItem> carritoList) {
        Map<Integer, Productos> productosMap = productosCache.getValue();
        for (CarritoItem item : carritoList) {
            if (!productosMap.containsKey(item.getId_producto())) {
                productosRepository.obtenerProductosLivePorId(item.getId_producto()).observeForever(producto -> {
                    if (producto != null) {
                        productosMap.put(item.getId_producto(), producto);
                        productosCache.setValue(productosMap);  // Actualiza el mapa de productos
                    }
                });
            }
        }
    }
}
