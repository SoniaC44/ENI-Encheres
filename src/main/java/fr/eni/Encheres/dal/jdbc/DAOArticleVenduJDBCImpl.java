package fr.eni.Encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import fr.eni.Encheres.bo.ArticleVendu;
import fr.eni.Encheres.bo.Categorie;
import fr.eni.Encheres.bo.Enchere;
import fr.eni.Encheres.bo.Utilisateur;
import fr.eni.Encheres.dal.ConnectionProvider;
import fr.eni.Encheres.dal.DALException;
import fr.eni.Encheres.dal.DAOArticleVendu;

public class DAOArticleVenduJDBCImpl implements DAOArticleVendu{
	
	private final String CODE_ETAT_VENTE_EN_COURS = "ENC";
	private final String CODE_ETAT_VENTE_NON_COMMENCEE = "NOC";
	private final String CODE_ETAT_VENTE_TERMINEE = "TER";
	private final String MSG_ERREUR_PARSE = "le code de la catégorie doit être de type int";
	
	
	private final String SELECT_ALL_ARTICLES = "SELECT * FROM ARTICLES_VENDUS av "
			+ "INNER JOIN UTILISATEURS u on av.no_utilisateur = u.no_utilisateur "
			+ "INNER JOIN CATEGORIES c on av.no_categorie = c.no_categorie "
			+ "WHERE date_debut_encheres <= ? AND date_fin_encheres >= ?";
	private final String SELECT_WITH_CATEGORIE = "SELECT * FROM ARTICLES_VENDUS "
													+ "INNER JOIN UTILISATEURS ON ARTICLES_VENDUS.no_utilisateur =UTILISATEURS.no_utilisateur "
													+ "WHERE (date_debut_encheres <= ? AND date_fin_encheres >= ?) AND no_categorie = ?";
	private final String SELECT_WITH_NOM = "SELECT * FROM ARTICLES_VENDUS "
												+ "INNER JOIN UTILISATEURS ON ARTICLES_VENDUS.no_utilisateur =UTILISATEURS.no_utilisateur "
												+ "WHERE (date_debut_encheres <= ? AND date_fin_encheres >= ?) AND (nom_article LIKE ? OR description LIKE ?)";
	private final String SELECT_WITH_NOM_AND_CATEGORIE = "SELECT * FROM ARTICLES_VENDUS "
															+ "INNER JOIN UTILISATEURS ON ARTICLES_VENDUS.no_utilisateur =UTILISATEURS.no_utilisateur "
															+ "WHERE (date_debut_encheres <= ? AND date_fin_encheres >= ?) AND (nom_article LIKE ? OR description LIKE ?) AND no_categorie = ?";
	
