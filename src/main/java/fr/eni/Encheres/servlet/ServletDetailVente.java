package fr.eni.Encheres.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.Encheres.bll.ArticleVenduManager;
import fr.eni.Encheres.bll.BLLException;
import fr.eni.Encheres.bll.EnchereManager;
import fr.eni.Encheres.bo.ArticleVendu;
import fr.eni.Encheres.bo.Enchere;

/**
 * Servlet implementation class ServletDetailVente
 */
public class ServletDetailVente extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private final String ERR_ARTICLE = "errorArticle";
	private final String MSG_ERR_PROFIL_ACCES_BDD_IMPOSSIBLE = "L'acc�s � la BDD impossible";
	private final String ERR_URL = "Article non reconnu";

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
			Integer id_article = null;
		
		if(request.getParameter("id_article") != null && !request.getParameter("id_article").isEmpty())
		{
			try{
				id_article = Integer.parseInt(request.getParameter("id_article"));
				ArticleVendu article = null;
				//Enchere mise = null;
				ArticleVenduManager avm = new ArticleVenduManager();
				//EnchereManager em = new EnchereManager();
				try {
					article  = avm.selectionnerDetailVente(id_article);
					//mise = em.recupererEnchereMaxArticle(id_article);
					request.setAttribute("id_article", id_article);
					if(article.getNoArticle()==null){
						request.setAttribute(ERR_ARTICLE, MSG_ERR_PROFIL_ACCES_BDD_IMPOSSIBLE);
					}else {				
						request.setAttribute("inputNomArticle", article.getNomArticle());
						request.setAttribute("inputDescription", article.getDescription());
						request.setAttribute("inputDateFin", article.getDateFinEncheres());
						request.setAttribute("inputPseudo", article.getUtilisateur().getPseudo());
						request.setAttribute("inputPrixInitial", article.getMiseAPrix());
						request.setAttribute("inputAdresse", article.getUtilisateur().getRue());
						request.setAttribute("inputCodePostal", article.getUtilisateur().getCodePostal());
						request.setAttribute("inputVille", article.getUtilisateur().getVille());
						request.setAttribute("inputCategorie", article.getCategorie().getLibelle());
						System.out.println(article);
						/*if(mise!=null){
							request.setAttribute("inputMise", mise.getMontantEnchere());
							request.setAttribute("inputMiseur", mise.getUtilisateur());
							}else {
								request.setAttribute("inputMise", "Aucune enchère");
								request.setAttribute("inputMiseur", "");
						
					}*/
					}
				}
		 catch (BLLException e) {
			e.printStackTrace();
			request.setAttribute(ERR_ARTICLE, MSG_ERR_PROFIL_ACCES_BDD_IMPOSSIBLE);
		}
	
		}
			catch (Exception e) {
				request.setAttribute(ERR_URL, MSG_ERR_PROFIL_ACCES_BDD_IMPOSSIBLE);
			}
			
		}
	
	
	
			System.out.println(id_article);
			
			RequestDispatcher rd = request.getServletContext().getNamedDispatcher("detailVenteJSP");
			rd.forward(request, response);
		}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
