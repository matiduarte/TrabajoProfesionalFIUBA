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

@Path("/patientstudies")
public class PatientStudiesService {

	private static final Logger logger = Logger.getLogger( PatientStudiesService.class.getName() );

	@Path("{id}")
	@GET
	@Produces("application/json")
	public List<Study> getPatientStudies(@PathParam("id") Integer id){
		
		List<Study> listOfStudies = Study.getByPatientId(id);	 
		if (listOfStudies.isEmpty())
			logger.log(Level.INFO, "El paciente solicitado no tiene medicamentos asignados.");
		return listOfStudies;
	}
	
	@POST
    @Path("study")
	@Consumes("application/json")
    public Response saveUserStudy(Study study) {
		
		study.save();
		String output = study.toString();
		//TODO: Ver que responder al cliente.
		return Response.status(200).entity(output).build();

    }
	
}
