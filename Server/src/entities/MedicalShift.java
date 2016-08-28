package entities;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import DataBase.StoreData;

@XmlRootElement
public class MedicalShift {

	private int id;
	private int doctorId;
	private String patientName;
	private String date;
	private String time;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
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

	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public static List<MedicalShift> getByDoctorIdAndDate(int doctorId, String date){
		return (List<MedicalShift>)StoreData.getByTwoFields(MedicalShift.class, "doctorId", String.valueOf(doctorId), "date", date);
	}
	public void save(){
		StoreData.save(this);
	}
	
}
