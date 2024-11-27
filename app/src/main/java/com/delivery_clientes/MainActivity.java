package com.delivery_clientes;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.delivery_clientes.data.db.AppDatabase;
import com.delivery_clientes.data.db.dao.CategoriasDAO;
import com.delivery_clientes.data.db.entities.Categorias;
import com.delivery_clientes.data.db.entities.Negocios;
import com.delivery_clientes.data.db.entities.PedidoDetalle;
import com.delivery_clientes.data.db.entities.Pedidos;
import com.delivery_clientes.data.db.entities.Productos;
import com.delivery_clientes.data.db.entities.Seguimiento;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.delivery_clientes.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int SPLASH_SCREEN_TIMEOUT = 3000;

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }, SPLASH_SCREEN_TIMEOUT);

//
//        //Eliminar posteriormente
//
//        //Categorias
//        Categorias categoria1 = new Categorias();
//        categoria1.setNombre("Categoria 1");
//
//        Categorias categoria2 = new Categorias();
//        categoria2.setNombre("Categoria 2");
//
//        Categorias categoria3 = new Categorias();
//        categoria3.setNombre("Categoria 3");
//
//        //Negocio
//        Negocios negocio1 = new Negocios();
//        negocio1.setNombre("Negocio1");
//        negocio1.setCuit("123456");
//        negocio1.setEmail("negocio@email.com");
//        negocio1.setEstado(1);
//        negocio1.setDireccion_id(1);
//        negocio1.setTelefono("385123456");
//
//        //Productos
//        Productos prod1 = new Productos();
//        prod1.setNombre("Producto 1");
//        prod1.setDescripcion("Descripcion 1");
//        prod1.setPrecio(100);
//        prod1.setStock(10);
//
//        prod1.setCategoria_id(1);
//        prod1.setNegocio_id(1);
//

//        Productos prod2 = new Productos();
//        prod2.setNombre("Producto 2");
//        prod2.setDescripcion("Descripcion 2");
//        prod2.setPrecio(2000);
//        prod2.setStock(20);
//        prod2.setCategoria_id(1);
//        prod2.setNegocio_id(1);
//
//
//        //Pedido
//        Pedidos ped = new Pedidos();
//        ped.setCliente_id(2);
//        ped.setEstado("test3");
//        ped.setNegocio_id(2);
//        ped.setRepartidor_id(3);
//        ped.setFecha_pedido("2024-11-25 18:00:00");
//
//        //Pedido-detalle
//        PedidoDetalle det = new PedidoDetalle();
//        det.setPedido_id(ped.getId());
//        det.setCantidad(2);
//        det.setProducto_id(1);
//        det.setPrecio_unitario(3000.0);
//
//        //Seguimiento
//        Seguimiento seg = new Seguimiento();
//        seg.setPedido_id(ped.getId());
//        seg.setEstado("En espera");
//        seg.setFecha_actualizacion("2024-11-25 18:00:00");
//
//        Seguimiento seg2 = new Seguimiento();
//        seg2.setPedido_id(ped.getId());
//        seg2.setEstado("Aceptado");
//        seg2.setFecha_actualizacion("2024-11-25 19:00:00");
//
//        Seguimiento seg3 = new Seguimiento();
//        seg3.setPedido_id(ped.getId());
//        seg3.setEstado("Entregado");
//        seg3.setFecha_actualizacion("2024-11-25 20:30:00");
//
//        new Thread(() -> {
//            AppDatabase db = AppDatabase.getInstance(getApplication());
//            db.categoriasDAO().insertarCategoria(categoria1);
//            db.categoriasDAO().insertarCategoria(categoria2);
//            db.categoriasDAO().insertarCategoria(categoria3);
//
//            db.negociosDAO().insertarNegocio(negocio1);
//
//            db.productosDAO().insertarProducto(prod2);
//            db.pedidosDAO().insertarPedido(ped);
//            db.pedidosDetalleDAO().insertarPedidoDetalle(det);
//            db.seguimientoDAO().insertarSeguimiento(seg);
//            db.seguimientoDAO().insertarSeguimiento(seg2);
//            db.seguimientoDAO().insertarSeguimiento(seg3);
//        }).start();

    }

}