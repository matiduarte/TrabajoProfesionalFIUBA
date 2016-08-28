package service;

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

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import entities.Bed;
import entities.Floor;
import entities.MedicalShift;
import entities.User;

@Path("/medicalshift")
public class MedicalShiftService {

	private static final Logger logger = Logger.getLogger( MedicalShiftService.class.getName() );

	@Path("{id}/{date}")
	@GET
	@Produces("application/json")
	public ServiceResponse getMedicalShift(@PathParam("id") Integer id,@PathParam("date") String date){

		List<MedicalShift> listOfMedicalShift = MedicalShift.getByDoctorIdAndDate(id, date);
		JSONObject jo = new JSONObject();
		try {
			Gson g = new Gson();
			String medicalShifts = g.toJson(listOfMedicalShift);
			jo.put("shifts", medicalShifts);

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return new ServiceResponse(true,"",jo.toString());
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
