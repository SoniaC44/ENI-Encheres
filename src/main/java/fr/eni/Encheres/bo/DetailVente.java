package fr.eni.Encheres.bo;

import java.time.LocalDate;

public class DetailVente {
	
	private int noArticle;
	private String nomArticle;
	private String description;
	private LocalDate dateFinEncheres;
	private int miseAPrix;
	private Utilisateur utilisateurMise;
	private int prixVente;
	private Utilisateur utilisateur;
	private Categorie categorie;

	public DetailVente() {
		super();
	}

	public DetailVente(int noArticle, String nomArticle, String description, LocalDate dateFinEncheres, int miseAPrix,
			Utilisateur utilisateurMise, int prixVente, Utilisateur utilisateur, Categorie categorie) {
		super();
		this.noArticle = noArticle;
		this.nomArticle = nomArticle;
		this.description = description;
		this.dateFinEncheres = dateFinEncheres;
		this.miseAPrix = miseAPrix;
		this.utilisateurMise = utilisateurMise;
		this.prixVente = prixVente;
		this.utilisateur = utilisateur;
		this.categorie = categorie;
	}

	public int getNoArticle() {
		return noArticle;
	}

	public String getNomArticle() {
		return nomArticle;
	}

	public String getDescription() {
		return description;
	}

	public LocalDate getDateFinEncheres() {
		return dateFinEncheres;
	}

	public int getMiseAPrix() {
		return miseAPrix;
	}

	public Utilisateur getUtilisateurMise() {
		return utilisateurMise;
	}

	public int getPrixVente() {
		return prixVente;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public Categorie getCategorie() {
		return categorie;
	}

	public void setNoArticle(int noArticle) {
		this.noArticle = noArticle;
	}

	public void setNomArticle(String nomArticle) {
		this.nomArticle = nomArticle;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDateFinEncheres(LocalDate dateFinEncheres) {
		this.dateFinEncheres = dateFinEncheres;
	}

	public void setMiseAPrix(int miseAPrix) {
		this.miseAPrix = miseAPrix;
	}

	public void setUtilisateurMise(Utilisateur utilisateurMise) {
		this.utilisateurMise = utilisateurMise;
	}

	public void setPrixVente(int prixVente) {
		this.prixVente = prixVente;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	@Override
	public String toString() {
		return "DetailVente [noArticle=" + noArticle + ", nomArticle=" + nomArticle + ", description=" + description
				+ ", dateFinEncheres=" + dateFinEncheres + ", miseAPrix=" + miseAPrix + ", utilisateurMise="
				+ utilisateurMise + ", prixVente=" + prixVente + ", utilisateur=" + utilisateur + ", categorie="
				+ categorie + "]";
	}
	
}
