package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.security.SecurityUtil;

import entities.User;
import entities.User.UserRole;

/**
 * Servlet implementation class SignInController
 */
@WebServlet("/signin")
public class SignInController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignInController() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    	    throws ServletException, IOException {
    	       getServletConfig().getServletContext().getRequestDispatcher("/signin.jsp").forward(request,response);
    	    }
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    	if(request.getParameter("logout") != null){
    		security.SecurityUtil.logoutUser(request);
    	}
        processRequest(request, response);
    } 
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    	
    	String us = request.getParameter("user");
    	String password = request.getParameter("password");
    	
    	User user = User.getByUserName(us);
    	boolean mismoPass = false;
    	boolean existe = false;
    	
    	if (user != null){
    		existe = true;
    		if (user.getPassword().equals(password)){
    			mismoPass = true;
    		}
    	}
    	
    	if (!existe || ( user.getRole() != UserRole.ADMINISTRATOR || user.getRole() != UserRole.SECRETARY)){
    		request.setAttribute("errorUser", "true");
    		mismoPass = true;
    	}
    	
    	if (!mismoPass){
    		request.setAttribute("errorPass", "true");    	
    	}
    	
    	if (existe && mismoPass){
    		security.SecurityUtil.loginUser(user, request);
    		response.sendRedirect(request.getContextPath() + "/admin");
    	}else{
    		processRequest(request, response);
    	}
    }

}
