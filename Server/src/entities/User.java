package entities;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import entities.User.UserRole;
import DataBase.StoreData;

@XmlRootElement
public class User {  
	
private int id;
private String dni;
private String firstName;
private String lastName;
private String userName;
private String password;
private Integer roleId;
private byte[] profilePicture;
private String profilePictureString;

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

public String getDni() {
	return dni;
}
public void setDni(String dni) {
	this.dni = dni;
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

public static User getByUserName(String userName){
	List<?> list = StoreData.getByField(User.class, "userName", userName);
	User user = null;
	if(list != null && list.size() > 0){
		user = (User)list.get(0);
	}
	
	return user;
}

public static User getByDNI(String dni){
	List<?> list = StoreData.getByField(User.class, "dni", dni);
	User user = null;
	if(list != null && list.size() > 0){
		user = (User)list.get(0);
	}
	
	return user;
}

public void save(){
	StoreData.save(this);
}
public String getUserName() {
	return userName;
}
public void setUserName(String userName) {
	this.userName = userName;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public static List<User> getByRole(UserRole roleId) {
	return (List<User>)StoreData.getByField(User.class, "roleId", String.valueOf(roleId.ordinal()));
}
public byte[] getProfilePicture() {
	return profilePicture;
}
public void setProfilePicture(byte[] profileImage) {
	this.profilePicture = profileImage;
}
public String getProfilePictureString() {
	return profilePictureString;
}
public void setProfilePictureString(String profilePictureString) {
	this.profilePictureString = profilePictureString;
}

}  