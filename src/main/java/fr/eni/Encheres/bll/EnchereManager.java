package fr.eni.Encheres.bll;

import java.util.ArrayList;
import java.util.List;

import fr.eni.Encheres.bo.Enchere;
import fr.eni.Encheres.dal.DALException;
import fr.eni.Encheres.dal.DAOEncheres;
import fr.eni.Encheres.dal.DAOFactory;

public class EnchereManager {
	
	private DAOEncheres daoEnchere;

	public EnchereManager() {
		super();
		this.daoEnchere = DAOFactory.getDAOEncheres();
	}
	
	public List<Enchere> recupererEncheresUser(int noUtilisateur){
		List<Enchere> listeE = new ArrayList<Enchere>();
		return listeE;
	}
	
	public Enchere recupererEnchereMaxArticle(int noArticle) throws BLLException{
		 try {
			return this.daoEnchere.selectEnchereMaxArticle(noArticle);
		} catch (DALException ex) {
			throw new BLLException("BLL - Impossible de récupérer l'enchere maximale sur un article", ex);
		}
	}
	
	
}
