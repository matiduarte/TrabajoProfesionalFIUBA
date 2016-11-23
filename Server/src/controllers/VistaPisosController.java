package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DataBase.StoreData;
import entities.Bed;
import entities.Floor;
import entities.User;
import entities.User.UserRole;

@WebServlet("/vistaPisos")
public class VistaPisosController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public VistaPisosController() {
        super();
    }

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    	    throws ServletException, IOException {
		
		List<Floor> pisos = Floor.getAll();
		request.setAttribute("listaPisos", pisos);
		getServletConfig().getServletContext().getRequestDispatcher("/listaVistaPisos.jsp").forward(request,response);
	}
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    	if(!security.SecurityUtil.checkUserRole(request, response, UserRole.ADMINISTRATOR, UserRole.SECRETARY)){
			return;
		}
    	
    	if (request.getParameter("id") != null) {
    		request.setAttribute("id", request.getParameter("id"));
			int id = Integer.valueOf(request.getParameter("id"));
			List<Bed> beds = Bed.getByFloorId(id);
			request.setAttribute("bedList", beds);
			List<User> users = new ArrayList();
			for (Bed bed : beds) {
				if (bed.getPatientId() != 0) {
					User user = User.getById(bed.getPatientId());
					users.add(user);
				}
			}
			request.setAttribute("users", users);
			Floor floor = Floor.getById(id);
			request.setAttribute("floorName", floor.getName());
    		getServletConfig().getServletContext().getRequestDispatcher("/vistaPisos.jsp").forward(request,response);
    	}
        processRequest(request, response);
    } 
    
    /**
     * Asigna paciente a cama
     */
    
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int bedId = Integer.parseInt(request.getParameter("bedId"));
		int patientId = Integer.parseInt(request.getParameter("patient"));
		
		List<Bed> beds = Bed.getAll();
		for (Bed bedAux : beds) {
			if (bedAux.getPatientId() == patientId) {
				bedAux.setPatientId(0);
				bedAux.save();
				break;
			}
		}
		System.out.println("PId: " + patientId);
		Bed bed = Bed.getById(bedId);
		bed.setPatientId(patientId);
		bed.save();
		response.sendRedirect("vistaPisos?id=" + request.getParameter("id"));
	}
	
}

