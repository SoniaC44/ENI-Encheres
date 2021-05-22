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
public class ServletModificationProfil extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String SERVLET_ACCUEIL = "/Accueil";
	private final String SERVLET_SUPPRESSION = "/SupprimerMonCompte";
	private final String SERVLET_PROFIL = "/Profil";
	private final String NOM_ATTRIBUT_SESSION_UTILISATEUR = "utilisateurSession";

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		UtilisateurManager um = new UtilisateurManager();
		
		String referrer = request.getHeader("referer");
		if(referrer.contains(request.getContextPath() + SERVLET_ACCUEIL) 
				|| referrer.contains(request.getContextPath() + SERVLET_SUPPRESSION ) 
				|| referrer.contains(request.getContextPath() + SERVLET_PROFIL ))
		{
			um.preRemplirProfil(request);
		}
			
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
		
		HttpSession session = request.getSession();
		if(session.getAttribute(NOM_ATTRIBUT_SESSION_UTILISATEUR)==null){
			existeErreur = true;
			request.setAttribute("msgErreur_general", "Vous avez été déconnecté, modification impossible. Veuillez vous reconnecter");
		}else {
			//on va devoir vérifier chacun des saisies qui ne doivent pas être nulles
			UtilisateurManager um = new UtilisateurManager();
			
			existeErreur = um.verifierSaisiesProfil(request, user);		
			
			if(!existeErreur)
			{
				try {
					user.setNoUtilisateur(((Utilisateur)session.getAttribute(NOM_ATTRIBUT_SESSION_UTILISATEUR)).getNoUtilisateur());
					// modifier user en BDD
					um = new UtilisateurManager();
					um.modifierProfilUtilisateur(user);
					
					//si reussi, on modifie en session
					session.setAttribute(NOM_ATTRIBUT_SESSION_UTILISATEUR, user);
					
					response.sendRedirect(request.getServletContext().getContextPath() + SERVLET_ACCUEIL);
		
				} catch (BLLException e) {
					existeErreur = true;
					request.setAttribute("msgErreur_general", "Une erreur s'est produite : " + e.getMessage());
				}
			}
		}
		
		if(existeErreur)
		{
			//on reaffiche la jsp avec les erreurs
			doGet(request, response);
		}
	}

}
