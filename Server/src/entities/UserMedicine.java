package entities;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import DataBase.StoreData;
@XmlRootElement
public class UserMedicine {  
	
private int id;  
private String observations; 
private int medicineId;
private int doctorId;
private int patientId;
private Integer date;
private String doctorName;
private String name;
  
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
public static List<UserMedicine> getMedicinesByPatientId(Integer id) {
	List<UserMedicine> userMedicines = UserMedicine.getByPatientId(id);
		
	//Agrego nombre del medicamento y nombre del medico
	List<UserMedicine> result = new ArrayList<UserMedicine>();
	for (UserMedicine userMedicine : userMedicines) {
		User doctor = User.getById(userMedicine.getDoctorId());
		if(doctor != null){
			userMedicine.setDoctorName(doctor.getFirstName() + " " + doctor.getLastName());
		}
		
		Medicine m = Medicine.getByMedicineId(userMedicine.getMedicineId());
		
		if(m != null){
			userMedicine.setName(m.getName());
		}
		result.add(userMedicine);
	}
	
	return result;
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
  
}  