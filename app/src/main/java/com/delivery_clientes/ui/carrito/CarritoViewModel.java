package com.delivery_clientes.ui.carrito;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.delivery_clientes.data.db.entities.Clientes;
import com.delivery_clientes.data.db.entities.PedidoDetalle;
import com.delivery_clientes.data.db.entities.Pedidos;
import com.delivery_clientes.data.db.entities.Productos;
import com.delivery_clientes.data.repository.ClientesRepository;
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
    private ClientesRepository clientesRepository;

    private MutableLiveData<List<CarritoItem>> carritoItems = new MutableLiveData<>(new ArrayList<>());
    private MutableLiveData<Map<Integer, Productos>> productosCache = new MutableLiveData<>(new HashMap<>());

    private String email;
    private final LiveData<Clientes> clientesLiveData;

    public CarritoViewModel(@NonNull Application application) {
        super(application);
        email = getEmail();
        productosRepository = new ProductosRepository(application);
        pedidosRepository = new PedidosRepository(application);
        detalleRepository = new DetalleRepository(application);
        clientesRepository = new ClientesRepository(application);
        clientesLiveData = clientesRepository.obtenerClientesPorEmailLive(email);
    }

    public LiveData<Clientes> getClientesLiveData() {
        return clientesLiveData;
    }

    public LiveData<List<CarritoItem>> getCarritoItems() {
        return carritoItems;
    }

    public LiveData<Map<Integer, Productos>> getProductosCache() {
        return productosCache;
    }

    public void addCarritoItem(int idProducto) {
        List<CarritoItem> productosActuales = new ArrayList<>(carritoItems.getValue());

        boolean existe = false;
        for (CarritoItem item : productosActuales) {
            if (item.getId_producto() == idProducto) {
                item.setCantidad(item.getCantidad() + 1);
                existe = true;
                break;
            }
        }

        if (!existe) {
            productosActuales.add(new CarritoItem(idProducto, 1));
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
                        Log.e("EN observarProductos","productosCache: " + productosCache.getValue());
                    }
                });
            }
        }
    }

    public void vaciarCarrito() {
        carritoItems.setValue(new ArrayList<>());
        productosCache.setValue(new HashMap<>());
    }

    public void restarCarritoItem(int idProducto) {
        List<CarritoItem> productosActuales = new ArrayList<>(carritoItems.getValue());
        for (CarritoItem item : productosActuales) {
            if (item.getId_producto() == idProducto) {
                if (item.getCantidad() > 1) {
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

    public LiveData<Long> registrarPedido(double total, List<CarritoItem> carritoItems) {
        MutableLiveData<Long> pedidoIdLiveData = new MutableLiveData<>();
        Map<Integer, Productos> productosMap = productosCache.getValue();
        Executors.newSingleThreadExecutor().execute(() -> {
            Clientes cliente = clientesRepository.obtenerClientesPorEmail(email);
            Log.e("EN registrarPedido","cliente_id: " + cliente.getId());
            Log.e("EN registrarPedido","CLIENTE: " + cliente);
            if (cliente == null) {
                Log.e("registrarPedido", "Cliente no encontrado para el email: " + getEmail());
                pedidoIdLiveData.postValue(null);
                return;
            }
            crearPedido(cliente.getId(), carritoItems, pedidoIdLiveData, productosMap);
        });

        return pedidoIdLiveData;
    }

    private void crearPedido(int clienteId, List<CarritoItem> carritoItems, MutableLiveData<Long> pedidoIdLiveData, Map<Integer, Productos> productosMap) {
        Log.e("EN crearPedido","cliente_id param: " + clienteId);
        // Verifica que el carrito no esté vacío
        if (carritoItems == null || carritoItems.isEmpty()) {
            Log.d("registrarPedido", "El carrito está vacío");
            pedidoIdLiveData.postValue(null); // Retorna nulo si el carrito está vacío
            return;
        }

//         Se intenta obtener el negocio_id desde el primer producto válido en el carrito
//        Map<Integer, Productos> productosMap = productosCache.getValue();
        if (productosMap == null || productosMap.isEmpty()) {
            Log.d("registrarPedido", "No hay productos en caché");
            pedidoIdLiveData.postValue(null); // Retorna nulo si no hay productos en caché
            return;
        }

        int negocioId = -1; // Valor predeterminado en caso de error
        for (CarritoItem item : carritoItems) {
            Productos producto = productosMap.get(item.getId_producto());
            if (producto != null) {
                negocioId = producto.getNegocio_id();
                Log.d("registrarPedido", "Negocio ID encontrado: " + negocioId);
                break;
            }
        }

        if (negocioId == -1) {
            Log.d("registrarPedido", "No se encontró un negocio_id válido");
            pedidoIdLiveData.postValue(null); // Retorna nulo si no se encuentra un negocio_id válido
            return;
        }

        Log.d("registrarPedido", "Registrando pedido para el cliente ID: " + clienteId);
        // Se crea el pedido
        Pedidos nuevoPedido = new Pedidos(
                clienteId, // Usar el clienteId obtenido
                negocioId,
                1, // Aquí va el id del repartidor
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()),
                "Pendiente"
        );

        // Se inserta el pedido en la db
        long pedidoId = pedidosRepository.insertarPedido(nuevoPedido);
        Log.e("PEDIDO CREADO","pedidoId: " + pedidoId);

        // Se crean los detalles del pedido
        for (CarritoItem item : carritoItems) {
            Productos producto = productosMap.get(item.getId_producto());
            if (producto != null) {
                PedidoDetalle pedidoDetalle = new PedidoDetalle(
                        (int) pedidoId,
                        item.getId_producto(),
                        item.getCantidad(),
                        producto.getPrecio()
                );
                detalleRepository.insertarDetallePedido(pedidoDetalle);
                Log.e("DETALLE CREADO", "detalle: " + pedidoDetalle);
            } else {
                Log.e("PRODUCTOS NULOS?","null");
            }

        }
        Log.d("registrarPedido", "Pedido registrado con ID: " + pedidoId);
        pedidoIdLiveData.postValue(pedidoId);

    }


    private String getEmail() {
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE);
        return sharedPreferences.getString("email", null);  // Retorna el email almacenado, o null si no existe
    }


}
