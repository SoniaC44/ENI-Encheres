package fr.eni.Encheres.bll;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import fr.eni.Encheres.bo.ArticleVendu;
import fr.eni.Encheres.bo.Enchere;
import fr.eni.Encheres.bo.Retrait;
import fr.eni.Encheres.dal.DALException;
import fr.eni.Encheres.dal.DAOArticleVendu;
import fr.eni.Encheres.dal.DAOFactory;

public class ArticleVenduManager {

	private DAOArticleVendu daoArticleVendu;

	public ArticleVenduManager() {
		super();
		daoArticleVendu = DAOFactory.getDAOArticleVendu();
	}
	
	public List<ArticleVendu> selectionnerTousLesArticlesEnVente() throws BLLException {
		
		try {
			return this.daoArticleVendu.selectArticlesVenteEnCours();
		} catch (DALException ex) {
			throw new BLLException("BLL - Impossible de récupérer la liste des articles vendus", ex);
		}	
	}
	
	public ArticleVendu selectionnerDetailVente(int noArticle) throws BLLException {
			
		try {
			return this.daoArticleVendu.selectArticleVendu(noArticle);
		} catch (DALException ex) {
			throw new BLLException("BLL - Impossible de récupérer le détail d'un article vendu", ex);
		}	
	}

	public List<ArticleVendu> selectionnerArticlesRecherches(String choixCategorie, String choixNomArticle) throws BLLException {
		try {
			if(choixCategorie.equals("00") && choixNomArticle.trim().isEmpty()) {
				return this.daoArticleVendu.selectArticlesVenteEnCours();
			}else if (choixNomArticle.trim().isEmpty() && !choixCategorie.equals("00")){
				return this.daoArticleVendu.selectArticlesDUneCategorie(choixCategorie);
			}else if(!choixNomArticle.trim().isEmpty() && choixCategorie.equals("00")){
				return this.daoArticleVendu.selectArticlesParNom(choixNomArticle);
			}else {
				return this.daoArticleVendu.selectArticleParNomEtParCategorie(choixCategorie,choixNomArticle);
			}
		} catch (DALException ex) {
			throw new BLLException("BLL - Impossible de récupérer la liste des articles vendus", ex);
		}
	}

	public void ajouterArticle(ArticleVendu article) throws BLLException {
		try {
			this.daoArticleVendu.insertArticleVendu(article);
		} catch (DALException ex) {
			throw new BLLException("BLL - Impossible d'ajouter un article", ex);
		}	
	}

