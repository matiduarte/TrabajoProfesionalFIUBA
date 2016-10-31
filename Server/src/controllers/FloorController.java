package controllers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import entities.Floor;
import entities.User;
import entities.User.UserRole;

/**
 * Servlet implementation class AdminController
 */
@WebServlet("/addFloor")
@MultipartConfig
public class FloorController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FloorController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
//		if (request.getParameter("id") != null){
//			request.setAttribute("id", request.getParameter("id"));
//			int id = Integer.valueOf(request.getParameter("id"));
//			User user = User.getById(id);
//			request.setAttribute("name", user.getFirstName());
//			request.setAttribute("lastName", user.getLastName());
//			request.setAttribute("dni", user.getDni());
//			request.setAttribute("user", user.getUserName());
//		}
		
	    getServletConfig().getServletContext().getRequestDispatcher("/piso.jsp").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String posicionesCamas = request.getParameter("posicionesCamas");
		System.out.println("posiciones: " + posicionesCamas);
		
		Part filePart = request.getPart("imagenPiso"); // Retrieves <input type="file" name="file">
//	    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
	    InputStream is = filePart.getInputStream();
	    System.out.println("imagen: " + is.toString());
	    
	    Floor floor = new Floor();
	    
	    ByteArrayOutputStream buffer = new ByteArrayOutputStream();

	    int nRead;
	    byte[] data = new byte[16384];

	    while ((nRead = is.read(data, 0, data.length)) != -1) {
	      buffer.write(data, 0, nRead);
	    }

	    buffer.flush();
	    
	    floor.setImage(data);
	    floor.save();
	    
//    	if((request.getParameter("id") != null) && !(request.getParameter("id") == "")){
//			int id = Integer.valueOf(request.getParameter("id"));
//			user = User.getById(id);
//			user.setId(id);
//			user.setUserName(userName);
//    		user.setRole(UserRole.ADMINISTRATOR);
//    		user.setPassword(password);
//    		user.setFirstName(name);
//    		user.setDni(dni);
//    		user.setLastName(lastName);
//    		existe = false;
//    		user.save();
//    	} else {
//    		user = User.getByDNI(dni);
//    		if (user == null) {
//        		existe = false;
//        		user = new User();
//        		user.setUserName(userName);
//        		user.setRole(UserRole.ADMINISTRATOR);
//        		user.setPassword(password);
//        		user.setFirstName(name);
//        		user.setDni(dni);
//        		user.setLastName(lastName);
//        		
//        		user.save();
//    		}
//    	}
    	
    	
    	
//		String finalizar_btn = request.getParameter("finalizar");
//		
//		if (finalizar_btn != null){
//			if (!existe){
//				
//				HttpSession session = request.getSession(true);
//				session.setAttribute("usuarioExitoso", true);
//				response.sendRedirect(request.getContextPath() + "/listaMedicos");
//				
//			}else{
//				request.setAttribute("errormsg", "Usuario existente.");
//				request.setAttribute("user", userName);
//				request.setAttribute("name", name);
//				request.setAttribute("lastName", lastName);
//				request.setAttribute("dni", dni);
//				getServletConfig().getServletContext().getRequestDispatcher("/doctor.jsp").forward(request,response);
//			}
//		}
	}

}
