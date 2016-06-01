package entities;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import DataBase.StoreData;

@XmlRootElement
public class Room {
	
	private int id;
	private int floor;
	private int x1;
	private int y1;
	private int x2;
	private int y2;
		
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getFloor() {
		return floor;
	}
	public void setFloor(int floor) {
		this.floor = floor;
	}
	public int getX1() {
		return x1;
	}
	public void setX1(int x1) {
		this.x1 = x1;
	}
	public int gety1() {
		return y1;
	}
	public void setY1(int y1) {
		this.y1 = y1;
	}
	public int getX2() {
		return x1;
	}
	public void setX2(int x2) {
		this.x2 = x2;
	}
	public int gety2() {
		return y2;
	}
	public void setY2(int y2) {
		this.y2 = y2;
	}
	public static Room getById(int id) {
		return (Room)StoreData.getById(Room.class, id);
	}
	public static List<Room> getByFloorId(int floor) {
		return (List<Room>)StoreData.getByField(Room.class, "floor", String.valueOf(floor));
	}
	public void save(){
		StoreData.save(this);
	}
}
