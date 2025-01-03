package com.delivery_clientes;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.delivery_clientes.utils.NotificationHelper;
import com.jakewharton.threetenabp.AndroidThreeTen;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ContainerActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private static final int REQUEST_CODE_LOC_PERM = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidThreeTen.init(this);
        setContentView(R.layout.activity_container);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Obtiene el NavHostFragment y su NavController
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView);
        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();

            // Configura la navegación con BottomNavigationView
            NavigationUI.setupWithNavController(bottomNavigationView, navController);

            // Agrega un listener para controlar la navegación y limpiar la pila
            bottomNavigationView.setOnItemSelectedListener(item -> {
                if (item.getItemId() == R.id.homeFragment) {
                    navController.navigate(R.id.homeFragment, null, new NavOptions.Builder()
                            .setPopUpTo(R.id.homeFragment, true) // Elimina todos los fragmentos previos
                            .build());
                    return true;
                } else if (item.getItemId() == R.id.pedidosFragment) {
                    navController.navigate(R.id.pedidosFragment, null, new NavOptions.Builder()
                            .setPopUpTo(R.id.homeFragment, true) // Elimina todos los fragmentos previos
                            .build());
                    return true;
                } else if (item.getItemId() == R.id.profileFragment) {
                    navController.navigate(R.id.profileFragment, null, new NavOptions.Builder()
                            .setPopUpTo(R.id.homeFragment, true) // Elimina todos los fragmentos previos
                            .build());
                    return true;
                }
                return false;
            });

        }

        // Crea el canal de notificación
        NotificationHelper.createNotificationChannel(this);

        if (ActivityCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ){
            ActivityCompat.requestPermissions(this , new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE_LOC_PERM);
            return;
        }
    }

    @Override
    public void onBackPressed() {
        // Obtiene el NavHostFragment
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView);
        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();
            NavDestination currentDestination = navController.getCurrentDestination();

            // Verifica si el destino actual es HomeFragment
            if (currentDestination != null && currentDestination.getId() == R.id.homeFragment) {
                moveTaskToBack(true);
            } else {
                navController.navigate(R.id.homeFragment, null, new NavOptions.Builder()
                        .setPopUpTo(R.id.homeFragment, true)  // Limpia la pila de fragmentos previos
                        .build());
            }
        } else {
            super.onBackPressed();
        }
    }

}