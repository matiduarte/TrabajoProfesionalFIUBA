package service;

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


import entities.Study;
import entities.UserTreatment;

@Path("/patientreatments")
public class PatientTreatmentsService {

	private static final Logger logger = Logger.getLogger( PatientTreatmentsService.class.getName() );

	@Path("{id}")
	@GET
	@Produces("application/json")
	public List<UserTreatment> getTreatments(@PathParam("id") Integer id){
		
		List<UserTreatment> listOfTreatments = UserTreatment.getByPatientId(id);	 
		if (listOfTreatments.isEmpty())
			logger.log(Level.INFO, "El paciente solicitado no tiene tratamientos asignados.");
		return listOfTreatments;
	}
	
	@POST
    @Path("treatment")
	@Consumes("application/json")
    public Response saveTreatment(UserTreatment treatment) {
		
		treatment.save();
		String output = treatment.toString();
		//TODO: Ver que responder al cliente.
		return Response.status(200).entity(output).build();

    }
	
}
