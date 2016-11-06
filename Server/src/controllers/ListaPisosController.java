package controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DataBase.StoreData;
import entities.Bed;
import entities.Floor;
import entities.User.UserRole;

@WebServlet("/listaPisos")
public class ListaPisosController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public ListaPisosController() {
        super();
    }

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    	    throws ServletException, IOException {
		
		List<Floor> pisos = Floor.getAll();
		request.setAttribute("listaPisos", pisos);
		getServletConfig().getServletContext().getRequestDispatcher("/listarPisos.jsp").forward(request,response);
	}
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
   	
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
