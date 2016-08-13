package entities;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import DataBase.StoreData;

@XmlRootElement
public class Bed {
	
	private int id;
	private int patientId;
	private int roomId;
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
	public int getRoomId() {
		return roomId;
	}
	public void setRoomId(int roomId) {
		this.roomId = roomId;
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
	public static List<Bed> getByRoomId(int room) {
		return (List<Bed>)StoreData.getByField(Bed.class, "roomId", String.valueOf(room));
	}
	public void save(){
		StoreData.save(this);
	}
}
