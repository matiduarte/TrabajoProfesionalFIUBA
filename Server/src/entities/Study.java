package entities;


public class Study {  
	
private int id;  
private String type;
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
public int getDate() {
	return date;
}
public void setDate(int date) {
	this.date = date;
}  

  
  
}  