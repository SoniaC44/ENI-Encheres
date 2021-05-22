package fr.eni.Encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.eni.Encheres.bo.Categorie;
import fr.eni.Encheres.dal.ConnectionProvider;
import fr.eni.Encheres.dal.DALException;
import fr.eni.Encheres.dal.DAOCategorie;

public class DAOCategorieJDBCImpl implements DAOCategorie{
	
	private final String SELECT_ALL_CATEGORIES = "SELECT * FROM CATEGORIES";

	@Override
	public List<Categorie> selectCategories() throws DALException {
		List<Categorie> listeCategorie = new ArrayList<Categorie>();
		Categorie uneCategorie = null;
		
		try(	Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement pst = cnx.prepareStatement(SELECT_ALL_CATEGORIES);
				ResultSet rs = pst.executeQuery();)
		{
			while(rs.next())
			{
				uneCategorie = new Categorie();
				uneCategorie.setNoCategorie(rs.getInt("no_categorie"));
				uneCategorie.setLibelle(rs.getString("libelle"));
				
				listeCategorie.add(uneCategorie);
			}

		}catch (SQLException ex) 
		{
			throw new DALException("erreur lors de la récupération des catégories", ex);
		}
		
		return listeCategorie;
	}

	
	
	
}
