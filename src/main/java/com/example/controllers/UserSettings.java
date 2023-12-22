package com.example.controllers;
import java.io.Serializable;

public class UserSettings implements Serializable {
    private String login;
    private String password; // Новое поле для хранения пароля
    private boolean remember;

    // геттеры и сеттеры
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRemember() {
        return remember;
    }

    public void setRemember(boolean remember) {
        this.remember = remember;
    }
}
