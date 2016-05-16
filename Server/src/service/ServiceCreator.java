package service;

import java.util.HashMap;

import service.Service;

public class ServiceCreator {
	
	
	private HashMap<String, Service> listOfServices;
	
	public ServiceCreator(){
		this.setListOfServices(ServiceContainer.getListOfService());
	}
	
	public Service create(String serviceName){
		
		String upperCaseSN = serviceName.toUpperCase();
		Service s = this.getListOfServices().get(upperCaseSN);
		if (s != null)
			return s;
					
		return null;			
	}

	public HashMap<String, Service> getListOfServices() {
		return listOfServices;
	}

	public void setListOfServices(HashMap<String, Service> listOfServices) {
		this.listOfServices = listOfServices;
	}

}
