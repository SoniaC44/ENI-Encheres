package fr.eni.Encheres.dal;

import java.util.List;

import fr.eni.Encheres.bo.Enchere;

public interface DAOEncheres {

	public List<Enchere> selectAllEncheresUser(int noUtilisateur) throws DALException;
	
	public Enchere selectEnchereMaxArticle(int noArticle) throws DALException;
}
