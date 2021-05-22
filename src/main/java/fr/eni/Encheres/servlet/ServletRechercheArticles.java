package fr.eni.Encheres.servlet;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.Encheres.bll.ArticleVenduManager;
import fr.eni.Encheres.bll.BLLException;
import fr.eni.Encheres.bo.ArticleVendu;

/**
 * Servlet implementation class ServletRechercheArticles
 */
public class ServletRechercheArticles extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String SERVLET_ACCUEIL = "/Accueil";
       
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String choixCategorie = request.getParameter("selectCategorie");
		String choixNomArticle = request.getParameter("inputRechercheArticle");
		HttpSession session = request.getSession();
		
		ArticleVenduManager avm = new ArticleVenduManager();
		try {
			List<ArticleVendu> articlesVendusRecherches = avm.selectionnerArticlesRecherches(choixCategorie,choixNomArticle);
			session.setAttribute("articles", articlesVendusRecherches);
			session.setAttribute("erreurRecherche",null);
		} catch (BLLException e) {
			session.setAttribute("erreurRecherche", e.getMessage());
		}
		
		session.setAttribute("choixCategorie", choixCategorie);
		session.setAttribute("choixNomArticle", choixNomArticle.trim());
		
		response.sendRedirect(request.getServletContext().getContextPath() + SERVLET_ACCUEIL);
	}

}
