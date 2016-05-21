package entities;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import DataBase.StoreData;

@XmlRootElement
public class MedicalShift {

	private int id;
	private int doctorId;
	private int date;
	private String patientName;
	
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
	public int getDate() {
		return date;
	}
	public void setDate(int date) {
		this.date = date;
	}
	public static List<MedicalShift> getByDoctorId(int doctorId){
		return (List<MedicalShift>)StoreData.getByField(MedicalShift.class, "doctorId", String.valueOf(doctorId));
	}
	public void save(){
		StoreData.save(this);
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	
}
