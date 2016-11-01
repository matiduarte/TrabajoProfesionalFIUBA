package controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DataBase.StoreData;
import entities.User;
import entities.User.UserRole;

@WebServlet("/listaPacientes")
public class ListaPacientesController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public ListaPacientesController() {
        super();
    }

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    	    throws ServletException, IOException {
		
		List<User> pacientes = User.getByRole(UserRole.PATIENT);
		request.setAttribute("listaPacientes", pacientes);
		getServletConfig().getServletContext().getRequestDispatcher("/listarPacientes.jsp").forward(request,response);
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
    	StoreData.delete(User.getById(Integer.parseInt(id)));
//    	getServletConfig().getServletContext().getRequestDispatcher("/redirect:/listaPacientes").forward(request,response);
    	response.sendRedirect("listaPacientes");
    }
	
}
