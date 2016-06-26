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
import entities.User;

@Path("/userlogin")
public class UserLoginService {

	private static final Logger logger = Logger.getLogger( UserLoginService.class.getName() );
	
	@POST
    @Path("")
	@Consumes("application/json")
    public Response saveUserStudy(String userName, String password) {
		User user = User.getByUserName(userName);
		if(user != null){
			//Habria que ver si vamos a encriptar el password
			if(user.getPassword() == password){
				//Devolver OK
				return Response.status(200).entity("Ok").build();
			}
		}
		//TODO: devoler error
		return Response.status(200).entity("Fail").build();

    }
	
}