	public boolean verifierSaisiesArticle(HttpServletRequest request, ArticleVendu article) {
		boolean existeErreur = false;
		boolean dateErreur = false;
		LocalDate datefin = null;
		LocalDate dateDebut = null;
		
		if(request.getParameter("inputArticle") != null && !request.getParameter("inputArticle").trim().isEmpty())
		{
			article.setNomArticle(request.getParameter("inputArticle").trim());
			request.setAttribute("inputArticle", request.getParameter("inputArticle").trim());
		}
		else 
		{
			//message d'erreur
			request.setAttribute("msgErreur_titreArticle", "Le nom complet de l'article doit être saisi");
			existeErreur = true;
		}

		if(request.getParameter("inputDescription") != null && !request.getParameter("inputDescription").trim().isEmpty())
		{
			article.setDescription(request.getParameter("inputDescription").trim());
			request.setAttribute("inputDescription", request.getParameter("inputDescription").trim());
		}else
		{
			//message d'erreur
			request.setAttribute("msgErreur_description", "Vous devez saisir une description");
			existeErreur = true;
		}
		
		if(request.getParameter("selectCategorie") != null)
		{
			String choix = request.getParameter("selectCategorie");
			request.setAttribute("choixCategorie", request.getParameter("selectCategorie"));
			article.getCategorie().setNoCategorie(Integer.parseInt(choix));
		}

		if(request.getParameter("inputMiseAPrix") != null && !request.getParameter("inputMiseAPrix").trim().isEmpty())
		{
			try{
				article.setMiseAPrix(Integer.parseInt(request.getParameter("inputMiseAPrix").trim()));
			}
			catch (NumberFormatException e) {
				//message d'erreur
				request.setAttribute("msgErreur_prix", "Le prix ne doit être composé que de chiffres !");
				existeErreur = true;
			}
			request.setAttribute("inputMiseAPrix", request.getParameter("inputMiseAPrix").trim());
		}else 
		{
			//message d'erreur
			request.setAttribute("msgErreur_prix", "Le prix doit être saisi");
			existeErreur = true;
		}
		
		if(request.getParameter("inputDateDebut") != null && !request.getParameter("inputDateDebut").trim().isEmpty())
		{
			try{
				dateDebut = LocalDate.parse(request.getParameter("inputDateDebut").trim());
				if(dateDebut.isBefore(LocalDate.now())){
					//message d'erreur
					request.setAttribute("msgErreur_dateDebut", "La date de début d'enchère ne peut pas être antérieure à la date du jour !");
					existeErreur = true;
					dateErreur = true;
				}else
					article.setDateDebutEncheres(LocalDate.parse(request.getParameter("inputDateDebut").trim()));
			}
			catch (DateTimeParseException e) {
				//message d'erreur
				request.setAttribute("msgErreur_dateDebut", "La date de début d'enchère saisie n'est pas correcte !");
				existeErreur = true;
				dateErreur = true;
			}
			request.setAttribute("inputDateDebut", request.getParameter("inputDateDebut").trim());
		}else 
		{
			//message d'erreur
			request.setAttribute("msgErreur_dateDebut", "La date de début d'enchère doit être saisie");
			existeErreur = true;
			dateErreur = true;
		}
		
		if(request.getParameter("inputDateFin") != null && !request.getParameter("inputDateFin").trim().isEmpty())
		{
			try{
				datefin = LocalDate.parse(request.getParameter("inputDateFin").trim());
				if(datefin.isBefore(LocalDate.now())){
					//message d'erreur
					request.setAttribute("msgErreur_dateFin", "La date de fin d'enchère ne peut pas être antérieure à la date du jour !");
					existeErreur = true;
					dateErreur = true;
				}else
					article.setDateFinEncheres(LocalDate.parse(request.getParameter("inputDateFin").trim()));
			}catch (DateTimeParseException e) {
				//message d'erreur
				request.setAttribute("msgErreur_dateFin", "La date de fin d'enchère saisie n'est pas correcte !");
				existeErreur = true;
				dateErreur = true;
			}
			request.setAttribute("inputDateFin", request.getParameter("inputDateFin").trim());
		}else 
		{
			//message d'erreur
			request.setAttribute("msgErreur_dateFin", "La date de fin d'enchère doit être saisie");
			existeErreur = true;
			dateErreur = true;
		}
		
		if(!dateErreur && dateDebut != null && datefin != null){
			//on vérifie si la date de fin n'est pas anterieure à la date de début
			if(datefin.isBefore(dateDebut)) {
				//message d'erreur
				request.setAttribute("msgErreur_dateFin", "La date de fin d'enchère doit être supérieure à la date de début d'enchère");
				existeErreur = true;
			}
		}
		
		//maintenant la partie Retrait
		RetraitManager rm = new RetraitManager();
		//Retrait unLieuDeRetrait
		Retrait unLieuRetrait = new Retrait();
		
		boolean existeErreurRetrait = rm.verifierSaisiesRetrait(request, unLieuRetrait);
		article.setLieuRetrait(unLieuRetrait);
		
		if(existeErreurRetrait)
			existeErreur = true;
		
		return existeErreur;
	}

	public void mettreAJourArticlesEtCredits() throws BLLException {
		List<ArticleVendu> listeArticles = new ArrayList<ArticleVendu>();
		EnchereManager em = new EnchereManager();
		Enchere enchereMax = null;
		try {
			//on récupere l'i des articles dont la date de vente est passée (< LocalDate.now() )
			// et dont le prix de vente n'est pas initialisé
			listeArticles = this.daoArticleVendu.selectArticlesVenteTerminee();
			
			for(ArticleVendu av : listeArticles) {
				//on va rechercher l'encher maximale pour cet article
				enchereMax = em.recupererEnchereMaxArticle(av.getNoArticle());
				
				//si on a une enchere max
				if(enchereMax!= null) {
					enchereMax.setArticle(av);
					
					//on va mettre le prix de vente à jour avec l'enchere max
					// et en même temps mettre à jour les credits du vendeur et de l'acheteur
					this.daoArticleVendu.updateArticleSetPrixVenteEtSetCredit(enchereMax);
				}
			}
		} catch (DALException e) {
			throw new BLLException("BLL - Impossible de mettre à jour les articles et les credits");
		}
	}
}
