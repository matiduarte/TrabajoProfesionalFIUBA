package service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import entities.Medicine;

@Path("/medicinesupply")
public class MedicineSupplyService implements Service {

	private static final String SERVICE_NAME = "MEDICINE_SUPPLY";

	@Path("{id}")
	@GET
	@Produces("application/json")
	public Medicine getMedicineSupply(@PathParam("id") Integer id){
		
		/*
		 * Voy a buscar los datos con el id a la bdd
		 * Devuelvo la medicina del paciente. 
		 */
		System.out.println("ENTRE");
		return new Medicine(id, "Paracetamol", "500mg");
	}
	
	@POST
    @Path("/post")
	@Consumes("application/json")
	@Produces("application/json")
    public Medicine postStrMsg(Medicine m) {
        return m;
    }
	
	public static String getServiceName() {
		return SERVICE_NAME;
	}
	
}
