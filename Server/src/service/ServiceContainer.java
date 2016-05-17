package service;

import java.util.HashMap;

/**
 * Cada vez que se cree un servicio nuevo se lo debe agregar a esta clase
 * @author matias
 *
 */
public class ServiceContainer {
	
	private static HashMap <String, Service> listOfService;
	
	/**
	 * Agregar los nuevos servicios a la lista
	 * @return listOfService
	 */
	public static HashMap<String, Service> getListOfService(){
		
		listOfService = new HashMap<>();
		listOfService.put(MedicineSupplyService.getServiceName(), new MedicineSupplyService());
		return listOfService;
	}

}