	private final String SELECT_DETAIL_ARTICLE = "SELECT * FROM ARTICLES_VENDUS av "
			+ "INNER JOIN CATEGORIES c on av.no_categorie = c.no_categorie "
			+ "INNER JOIN UTILISATEURS u on av.no_utilisateur = u.no_utilisateur "
			+ "LEFT JOIN ENCHERES e on av.no_article = e.no_article "
			+ "WHERE av.no_article = ?";
	private final String INSERT_ARTICLE = "INSERT INTO ARTICLES_VENDUS (nom_article,description,date_debut_encheres,date_fin_encheres,prix_initial,no_utilisateur,no_categorie) "
											+ "VALUES(?,?,?,?,?,?,?)";
	private final String INSERT_RETRAIT = "INSERT INTO RETRAITS (no_article,rue,code_postal,ville) VALUES (?,?,?,?)";
	private final String SELECT_ALL_ARTICLES_VENTE_TERMINEE = "SELECT no_article, no_utilisateur FROM ARTICLES_VENDUS WHERE date_fin_encheres < ? AND prix_vente IS NULL";
	private final String UPDATE_ARTICLE_SET_PRIX_VENTE = "UPDATE ARTICLES_VENDUS SET prix_vente = ? WHERE no_article = ?";
	private final String UPDATE_UTILISATEURS_SET_CREDIT = "UPDATE UTILISATEURS SET credit = ((SELECT credit FROM UTILISATEURS WHERE no_utilisateur = ?) + (?)) WHERE no_utilisateur = ?";

	
	@Override
	public List<ArticleVendu> selectArticlesVenteEnCours() throws DALException {
		List<ArticleVendu> listeArticlesVendus = new ArrayList<ArticleVendu>();
		ArticleVendu unArticle = null;
		
		try(	Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement pst = cnx.prepareStatement(SELECT_ALL_ARTICLES);)
		{
			pst.setDate(1, Date.valueOf(LocalDate.now()));
			pst.setDate(2, Date.valueOf(LocalDate.now()));
			try(ResultSet rs = pst.executeQuery();){
				while(rs.next())
				{
					unArticle = new ArticleVendu();
					unArticle.setNoArticle(rs.getInt("no_article"));
					unArticle.setNomArticle(rs.getString("nom_article"));
					unArticle.setDescription(rs.getString("description"));
					
					LocalDate date_debut = rs.getDate("date_debut_encheres").toLocalDate();
					unArticle.setDateDebutEncheres(date_debut);
					LocalDate date_fin = rs.getDate("date_fin_encheres").toLocalDate();
					unArticle.setDateFinEncheres(date_fin);
					
					unArticle.setMiseAPrix(rs.getInt("prix_initial"));
					unArticle.setPrixVente(rs.getInt("prix_vente"));
					
					//gestion etat de vente de l'article
					String etat = "";
					if(date_debut.isAfter(LocalDate.now())) {
						etat = CODE_ETAT_VENTE_NON_COMMENCEE;
					}else if(date_fin.isBefore(LocalDate.now())) {
						etat = CODE_ETAT_VENTE_TERMINEE;
					}else {
						etat = CODE_ETAT_VENTE_EN_COURS;
					}
					unArticle.setEtatVente(etat);
					
					Categorie uneCategorie = new Categorie();
					uneCategorie.setNoCategorie(rs.getInt("no_categorie"));
					uneCategorie.setLibelle(rs.getString("libelle"));
					unArticle.setCategorie(uneCategorie);
					
					Utilisateur unUtilisateur = new Utilisateur();
					unUtilisateur.setNoUtilisateur(rs.getInt("no_utilisateur"));
					unUtilisateur.setPseudo(rs.getString("pseudo"));
					unUtilisateur.setPrenom(rs.getString("prenom"));
					unUtilisateur.setNom(rs.getString("nom"));
					unUtilisateur.setEmail(rs.getString("email"));
					unUtilisateur.setTelephone(rs.getString("telephone"));
					unUtilisateur.setRue(rs.getString("rue"));
					unUtilisateur.setCodePostal(rs.getInt("code_postal"));
					unUtilisateur.setVille(rs.getString("ville"));
					unArticle.setUtilisateur(unUtilisateur);
					
					listeArticlesVendus.add(unArticle);
				}
			}

		}catch (SQLException ex) 
		{
			throw new DALException("erreur lors de la récupération de la liste des articles vendus", ex);
		}
		
		return listeArticlesVendus;
	}

	@Override
	public ArticleVendu getArticleVendu(int noArticle) throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertArticleVendu(ArticleVendu article) throws DALException {
		try(	Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement pstA = cnx.prepareStatement(INSERT_ARTICLE, PreparedStatement.RETURN_GENERATED_KEYS);
				PreparedStatement pstR = cnx.prepareStatement(INSERT_RETRAIT);)
		{
			cnx.setAutoCommit(false);
			
			pstA.setString(1, article.getNomArticle());
			pstA.setString(2, article.getDescription());
			pstA.setDate(3, Date.valueOf(article.getDateDebutEncheres()));
			pstA.setDate(4, Date.valueOf(article.getDateFinEncheres()));
			pstA.setInt(5, article.getMiseAPrix());
			pstA.setInt(6, article.getUtilisateur().getNoUtilisateur());
			pstA.setInt(7, article.getCategorie().getNoCategorie());
			
			pstA.executeUpdate();
			
			 try (ResultSet generatedKeys = pstA.getGeneratedKeys()) {
		            if (generatedKeys.next()) {
		            	article.setNoArticle(generatedKeys.getInt(1));
		            }	
		     }
			 
			 pstR.setInt(1, article.getNoArticle());
			 pstR.setString(2, article.getLieuRetrait().getRue());
			 pstR.setInt(3, article.getLieuRetrait().getCodePostal());
			 pstR.setString(4, article.getLieuRetrait().getVille());
			 
			 pstR.executeUpdate();
			 
			 cnx.commit();
			
		}catch (SQLException ex) 
		{
			throw new DALException("erreur lors de l'ajout de l'utilisateur en BDD", ex);
		}	
	}

