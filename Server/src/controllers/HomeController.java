package controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.StudyType;
import entities.User;
import entities.User.UserRole;

/**
 * Servlet implementation class HomeController
 */
@WebServlet("/admin")
public class HomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeController() {
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
		
		User userLogged = security.SecurityUtil.getUser(request);
		List<User> doctors = User.getAllDoctors();
		List<User> nurses = User.getAllNurses();
		List<User> patients = User.getAllPatients();
		
		List<StudyType> studyType = StudyType.getAll();
		
		request.setAttribute("name", userLogged.getFirstName() + " " + userLogged.getLastName());
		
		if(doctors != null){
			request.setAttribute("doctors", doctors.size());
		}else{
			request.setAttribute("doctors", 0);
		}

		if(nurses != null){
			request.setAttribute("nurses", nurses.size());
		}else{
			request.setAttribute("nurses", 0);
		}
		
		if(patients != null){
			request.setAttribute("patients", patients.size());
		}else{
			request.setAttribute("patients", 0);
		}
		
		if(studyType != null){
			request.setAttribute("studyType", studyType.size());
		}else{
			request.setAttribute("studyType", 0);
		}
		
		getServletConfig().getServletContext().getRequestDispatcher("/adminContent.jsp").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
