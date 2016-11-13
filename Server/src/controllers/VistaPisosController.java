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
    		getServletConfig().getServletContext().getRequestDispatcher("/vistaPisos.jsp").forward(request,response);
    	}
        processRequest(request, response);
    } 
    
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String id = (String)request.getParameter("deleteId");
    	StoreData.delete(Floor.getById(Integer.parseInt(id)));
    	List<Bed> bedList = Bed.getByFloorId(Integer.parseInt(id));
    	for (Bed bed : bedList) {
    		StoreData.delete(bed);
    	}
    	response.sendRedirect("listaPisos");
    }
	
}

