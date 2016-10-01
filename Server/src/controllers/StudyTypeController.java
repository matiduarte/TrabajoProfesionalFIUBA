package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.Medicine;
import entities.StudyType;

/**
 * Servlet implementation class StudyTypeController
 */
@WebServlet("/studyType")
public class StudyTypeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StudyTypeController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getServletConfig().getServletContext().getRequestDispatcher("/studyType.jsp").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
    	
    	StudyType studyType = new StudyType();
    	studyType.setName(name);
    	studyType.save();
    	
		String finalizar_btn = request.getParameter("finalizar");
		
		if (finalizar_btn != null){
			
			response.sendRedirect(request.getContextPath() + "/admin");
				
		}
	}

}
