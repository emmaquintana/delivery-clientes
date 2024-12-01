package com.delivery_clientes.ui.login;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.delivery_clientes.data.db.AppDatabase;
import com.delivery_clientes.data.db.entities.Clientes;
import com.delivery_clientes.data.db.entities.Usuario;
import com.delivery_clientes.data.repository.ClientesRepository;
import com.delivery_clientes.data.repository.UsuariosRepository;
import com.delivery_clientes.utils.SingleLiveEvent;

public class LoginViewModel extends AndroidViewModel {

//    private AppDatabase db;
    private SingleLiveEvent<LoginResult> loginResult = new SingleLiveEvent<>();
    private ClientesRepository clientesRepository;
    private UsuariosRepository usuariosRepository;

    private MutableLiveData<Integer> clienteId = new MutableLiveData<>();

    public LoginViewModel(Application application){
        super(application);
        clientesRepository = new ClientesRepository(application);
        usuariosRepository = new UsuariosRepository(application);
//        db = Room.databaseBuilder(application, AppDatabase.class, "usuarios-database")
//                .fallbackToDestructiveMigration()
//                .build();
    }

    public SingleLiveEvent<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String email, String password){
        new Thread(() -> {
            Usuario usuario = usuariosRepository.login(email, password);
            Log.d("User Login","Usuario - Email: " + usuario.getEmail());
            if(usuario != null){
                saveLoginState(true, email); // Guarda que el usuario está logueado
                loginResult.postValue(new LoginResult(true));
                obtenerClienteId();
            } else {
                saveLoginState(false, null); // No está logueado
                loginResult.postValue(new LoginResult(false,"Usuario o Contraseña incorrectos."));
            }
        }).start();
    }

    public void logout() {
        saveLoginState(false, null);
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("isLoggedIn", false); // Por defecto, no está logueado
    }

    public String getEmail(){
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE);
        return sharedPreferences.getString("email", null);
    }

    public LiveData<Integer> getClienteIdLiveData(){
        return clienteId;
    }

    public void obtenerClienteId(){
        String email = getEmail();
        Log.d("obtenerClienteId", "Email usado: " + email);

        new Thread(() -> {
            Clientes cliente = clientesRepository.obtenerClientesPorEmail(getEmail());
            Log.d("obtenerClienteId", "Cliente obtenido: " + (cliente != null ? cliente.getId() : "null"));
            if(cliente != null){
                clienteId.postValue(cliente.getId());
            } else {
                clienteId.postValue(-1);
            }
        }).start();
    }

    public Integer obtenerClienteActual(){
        Integer id = clienteId.getValue();
        if (id == null) {
            Log.e("LoginViewModel", "El clienteId aún no está inicializado");
            return null;
        }
        return id;
    }


    public void saveLoginState(boolean isLoggedIn, String email) {
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", isLoggedIn);
        editor.putString("email", email);
        editor.apply();
    }


}