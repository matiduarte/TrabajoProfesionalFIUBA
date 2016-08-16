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
    private int patientId;
    private int roomId;
    private int x;
    private int y;

    public Bed() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
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

    public static List<Bed> getBeds(JSONArray jsonArray) {
        List<Bed> beds = new ArrayList<>();
        for(int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject bed = (JSONObject) jsonArray.get(i);
                Bed b = getBedFromJSONObject(bed);
                beds.add(b);
            } catch (JSONException e ) {
                e.printStackTrace();
            }
        }

        return beds;
    }

    private static Bed getBedFromJSONObject(JSONObject jsonBed) throws JSONException {
        Bed b = new Bed();
        b.setId(jsonBed.getInt("id"));
        b.setPatientId(jsonBed.getInt("patientId"));
        b.setRoomId(jsonBed.getInt("floorId"));
        b.setX(jsonBed.getInt("x"));
        b.setY(jsonBed.getInt("y"));
        return b;
    }
}
