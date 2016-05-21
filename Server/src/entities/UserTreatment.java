package entities;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import DataBase.StoreData;

@XmlRootElement
public class UserTreatment {  
	
private int id;  
private String observations; 
private int patientId;
private int doctorId;
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

public static List<UserTreatment> getByPatientId(int patienId){
	return (List<UserTreatment>)StoreData.getByField(UserTreatment.class, "patientId", String.valueOf(patienId));
}

public void save(){
	StoreData.save(this);
}  
  
}  