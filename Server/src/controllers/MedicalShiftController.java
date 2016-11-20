package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.MedicalShift;
import entities.User;
import entities.User.UserRole;

/**
 * Servlet implementation class NurseController
 */
@WebServlet("/medicalShift")
public class MedicalShiftController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MedicalShiftController() {
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
		request.setAttribute("patients", User.getAllPatients());
		
		if (request.getParameter("id") != null){
			request.setAttribute("id", request.getParameter("id"));
			int id = Integer.valueOf(request.getParameter("id"));
			MedicalShift medicalShift = MedicalShift.getById(id);
			request.setAttribute("date", formatDate(medicalShift.getDate()));
			request.setAttribute("doctorId", medicalShift.getDoctorId());
			request.setAttribute("patientId", medicalShift.getPatientId());
			request.setAttribute("time", medicalShift.getTime());
		}
		
		getServletConfig().getServletContext().getRequestDispatcher("/medicalShift.jsp").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String date = request.getParameter("date");
		date = formatDate(date);
    	int doctorId = Integer.parseInt(request.getParameter("doctorId"));
    	User doctor = User.getById(doctorId);
    	int patientId = Integer.parseInt(request.getParameter("patientId"));
    	User patient = User.getById(patientId);
    	String time = request.getParameter("time");
		MedicalShift medicalShift = null;
    	boolean existe = true;
    	
    	if((request.getParameter("id") != null) && !(request.getParameter("id") == "")){
			int id = Integer.valueOf(request.getParameter("id"));
			medicalShift = MedicalShift.getById(id);
			existe = false;
			medicalShift.setId(id);
			medicalShift.setDate(date);
			medicalShift.setDoctorId(doctorId);
			medicalShift.setDoctorName(doctor.getFirstName() + " " + doctor.getLastName());
			medicalShift.setPatientId(patientId);
			medicalShift.setPatientName(patient.getFirstName() + " " + patient.getLastName());
			medicalShift.setTime(time);
			medicalShift.save();
    	}else{
//    		user = User.getByDNI(dni);
	    	if (medicalShift == null) {
	    		existe = false;
	    		medicalShift = new MedicalShift();
				medicalShift.setDate(date);
				medicalShift.setDoctorId(doctorId);
				medicalShift.setDoctorName(doctor.getFirstName() + " " + doctor.getLastName());
				medicalShift.setPatientId(patientId);
				medicalShift.setPatientName(patient.getFirstName() + " " + patient.getLastName());
				medicalShift.setTime(time);
				medicalShift.save();
	    	}
    	}
    	
		if (!existe){
			
			HttpSession session = request.getSession(true);
			session.setAttribute("usuarioExitoso", true);
			response.sendRedirect(request.getContextPath() + "/listaTurnos");
			
		}else{
			request.setAttribute("errormsg", "Turno existente.");
			request.setAttribute("date", date);
			request.setAttribute("doctorId", doctorId);
			request.setAttribute("patientId", patientId);
			request.setAttribute("time", time);
			getServletConfig().getServletContext().getRequestDispatcher("/medicalShift.jsp").forward(request,response);
		}
	}
	
	private String formatDate(String date) {
		String[] partes = date.split("-");
		String newDate= partes[2]+"-"+partes[1]+"-"+partes[0];		
		return newDate;
	}
}