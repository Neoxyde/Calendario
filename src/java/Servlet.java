/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author David
 */
@WebServlet(urlPatterns =
{
    "/Servlet"
})
public class Servlet extends HttpServlet
{
    /**
     * Stores the user's name and serves as a login check.
     */
    private HttpSession session;
    
    /**
     * Stores all the users dates.
     */
    private Cookie cookie;
    
    /**
     * Used to tell if the username has been already registered.
     */
    private boolean isRegistered;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException
    {
	response.setContentType("text/html;charset=UTF-8");
	
	//Try to get a session to decide if the user accessed for the first time.
	session = request.getSession(false);
	
	//If the user has accessed for the first time.
	if (session == null)
	{
	    isRegistered = false;
	    //Retrieve the session.
	    session = request.getSession();
	    //Print the first page.
	    try (PrintWriter out = response.getWriter())
	    {
		printFirstPage(out);
	    }
	}
	//If the user already accessed before.
	else
	{
	    //If the user has not got a username assigned yet.
	    if (!isRegistered)
	    {
		session.setAttribute("username", request.getParameter("name"));
		response.addCookie(new Cookie(request.getParameter("name"), ""));
		isRegistered = true;
	    }
	    else
	    {
		//TODO add data handling from cookie
	    }
	    
	    //Print the main page.
	    try (PrintWriter out = response.getWriter())
	    {
		printMainPage(out,(String) session.getAttribute("username"));
	    }
	}
    }
    
    /**
     * Prints the first page.
     * @param out The PrintWriter used to output the page.
     */
    private void printFirstPage(PrintWriter out)
    {
	out.println("<!DOCTYPE html>\n"
		+ "<html lang=\"es\">\n"
		+ "<head>\n"
		+ "    <meta charset=\"UTF-8\">\n"
		+ "    <title>Calendario</title>\n"
		+ "    <!--<link rel=\"stylesheet\" href=\"headerStyle.css\">-->\n"
		+ "</head>\n"
		+ "<body>\n"
		+ "    <div class=\"header\"><h2>Mi Calendario</h2></div>\n"
		+ "    <div class=\"body\">\n"
		+ "        <div id=\"msgwelcome\">Introduce tu nombre</div>\n"
		+ "        <div id=\"nameform\">\n"
		+ "            <form action=\"Servlet\" method=\"post\">\n"
		+ "                <input type=\"text\" placeholder=\"Nombre\" name=\"name\">\n"
		+ "                <input type=\"reset\" value=\"Restablecer\">\n"
		+ "                <input type=\"submit\" value=\"Enviar\">\n"
		+ "            </form>\n"
		+ "        </div>\n"
		+ "    </div>\n"
		+ "</body>\n"
		+ "</html>");	
    }
    
    /**
     * Prints the main page.
     * @param out The PrintWriter used to output the page.
     * @param username String used to customize the main page with the user's name.
     */
    private void printMainPage(PrintWriter out, String username)
    {
	out.println("<!DOCTYPE html>\n"
		+ "<html lang=\"en\">\n"
		+ "<head>\n"
		+ "    <meta charset=\"UTF-8\">\n"
		+ "    <title>Calendario</title>\n"
		+ "    <!--<link rel=\"stylesheet\" href=\"headerStyle.css\">-->\n"
		+ "</head>\n"
		+ "<body>\n"
		+ "    <div class=\"header\"><h2>Mi Calendario</h2></div>\n"
		+ "    <div class=\"body\">\n"
		+ "        <div id=\"msgwelcome\">Bienvenido, " + username +"</div>\n"
		+ "        <div class=\"dates\" style=\"margin-bottom:2em;\">\n"
		+ "            <h4>Citas</h4>\n"
		+ "            <p>Día Mes Año Descripción</p>\n"
		+ "        </div>\n"
		+ "        <div class=\"newdate\" style=\"margin-top:2em;\">\n"
		+ "            <h4>Crear nueva cita</h4>\n"
		+ "            <form action=\"Servlet\" method=\"POST\">\n"
		+ "                Fecha\n"
		+ "                <br>\n"
		+ "                <input type=\"date\" name=\"date\" id=\"date\">\n"
		+ "                <br><br>\n"
		+ "                Descripción\n"
		+ "                <br>\n"
		+ "                <input type=\"text\" name=\"description\">\n"
		+ "                <input type=\"submit\" value=\"Enviar\">\n"
		+ "            </form>\n"
		+ "        </div>\n"
		+ "    </div>\n"
		+ "</body>\n"
		+ "</html>");
    }
    
    private void getCookie(HttpServletRequest request)
    {
	request.getCookies();
    }
    
    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException
    {
	processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException
    {
	processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo()
    {
	return "Short description";
    }// </editor-fold>

}
