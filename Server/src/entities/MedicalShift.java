package entities;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import DataBase.StoreData;

@XmlRootElement
public class MedicalShift {

	private int id;
	private int doctorId;
	private int patientId;
	private String doctorName;
	private String patientName;
	private String fecha;
	private String time;
	
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
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
	
	public String getDoctorName() {
		return doctorName;
	}
	
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	
	public int getPatientId() {
		return patientId;
	}
	
	public void setPatientId(int patientId) {
		this.patientId = patientId;
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
	
	public static MedicalShift getById(int id){
		return (MedicalShift)StoreData.getById(MedicalShift.class, id);
	}
	
	public static List<MedicalShift> getByDoctorIdAndDate(int doctorId, String date){
		return (List<MedicalShift>)StoreData.getByTwoFields(MedicalShift.class, "doctorId", String.valueOf(doctorId), "fecha", date);
	}
	
	public static List<MedicalShift> getAll(){
		return (List<MedicalShift>)StoreData.getByField(MedicalShift.class, "1", "1");
	}
	
	public void save(){
		StoreData.save(this);
	}
	
}
