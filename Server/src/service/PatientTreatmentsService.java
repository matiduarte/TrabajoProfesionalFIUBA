package service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.google.gson.Gson;

import entities.DoctorPatient;
import entities.Study;
import entities.UserTreatment;

@Path("/patienttreatments")
public class PatientTreatmentsService {

	private static final Logger logger = Logger.getLogger( PatientTreatmentsService.class.getName() );

	@Path("{id}")
	@GET
	@Produces("application/json")
	public ServiceResponse getTreatments(@PathParam("id") Integer id){
		
		List<UserTreatment> listOfTreatments = UserTreatment.getByPatientId(id);	 
		if (listOfTreatments.isEmpty()){
			logger.log(Level.INFO, "El paciente solicitado no tiene tratamientos asignados.");
		}
		
		JSONObject jo = new JSONObject();
		try {
			Gson g = new Gson();
			String patientsString = g.toJson(listOfTreatments);
			jo.put("treatments", patientsString);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return new ServiceResponse(true, "", jo.toString());
	}
	
	@POST
	@Consumes("application/x-www-form-urlencoded")
	@Produces("application/json")
    public ServiceResponse saveTreatment(@FormParam("patientId")int patientId, @FormParam("doctorId")int doctorId, @FormParam("observations")String observations) {
		UserTreatment treatment = new UserTreatment();
		treatment.setDoctorId(doctorId);
		treatment.setPatientId(patientId);
		treatment.setObservations(observations);
		
		treatment.save();
		return new ServiceResponse(true, "", "");

    }
	
}
