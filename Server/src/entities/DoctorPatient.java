package entities;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import javax.xml.bind.annotation.XmlRootElement;

import DataBase.StoreData;
@XmlRootElement
public class DoctorPatient {  
	
private int id;  
private int doctorId;
private int patientId;
  
public int getId() {  
    return id;  
}  
public void setId(int id) {  
    this.id = id;  
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

public static List<DoctorPatient> getByDoctorId(int doctorId){
	return (List<DoctorPatient>)StoreData.getByField(DoctorPatient.class, "doctorId", String.valueOf(doctorId));
}

public static List<DoctorPatient> getByPatientId(int patientId){
	return (List<DoctorPatient>)StoreData.getByField(DoctorPatient.class, "patientId", String.valueOf(patientId));
}

public static  List<User>  getPatientsByDoctorId(int doctorId){
	List<DoctorPatient> listOfDoctorPatients = DoctorPatient.getByDoctorId(doctorId);	 
	List<User> listOfPatients = new ArrayList<User>();

	if(listOfDoctorPatients != null){
		for (DoctorPatient doctorPatient : listOfDoctorPatients) {
			User patient = User.getById(doctorPatient.getPatientId());
			if(patient != null){
				listOfPatients.add(patient);
			}
		}
	}
	
	
	return listOfPatients;
}

public static  List<User>  getDoctorsByPatientId(int patientId){
	List<DoctorPatient> listOfDoctorPatients = DoctorPatient.getByPatientId(patientId);	 
	List<User> listOfDoctors = new ArrayList<User>();

	if(listOfDoctorPatients != null){
		for (DoctorPatient doctorPatient : listOfDoctorPatients) {
			User doctor = User.getById(doctorPatient.getDoctorId());
			if(doctor != null){
				listOfDoctors.add(doctor);
			}
		}
	}
	
	
	return listOfDoctors;
}

public void save(){
	StoreData.save(this);
}

public void delete(){
	StoreData.delete(this);
}

public static void deleteByPatientId(int patientId) {
	List<DoctorPatient> doctorPatientsToDelete = (List<DoctorPatient>)StoreData.getByField(DoctorPatient.class, "patientId", String.valueOf(patientId));
	for (DoctorPatient doctorPatient : doctorPatientsToDelete) {
		doctorPatient.delete();
	}
	
}
  
}  