package com.delivery_clientes.ui.login;

public class LoginResult {
    private boolean success;
    private String error;

    public LoginResult(boolean success){
        this.success = success;
        this.error = "Login Exitoso";
    }

    public LoginResult(boolean success, String error){
        this.success = success;
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
