package com.delivery_clientes.ui.register;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.delivery_clientes.data.db.AppDatabase;
import com.delivery_clientes.data.db.entities.Usuario;


public class SignUpViewModel extends AndroidViewModel {

    private AppDatabase db;
    private MutableLiveData<SignUpResult> signUpResult = new MutableLiveData<>();

    public SignUpViewModel(Application application){
        super(application);
        db = Room.databaseBuilder(application, AppDatabase.class, "usuarios-database")
                .fallbackToDestructiveMigration()
                .build();
    }

    public MutableLiveData<SignUpResult> getSignUpResult() {
        return signUpResult;
    }

    public void register(String username, String password){
        new Thread(() -> {
            Usuario usuarioExistente = db.usuariosDAO().findUserByUsername(username);
            if(usuarioExistente != null){
                signUpResult.postValue(new SignUpResult(false,"Nombre de usuario en uso"));
            } else {
                db.usuariosDAO().insertUsuario(new Usuario(username,password));
                signUpResult.postValue(new SignUpResult(true,"Registro exitoso"));
            }
        }).start();
    }

}
