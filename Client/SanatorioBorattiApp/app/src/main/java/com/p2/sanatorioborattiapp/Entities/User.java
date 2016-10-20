package com.p2.sanatorioborattiapp.Entities;


import android.content.Context;
import android.support.annotation.NonNull;

import com.p2.sanatorioborattiapp.DataBase.DbHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * @author DK
 * Representa a un usuario registrado en el sistema.
 */
public class User{

    private long id = 0;
    private int userId = 0;
    private String firstName = "";
    private String lastName = "";
    private String userName = "";
    private String password = "";
    private String profileImage = "";
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
     * Getter.
     * @return userName.
     */
    public String getUserName(){return this.userName; }

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
     * @param userName
     */
    public void setUserName(String userName){
        this.userName = userName;
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

    public static List<User> getPatients(JSONArray jsonArray) {
        List<User> patients = new ArrayList<User>();
        for(int i=0; i < jsonArray.length(); i++){
            try {
                JSONObject patient = (JSONObject)jsonArray.get(i);
                User u = getUserFromJSONObject(patient);
                patients.add(u);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return patients;
    }

    @NonNull
    public static User getUserFromJSONObject(JSONObject jsonUser) throws JSONException {
        User u = new User();
        u.setUserName(jsonUser.getString("userName"));
        u.setFirstName(jsonUser.getString("firstName"));
        u.setLastName(jsonUser.getString("lastName"));

        if(jsonUser.has("profilePictureString")){
            u.setProfileImage(jsonUser.getString("profilePictureString"));
        }

        u.setUserId(jsonUser.getInt("id"));
        return u;
    }

    public String getCompleteName() {
        return this.getFirstName() + " " + this.getLastName();
    }


    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
