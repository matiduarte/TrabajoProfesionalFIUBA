package com.p2.sanatorioborattiapp.Entities;


import android.content.Context;

import com.p2.sanatorioborattiapp.DataBase.DbHelper;


/**
 * @author DK
 * Representa a un usuario registrado en el sistema.
 */
public class User{

    private long id = 0;
    private int userId = 0;
    private String firstName = "";
    private String lastName = "";
    private String password = "";
    private int isLogged;



    /**
     * Crea un usuario vacio.
     */
    public User(){
    }

    public User(long id, int userId, String firstName, String lastName, String password){
        this.id = id;
        this.userId = userId;;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }



    /**
     * Getter.
     * @return id: identificador para la base de datos
     */
    public long getId(){
        return this.id;
    }
    /**
     * Getter.
     * @return userId: telefono hasheado.
     */
    public int getUserId(){
        return this.userId;
    }


    /**
     * Getter.
     * @return password.
     */
    public String getPassword(){return this.password; }

    /**
     * Setter.
     * @param id identificador de la base de datos.
     */
    public void setId(long id){
        this.id = id;
    }
    /**
     * Setter
     * @param userId
     */
    public void setUserId(int userId){
        this.userId = userId;
    }

    /**
     * Setter
     * @param firstName
     */
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    /**
     * Setter
     * @param lastName
     */
    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    /**
     * Setter
     * @param password
     */
    public void setPassword(String password){this.password = password; }


//Methods

    /**
     * Almacena un usuario en la base de datos.
     * @param context this.
     */
    public void save(Context context) {
        DbHelper helper = new DbHelper(context);
        helper.insertUser(this);
    }


    /**
     * Getter.
     * @return isLogged: verdadero si el usuario esta logueado, falso sino.
     */
    public int getIsLogged() {
        return isLogged;
    }
    /**
     * Setter.
     * @param isLogged isLogged: verdadero si el usuario esta logueado, falso sino.
     */
    public void setIsLogged(int isLogged) {
        this.isLogged = isLogged;
    }


    public String getFirstName() {
        return this.firstName;
    }


    public String getLastName() {
        return this.lastName;
    }
}
