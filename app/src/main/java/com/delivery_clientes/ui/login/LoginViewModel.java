package com.delivery_clientes.ui.login;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.delivery_clientes.data.db.AppDatabase;
import com.delivery_clientes.data.db.entities.Usuario;
import com.delivery_clientes.utils.SingleLiveEvent;

public class LoginViewModel extends AndroidViewModel {

    private AppDatabase db;
//    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<LoginResult>();
    private SingleLiveEvent<LoginResult> loginResult = new SingleLiveEvent<>();

    public LoginViewModel(Application application){
        super(application);
        db = Room.databaseBuilder(application, AppDatabase.class, "usuarios-database")
                .fallbackToDestructiveMigration()
                .build();
    }

//    public MutableLiveData<LoginResult> getLoginResult(){
//        return loginResult;
//    }

    public SingleLiveEvent<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String email, String password){
        new Thread(() -> {
            Usuario usuario = db.usuariosDAO().login(email, password);
            if(usuario != null){
                saveLoginState(true); // Guarda que el usuario está logueado
                loginResult.postValue(new LoginResult(true));
            }else {
                saveLoginState(false); // No está logueado
                loginResult.postValue(new LoginResult(false,"Usuario o Contraseña incorrectos."));
            }
        }).start();
    }

    public void logout() {
        saveLoginState(false);
    }

    public void register(Usuario usuario){
        new Thread(() -> {
            db.usuariosDAO().insertUsuario(usuario);
        }).start();
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("isLoggedIn", false); // Por defecto, no está logueado
    }


    public void saveLoginState(boolean isLoggedIn) {
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", isLoggedIn);
        editor.apply();
    }


}