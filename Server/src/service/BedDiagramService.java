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
			List<User> patients = new ArrayList<User>();			
			for (Bed bed : bedList) {
				User user = User.getById(bed.getPatientId());
				if (user != null) {
					patients.add(user);
				} else {
					patients.add(new User());
				}
				
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
	
	@Path("/verifyName/{name}/{id}")
	@GET
	@Produces("application/json")
	public ServiceResponse verifyName(@PathParam("name") String name, @PathParam("id") Integer id){
		boolean existe = false;
		Floor floor	= Floor.getByName(name);
		if (floor != null && floor.getId() != id) {
			existe = true;
		}
		JSONObject jo = new JSONObject();
		try {
			jo.put("existe", existe);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return new ServiceResponse(true,"",jo.toString());
	}
	
	@Path("/totalfloors")
	@GET
	@Produces("application/json")
	public ServiceResponse getFloors(){
		List<Floor> floors = Floor.getAll();
		for (Floor floor : floors) {
			floor.setImage(null);
		}
		JSONObject jo = new JSONObject();
		try {
			Gson g = new Gson();
			String floorsString = g.toJson(floors);
			jo.put("floors", floorsString);
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
