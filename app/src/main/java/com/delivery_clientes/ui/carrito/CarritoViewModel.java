package com.delivery_clientes.ui.carrito;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.delivery_clientes.data.db.entities.PedidoDetalle;
import com.delivery_clientes.data.db.entities.Pedidos;
import com.delivery_clientes.data.db.entities.Productos;
import com.delivery_clientes.data.repository.DetalleRepository;
import com.delivery_clientes.data.repository.PedidosRepository;
import com.delivery_clientes.data.repository.ProductosRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Executors;

public class CarritoViewModel extends AndroidViewModel {

    private ProductosRepository productosRepository;
    private PedidosRepository pedidosRepository;
    private DetalleRepository detalleRepository;

    private MutableLiveData<List<CarritoItem>> carritoItems = new MutableLiveData<>(new ArrayList<>());
    private MutableLiveData<Map<Integer, Productos>> productosCache = new MutableLiveData<>(new HashMap<>());

    public CarritoViewModel(@NonNull Application application) {
        super(application);
        productosRepository = new ProductosRepository(application);
        pedidosRepository = new PedidosRepository(application);
        detalleRepository = new DetalleRepository(application);
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
        Map<Integer, Productos> productosMap = productosCache.getValue();   // Obtiene el mapa actual de productos

        for (CarritoItem item : carritoList) {                              // Recorre los elementos del carrito
            if (!productosMap.containsKey(item.getId_producto())) {         // Si el producto no está en la caché lo busca en el repositorio
                productosRepository.obtenerProductosLivePorId(item.getId_producto()).observeForever(producto -> {
                    if (producto != null) {                                 // Si el producto se encuentra en el repositorio
                        productosMap.put(item.getId_producto(), producto);  // Lo añade al mapa
                        productosCache.setValue(productosMap);              // Notifica los cambios en productosCache
                    }
                });
            }
        }
    }

    public LiveData<Long> registrarPedido(double total, List<CarritoItem> carritoItems){
        MutableLiveData<Long> pedidoIdLiveData = new MutableLiveData<>();
        Executors.newSingleThreadExecutor().execute(() -> {

            // Verifica que el carrito no esté vacío
            if (carritoItems == null || carritoItems.isEmpty()) {
                pedidoIdLiveData.postValue(null); // Retorna nulo si el carrito está vacío
                return;
            }

            // Se intenta obtener el negocio_id desde el primer producto válido en el carrito
            Map<Integer, Productos> productosMap = productosCache.getValue();
            if (productosMap == null || productosMap.isEmpty()) {
                pedidoIdLiveData.postValue(null); // Retorna nulo si no hay productos en caché
                return;
            }

            int negocioId = -1; // Valor predeterminado en caso de error
            for (CarritoItem item : carritoItems) {
                Productos producto = productosMap.get(item.getId_producto());
                if (producto != null) {
                    negocioId = producto.getNegocio_id();
                    break;
                }
            }

            if (negocioId == -1) {
                pedidoIdLiveData.postValue(null); // Retorna nulo si no se encuentra un negocio_id válido
                return;
            }

            //Se crea el pedido
            Pedidos nuevoPedido = new Pedidos(
                    1, //aca iria el id del cliente
                    negocioId,
                    1, //aca iria el id del repartidor
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()), "Pendiente");

            //Se inserta el pedido en la db
            long pedidoId = pedidosRepository.insertarPedido(nuevoPedido);

            //Se crean los detalles del pedido
            for (CarritoItem item : carritoItems){
                Productos producto = productosMap.get(item.getId_producto());
                if (producto != null){
                    PedidoDetalle pedidoDetalle = new PedidoDetalle(
                            (int) pedidoId,
                            item.getId_producto(),
                            item.getCantidad(),
                            producto.getPrecio()
                    );
                    detalleRepository.insertarDetallePedido(pedidoDetalle);
                }
            }
            pedidoIdLiveData.postValue(pedidoId);
        });
        return pedidoIdLiveData;
    }

    public void vaciarCarrito() {
        carritoItems.setValue(new ArrayList<>());
        productosCache.setValue(new HashMap<>());
    }

    public void restarCarritoItem(int idProducto){
        List<CarritoItem> productosActuales = new ArrayList<>(carritoItems.getValue());
        for(CarritoItem item : productosActuales){
            if(item.getId_producto() == idProducto){
                if(item.getCantidad() > 1){
                    item.setCantidad(item.getCantidad() - 1);
                } else {
                    productosActuales.remove(item);
                }
                break;
            }
        }
        carritoItems.setValue(productosActuales);
    }

    public void borrarCarritoItem(int idProducto) {
        List<CarritoItem> productosActuales = new ArrayList<>(carritoItems.getValue());
        productosActuales.removeIf(item -> item.getId_producto() == idProducto);
        carritoItems.setValue(productosActuales);
    }

}
