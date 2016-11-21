package com.p2.sanatorioborattiapp.Entities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by fran on 28/08/16.
 */
public class MedicalShift {

    private int id;
    private int doctorId;
    private String patientName;
    private String date;
    private String time;

    public MedicalShift() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public static List<MedicalShift> getShifts(JSONArray shiftsJsonArray) {
        List<MedicalShift> shifts = new ArrayList<>();
        for(int i = 0; i < shiftsJsonArray.length(); i++) {
            try {
                JSONObject shift = (JSONObject) shiftsJsonArray.get(i);
                MedicalShift m = getShiftFromJSONObject(shift);
                shifts.add(m);
            } catch (JSONException e ) {
                e.printStackTrace();
            }
        }

        return shifts;
    }

    private static MedicalShift getShiftFromJSONObject(JSONObject jsonShift) throws JSONException {
        MedicalShift m = new MedicalShift();
        m.setId(jsonShift.getInt("id"));
        m.setDate(jsonShift.getString("fecha"));
        m.setDoctorId(jsonShift.getInt("doctorId"));
        m.setPatientName(jsonShift.getString("patientName"));
        m.setTime(jsonShift.getString("time"));
        return m;
    }

    public static void orderShifts(List<MedicalShift> shifts) {
        Collections.sort(shifts, new Comparator<MedicalShift>() {
            @Override
            public int compare(final MedicalShift object1, final MedicalShift object2) {
                String time1 = object1.getTime();
                String time2 = object2.getTime();
                int hour1 = Integer.parseInt(time1.split(":")[0]);
                int minutes1 = Integer.parseInt(time1.split(":")[1]);
                int hour2 = Integer.parseInt(time2.split(":")[0]);
                int minutes2 = Integer.parseInt(time2.split(":")[1]);
                if (hour1 < hour2) {
                    return -1;
                } else {
                    if (hour1 > hour2) {
                        return 1;
                    } else {
                        if (minutes1 < minutes2) {
                            return -1;
                        } else {
                            if (minutes1 > minutes2) {
                                return 1;
                            } else {
                                return 0;
                            }
                        }
                    }
                }
            }
        } );
    }
}
