package service;

import java.util.ArrayList;
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
import entities.UserMedicine;

@Path("/medicinesupply")
public class MedicineSupplyService {

	private static final String SERVICE_NAME = "MEDICINE_SUPPLY";
	private static final Logger logger = Logger.getLogger( MedicineSupplyService.class.getName() );

	@Path("{id}")
	@GET
	@Produces("application/json")
	public ServiceResponse getMedicineSupply(@PathParam("id") Integer id){
		
		List<UserMedicine> listOfUserMedicines = UserMedicine.getMedicinesByPatientId(id);
	    if (!listOfUserMedicines.isEmpty()){
	    	JSONObject jo = new JSONObject();
			try {
				Gson g = new Gson();
				String medicinesString = g.toJson(listOfUserMedicines);
				jo.put("medicines", medicinesString);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return new ServiceResponse(true, "", jo.toString());
	    } else {
	    	logger.log(Level.WARNING, "El usuario no posee ningun medicamento asignado.");
	    }
	    
	    return new ServiceResponse(false, "", "");
	}
	
	@POST
    @Path("")
	@Consumes("application/x-www-form-urlencoded")
	@Produces("application/json")
    public ServiceResponse saveUserMedicineSupply(@FormParam("patientId")int patientId, @FormParam("doctorId")int doctorId, @FormParam("medicineId")int medicineId,@FormParam("observations")String observations) {
		UserMedicine userMedicine = new UserMedicine();
		userMedicine.setPatientId(patientId);
		userMedicine.setDoctorId(doctorId);
		userMedicine.setMedicineId(medicineId);
		userMedicine.setObservations(observations);
		userMedicine.save();
		
		return new ServiceResponse(true, "", "");
    }
	
	@POST
    @Path("/delete")
	@Consumes("application/x-www-form-urlencoded")
	@Produces("application/json")
    public ServiceResponse deleteUserMedicineSupply(@FormParam("id") Integer id) {
		UserMedicine userMedicine = UserMedicine.getById(id);
		if(userMedicine != null){
			userMedicine.delete();
		}

		return new ServiceResponse(true, "", "");
    }
	
	public static String getServiceName() {
		return SERVICE_NAME;
	}
	
}
