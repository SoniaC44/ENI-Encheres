package fr.eni.Encheres.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ServletDeconnexion
 */
public class ServletDeconnexion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String SERVLET_ACCUEIL = "/Accueil";
	private final String DECONNEXION_ATTRIBUT = "deconnexionOK";
	private final String MSG_DECONNEXION = "Vous êtes déconnecté.";
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.invalidate();
		
		//on recupere une nouvelle session sans user dans les attributs
		session = request.getSession();
		session.setAttribute(DECONNEXION_ATTRIBUT, MSG_DECONNEXION);
		
		response.sendRedirect(request.getServletContext().getContextPath() + SERVLET_ACCUEIL);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
