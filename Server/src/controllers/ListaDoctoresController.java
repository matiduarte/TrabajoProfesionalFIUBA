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

@WebServlet("/listaMedicos")
public class ListaDoctoresController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public ListaDoctoresController() {
        super();
    }

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    	    throws ServletException, IOException {
		
		List<User> medicos = User.getByRole(UserRole.DOCTOR);
		request.setAttribute("listaMedicos", medicos);
		getServletConfig().getServletContext().getRequestDispatcher("/listarMedicos.jsp").forward(request,response);
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
//    	getServletConfig().getServletContext().getRequestDispatcher("/redirect:/listaMedicos").forward(request,response);
    	response.sendRedirect("listaMedicos");
    }
	
}
