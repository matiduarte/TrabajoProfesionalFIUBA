package service;

import java.util.HashMap;
import java.util.Map.Entry;
import service.Service;

public class ServiceCreator {
	
	private HashMap<String, Service> listOfServices;
	
	public ServiceCreator(){
		this.listOfServices = new HashMap<>();
	}
	
	public Service create(String serviceName){
		
		for(Entry<String, Service> e : getListOfServices().entrySet()) {
	        String name = e.getKey();
	        Service service = e.getValue();
	        if (name.equals(serviceName))
	        	return service.create();
	    }
		
		return null;
	}

	public HashMap<String, Service> getListOfServices() {
		return listOfServices;
	}

	public void setListOfServices(HashMap<String, Service> listOfServices) {
		this.listOfServices = listOfServices;
	}
}
