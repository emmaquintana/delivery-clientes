package com.delivery_clientes.ui.login;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.delivery_clientes.data.db.AppDatabase;
import com.delivery_clientes.data.db.entities.Usuario;

public class LoginViewModel extends AndroidViewModel {

    private AppDatabase db;
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<LoginResult>();

    public LoginViewModel(Application application){
        super(application);
        db = Room.databaseBuilder(application, AppDatabase.class, "usuarios-database")
                .fallbackToDestructiveMigration()
                .build();
    }

    public MutableLiveData<LoginResult> getLoginResult(){
        return loginResult;
    }

    public void login(String email, String password){
        new Thread(() -> {
            Usuario usuario = db.usuariosDAO().login(email, password);
            if(usuario != null){
                loginResult.postValue(new LoginResult(true));
            }else {
                loginResult.postValue(new LoginResult(false,"Usuario o Contraseña incorrectos."));
            }
        }).start();
    }

    public void register(Usuario usuario){
        new Thread(() -> {
            db.usuariosDAO().insertUsuario(usuario);
        }).start();
    }
}