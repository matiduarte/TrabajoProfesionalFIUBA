package service;

import java.util.HashMap;
/**
 * Cada vez que se cree un servicio nuevo
 * se lo debe agregar a esta clase
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
		listOfService.put(TestService.getServiceName(), new TestService());
		listOfService.put(TesService2.getServiceName(), new TesService2());
		return listOfService;
	}

}
