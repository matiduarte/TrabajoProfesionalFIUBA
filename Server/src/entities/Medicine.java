package entities;



import javax.xml.bind.annotation.XmlRootElement;

import DataBase.StoreData;

@XmlRootElement
public class Medicine {  
	
private int id;  
private String name;
private String observations;  
  
public Medicine(){}

public Medicine(int id, String name, String observations) {
	this.id = id;
	this.name = name;
	this.observations = observations;
}
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
public String getObservations() {
	return observations;
}
public void setObservations(String observations) {
	this.observations = observations;
}  

public static Medicine getByMedicineId(int medicineId){
	return (Medicine)StoreData.getById(Medicine.class, medicineId);
}
  
public void save(){
	StoreData.save(this);
}
  
}  