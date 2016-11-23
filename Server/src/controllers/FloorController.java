package controllers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import DataBase.StoreData;
import entities.Bed;
import entities.Floor;

/**
 * Servlet implementation class AdminController
 */
@WebServlet("/addFloor")
@MultipartConfig
public class FloorController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FloorController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("id") != null){
			int id = Integer.valueOf(request.getParameter("id"));
			request.setAttribute("id", id);
			List<Bed> beds = Bed.getByFloorId(id);
			request.setAttribute("bedList", beds);
			Floor floor = Floor.getById(id);
			request.setAttribute("floorName", floor.getName());
		}
		
	    getServletConfig().getServletContext().getRequestDispatcher("/piso.jsp").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String posicionesCamas = request.getParameter("posicionesCamas");
		Floor floor;
		String name = request.getParameter("name");
    	if((request.getParameter("id") != null) && !(request.getParameter("id").equals(""))){
    		int id = Integer.parseInt(request.getParameter("id"));
    		floor = Floor.getById(id);
    		String cambio = request.getParameter("imagenCambiada");
    		floor.setName(name);
    		if (cambio.equals("1")) {
    			Part filePart = request.getPart("archivoImagenPiso"); // Retrieves <input type="file" name="file">
        		byte[] imagen = getImage(filePart);
        		floor.setImage(imagen);
    		}
    		floor.save();
    	} else {
    		Part filePart = request.getPart("archivoImagenPiso"); // Retrieves <input type="file" name="file">
    		byte[] imagen = getImage(filePart);
    		floor = new Floor();
    		floor.setName(name);
    		floor.setImage(imagen);
    	    floor.save();
    	}
    	savePositions(posicionesCamas,floor);
    	
    	response.sendRedirect(request.getContextPath() + "/listaPisos");
	}

	private void pisoExiste(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}

	private void savePositions(String posicionesCamas, Floor floor) {
		int floorId = floor.getId();
		List<Bed> bedList = Bed.getByFloorId(floorId);
		String[] posiciones = posicionesCamas.split(";");
		for (int i = 0; i < posiciones.length; i++) {
			Bed bed = new Bed();
			String[] posicion = posiciones[i].split(",");
			int x = (int)Float.parseFloat(posicion[0]);
			int y = (int)Float.parseFloat(posicion[1]);
			if (posicion.length == 3) {
				int bedId = Integer.parseInt(posicion[2]);
				bed.setId(bedId);
				for (Iterator<Bed> it = bedList.iterator(); it.hasNext();) {
					Bed bedAux = it.next();
					if (bedAux.getId() == bedId) {
						bed.setPatientId(bedAux.getPatientId());
						it.remove();
					}
				}
			}
			bed.setFloorId(floorId);
			bed.setX(x);
			bed.setY(y);
			bed.save();
		}
		for (Bed bedAux : bedList) {
			StoreData.delete(bedAux);
		}
	}

	private byte[] getImage(Part filePart) throws IOException {
		InputStream is = filePart.getInputStream();
	    
	    ByteArrayOutputStream buffer = new ByteArrayOutputStream();

	    int nRead;
	    byte[] data = new byte[16384];

	    while ((nRead = is.read(data, 0, data.length)) != -1) {
	      buffer.write(data, 0, nRead);
	    }

	    buffer.flush();
	    
	    return buffer.toByteArray();
	}

}
