package entities;

import java.util.List;

import DataBase.StoreData;

public class StudyType {  
	private int id;  
	private String name;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public static List<StudyType> getAll(){
		return (List<StudyType>)StoreData.getByField(StudyType.class, "1", "1");
	}
	
	public void save(){
		StoreData.save(this);
	}

}  