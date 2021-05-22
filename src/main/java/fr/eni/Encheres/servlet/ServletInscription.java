package fr.eni.Encheres.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.Encheres.bll.BLLException;
import fr.eni.Encheres.bll.UtilisateurManager;
import fr.eni.Encheres.bo.Utilisateur;

/**
 * Servlet implementation class ServletInscription
 */
public class ServletInscription extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String SERVLET_ACCUEIL = "/Accueil";
	private final String NOM_ATTRIBUT_SESSION_UTILISATEUR = "utilisateurSession";

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = request.getServletContext().getNamedDispatcher("monProfilJSP");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		boolean existeErreur = false;
		Utilisateur user = new Utilisateur();
		
		//on va devoir vérifier chacun des saisies qui ne doivent pas être nulles
		UtilisateurManager um = new UtilisateurManager();
		
		existeErreur = um.verifierSaisiesProfil(request, user);
		
		if(!existeErreur)
		{
			try {
				
				//on rajoute les données qui ne sont pas saisies
				user.setCredit(0);
				user.setAdministrateur(new Byte("0"));
				// ajout user en BDD
				um = new UtilisateurManager();
				um.ajouterUtilisateur(user);
				
				HttpSession session = request.getSession();
				//on enregistre dans la session l'user si on l'a trouvé bien enregistrer en BDD.
				session.setAttribute(NOM_ATTRIBUT_SESSION_UTILISATEUR,user);
				
				response.sendRedirect(request.getServletContext().getContextPath() + SERVLET_ACCUEIL);
	
			} catch (BLLException e) {
				e.printStackTrace();
				existeErreur = true;
				request.setAttribute("msgErreur_general", "Une erreur s'est produite : " + e.getMessage());
			}
		}
		
		if(existeErreur)
		{
			//on reaffiche la jsp avec les erreurs
			doGet(request, response);
		}
	}

}
