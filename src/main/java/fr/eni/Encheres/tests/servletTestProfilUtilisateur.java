package fr.eni.Encheres.tests;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.Encheres.bll.UtilisateurManager;
import fr.eni.Encheres.bo.Utilisateur;


public class servletTestProfilUtilisateur extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public servletTestProfilUtilisateur() {
		super();
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id_user = 6;
		
		Utilisateur user = null;
		UtilisateurManager um = new UtilisateurManager();
		try {
			user = um.afficherProfilUtilisateur(id_user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.getWriter().append(user.toString());
		
	}
	

}
