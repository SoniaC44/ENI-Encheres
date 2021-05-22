package fr.eni.Encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.eni.Encheres.bo.Utilisateur;
import fr.eni.Encheres.dal.ConnectionProvider;
import fr.eni.Encheres.dal.DALException;
import fr.eni.Encheres.dal.DAOUtilisateur;

public class DAOUtilisateurJDBCImpl implements DAOUtilisateur {
	
	private final String SELECT_USER_ID_PSEUDO = "SELECT COUNT(*) AS total FROM UTILISATEURS WHERE pseudo LIKE ?";
	private final String SELECT_USER_ID_EMAIL = "SELECT COUNT(*) AS total FROM UTILISATEURS WHERE email LIKE ?";
	private final String SELECT_USER = "SELECT * FROM UTILISATEURS WHERE (pseudo LIKE ? OR email LIKE ?) AND mot_de_passe=?";
	private final String INSERT_USER = "INSERT INTO UTILISATEURS(pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur) values(?,?,?,?,?,?,?,?,?,?,?)";
	private final String SELECT_PROFIL_USER = "SELECT pseudo, nom, prenom, email, telephone, rue, code_postal, ville FROM UTILISATEURS WHERE no_utilisateur = ?";
	private final String UPDATE_USER = "UPDATE UTILISATEURS " 
										+ "SET pseudo = ?, nom = ?, prenom = ?, email = ?, telephone = ?, rue = ?, code_postal = ?, ville = ?, mot_de_passe = ? "
										+ "WHERE no_utilisateur = ?";
	private final String DELETE_USER = "DELETE UTILISATEURS "
			+ "WHERE no_utilisateur = ?";
	private final String DELETE_ENCHERE = "DELETE ENCHERES "
											+ "WHERE no_utilisateur = ?";
	private final String DELETE_ENCHERE_IN = "DELETE ENCHERES "
												+ "WHERE no_article IN (SELECT no_article FROM ARTICLES_VENDUS WHERE no_utilisateur = ?)";
	private final String DELETE_ARTICLE = "DELETE ARTICLES_VENDUS "
											+ "WHERE no_utilisateur = ?";


