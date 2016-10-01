package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import service.mailing.Mailer;
import entities.User;
import entities.User.UserRole;

/**
 * Servlet implementation class AltaMedicoController
 */
@WebServlet("/doctor")
public class DoctorController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoctorController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getServletConfig().getServletContext().getRequestDispatcher("/doctor.jsp").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String userName = request.getParameter("user");
    	String password = request.getParameter("password");
    	String name = request.getParameter("name");
    	String lastName = request.getParameter("lastName");
    	String dni = request.getParameter("dni");
    	User user = User.getByUserName(userName);
    	boolean existe = true;
    	
    	if (user == null) {
    		existe = false;
    		user = new User();
    		user.setUserName(userName);
    		user.setRole(UserRole.DOCTOR);
    		user.setPassword(password);
    		user.setFirstName(name);
    		user.setDni(dni);
    		user.setLastName(lastName);
    		
    		user.save();
    	}
    	
		String finalizar_btn = request.getParameter("finalizar");
		
		if (finalizar_btn != null){
			if (!existe){
				
				HttpSession session = request.getSession(true);
				session.setAttribute("usuarioExitoso", true);
				response.sendRedirect(request.getContextPath() + "/admin");
				
			}else{
				request.setAttribute("errormsg", "Usuario existente.");
				request.setAttribute("user", userName);
				request.setAttribute("name", name);
				request.setAttribute("lastName", lastName);
				request.setAttribute("dni", dni);
				getServletConfig().getServletContext().getRequestDispatcher("/doctor.jsp").forward(request,response);
			}
		}
	}

}
