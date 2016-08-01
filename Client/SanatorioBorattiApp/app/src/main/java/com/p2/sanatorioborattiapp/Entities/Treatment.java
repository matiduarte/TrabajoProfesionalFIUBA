package com.p2.sanatorioborattiapp.Entities;


import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * @author DK
 * Representa a un usuario registrado en el sistema.
 */
public class Treatment {

    private long id = 0;
    private int patientId = 0;
    private int doctorId = 0;
    private String observations = "";
    private String doctorName = "";



    /**
     * Crea un usuario vacio.
     */
    public Treatment(){
    }

    public static List<Treatment> getTreatments(JSONArray jsonArray) {
        List<Treatment> patients = new ArrayList<Treatment>();
        for(int i=0; i < jsonArray.length(); i++){
            try {
                JSONObject patient = (JSONObject)jsonArray.get(i);
                Treatment u = getUserFromJSONObject(patient);
                patients.add(u);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return patients;
    }

    @NonNull
    private static Treatment getUserFromJSONObject(JSONObject jsonTreatment) throws JSONException {
        Treatment t = new Treatment();
        t.setId(jsonTreatment.getInt("id"));
        t.setObservations(jsonTreatment.getString("observations"));
        t.setPatientId(jsonTreatment.getInt("patientId"));
        t.setDoctorId(jsonTreatment.getInt("doctorId"));
        t.setDoctorName(jsonTreatment.getString("doctorName"));

        return t;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }
}
