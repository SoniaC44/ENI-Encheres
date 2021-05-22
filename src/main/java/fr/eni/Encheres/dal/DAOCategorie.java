package fr.eni.Encheres.dal;

import java.util.List;

import fr.eni.Encheres.bo.Categorie;

public interface DAOCategorie {
	
	public List<Categorie> selectCategories() throws DALException;
}
