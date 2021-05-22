package fr.eni.Encheres.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.Encheres.bll.ArticleVenduManager;
import fr.eni.Encheres.bll.BLLException;
import fr.eni.Encheres.bo.ArticleVendu;

/**
 * Servlet implementation class servletTestGetUser
 */
public class servletTestGetListeArticlesEnCoursDeVente extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<ArticleVendu> articles = new ArrayList<ArticleVendu>();
		ArticleVenduManager avm = new ArticleVenduManager();
		
		try {
			articles = avm.selectionnerTousLesArticlesEnVente();
			
		} catch (BLLException e) {
			e.printStackTrace();
		}
		StringBuffer sb = new StringBuffer();
		for(ArticleVendu av : articles) {
			sb.append(av.toString());
			sb.append(System.lineSeparator());
		}
		response.getWriter().append(sb);
	}
}
