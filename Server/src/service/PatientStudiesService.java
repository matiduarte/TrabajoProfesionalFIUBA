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

import entities.Medicine;
import entities.Study;
import entities.StudyType;
import entities.UserMedicine;

@Path("/patientstudies")
public class PatientStudiesService {

	private static final Logger logger = Logger.getLogger( PatientStudiesService.class.getName() );

	@Path("{id}")
	@GET
	@Produces("application/json")
	public ServiceResponse getPatientStudies(@PathParam("id") Integer id){
		
		List<Study> listOfStudies = Study.getByPatientId(id);	 
		if (!listOfStudies.isEmpty()){
	    	JSONObject jo = new JSONObject();
			try {
				Gson g = new Gson();
				String studiesString = g.toJson(listOfStudies);
				jo.put("studies", studiesString);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return new ServiceResponse(true, "", jo.toString());
	    } else {
	    	logger.log(Level.WARNING, "El usuario no posee ningun estudio asignado.");
	    }
		
		return new ServiceResponse(false, "", "");
	}
	
	@POST
    @Path("")
	@Consumes("application/x-www-form-urlencoded")
	@Produces("application/json")
    public ServiceResponse saveUserStudy(@FormParam("patientId")int patientId, @FormParam("doctorId")int doctorId, @FormParam("studyType")String studyType, @FormParam("priority")int priority, @FormParam("observations")String observations) {
		Study study = new Study();
		study.setPatientId(patientId);
		study.setDoctorId(doctorId);
		study.setType(studyType);
		study.setPriority(priority);
		study.setObservations(observations);
		study.save();
		
		return new ServiceResponse(true, "", "");

    }
	
	@Path("")
	@GET
	@Produces("application/json")
	public ServiceResponse geAllStudies(){
		
		List<StudyType> studies = StudyType.getAll();
	    if (!studies.isEmpty()){
	    	JSONObject jo = new JSONObject();
			try {
				Gson g = new Gson();
				String studiesString = g.toJson(studies);
				jo.put("studies", studiesString);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return new ServiceResponse(true, "", jo.toString());
	    } else {
	    	logger.log(Level.WARNING, "No hay estudios guardados en la base de datos.");
	    }
	    
	    return new ServiceResponse(false, "", "");
	}
	
}
