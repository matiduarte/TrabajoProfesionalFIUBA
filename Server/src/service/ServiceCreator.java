package service;

import service.Service;

public class ServiceCreator {
	
	
	/**
	 * Cada vez que se agrega un servicio hay que agregar un if
	 * Head First Style
	 * @param serviceName
	 * @return Service
	 */
	public Service create(String serviceName){
		
		String upperCaseSN = serviceName.toUpperCase();
		if (upperCaseSN.equals(TestService.getServiceName()))
			return new TestService();
		
		return null;
		
	}

}
