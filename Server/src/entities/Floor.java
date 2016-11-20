package entities;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.annotation.XmlRootElement;

import DataBase.StoreData;

@XmlRootElement
public class Floor {
	
	private int id;
	private String name;
	private byte[] image;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public static Floor getById(int id) {
		return (Floor)StoreData.getById(Floor.class, id);
	}
	
	public byte[] getImage() {
		return image;
	}
	
	public void setImage(byte[] image) {
		this.image = image;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public static long getTotalNumber() {
		return StoreData.getCount(Floor.class);
	}
	
	public void save(){
		StoreData.save(this);
	}
	
	public void setImage(String photoFilePath) throws IOException {
        byte[] imageBytes = readBytesFromFile(photoFilePath);
        setImage(imageBytes);
    }
     
    private byte[] readBytesFromFile(String filePath) throws IOException {
        File inputFile = new File(filePath);
        FileInputStream inputStream = new FileInputStream(inputFile);
         
        byte[] fileBytes = new byte[(int) inputFile.length()];
        inputStream.read(fileBytes);
        inputStream.close();
         
        return fileBytes;
    }
    
    public String getImageAsString() {
    	return DatatypeConverter.printBase64Binary(getImage());
    }
    
    public static List<Floor> getAll(){
    	return (List<Floor>)StoreData.getByField(Floor.class, "1", "1");
    }
    
    public static Floor getByName(String name){
    	List<?> list = StoreData.getByField(Floor.class, "name", name);
    	Floor floor = null;
    	if (list != null && list.size() > 0){
    		floor = (Floor)list.get(0);
    	}
    	return floor;
    }
}