	@Override
	public void insertUser(Utilisateur utilisateur) throws DALException {
		try(	Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement pst = cnx.prepareStatement(INSERT_USER, PreparedStatement.RETURN_GENERATED_KEYS);)
		{
		
			pst.setString(1, utilisateur.getPseudo());
			pst.setString(2, utilisateur.getNom());
			pst.setString(3, utilisateur.getPrenom());
			pst.setString(4, utilisateur.getEmail());
			pst.setObject(5, utilisateur.getTelephone(), java.sql.Types.INTEGER);
			pst.setString(6, utilisateur.getRue());
			pst.setInt(7, utilisateur.getCodePostal());
			pst.setString(8, utilisateur.getVille());
			pst.setString(9, utilisateur.getMotDePasse());
			pst.setInt(10, utilisateur.getCredit());	
			pst.setByte(11, utilisateur.getAdministrateur());
			
			pst.executeUpdate();
			
			 try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
		            if (generatedKeys.next()) {
		            	utilisateur.setNoUtilisateur(generatedKeys.getInt(1));
		            }	
		     }
			 
			pst.close();
			
		}catch (SQLException ex) 
		{
			throw new DALException("erreur lors de l'ajout de l'utilisateur en BDD", ex);
		}	
		
	}
	
	@Override
	public boolean existeUserPseudo(String pseudo) throws DALException {
		boolean existeUnUser = false;
		int nbUtilisateur = 0;
		try(	Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement pst = cnx.prepareStatement(SELECT_USER_ID_PSEUDO);)
		{
			pst.setString(1, pseudo);
						
			try(ResultSet rs = pst.executeQuery())
			{
				if(rs.next())
				{
					nbUtilisateur = rs.getInt("total");
				}
			}
		}catch (SQLException ex) 
		{
			throw new DALException("erreur lors de la récupération de l'utilisateur en BDD", ex);
		}
		if(nbUtilisateur>0)
			existeUnUser = true;
		
		return existeUnUser;
	}
	
	@Override
	public boolean existeUserEmail(String email) throws DALException {
		boolean existeUnUser = false;
		int nbUtilisateur = 0;
		try(	Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement pst = cnx.prepareStatement(SELECT_USER_ID_EMAIL);)
		{
			pst.setString(1, email);
						
			try(ResultSet rs = pst.executeQuery())
			{
				if(rs.next())
				{
					nbUtilisateur = rs.getInt("total");
				}
			}
		}catch (SQLException ex) 
		{
			throw new DALException("erreur lors de la récupération de l'utilisateur en BDD", ex);
		}
		if(nbUtilisateur>0)
			existeUnUser = true;
		
		return existeUnUser;
	}

	@Override
	public Utilisateur getUser(String identifiant, String motDePasse) throws DALException {
		Utilisateur utilisateur = null;
			
		try(	Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement pst = cnx.prepareStatement(SELECT_USER);)
		{
			pst.setString(1, identifiant);
			pst.setString(2, identifiant);
			pst.setString(3, motDePasse);
			
			
			try(ResultSet rs = pst.executeQuery())
			{
				while(rs.next())
				{
					utilisateur = new Utilisateur();
					utilisateur.setNoUtilisateur(rs.getInt("no_utilisateur"));
					utilisateur.setPseudo(rs.getString("pseudo"));
					utilisateur.setPrenom(rs.getString("prenom"));
					utilisateur.setNom(rs.getString("nom"));
					utilisateur.setEmail(rs.getString("email"));
					if(rs.getString("telephone")!= null)
						utilisateur.setTelephone(rs.getString("telephone"));
					utilisateur.setRue(rs.getString("rue"));
					utilisateur.setCodePostal(Integer.parseInt(rs.getString("code_postal")));
					utilisateur.setVille(rs.getString("ville"));
					utilisateur.setMotDePasse(rs.getString("mot_de_passe"));
					utilisateur.setCredit(rs.getInt("credit"));
					utilisateur.setAdministrateur(rs.getByte("administrateur"));
				}
			}
		}catch (SQLException ex) 
		{
			throw new DALException("erreur lors de la récupération de l'utilisateur en BDD", ex);
		}
		return utilisateur;
	}
	

	@Override
	public Utilisateur getProfilUser(int no_utilisateur) throws DALException {	
		Utilisateur utilisateur = null;
		try(	Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement pst = cnx.prepareStatement(SELECT_PROFIL_USER);)
		{
			utilisateur = new Utilisateur();
			pst.setInt(1, no_utilisateur);
			try(ResultSet rs = pst.executeQuery())
			{
				while(rs.next())
				{
				utilisateur.setPseudo(rs.getString("pseudo"));
				utilisateur.setNom(rs.getString("nom"));
				utilisateur.setPrenom(rs.getString("prenom"));
				utilisateur.setEmail(rs.getString("email"));
				if(rs.getString("telephone")!= null)
					utilisateur.setTelephone(rs.getString("telephone"));
				utilisateur.setRue(rs.getString("rue"));
				utilisateur.setCodePostal(Integer.parseInt(rs.getString("code_postal")));
				utilisateur.setVille(rs.getString("ville"));
				}
			}
			
		} catch (SQLException ex) {
			throw new DALException("erreur lors de la r�cup�ration pour l'affichage du profil de l'utilisateur s�l�ctionn� en BDD", ex);
		}	
		return utilisateur;
	}

	@Override
	public void updateUser(Utilisateur utilisateur) throws DALException {

		try(	Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement pst = cnx.prepareStatement(UPDATE_USER, PreparedStatement.RETURN_GENERATED_KEYS);)
		{
			pst.setString(1, utilisateur.getPseudo());
			pst.setString(2, utilisateur.getNom());
			pst.setString(3, utilisateur.getPrenom());
			pst.setString(4, utilisateur.getEmail());
			pst.setObject(5, utilisateur.getTelephone(), java.sql.Types.INTEGER);
			pst.setString(6, utilisateur.getRue());
			pst.setInt(7, utilisateur.getCodePostal());
			pst.setString(8, utilisateur.getVille());
			pst.setString(9, utilisateur.getMotDePasse());
			pst.setInt(10, utilisateur.getNoUtilisateur());
			
			pst.executeUpdate();
			
		}catch (SQLException ex) 
		{
			throw new DALException("erreur lors de la modification de l'utilisateur en BDD", ex);
		}	
	}
	
	@Override
	public void deleteUser(int noUtilisateur) throws DALException {

		try(	Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement pstDU = cnx.prepareStatement(DELETE_USER);
				PreparedStatement pstDE = cnx.prepareStatement(DELETE_ENCHERE);
				PreparedStatement pstDEin = cnx.prepareStatement(DELETE_ENCHERE_IN);
				PreparedStatement pstDA = cnx.prepareStatement(DELETE_ARTICLE);) 
		{
			cnx.setAutoCommit(false);
			
			// 1 - delete encheres liees à l'user
			pstDE.setInt(1, noUtilisateur);
			pstDE.executeUpdate();
			
			// 2 - delete encheres liées aux articles vendus par l'utilisateur
			pstDEin.setInt(1, noUtilisateur);
			pstDEin.executeUpdate();
			
			// 3 - delete articles vendus par l'utilisateur
			pstDA.setInt(1, noUtilisateur);
			pstDA.executeUpdate();
			
			// 4 - delete utilisateur
			pstDU.setInt(1, noUtilisateur);
			pstDU.executeUpdate();
					
			cnx.commit();
			cnx.setAutoCommit(true);
				
		}catch (SQLException ex) 
		{
			throw new DALException("erreur lors de la suppression de l'utilisateur en BDD", ex);
		}	
	}
	
}
