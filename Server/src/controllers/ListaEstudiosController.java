package controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DataBase.StoreData;
import entities.StudyType;
import entities.User.UserRole;

@WebServlet("/listaEstudios")
public class ListaEstudiosController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public ListaEstudiosController() {
        super();
    }

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    	    throws ServletException, IOException {
		
		List<StudyType> estudios = StudyType.getAll();
		request.setAttribute("listaEstudios", estudios);
		getServletConfig().getServletContext().getRequestDispatcher("/listarEstudios.jsp").forward(request,response);
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
    	StoreData.delete(StudyType.getById(Integer.parseInt(id)));
    	response.sendRedirect("listaEstudios");
    }
	
}
