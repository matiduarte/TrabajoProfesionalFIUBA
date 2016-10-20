package service;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.xml.bind.DatatypeConverter;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.google.gson.Gson;

import entities.User;

@Path("/userprofile")
public class UserProfileService {
	
	
	@Path("{id}")
	@GET
	@Produces("application/json")
	public ServiceResponse getUserProfile(@PathParam("id") Integer id){
		
		User user = User.getById(id);
		if(user != null){
			if(user.getProfilePicture() != null){
				user.setProfilePictureString(DatatypeConverter.printBase64Binary(user.getProfilePicture()));
			}
			
			Gson g = new Gson();
			String userString = g.toJson(user);
			JSONObject jo = new JSONObject();
			try {
				jo.put("user", userString );
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return new ServiceResponse(true, "", jo.toString());
		}
		return new ServiceResponse(false, "Usuario no encontrado", "");
	}
	
    @POST
	@Consumes("application/x-www-form-urlencoded")
	@Produces("application/json")
    public ServiceResponse saveUserProfile(@FormParam("id")int id, @FormParam("firstName")String firstName, @FormParam("lastName")String lastName, @FormParam("profilePicture")String profilePicture) {
		
		User user = User.getById(id);
		if(user != null){
			user.setFirstName(firstName);
			user.setLastName(lastName);
			
			byte[] profileImageBites = DatatypeConverter.parseBase64Binary(profilePicture);
			user.setProfilePicture(profileImageBites);
			
			user.save();
			return new ServiceResponse(true, "", "");
		}
		return new ServiceResponse(false, "Usuario no encontrado", "");

    }
}
