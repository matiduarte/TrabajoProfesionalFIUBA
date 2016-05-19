package service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import entities.User;

@Path("/userprofile")
public class UserProfileService {
	
	
	@Path("{id}")
	@GET
	@Produces("application/json")
	public User getUserProfile(@PathParam("id") Integer id){
		
		User doctor = User.getById(id);
		return doctor;
	}
	
	@POST
    @Path("user")
	@Consumes("application/json")
    public Response saveUserMedicineSupply(User user) {
		
		user.save();
		String output = user.toString();
		//TODO: Ver que responder al cliente.
		return Response.status(200).entity(output).build();

    }
}
