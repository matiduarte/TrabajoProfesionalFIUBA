package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import entities.User;

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
    	
    	if (!existe){
    		request.setAttribute("errorUser", "true");
    		mismoPass = true;
    	}
    	
    	if (!mismoPass){
    		request.setAttribute("errorPass", "true");    	
    	}
    	
    	if (existe && mismoPass){
		UsernamePasswordToken token = new UsernamePasswordToken(us, password);
		token.setRememberMe(true);
		SecurityUtils.getSubject().login(token);

    		response.sendRedirect(request.getContextPath() + "/admin");
	}
    	else{
    		processRequest(request, response);
	}
     
    }

}
