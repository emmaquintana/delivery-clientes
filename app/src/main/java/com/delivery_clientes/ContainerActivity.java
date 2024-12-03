package com.delivery_clientes;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.delivery_clientes.utils.NotificationHelper;
import com.jakewharton.threetenabp.AndroidThreeTen;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ContainerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidThreeTen.init(this);
        setContentView(R.layout.activity_container);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Obtiene el NavHostFragment y su NavController
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView);
        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();

            // Configura la navegación con BottomNavigationView
            NavigationUI.setupWithNavController(bottomNavigationView, navController);
        }

        // Crear el canal de notificación
        NotificationHelper.createNotificationChannel(this);
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
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }
}