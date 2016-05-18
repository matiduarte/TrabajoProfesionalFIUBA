package entities;

import java.util.List;

import DataBase.StoreData;

public class UserMedicine {  
	
private int id;  
private String observations; 
private int medicineId;
private int doctorId;
private int patientId;
private int date;
  
public int getId() {  
    return id;  
}  
public void setId(int id) {  
    this.id = id;  
}
public String getObservations() {
	return observations;
}
public void setObservations(String observations) {
	this.observations = observations;
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
public int getDate() {
	return date;
}
public void setDate(int date) {
	this.date = date;
}
public int getMedicineId() {
	return medicineId;
}
public void setMedicineId(int medicineId) {
	this.medicineId = medicineId;
}  

public static List<UserMedicine> getByPatientId(int patienId){
	return (List<UserMedicine>)StoreData.getByField(UserMedicine.class, "patientId", String.valueOf(patienId));
}

public void save(){
	StoreData.save(this);
}
  
}  