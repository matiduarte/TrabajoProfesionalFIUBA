package controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.DoctorPatient;
import entities.User;
import entities.User.UserRole;

/**
 * Servlet implementation class AltaPacientesController
 */
@WebServlet("/patient")
public class PatientController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PatientController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(!security.SecurityUtil.checkUserRole(request, response, UserRole.ADMINISTRATOR, UserRole.SECRETARY)){
			return;
		}
		
		request.setAttribute("doctors", User.getAllDoctors());
		if (request.getParameter("id") != null){
			request.setAttribute("id", request.getParameter("id"));
			int id = Integer.valueOf(request.getParameter("id"));
			User user = User.getById(id);
			request.setAttribute("name", user.getFirstName());
			request.setAttribute("lastName", user.getLastName());
			request.setAttribute("dni", user.getDni());
			
			request.setAttribute("currentDoctors", DoctorPatient.getDoctorsByPatientId(id));
		}
		
		getServletConfig().getServletContext().getRequestDispatcher("/patient.jsp").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String dni = request.getParameter("dni");
    	String name = request.getParameter("name");
    	String lastName = request.getParameter("lastName");
    	User user = null;
    	boolean existe = true;
    	
    	
    	if((request.getParameter("id") != null) && !(request.getParameter("id") == "")){
    		int id = Integer.valueOf(request.getParameter("id"));
			user = User.getById(id);
			user.setId(id);
			user.setRole(UserRole.PATIENT);
    		user.setFirstName(name);
    		user.setLastName(lastName);
    		existe = false;
    		user.setDni(dni);
    		user.save();
    	} else {
    		user = User.getByDNI(dni);
	    	if (user == null) {
	    		existe = false;
	    		user = new User();
	    		user.setRole(UserRole.PATIENT);
	    		user.setFirstName(name);
	    		user.setLastName(lastName);
	    		user.setDni(dni);
	    		
	    		user.save();
	    	}
    	}
    	
    	String doctorsString = request.getParameter("doctors");
    	
    	
    	DoctorPatient.deleteByPatientId(user.getId());
    	if(doctorsString != ""){
	    	String[] doctorIds = doctorsString.split(",");
	    	for (String doctorId : doctorIds) {
				DoctorPatient doctorPatient = new DoctorPatient();
				doctorPatient.setDoctorId(Integer.parseInt(doctorId));
				doctorPatient.setPatientId(user.getId());
				doctorPatient.save();
			}
    	}
    	
		String finalizar_btn = request.getParameter("finalizar");
		
		if (finalizar_btn != null){
			if (!existe){
				
				HttpSession session = request.getSession(true);
				session.setAttribute("usuarioExitoso", true);
				response.sendRedirect(request.getContextPath() + "/listaPacientes");
				
			}else{
				request.setAttribute("errormsg", "Usuario existente.");
				request.setAttribute("dni", dni);
				request.setAttribute("name", name);
				request.setAttribute("lastName", lastName);
				getServletConfig().getServletContext().getRequestDispatcher("/patient.jsp").forward(request,response);
			}
		}
	}

}
