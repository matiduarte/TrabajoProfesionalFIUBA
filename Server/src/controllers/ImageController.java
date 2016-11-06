package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.Floor;

@WebServlet("/images")
public class ImageController extends HttpServlet {

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id= request.getParameter("id");
        Floor floor = Floor.getById(Integer.parseInt(id));
        byte[] image = floor.getImage();
//        response.setContentType(getServletContext().getMimeType(imageName));
        response.setContentLength(image.length);
        response.getOutputStream().write(image);
        
    }
}
