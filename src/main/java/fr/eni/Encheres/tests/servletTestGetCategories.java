package fr.eni.Encheres.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.Encheres.bll.BLLException;
import fr.eni.Encheres.bll.CategorieManager;
import fr.eni.Encheres.bo.Categorie;

/**
 * Servlet implementation class servletTestGetUser
 */
public class servletTestGetCategories extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<Categorie> cat = new ArrayList<Categorie>();
		CategorieManager cm = new CategorieManager();
		
		try {
			cat = cm.selectionnerCatagories();
			
		} catch (BLLException e) {
			e.printStackTrace();
		}
		StringBuffer sb = new StringBuffer();
		for(Categorie c : cat) {
			sb.append(c.toString());
			sb.append(System.lineSeparator());
		}
		response.getWriter().append(sb);
	}
}
