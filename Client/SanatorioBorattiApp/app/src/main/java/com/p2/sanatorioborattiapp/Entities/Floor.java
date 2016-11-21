package com.p2.sanatorioborattiapp.Entities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fran on 12/08/16.
 */
public class Floor {

    private int id;
    private String name;

    public Floor() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static List<Floor> getFloors(JSONArray floorsJsonArray) {
        List<Floor> beds = new ArrayList<>();
        for(int i = 0; i < floorsJsonArray.length(); i++) {
            try {
                JSONObject floor = (JSONObject) floorsJsonArray.get(i);
                Floor b = getFloorFromJSONObject(floor);
                beds.add(b);
            } catch (JSONException e ) {
                e.printStackTrace();
            }
        }

        return beds;
    }

    private static Floor getFloorFromJSONObject(JSONObject jsonBed) throws JSONException {
        Floor f = new Floor();
        f.setId(jsonBed.getInt("id"));
        f.setName(jsonBed.getString("name"));
        return f;
    }
}
