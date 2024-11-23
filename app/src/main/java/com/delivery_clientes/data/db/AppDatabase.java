package com.delivery_clientes.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.delivery_clientes.data.db.dao.CategoriasDAO;
import com.delivery_clientes.data.db.dao.ClientesDAO;
import com.delivery_clientes.data.db.dao.DireccionesDAO;
import com.delivery_clientes.data.db.dao.NegociosDAO;
import com.delivery_clientes.data.db.dao.PedidosDAO;
import com.delivery_clientes.data.db.dao.PedidosDetalleDAO;
import com.delivery_clientes.data.db.dao.ProductosDAO;
import com.delivery_clientes.data.db.dao.RepartidoresDAO;
import com.delivery_clientes.data.db.dao.SeguimientoDAO;
import com.delivery_clientes.data.db.dao.UsuariosDAO;
import com.delivery_clientes.data.db.entities.Categorias;
import com.delivery_clientes.data.db.entities.Clientes;
import com.delivery_clientes.data.db.entities.Direcciones;
import com.delivery_clientes.data.db.entities.Negocios;
import com.delivery_clientes.data.db.entities.PedidoDetalle;
import com.delivery_clientes.data.db.entities.Pedidos;
import com.delivery_clientes.data.db.entities.Productos;
import com.delivery_clientes.data.db.entities.Repartidores;
import com.delivery_clientes.data.db.entities.Seguimiento;
import com.delivery_clientes.data.db.entities.Usuario;

@Database(entities = {Usuario.class,
        Categorias.class,
        Clientes.class,
        Direcciones.class,
        Negocios.class,
        Pedidos.class,
        PedidoDetalle.class,
        Productos.class,
        Repartidores.class,
        Seguimiento.class},
        version = 4)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UsuariosDAO usuariosDAO();
    public abstract CategoriasDAO categoriasDAO();
    public abstract ClientesDAO clientesDAO();
    public abstract DireccionesDAO direccionesDAO();
    public abstract NegociosDAO negociosDAO();
    public abstract PedidosDAO pedidosDAO();
    public abstract PedidosDetalleDAO pedidosDetalleDAO();
    public abstract ProductosDAO productosDAO();
    public abstract RepartidoresDAO repartidoresDAO();
    public abstract SeguimientoDAO seguimientoDAO();

}
