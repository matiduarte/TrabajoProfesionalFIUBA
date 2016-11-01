package security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.User;
import entities.User.UserRole;

public class SecurityUtil {  
	
	public static void loginUser(User user, HttpServletRequest request){
		HttpSession session = request.getSession();
		session.setAttribute("user", user);
	}
	
	public static void logoutUser(HttpServletRequest request){
		HttpSession session = request.getSession();
		session.removeAttribute("user");
	}
	
	public static boolean checkUserRole(HttpServletRequest request, HttpServletResponse response, UserRole...roles){
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		if(user == null || !SecurityUtil.checkAllRoles(user.getRole(),roles)){
			try {
				response.sendRedirect(request.getContextPath() + "/signin");
				return false;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
		
	}

	private static boolean checkAllRoles(UserRole role, UserRole[] roles) {
		for (UserRole userRole : roles) {
			if(role == userRole){
				return true;
			}
		}
		return false;
	}
}