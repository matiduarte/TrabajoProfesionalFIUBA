package entities;

import javax.xml.bind.annotation.XmlRootElement;

import DataBase.StoreData;

@XmlRootElement
public class Floor {
	
	private int id;
	private int width;
	private int length;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public static Floor getById(int id) {
		return (Floor)StoreData.getById(Floor.class, id);
	}
	public void save(){
		StoreData.save(this);
	}
}
