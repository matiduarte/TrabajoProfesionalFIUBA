package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.Medicine;

/**
 * Servlet implementation class MedicineController
 */
@WebServlet("/medicine")
public class MedicineController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MedicineController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getServletConfig().getServletContext().getRequestDispatcher("/medicine.jsp").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
    	String name = request.getParameter("name");
    	
    	Medicine medicine = new Medicine();
    	medicine.setName(name);
    	medicine.save();
    	
		String finalizar_btn = request.getParameter("finalizar");
		
		if (finalizar_btn != null){
			
			response.sendRedirect(request.getContextPath() + "/admin");
				
		}
	}

}
