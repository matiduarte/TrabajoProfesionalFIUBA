package service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import entities.Bed;
import entities.Floor;
import entities.Room;


@Path("/bedDiagram")
public class BedDiagramService {

	@Path("{floor}")
	@GET
	@Produces("application/json")
	public Floor getFloor(@PathParam("floor") Integer floor){
		List<Room> roomList = Room.getByFloorId(floor);
		for(Room room : roomList) {
			int roomId = room.getId();
			List<Bed> bedList = Bed.getByRoomId(roomId);
			
		}
		
		//Habria q devolver la lista de cuartos, de camas y/o pacientes. Despues de devolver cuartos y camas puede pedir pacientes.
		return new Floor();
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
