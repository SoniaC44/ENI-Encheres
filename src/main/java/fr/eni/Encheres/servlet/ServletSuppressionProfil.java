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
public class ServletSuppressionProfil extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String SERVLET_DECONNEXION = "/Deconnexion";
	private final String SERVLET_MODIFICATION_PROFIL = "ServletModificationProfil";
	private final String NOM_ATTRIBUT_SESSION_UTILISATEUR = "utilisateurSession";

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		boolean existeErreur = false;
		Utilisateur user = new Utilisateur();
		
		HttpSession session = request.getSession();
		if(session.getAttribute(NOM_ATTRIBUT_SESSION_UTILISATEUR)==null){
			existeErreur = true;
			request.setAttribute("msgErreur_general", "Vous avez été déconnecté, suppression impossible. Veuillez vous reconnecter");
		}else {
			try {
				user.setNoUtilisateur(((Utilisateur)session.getAttribute(NOM_ATTRIBUT_SESSION_UTILISATEUR)).getNoUtilisateur());
				// supprimer user en BDD
				UtilisateurManager um = new UtilisateurManager();
				um.supprimerProfilUtilisateur(user.getNoUtilisateur());
				
				//si reussi, on modifie en session
				//pour cela on déconnecte l'utilisateur
				response.sendRedirect(request.getServletContext().getContextPath() + SERVLET_DECONNEXION);
		
			} catch (BLLException e) {
				existeErreur = true;
				request.setAttribute("msgErreur_general", "Une erreur s'est produite : " + e.getMessage());
			}
		}
		
		if(existeErreur)
		{
			//on reaffiche la jsp de modification de profil avec les erreurs
			RequestDispatcher rd = request.getServletContext().getNamedDispatcher(SERVLET_MODIFICATION_PROFIL);
			rd.forward(request, response);
		}
	}
}
