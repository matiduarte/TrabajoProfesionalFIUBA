package service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
public class MedicineSupplyService {

	private static final String SERVICE_NAME = "MEDICINE_SUPPLY";
	private static final Logger logger = Logger.getLogger( MedicineSupplyService.class.getName() );

	@Path("{id}")
	@GET
	@Produces("application/json")
	public List<Medicine> getMedicineSupply(@PathParam("id") Integer id){
		
		List<UserMedicine> listOfUserMedicines = UserMedicine.getByPatientId(id);
	    List<Medicine> listOfMedicines = new ArrayList<>();
	    if (!listOfUserMedicines.isEmpty()){
			for (UserMedicine um : listOfUserMedicines)
				listOfMedicines.add(Medicine.getByMedicineId(um.getMedicineId()));
	    } else {
	    	logger.log(Level.WARNING, "El usuario no posee ningun medicamento asignado.");
	    }
	    
		return listOfMedicines;
	}
	
	@POST
    @Path("medicine")
	@Consumes("application/json")
    public Response saveUserMedicineSupply(UserMedicine userMedicine) {
		
		userMedicine.save();
		String output = userMedicine.toString();
		//TODO: Ver que responder al cliente.
		return Response.status(200).entity(output).build();

    }
	
	public static String getServiceName() {
		return SERVICE_NAME;
	}
	
}
