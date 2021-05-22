package fr.eni.Encheres.bo;

import java.time.LocalDate;

public class ArticleVendu {
	
	private Integer noArticle;
	private String nomArticle;
	private String description;
	private LocalDate dateDebutEncheres;
	private LocalDate dateFinEncheres;
	private int miseAPrix;
	private int prixVente;
	private String etatVente; //NOC : non commencee ,ENC: en cours, TER terminee
	private Utilisateur utilisateur;
	private Categorie categorie;
	private Retrait lieuRetrait;
	private int miseMax;
	
	public ArticleVendu() {
		super();
		this.utilisateur = new Utilisateur();
		this.lieuRetrait = new Retrait();
		this.categorie = new Categorie();
	}


	public ArticleVendu(Integer noArticle, String nomArticle, String description, LocalDate dateDebutEncheres,
			LocalDate dateFinEncheres, int miseAPrix, int prixVente, String etatVente, Utilisateur utilisateur,
			Categorie categorie,Retrait lieuRetrait, int miseMax) {
		super();
		this.noArticle = noArticle;
		this.nomArticle = nomArticle;
		this.description = description;
		this.dateDebutEncheres = dateDebutEncheres;
		this.dateFinEncheres = dateFinEncheres;
		this.miseAPrix = miseAPrix;
		this.prixVente = prixVente;
		this.etatVente = etatVente;
		this.utilisateur = utilisateur;
		this.categorie = categorie;
		this.lieuRetrait = lieuRetrait;
		this.lieuRetrait.setNoArticle(this.noArticle);
		this.miseMax = miseMax;
	}
	
	public Integer getNoArticle() {
		return noArticle;
	}



	public void setNoArticle(Integer noArticle) {
		this.noArticle = noArticle;
	}



	public String getNomArticle() {
		return nomArticle;
	}



	public void setNomArticle(String nomArticle) {
		this.nomArticle = nomArticle;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public LocalDate getDateDebutEncheres() {
		return dateDebutEncheres;
	}



	public void setDateDebutEncheres(LocalDate dateDebutEncheres) {
		this.dateDebutEncheres = dateDebutEncheres;
	}



	public LocalDate getDateFinEncheres() {
		return dateFinEncheres;
	}



	public void setDateFinEncheres(LocalDate dateFinEncheres) {
		this.dateFinEncheres = dateFinEncheres;
	}



	public int getMiseAPrix() {
		return miseAPrix;
	}



	public void setMiseAPrix(int miseAPrix) {
		this.miseAPrix = miseAPrix;
	}



	public int getPrixVente() {
		return prixVente;
	}



	public void setPrixVente(int prixVente) {
		this.prixVente = prixVente;
	}



	public String getEtatVente() {
		return etatVente;
	}



	public void setEtatVente(String etatVente) {
		this.etatVente = etatVente;
	}



	public Utilisateur getUtilisateur() {
		return utilisateur;
	}



	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}



	public Categorie getCategorie() {
		return categorie;
	}



	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}



	public Retrait getLieuRetrait() {
		return lieuRetrait;
	}


	public void setLieuRetrait(Retrait lieuRetrait) {
		this.lieuRetrait = lieuRetrait;

		if(this.noArticle!=null && this.lieuRetrait!= null)
			this.lieuRetrait.setNoArticle(this.noArticle);
	}


	public int getMiseMax() {
		return miseMax;
	}



	public void setMiseMax(int miseMax) {
		this.miseMax = miseMax;
	}




	@Override
	public String toString() {
		return "ArticleVendu [noArticle=" + noArticle + ", nomArticle=" + nomArticle + ", description=" + description
				+ ", dateDebutEncheres=" + dateDebutEncheres + ", dateFinEncheres=" + dateFinEncheres + ", miseAPrix="
				+ miseAPrix + ", prixVente=" + prixVente + ", etatVente=" + etatVente + ", utilisateur=" + utilisateur
				+ ", categorie=" + categorie + ", lieuRetrait=" + lieuRetrait + ", miseMax=" + miseMax + "]";
	}
	
}
