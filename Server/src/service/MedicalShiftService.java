package service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.List;
import entities.MedicalShift;

@Path("/medicalshift")
public class MedicalShiftService {

	@Path("{date}")
	@GET
	@Produces("application/json")
	public List<MedicalShift> getMedicalShift(@PathParam("date") Integer date){
		
		List<MedicalShift> listOfMedicalShift = MedicalShift.getByDoctorId(date);
		return listOfMedicalShift;
	}
	
	@POST
    @Path("shift")
	@Consumes("application/json")
    public Response saveUserMedicineSupply(MedicalShift medicalShift) {
		
		medicalShift.save();
		String output = medicalShift.toString();
		//TODO: Ver que responder al cliente.
		return Response.status(200).entity(output).build();

    }
}
