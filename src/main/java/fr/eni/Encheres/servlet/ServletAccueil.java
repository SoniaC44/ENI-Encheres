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
import fr.eni.Encheres.bo.ArticleVendu;
import fr.eni.Encheres.bo.Categorie;

/**
 * Servlet implementation class ServletAccueil
 */
public class ServletAccueil extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String ERR_GAL = "erreurGenerale";
	private final String MSG_ERR_GAL = "erreur lors de la récupération des données";
	private final String NOM_ATTRIBUT_SESSION_UTILISATEUR = "utilisateurSession";
	private final String LOGIN_ATTRIBUT = "loginOK";
	private final String MSG_LOGIN = "Vous êtes maintenant connecté.";
	private final String DECONNEXION_ATTRIBUT = "deconnexionOK";
	private final String MSG_DECONNEXION = "Vous êtes déconnecté.";
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			//recupere la liste des categorie
			CategorieManager cm = new CategorieManager();
			List<Categorie> categories = cm.selectionnerCatagories();
			request.setAttribute("categories", categories);
			
			HttpSession session = request.getSession();
			String referer = request.getHeader("referer");
			if(request.getHeader("referer")!=null && (request.getHeader("referer").contains("/Connexion") || request.getHeader("referer").contains("/Inscription"))){
				//traitement pour afficher alert connexion
				System.out.println("connexion");
				if(session.getAttribute(NOM_ATTRIBUT_SESSION_UTILISATEUR)!=null) {
					request.setAttribute(LOGIN_ATTRIBUT, MSG_LOGIN);
				}
			}
			if(session.getAttribute(DECONNEXION_ATTRIBUT)!=null){
				//traitement pour afficher alert connexion
				request.setAttribute(DECONNEXION_ATTRIBUT, MSG_DECONNEXION);
				//puis on set à null pour pa que le message s'affiche à chaque fois
				session.setAttribute(DECONNEXION_ATTRIBUT, null);
			}
			
			//recuperer la liste des articles si on vient de la servlet Rechercher
			if(session.getAttribute("articles")==null) {
				ArticleVenduManager avm = new ArticleVenduManager();
				List<ArticleVendu> articles = avm.selectionnerTousLesArticlesEnVente();
				request.setAttribute("articles", articles);
				session.setAttribute("erreurRecherche",null);
			}else
			{
				//sinon on affiche directement l'acccueil alors on récupère la liste complete des articles en vente
				request.setAttribute("articles", session.getAttribute("articles"));
			}
			
			//si on vient de la servlet Rechercher, on a fait certains choix sur le formulaire :
			if(session.getAttribute("choixCategorie")!=null) {
				request.setAttribute("choixCategorie", session.getAttribute("choixCategorie"));
			}
			
			if(session.getAttribute("choixNomArticle")!=null) {
				request.setAttribute("choixNomArticle", session.getAttribute("choixNomArticle"));
			}
			
			request.setAttribute("choix", "achats");
			
			//si on vient de la servlet Rechercher,et qu'il y a des erreurs
			if(session.getAttribute("erreurRecherche")!= null)
			{
				request.setAttribute(ERR_GAL, session.getAttribute("erreurRecherche"));
			}
			
		} catch (BLLException e) {
			e.printStackTrace();
			request.setAttribute(ERR_GAL, MSG_ERR_GAL);
		}
		
		RequestDispatcher rd = request.getServletContext().getNamedDispatcher("accueilJSP");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
