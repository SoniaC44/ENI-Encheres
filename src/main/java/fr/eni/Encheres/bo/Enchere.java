package fr.eni.Encheres.bo;

import java.time.LocalDate;

public class Enchere {

	private Utilisateur utilisateur;
	private int montantEnchere;
	private LocalDate dateEnchere;
	private ArticleVendu article;
	
	
	public Enchere() {
		super();
		this.utilisateur = new Utilisateur();
		this.article = new ArticleVendu();
	}

	public Enchere(Utilisateur utilisateur, int montantEnchere, LocalDate dateEnchere, ArticleVendu article) {
		super();
		this.utilisateur = utilisateur;
		this.montantEnchere = montantEnchere;
		this.dateEnchere = dateEnchere;
		this.article = article;
	}
	
	public Utilisateur getUtilisateur() {
		return utilisateur;
	}
	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}
	public int getMontantEnchere() {
		return montantEnchere;
	}
	public void setMontantEnchere(int montantEnchere) {
		this.montantEnchere = montantEnchere;
	}
	public LocalDate getDateEnchere() {
		return dateEnchere;
	}
	public void setDateEnchere(LocalDate dateEnchere) {
		this.dateEnchere = dateEnchere;
	}
	public ArticleVendu getArticle() {
		return article;
	}
	public void setArticle(ArticleVendu article) {
		this.article = article;
	}

	@Override
	public String toString() {
		return "Enchere [utilisateur=" + utilisateur + ", montantEnchere=" + montantEnchere + ", dateEnchere="
				+ dateEnchere + ", article=" + article + "]";
	}
	
	
}
