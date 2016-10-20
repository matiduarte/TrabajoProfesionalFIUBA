package controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DataBase.StoreData;
import entities.Medicine;

@WebServlet("/listaMedicamentos")
public class ListaMedicamentosController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public ListaMedicamentosController() {
        super();
    }

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    	    throws ServletException, IOException {
		
		List<Medicine> medicamentos = Medicine.getAll();
		request.setAttribute("listaMedicamentos", medicamentos);
		getServletConfig().getServletContext().getRequestDispatcher("/listarMedicamentos.jsp").forward(request,response);
	}
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 
    
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String id = (String)request.getParameter("deleteId");
    	StoreData.delete(Medicine.getByMedicineId(Integer.parseInt(id)));
//    	getServletConfig().getServletContext().getRequestDispatcher("/redirect:/listaMedicamentos").forward(request,response);
    	response.sendRedirect("listaMedicamentos");
    }
	
}
