package com.delivery_clientes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.delivery_clientes.data.db.AppDatabase;
import com.delivery_clientes.data.db.dao.CategoriasDAO;
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

        if(!getDB()){
            cargarDB();
        }

    }

    private void cargarDB() {
        //Categorias
        Categorias categoria1 = new Categorias();
        categoria1.setNombre("Cerveza");

        Categorias categoria2 = new Categorias();
        categoria2.setNombre("Gaseosa");

        Categorias categoria3 = new Categorias();
        categoria3.setNombre("Vodka");

        Categorias categoria4 = new Categorias();
        categoria4.setNombre("Energizantes");

        Categorias categoria5 = new Categorias();
        categoria5.setNombre("Fernet");

        Categorias categoria6 = new Categorias();
        categoria6.setNombre("Whiskey");

        //Negocio
        Negocios negocio1 = new Negocios();
        negocio1.setNombre("Negocio1");
        negocio1.setCuit("123456");
        negocio1.setEmail("negocio@email.com");
        negocio1.setEstado(1);
        negocio1.setDireccion_id(0);
        negocio1.setTelefono("385123456");

        //Productos
        Productos prod1 = new Productos();
        prod1.setNombre("Fernet");
        prod1.setDescripcion("Fernet 1L");
        prod1.setPrecio(11500);
        prod1.setStock(10);
        prod1.setImagen("https://w7.pngwing.com/pngs/359/583/png-transparent-fernet-liqueur-vermouth-distilled-beverage-beer-beer-food-distilled-beverage-alcohol-by-volume-thumbnail.png");
        prod1.setCategoria_id(5);
        prod1.setNegocio_id(1);


        Productos prod2 = new Productos();
        prod2.setNombre("Smirnoff");
        prod2.setDescripcion("700ml");
        prod2.setPrecio(8000);
        prod2.setStock(20);
        prod2.setCategoria_id(3);
        prod2.setNegocio_id(1);
        prod2.setImagen("https://i.imgur.com/IPUFffR.jpeg");

        Productos prod3 = new Productos();
        prod3.setNombre("Jack Daniels");
        prod3.setDescripcion("Whiskey 1L");
        prod3.setPrecio(47555);
        prod3.setStock(50);
        prod3.setCategoria_id(6);
        prod3.setNegocio_id(1);
        prod3.setImagen("https://i.imgur.com/edIzUFC.png");

        Productos prod4 = new Productos();
        prod4.setNombre("Coca Cola");
        prod4.setDescripcion("Plastico 2.25L");
        prod4.setPrecio(3325);
        prod4.setStock(20);
        prod4.setCategoria_id(2);
        prod4.setNegocio_id(1);
        prod4.setImagen("https://http2.mlstatic.com/D_NQ_NP_617991-MLA54054408366_022023-O.webp");

        Productos prod5 = new Productos();
        prod5.setNombre("Red Bull");
        prod5.setDescripcion("Lata 250ml");
        prod5.setStock(15);
        prod5.setPrecio(3500);
        prod5.setNegocio_id(1);
        prod5.setCategoria_id(4);
        prod5.setImagen("https://w7.pngwing.com/pngs/200/1006/png-transparent-250-ml-red-bull-energy-drink-can-energy-drink-red-bull-soft-drink-monster-energy-ounce-red-bull-grocery-store-carbonated-water-aluminum-can-thumbnail.png");

        Productos prod6 = new Productos();
        prod6.setNombre("Sprite");
        prod6.setDescripcion("Plastico 2.25L");
        prod6.setPrecio(3125);
        prod6.setStock(20);
        prod6.setCategoria_id(2);
        prod6.setNegocio_id(1);
        prod6.setImagen("https://w7.pngwing.com/pngs/667/773/png-transparent-sprite-liquor-bottle-sprite-soft-drink-sprite-plastic-bottle-package-mineral-water-thumbnail.png\n");

        Productos prod7 = new Productos();
        prod7.setNombre("Cerveza Corona");
        prod7.setDescripcion("330ml");
        prod7.setPrecio(3500);
        prod7.setStock(20);
        prod7.setCategoria_id(1);
        prod7.setNegocio_id(1);
        prod7.setImagen("https://w7.pngwing.com/pngs/405/956/png-transparent-corona-extra-beer-bottle-corona-pale-lager-beer-grupo-modelo-beer-corona-distilled-beverage-beer-bottle-alcohol-by-volume-thumbnail.png");

        Productos prod8 = new Productos();
        prod8.setNombre("Cerveza Heineken");
        prod8.setDescripcion("330ml");
        prod8.setPrecio(1500);
        prod8.setStock(20);
        prod8.setCategoria_id(1);
        prod8.setNegocio_id(1);
        prod8.setImagen("https://w7.pngwing.com/pngs/660/293/png-transparent-heineken-premium-light-pale-lager-beer-beer-alcohol-by-volume-beer-bottle-beer-thumbnail.png");

        Productos prod9 = new Productos();
        prod9.setNombre("Cerveza SKOL");
        prod9.setDescripcion("350ml");
        prod9.setPrecio(2500);
        prod9.setStock(20);
        prod9.setCategoria_id(1);
        prod9.setNegocio_id(1);
        prod9.setImagen("https://w7.pngwing.com/pngs/801/94/png-transparent-brahma-beer-low-alcohol-beer-skol-beverage-can-gelada-baboon-food-beer-liber-thumbnail.png");


        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(getApplication());
            db.categoriasDAO().insertarCategoria(categoria1);
            db.categoriasDAO().insertarCategoria(categoria2);
            db.categoriasDAO().insertarCategoria(categoria3);
            db.categoriasDAO().insertarCategoria(categoria4);
            db.categoriasDAO().insertarCategoria(categoria5);
            db.categoriasDAO().insertarCategoria(categoria6);

            db.negociosDAO().insertarNegocio(negocio1);

            db.productosDAO().insertarProducto(prod1);
            db.productosDAO().insertarProducto(prod2);
            db.productosDAO().insertarProducto(prod3);
            db.productosDAO().insertarProducto(prod4);
            db.productosDAO().insertarProducto(prod5);
            db.productosDAO().insertarProducto(prod6);
            db.productosDAO().insertarProducto(prod7);
            db.productosDAO().insertarProducto(prod8);
            db.productosDAO().insertarProducto(prod9);

            crearCuenta();

            SharedPreferences sharedPreferences = getApplication().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("db", true);
            editor.apply();
        }).start();

    }

    private void crearCuenta() {
        Direcciones dir = new Direcciones(null, 0, 0);
        Clientes clientes = new Clientes(1, "1990-01-01", "3855123456", "usr", "Perez", "Juan");
        Usuario usuario = new Usuario("usr", "usr");

        AppDatabase db = AppDatabase.getInstance(getApplication());
        long id_dir = db.direccionesDAO().insertarDireccion(dir);
        clientes.setDireccion_id((int) id_dir);
        long id_cliente = db.clientesDAO().insertarCliente(clientes);
        usuario.setClientes_id((int) id_cliente);
        db.usuariosDAO().insertUsuario(usuario);


    }

    public boolean getDB(){
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("db", false);
    }

}