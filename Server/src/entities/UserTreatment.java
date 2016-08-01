package entities;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import DataBase.StoreData;

@XmlRootElement
public class UserTreatment {  
	
private int id;  
private String observations; 
private int patientId;
private int doctorId;
private Integer date;
private String doctorName; 
  
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
public Integer getDate() {
	return date;
}
public void setDate(Integer date) {
	this.date = date;
}  

public static List<UserTreatment> getByPatientId(int patienId){
	List<UserTreatment> treatments = (List<UserTreatment>)StoreData.getByField(UserTreatment.class, "patientId", String.valueOf(patienId));
	
	//Agrego nombre de los medicos
	List<UserTreatment> result = new ArrayList<UserTreatment>();
	for (UserTreatment userTreatment : treatments) {
		User doctor = User.getById(userTreatment.getDoctorId());
		if(doctor != null){
			userTreatment.setDoctorName(doctor.getFirstName() + " " + doctor.getFirstName());
		}
		result.add(userTreatment);
	}
	
	return result;
}

public void save(){
	StoreData.save(this);
}
public String getDoctorName() {
	return doctorName;
}
public void setDoctorName(String doctorName) {
	this.doctorName = doctorName;
}  
  
}  