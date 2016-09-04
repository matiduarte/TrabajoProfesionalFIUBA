package entities;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import DataBase.StoreData;

@XmlRootElement
public class Study {  
	
private int id;  
private String type;
private String observations; 
private int patientId;
private int doctorId;
private String date;
private int priority;
private String doctorName;
private String patientName;
  
public int getId() {  
    return id;  
}  
public void setId(int id) {  
    this.id = id;  
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
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
public String getDate() {
	return date;
}
public void setDate(String date) {
	this.date = date;
}  

public static List<Study> getByPatientId(int patienId){
	List<Study> studies = (List<Study>)StoreData.getByField(Study.class, "patientId", String.valueOf(patienId));
	
	//Agrego nombre del medicamento y nombre del medico
	List<Study> result = new ArrayList<Study>();
	for (Study study : studies) {
		User doctor = User.getById(study.getDoctorId());
		if(doctor != null){
			study.setDoctorName(doctor.getFirstName() + " " + doctor.getLastName());
		}
		result.add(study);
	}
	
	return result;
}

public static List<Study> getByDoctorId(int doctorId){
	List<Study> result = new ArrayList<Study>();
	
	List<User> patients = DoctorPatient.getPatientsByDoctorId(doctorId);
	for (User patient : patients) {
		List<Study> studies = (List<Study>)StoreData.getByField(Study.class, "patientId", String.valueOf(patient.getId()));
		
		//Agrego nombre del medicamento y nombre del medico
		
		for (Study study : studies) {
			User doctor = User.getById(study.getDoctorId());
			if(doctor != null){
				study.setDoctorName(doctor.getFirstName() + " " + doctor.getLastName());
			}
			
			study.setPatientName(patient.getFirstName() + " " + patient.getLastName());
			result.add(study);
		}
	}
	
	return result;
}

public void save(){
	StoreData.save(this);
}
public int getPriority() {
	return priority;
}
public void setPriority(int priority) {
	this.priority = priority;
}
public String getDoctorName() {
	return doctorName;
}
public void setDoctorName(String doctorName) {
	this.doctorName = doctorName;
}
public String getPatientName() {
	return patientName;
}
public void setPatientName(String patientName) {
	this.patientName = patientName;
}  
  
}  