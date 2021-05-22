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
public class servletTestUpdateUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Utilisateur user1 = null;
		Utilisateur user2 = null;
		UtilisateurManager um = new UtilisateurManager();
		try {

			user1 = new Utilisateur();
			user1.setPrenom("Donna");
			user1.setNom("Semeurt");
			user1.setCodePostal(44200);
			user1.setCredit(0);
			user1.setMotDePasse("mo2pass");
			user1.setAdministrateur(new Byte("0"));
			user1.setEmail("donna.semeurt@gmail.com");
			user1.setPseudo("donna_diva");
			user1.setRue("rue de la Tour d'Auvergne");
			user1.setVille("Nantes");
			user1.setCredit(0);
			user1.setAdministrateur(new Byte("0"));
			
			um.ajouterUtilisateur(user1);
			
			user1 = um.selectionnerUtilisateur("donna_diva", "mo2pass");
			
			user2 = new Utilisateur();
			user2.setNoUtilisateur(user1.getNoUtilisateur());
			user2.setPrenom("Donna");
			user2.setNom("Semeurt");
			user2.setCodePostal(83200);
			user2.setCredit(0);
			user2.setMotDePasse("mo2pass");
			user2.setAdministrateur(new Byte("0"));
			user2.setEmail("donna.semeurt@gmail.com");
			user2.setPseudo("donna_diva");
			user2.setRue("rue des forgentiers");
			user2.setVille("Toulon");
			user2.setCredit(0);
			user2.setAdministrateur(new Byte("0"));
			
			um.modifierProfilUtilisateur(user2);
			
			user2 = um.selectionnerUtilisateur("donna_diva", "mo2pass");
			
			response.getWriter().append(user1.toString()).append(System.lineSeparator()).append(user2.toString());
			
		} catch (BLLException e) {
			e.printStackTrace();
		}
	}
}
