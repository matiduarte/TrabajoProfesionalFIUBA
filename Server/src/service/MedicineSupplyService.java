package service;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import entities.Medicine;
import entities.UserMedicine;

@Path("/medicinesupply")
public class MedicineSupplyService implements Service {

	private static final String SERVICE_NAME = "MEDICINE_SUPPLY";

	@Path("{id}")
	@GET
	@Produces("application/json")
	public List<Medicine> getMedicineSupply(@PathParam("id") Integer id){
		
		List<UserMedicine> listOfUserMedicines = UserMedicine.getByPatientId(id);
	    List<Medicine> listOfMedicines = new ArrayList<>();
	    if (listOfMedicines != null){
			for (UserMedicine um : listOfUserMedicines)
				listOfMedicines.add(Medicine.getByMedicineId(um.getMedicineId()));
	    } else {
	    	listOfMedicines.add(new Medicine(-1,"INVALID", "INVALID"));
	    }
	    
		return listOfMedicines;
	}
	
	@POST
    @Path("medicine")
	@Consumes("application/json")
    public Response postStrMsg(Medicine medicine) {
		/*
		 * Guardar los datos en la bdd
		 */
		String output = medicine.toString();
		return Response.status(200).entity(output).build();

    }
	
	public static String getServiceName() {
		return SERVICE_NAME;
	}
	
}
