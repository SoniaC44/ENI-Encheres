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
public class servletTestInsertUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Utilisateur user1 = null;
		Utilisateur user2 = null;
		UtilisateurManager um = new UtilisateurManager();
		try {

			user1 = new Utilisateur();
			user1.setPrenom("abel");
			user1.setNom("auboisdormant");
			user1.setCodePostal(44200);
			user1.setCredit(0);
			user1.setMotDePasse("mo2pass");
			user1.setAdministrateur(new Byte("0"));
			user1.setEmail("abel.auboisdormant@gmail.com");
			user1.setPseudo("abd44200");
			user1.setRue("rue de la Tour d'Auvergne");
			user1.setVille("Nantes");
			
			um.ajouterUtilisateur(user1);
			
			user2 = new Utilisateur();
			user2.setPrenom("laurent");
			user2.setNom("outant");
			user2.setCodePostal(83200);
			user2.setCredit(0);
			user2.setMotDePasse("mo2pass");
			user2.setAdministrateur(new Byte("0"));
			user2.setEmail("laurent.outant.com");
			user2.setPseudo("lolo83");
			user2.setRue("rue des forgentiers");
			user2.setVille("Toulon");
			
			um.ajouterUtilisateur(user2);
			
			response.getWriter().append(user1.toString()).append(System.lineSeparator()).append(user2.toString());
			
		} catch (BLLException e) {
			e.printStackTrace();
		}
	}
}
