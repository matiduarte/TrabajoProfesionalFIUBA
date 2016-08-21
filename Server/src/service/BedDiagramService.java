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

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.google.gson.Gson;

import entities.Bed;
import entities.Floor;
import entities.Room;
import entities.Study;
import entities.User;


@Path("/bedDiagram")
public class BedDiagramService {

	private static final Logger logger = Logger.getLogger( BedDiagramService.class.getName() );
	
	@Path("{floor}")
	@GET
	@Produces("application/json")
	public ServiceResponse getFloor(@PathParam("floor") Integer floor){
		List<Bed> bedList = Bed.getByFloorId(floor);
		
		if (!bedList.isEmpty()) {
			List<User> patients = new ArrayList<>();			
			for (Bed bed : bedList) {
				patients.add(User.getById(bed.getPatientId()));
			}
			JSONObject jo = new JSONObject();
			try {
				Gson g = new Gson();
				String bedsString = g.toJson(bedList);
				jo.put("beds", bedsString);
				String patientsString = g.toJson(patients);
				jo.put("patients", patientsString);
				jo.put("image", Floor.getById(floor).getImageAsString());
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return new ServiceResponse(true,"",jo.toString());
		} else {
			logger.log(Level.WARNING, "No se encontro ninguna cama para ese piso");
		}
		
		return new ServiceResponse(false,"","");
	}
	
	@Path("/totalfloors")
	@GET
	@Produces("application/json")
	public ServiceResponse getFloors(){
			
		JSONObject jo = new JSONObject();
		try {
			jo.put("floors", Floor.getTotalNumber());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return new ServiceResponse(true,"",jo.toString());
	}

	
	@POST
    @Path("shift")
	@Consumes("application/json")
    public Response saveUserMedicineSupply(Floor medicalShift) {
		
		medicalShift.save();
		String output = medicalShift.toString();
		//TODO: Ver que responder al cliente.
		return Response.status(200).entity(output).build();

    }
	
}
