package fr.eni.Encheres.dal;

import java.util.List;

import fr.eni.Encheres.bo.ArticleVendu;
import fr.eni.Encheres.bo.Enchere;

public interface DAOArticleVendu {
	
	public ArticleVendu getArticleVendu(int noArticle) throws DALException;
	
	public void insertArticleVendu(ArticleVendu article) throws DALException;
	
	public List<ArticleVendu> selectArticlesVenteEnCours() throws DALException;

	public ArticleVendu selectArticleVendu(int noArticle) throws DALException;

	public List<ArticleVendu> selectArticlesDUneCategorie(String choixCategorie) throws DALException;

	public List<ArticleVendu> selectArticlesParNom(String choixNomArticle) throws DALException;

	public List<ArticleVendu> selectArticleParNomEtParCategorie(String choixCategorie, String choixNomArticle) throws DALException;

	public List<ArticleVendu> selectArticlesVenteTerminee() throws DALException;

	public void updateArticleSetPrixVenteEtSetCredit(Enchere enchereMax) throws DALException;
}