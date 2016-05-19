package entities;

import javax.xml.bind.annotation.XmlRootElement;

import DataBase.StoreData;

@XmlRootElement
public class User {  
	
private int id;  
private String firstName;
private String lastName;
private Integer roleId;

public enum UserRole {
    DOCTOR, PATIENT, NURSE, SECRETARY, ADMINISTRATOR
}
  
public int getId() {  
    return id;  
}  
public void setId(int id) {  
    this.id = id;  
}  
public void setRoleId(Integer roleId) {  
    this.roleId = roleId;  
} 
public Integer getRoleId() {  
    return roleId;  
}  
public String getFirstName() {  
    return firstName;  
}  
public void setFirstName(String firstName) {  
    this.firstName = firstName;  
}  
public String getLastName() {  
    return lastName;  
}  
public void setLastName(String lastName) {  
    this.lastName = lastName;  
}
public UserRole getRole() {
	return UserRole.values()[roleId];
}
public void setRole(UserRole role) {
	this.roleId = role.ordinal();
}  
  
public static User getById(int id){
	return (User)StoreData.getById(User.class, id);
}

public void save(){
	StoreData.save(this);
}

}  