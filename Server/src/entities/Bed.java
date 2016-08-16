package entities;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import DataBase.StoreData;

@XmlRootElement
public class Bed {
	
	private int id;
	private int patientId;
	private int floorId;
	private int x;
	private int y;
	
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

	public int getFloorId() {
		return floorId;
	}
	public void setFloorId(int floorId) {
		this.floorId = floorId;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public static Bed getById(int id) {
		return (Bed)StoreData.getById(Bed.class, id);
	}
	public static List<Bed> getByFloorId(int room) {
		return (List<Bed>)StoreData.getByField(Bed.class, "floorId", String.valueOf(room));
	}
	public void save(){
		StoreData.save(this);
	}
}
