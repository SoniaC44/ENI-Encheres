package fr.eni.Encheres.tests;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.Encheres.bll.BLLException;
import fr.eni.Encheres.bll.UtilisateurManager;
import fr.eni.Encheres.bo.Utilisateur;

/**
 * Servlet implementation class servletTestGetUser
 */
public class servletTestGetUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Utilisateur user = null;
		UtilisateurManager um = new UtilisateurManager();
		try {
			user = um.selectionnerUtilisateur("Charles44", "cestsecret");
		} catch (BLLException e) {
			e.printStackTrace();
		}
		
		response.getWriter().append(user.toString());
	}
}
