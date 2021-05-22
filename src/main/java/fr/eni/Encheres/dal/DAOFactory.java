package fr.eni.Encheres.dal;

import fr.eni.Encheres.dal.jdbc.DAOArticleVenduJDBCImpl;
import fr.eni.Encheres.dal.jdbc.DAOCategorieJDBCImpl;
import fr.eni.Encheres.dal.jdbc.DAOEncheresJDBCImpl;
import fr.eni.Encheres.dal.jdbc.DAOUtilisateurJDBCImpl;

public class DAOFactory {
	
	public static DAOEncheres getDAOEncheres() {
		return new DAOEncheresJDBCImpl();
	}
	
	public static DAOArticleVendu getDAOArticleVendu() {
		return new DAOArticleVenduJDBCImpl();
	}
	
	public static DAOCategorie getDAOCategorie() {
		return new DAOCategorieJDBCImpl();
	}
	
	public static DAOUtilisateur getDAOUtilisateur() {
		return new DAOUtilisateurJDBCImpl();
	}
}
