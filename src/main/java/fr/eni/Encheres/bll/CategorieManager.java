package fr.eni.Encheres.bll;

import java.util.List;

import fr.eni.Encheres.bo.Categorie;
import fr.eni.Encheres.dal.DALException;
import fr.eni.Encheres.dal.DAOCategorie;
import fr.eni.Encheres.dal.DAOFactory;

public class CategorieManager {

	private DAOCategorie daoCategorie;

	public CategorieManager() {
		super();
		daoCategorie = DAOFactory.getDAOCategorie();
	}
	
	public List<Categorie> selectionnerCatagories() throws BLLException {
		
		try {
			return this.daoCategorie.selectCategories();
		} catch (DALException ex) {
			throw new BLLException("BLL - Impossible de récupérer la liste des articles vendus", ex);
		}
		
	}

}
