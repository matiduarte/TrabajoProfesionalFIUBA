package com.p2.sanatorioborattiapp.Entities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fran on 12/08/16.
 */
public class Bed {

    private int id;
    //private int patientId;
    private int roomId;
    private int x;
    private int y;
    private User patient;

    public Bed() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getPatient() {
        return patient;
    }

    public void setPatient(User patient) {
        this.patient = patient;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public static List<Bed> getBeds(JSONArray bedJsonArray,JSONArray patientsJsonArray) {
        List<Bed> beds = new ArrayList<>();
        for(int i = 0; i < bedJsonArray.length(); i++) {
            try {
                JSONObject bed = (JSONObject) bedJsonArray.get(i);
                JSONObject patient = (JSONObject) patientsJsonArray.get(i);
                Bed b = getBedFromJSONObject(bed,patient);
                beds.add(b);
            } catch (JSONException e ) {
                e.printStackTrace();
            }
        }

        return beds;
    }

    private static Bed getBedFromJSONObject(JSONObject jsonBed, JSONObject jsonPatient) throws JSONException {
        User u = new User();
        u.setFirstName(jsonPatient.getString("firstName"));
        u.setLastName(jsonPatient.getString("lastName"));
        u.setUserId(jsonPatient.getInt("id"));
        Bed b = new Bed();
        b.setId(jsonBed.getInt("id"));
        b.setPatient(u);
        b.setRoomId(jsonBed.getInt("floorId"));
        b.setX(jsonBed.getInt("x"));
        b.setY(jsonBed.getInt("y"));
        return b;
    }
}
