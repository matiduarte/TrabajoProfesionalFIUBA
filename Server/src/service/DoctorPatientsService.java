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

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import entities.DoctorPatient;
import entities.Study;
import entities.User;

@Path("/doctorpatients")
public class DoctorPatientsService {

	private static final Logger logger = Logger.getLogger( DoctorPatientsService.class.getName() );

	@Path("{id}")
	@GET
	@Produces("application/json")
	public ServiceResponse getDoctorPatients(@PathParam("id") Integer id){

		JSONObject jo = new JSONObject();
		try {
			jo.put("patients", DoctorPatient.getPatientsByDoctorId(id));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return new ServiceResponse(true, "", jo.toString());
	}
	
}