	@Override
	public ArticleVendu selectArticleVendu(int noArticle) throws DALException {
		ArticleVendu unArticle = null;
		
				
		try(	Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement pst = cnx.prepareStatement(SELECT_DETAIL_ARTICLE);)  
		{
			pst.setInt(1, noArticle);
			try(ResultSet rs = pst.executeQuery();){
					while (rs.next()) {
					unArticle = new ArticleVendu();
					unArticle.setNoArticle(rs.getInt("no_article"));
					unArticle.setNomArticle(rs.getString("nom_article"));
					unArticle.setDescription(rs.getString("description"));
					
					LocalDate date_fin = rs.getDate("date_fin_encheres").toLocalDate();
					unArticle.setDateFinEncheres(date_fin);
					
					unArticle.setMiseAPrix(rs.getInt("prix_initial"));
					unArticle.setPrixVente(rs.getInt("prix_vente"));
					
										
					Categorie uneCategorie = new Categorie();
					uneCategorie.setNoCategorie(rs.getInt("no_categorie"));
					uneCategorie.setLibelle(rs.getString("libelle"));
					unArticle.setCategorie(uneCategorie);
					
					Utilisateur unUtilisateur = new Utilisateur();
					unUtilisateur.setNoUtilisateur(rs.getInt("no_utilisateur"));
					unUtilisateur.setPseudo(rs.getString("pseudo"));
					unUtilisateur.setRue(rs.getString("rue"));
					unUtilisateur.setCodePostal(rs.getInt("code_postal"));
					unUtilisateur.setVille(rs.getString("ville"));
					unArticle.setUtilisateur(unUtilisateur);
					}
					
				
			}

		}catch (SQLException ex) 
		{
			throw new DALException("erreur lors de la récupération de la liste des articles vendus", ex);
		}
		
		return unArticle;
		
	}

	
	@Override
	public List<ArticleVendu> selectArticlesDUneCategorie(String choixCategorie) throws DALException {
		List<ArticleVendu> listeArticlesVendus = new ArrayList<ArticleVendu>();
		ArticleVendu unArticle = null;
		
		try(	Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement pst = cnx.prepareStatement(SELECT_WITH_CATEGORIE);)
		{
			pst.setDate(1, Date.valueOf(LocalDate.now()));
			pst.setDate(2, Date.valueOf(LocalDate.now()));
			try {
				pst.setInt(3, Integer.parseInt(choixCategorie));
			}catch (Exception e) {
				throw new SQLException(MSG_ERREUR_PARSE);
			}

			try(ResultSet rs = pst.executeQuery();){
				while(rs.next())
				{
					unArticle = new ArticleVendu();
					unArticle.setNoArticle(rs.getInt("no_article"));
					unArticle.setNomArticle(rs.getString("nom_article"));
					
					LocalDate date_debut = rs.getDate("date_debut_encheres").toLocalDate();
					unArticle.setDateDebutEncheres(date_debut);
					
					unArticle.setMiseAPrix(rs.getInt("prix_initial"));
					
					Utilisateur unUtilisateur = new Utilisateur();
					unUtilisateur.setNoUtilisateur(rs.getInt("no_utilisateur"));
					unUtilisateur.setPseudo(rs.getString("pseudo"));
					unArticle.setUtilisateur(unUtilisateur);
					
					listeArticlesVendus.add(unArticle);
				}
			}

		}catch (SQLException ex) 
		{
			throw new DALException("erreur lors de la récupération de la liste des articles vendus triés par categorie", ex);
		}
		
		return listeArticlesVendus;
	}
	

	@Override
	public List<ArticleVendu> selectArticlesParNom(String choixNomArticle) throws DALException {
		List<ArticleVendu> listeArticlesVendus = new ArrayList<ArticleVendu>();
		ArticleVendu unArticle = null;
		
		try(	Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement pst = cnx.prepareStatement(SELECT_WITH_NOM);)
		{
			pst.setDate(1, Date.valueOf(LocalDate.now()));
			pst.setDate(2, Date.valueOf(LocalDate.now()));
			pst.setString(3,"%"+choixNomArticle+"%");
			pst.setString(4,"%"+choixNomArticle+"%");


			try(ResultSet rs = pst.executeQuery();){
				while(rs.next())
				{
					unArticle = new ArticleVendu();
					unArticle.setNoArticle(rs.getInt("no_article"));
					unArticle.setNomArticle(rs.getString("nom_article"));
					
					LocalDate date_debut = rs.getDate("date_debut_encheres").toLocalDate();
					unArticle.setDateDebutEncheres(date_debut);
					
					unArticle.setMiseAPrix(rs.getInt("prix_initial"));
					
					Utilisateur unUtilisateur = new Utilisateur();
					unUtilisateur.setNoUtilisateur(rs.getInt("no_utilisateur"));
					unUtilisateur.setPseudo(rs.getString("pseudo"));
					unArticle.setUtilisateur(unUtilisateur);
					
					listeArticlesVendus.add(unArticle);
				}
			}

		}catch (SQLException ex) 
		{
			throw new DALException("erreur lors de la récupération de la liste des articles vendus triés par nom", ex);
		}
		
		return listeArticlesVendus;
	}
	

