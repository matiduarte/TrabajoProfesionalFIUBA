package com.p2.sanatorioborattiapp.Entities;


import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Study {

    private long id = 0;
    private int patientId = 0;
    private int doctorId = 0;
    private String type = "";
    private String observations = "";
    private String doctorName = "";
    private String patientName = "";
    private String name = "";
    private int priority = 0;


    /**
     * Crea un usuario vacio.
     */
    public Study(){
    }

    public static List<Study> getStudies(JSONArray jsonArray) {
        List<Study> medicines = new ArrayList<Study>();
        for(int i=0; i < jsonArray.length(); i++){
            try {
                JSONObject medicine = (JSONObject)jsonArray.get(i);
                Study u = getStudyFromJSONObject(medicine);
                medicines.add(u);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return medicines;
    }

    @NonNull
    private static Study getStudyFromJSONObject(JSONObject jsonStudy) throws JSONException {
        Study study = new Study();
        study.setId(jsonStudy.getInt("id"));

        if(jsonStudy.has("priority")){
            study.setPriority(jsonStudy.getInt("priority"));
        }
        if(jsonStudy.has("doctorName")){
            study.setDoctorName(jsonStudy.getString("doctorName"));
        }
        if(jsonStudy.has("patientName")){
            study.setPatientName(jsonStudy.getString("patientName"));
        }
        if(jsonStudy.has("name")){
            study.setName(jsonStudy.getString("name"));
        }
        if(jsonStudy.has("doctorId")){
            study.setDoctorId(jsonStudy.getInt("doctorId"));
        }
        if(jsonStudy.has("patientId")){
            study.setPatientId(jsonStudy.getInt("patientId"));
        }
        if(jsonStudy.has("type")){
            study.setType(jsonStudy.getString("type"));
        }
        if(jsonStudy.has("observations")){
            study.setObservations(jsonStudy.getString("observations"));
        }

        return study;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }
}
