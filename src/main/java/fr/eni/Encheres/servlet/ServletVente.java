package fr.eni.Encheres.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.Encheres.bll.ArticleVenduManager;
import fr.eni.Encheres.bll.BLLException;
import fr.eni.Encheres.bll.CategorieManager;
import fr.eni.Encheres.bll.RetraitManager;
import fr.eni.Encheres.bll.UtilisateurManager;
import fr.eni.Encheres.bo.ArticleVendu;
import fr.eni.Encheres.bo.Categorie;
import fr.eni.Encheres.bo.Utilisateur;

/**
 * Servlet implementation class ServletVente
 */
public class ServletVente extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String JSP_VENTE = "venteJSP";
	private final String SERVLET_ACCUEIL = "/Accueil";
	private final String NOM_ATTRIBUT_SESSION_UTILISATEUR = "utilisateurSession";
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CategorieManager cm = new CategorieManager();
		List<Categorie> categories;
		try {
			categories = cm.selectionnerCatagories();
			request.setAttribute("categories", categories);
		} catch (BLLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		RequestDispatcher rd = request.getServletContext().getNamedDispatcher(JSP_VENTE);
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		//on récupère la session
		HttpSession session = request.getSession();
		// on vérifie si l'utilisateur est toujours connecté
		
		if(session.getAttribute(NOM_ATTRIBUT_SESSION_UTILISATEUR)== null) {
			//redirection vers la page d'accueil
			response.sendRedirect(request.getServletContext().getContextPath() + SERVLET_ACCUEIL);
		}else {

			boolean existeErreur = false;
			ArticleVendu article = new ArticleVendu();
			
			//on récupere l'user en session
			Utilisateur userSession = (Utilisateur)session.getAttribute(NOM_ATTRIBUT_SESSION_UTILISATEUR);
			
			// on ajoute le numero utilisateur de l'utilisateur en session à l'article que l'on vient de créer.
			article.getUtilisateur().setNoUtilisateur(userSession.getNoUtilisateur());
			
			//on va devoir vérifier chacun des saisies qui ne doivent pas être nulles
			ArticleVenduManager avm = new ArticleVenduManager();
			existeErreur = avm.verifierSaisiesArticle(request, article);
			
			if(!existeErreur)
			{
				try {
					// ajout article en BDD
					avm.ajouterArticle(article);
					
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

}