	@Override
	public List<ArticleVendu> selectArticleParNomEtParCategorie(String choixCategorie, String choixNomArticle) throws DALException{
		List<ArticleVendu> listeArticlesVendus = new ArrayList<ArticleVendu>();
		ArticleVendu unArticle = null;
		try(	Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement pst = cnx.prepareStatement(SELECT_WITH_NOM_AND_CATEGORIE);)
		{
			pst.setDate(1, Date.valueOf(LocalDate.now()));
			pst.setDate(2, Date.valueOf(LocalDate.now()));
			pst.setString(3,"%"+choixNomArticle+"%");
			pst.setString(4,"%"+choixNomArticle+"%");
			try {
				pst.setInt(5, Integer.parseInt(choixCategorie));
			}catch (Exception e) {
				throw new SQLException(MSG_ERREUR_PARSE);
			}
	
	
			try(ResultSet rs = pst.executeQuery();){
				while(rs.next())
				{
					unArticle = new ArticleVendu();
					unArticle.setNoArticle(rs.getInt("no_article"));
					unArticle.setNomArticle(rs.getString("nom_article"));
					
					LocalDate date_debut = rs.getDate("date_debut_encheres").toLocalDate();
					unArticle.setDateDebutEncheres(date_debut);
					
					unArticle.setMiseAPrix(rs.getInt("prix_initial"));
					
					Utilisateur unUtilisateur = new Utilisateur();
					unUtilisateur.setNoUtilisateur(rs.getInt("no_utilisateur"));
					unUtilisateur.setPseudo(rs.getString("pseudo"));
					unArticle.setUtilisateur(unUtilisateur);
					
					listeArticlesVendus.add(unArticle);
				}
			}
	
		}catch (SQLException ex) 
		{
			throw new DALException("erreur lors de la récupération de la liste des articles vendus triés par nom et catégorie", ex);
		}
		
		return listeArticlesVendus;
	}

	@Override
	public List<ArticleVendu> selectArticlesVenteTerminee() throws DALException {
		List<ArticleVendu> listeArticlesVendus = new ArrayList<ArticleVendu>();
		ArticleVendu unArticle = null;
		
		try(	Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement pst = cnx.prepareStatement(SELECT_ALL_ARTICLES_VENTE_TERMINEE);)
		{
			pst.setDate(1, Date.valueOf(LocalDate.now()));
			try(ResultSet rs = pst.executeQuery();){
				while(rs.next())
				{
					unArticle = new ArticleVendu();
					unArticle.setNoArticle(rs.getInt("no_article"));
					unArticle.getUtilisateur().setNoUtilisateur(rs.getInt("no_utilisateur"));
					
					listeArticlesVendus.add(unArticle);
				}
			}

		}catch (SQLException ex) 
		{
			throw new DALException("erreur lors de la récupération de la liste des articles vendus dont la vente est terminée", ex);
		}
		
		return listeArticlesVendus;
	}

	@Override
	public void updateArticleSetPrixVenteEtSetCredit(Enchere enchereMax) throws DALException {

		try(	Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement pstA = cnx.prepareStatement(UPDATE_ARTICLE_SET_PRIX_VENTE);
				PreparedStatement pstUser = cnx.prepareStatement(UPDATE_UTILISATEURS_SET_CREDIT);)
		{
			cnx.setAutoCommit(false);
			
			pstA.setInt(1,enchereMax.getMontantEnchere());
			pstA.setInt(2, enchereMax.getArticle().getNoArticle());
			
			pstA.executeUpdate();
			
			//vendeur
			pstUser.setInt(1, enchereMax.getArticle().getUtilisateur().getNoUtilisateur());
			pstUser.setInt(2, enchereMax.getMontantEnchere());
			pstUser.setInt(3, enchereMax.getArticle().getUtilisateur().getNoUtilisateur());
			
			pstUser.executeUpdate();
			
			//acheteur
			pstUser.setInt(1, enchereMax.getArticle().getUtilisateur().getNoUtilisateur());
			pstUser.setInt(2, -1*enchereMax.getMontantEnchere());
			pstUser.setInt(3, enchereMax.getArticle().getUtilisateur().getNoUtilisateur());
			
			pstUser.executeUpdate();
			
			cnx.commit();


		}catch (SQLException ex) 
		{
			throw new DALException("erreur lors de la récupération de la liste des articles vendus dont la vente est terminée", ex);
		}
	}

}
