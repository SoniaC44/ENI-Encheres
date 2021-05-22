package fr.eni.Encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import fr.eni.Encheres.bo.Enchere;
import fr.eni.Encheres.dal.ConnectionProvider;
import fr.eni.Encheres.dal.DALException;
import fr.eni.Encheres.dal.DAOEncheres;

public class DAOEncheresJDBCImpl implements DAOEncheres {

	private final String SELECT_ENCHERE_MAX = "SELECT * FROM ENCHERES WHERE montant_enchere = (SELECT MAX(montant_enchere) FROM ENCHERES WHERE no_article = ?) AND no_article = ? ORDER BY date_enchere ASC";
	
	@Override
	public List<Enchere> selectAllEncheresUser(int noUtilisateur) throws DALException {
		return null;
	}

	@Override
	public Enchere selectEnchereMaxArticle(int noArticle) throws DALException {
		Enchere enchereMax = null;

		try(	Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement pst = cnx.prepareStatement(SELECT_ENCHERE_MAX);
				)
		{
			pst.setInt(1, noArticle);
			pst.setInt(2, noArticle);
			
			try(ResultSet rs = pst.executeQuery();)
			{
				if(rs.next())
				{
					enchereMax = new Enchere();
					enchereMax.getUtilisateur().setNoUtilisateur(rs.getInt("no_utilisateur"));
					enchereMax.setMontantEnchere(rs.getInt("montant"));
				}
			}

		}catch (SQLException ex) 
		{
			throw new DALException("erreur lors de la récupération des catégories", ex);
		}
		return enchereMax;
	}
	
}
