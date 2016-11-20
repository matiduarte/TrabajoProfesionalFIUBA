package controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DataBase.StoreData;
import entities.MedicalShift;
import entities.Medicine;
import entities.User.UserRole;

@WebServlet("/listaTurnos")
public class ListaMedicalShiftsController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public ListaMedicalShiftsController() {
        super();
    }

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    	    throws ServletException, IOException {
		
		List<MedicalShift> medicalShifts = MedicalShift.getAll();
		request.setAttribute("listaTurnos", medicalShifts);
		getServletConfig().getServletContext().getRequestDispatcher("/listarTurnos.jsp").forward(request,response);
	}
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    	if(!security.SecurityUtil.checkUserRole(request, response, UserRole.ADMINISTRATOR, UserRole.SECRETARY)){
			return;
		}
        processRequest(request, response);
    } 
    
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String id = (String)request.getParameter("deleteId");
    	StoreData.delete(MedicalShift.getById(Integer.parseInt(id)));
//    	getServletConfig().getServletContext().getRequestDispatcher("/redirect:/listaMedicamentos").forward(request,response);
    	response.sendRedirect("listaTurnos");
    }
	
}
