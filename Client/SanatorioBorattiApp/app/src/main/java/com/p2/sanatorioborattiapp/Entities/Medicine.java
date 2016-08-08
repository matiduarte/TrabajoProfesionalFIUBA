package com.p2.sanatorioborattiapp.Entities;


import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Medicine {

    private long id = 0;
    private int patientId = 0;
    private int doctorId = 0;
    private int medicineId = 0;
    private String observations = "";
    private String doctorName = "";
    private String name = "";



    /**
     * Crea un usuario vacio.
     */
    public Medicine(){
    }

    public static List<Medicine> getMedidines(JSONArray jsonArray) {
        List<Medicine> medicines = new ArrayList<Medicine>();
        for(int i=0; i < jsonArray.length(); i++){
            try {
                JSONObject medicine = (JSONObject)jsonArray.get(i);
                Medicine u = getStudyFromJSONObject(medicine);
                medicines.add(u);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return medicines;
    }

    @NonNull
    private static Medicine getStudyFromJSONObject(JSONObject jsonTreatment) throws JSONException {
        Medicine medicine = new Medicine();
        medicine.setId(jsonTreatment.getInt("id"));

        if(jsonTreatment.has("observations")){
            medicine.setObservations(jsonTreatment.getString("observations"));
        }
        if(jsonTreatment.has("doctorName")){
            medicine.setDoctorName(jsonTreatment.getString("doctorName"));
        }
        if(jsonTreatment.has("name")){
            medicine.setName(jsonTreatment.getString("name"));
        }
        if(jsonTreatment.has("doctorId")){
            medicine.setDoctorId(jsonTreatment.getInt("doctorId"));
        }
        if(jsonTreatment.has("patientId")){
            medicine.setPatientId(jsonTreatment.getInt("patientId"));
        }
        if(jsonTreatment.has("medicineId")){
            medicine.setMedicineId(jsonTreatment.getInt("medicineId"));
        }

        return medicine;
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

    public int getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(int medicineId) {
        this.medicineId = medicineId;
    }
}
