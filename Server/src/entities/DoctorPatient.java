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

public static  List<User>  getPatientsByDoctorId(int doctorId){
	List<DoctorPatient> listOfDoctorPatients = DoctorPatient.getByDoctorId(doctorId);	 
	List<User> listOfPatients = new ArrayList<User>();
	//Heroku log
	System.out.println("doctorId:" + doctorId);
	System.out.println(listOfDoctorPatients.size());
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

public void save(){
	StoreData.save(this);
}
  
}  