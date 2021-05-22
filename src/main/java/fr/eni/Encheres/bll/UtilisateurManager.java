package fr.eni.Encheres.bll;

import javax.servlet.http.HttpServletRequest;

import fr.eni.Encheres.bo.Utilisateur;
import fr.eni.Encheres.dal.DALException;
import fr.eni.Encheres.dal.DAOFactory;
import fr.eni.Encheres.dal.DAOUtilisateur;

public class UtilisateurManager {

	private DAOUtilisateur daoUtilisateur;
	private final String NOM_ATTRIBUT_SESSION_UTILISATEUR = "utilisateurSession";
	private final String msg_erreur_pseudo = "le pseudo est déjà utilisé par un utilisateur !";
	private final String msg_erreur_email = "le mail est déjà utilisé par un utilisateur !";

	public UtilisateurManager() {
		super();
		daoUtilisateur = DAOFactory.getDAOUtilisateur();
	}
	
	public Utilisateur selectionnerUtilisateur(String identifiant, String motDePasse) throws BLLException {
		
		try {
			return this.daoUtilisateur.getUser(identifiant, motDePasse);
		} catch (DALException ex) {
			throw new BLLException("BLL - Impossible de récupérer l'utilisateur", ex);
		}
		
	}

	public void ajouterUtilisateur(Utilisateur utilisateur) throws BLLException {
		StringBuffer msg_erreur = new StringBuffer();
		boolean existeErreur = false;
		try {
			if(this.daoUtilisateur.existeUserPseudo(utilisateur.getPseudo())) 
			{
				existeErreur = true;
				msg_erreur.append(msg_erreur_pseudo);
			}
			if(this.daoUtilisateur.existeUserEmail(utilisateur.getEmail()))
			{
				existeErreur = true;
				if(msg_erreur.length()>0)
					msg_erreur.append(" et ");
				msg_erreur.append(msg_erreur_email);
			}
			
			if(existeErreur) {
				throw new BLLException(msg_erreur.toString());
			}
			else
			{
				this.daoUtilisateur.insertUser(utilisateur);
			}
		} catch (DALException ex) {
			throw new BLLException("BLL - Impossible d'ajouter l'utilisateur", ex);
		}	
	}
	
	public Utilisateur afficherProfilUtilisateur(int no_utilisateur) throws BLLException {
		
		try {
			return this.daoUtilisateur.getProfilUser(no_utilisateur);
		} catch (Exception ex) {
			throw new BLLException("message erreur", ex);
		}
	}
	
	public void modifierProfilUtilisateur(Utilisateur utilisateur) throws BLLException{
		try {
			this.daoUtilisateur.updateUser(utilisateur);
		}catch(DALException ex) {
			throw new BLLException("BLL - Impossible de modifier l'utilisateur", ex);
		}
	}

	public boolean verifierSaisiesProfil(HttpServletRequest request, Utilisateur user) {

		boolean existeErreur = false;
		
		if(request.getParameter("inputPseudo") != null && !request.getParameter("inputPseudo").isEmpty())
		{
			user.setPseudo(request.getParameter("inputPseudo"));
			request.setAttribute("inputPseudo", request.getParameter("inputPseudo"));
		}
		else 
		{
			//message d'erreur
			request.setAttribute("msgErreur_pseudo", "Le pseudo doit être saisi");
			existeErreur = true;
		}

		if(request.getParameter("inputPrenom") != null && !request.getParameter("inputPrenom").isEmpty())
		{
			user.setPrenom(request.getParameter("inputPrenom"));
			request.setAttribute("inputPrenom", request.getParameter("inputPrenom"));
		}else
		{
			//message d'erreur
			request.setAttribute("msgErreur_prenom", "Le prénom doit être saisi");
			existeErreur = true;
		}

		if(request.getParameter("inputCodePostal") != null && !request.getParameter("inputCodePostal").isEmpty())
		{
			try{
				user.setCodePostal(Integer.parseInt(request.getParameter("inputCodePostal")));
			}
			catch (NumberFormatException e) {
				//message d'erreur
				request.setAttribute("msgErreur_codePostal", "Le code postal ne doit être composé que de chiffres !");
				existeErreur = true;
			}
			request.setAttribute("inputCodePostal", request.getParameter("inputCodePostal"));
		}else 
		{
			//message d'erreur
			request.setAttribute("msgErreur_codePostal", "Le code postal doit être saisi");
			existeErreur = true;
		}
		
		// -------------  gestion mot de passe => 
		String mdp = "";
		String confirm = "";
		
		//1) mdp doit être saisi
		if(request.getParameter("inputPassword") != null && !request.getParameter("inputPassword").isEmpty())
		{
			mdp = request.getParameter("inputPassword");
			request.setAttribute("inputMdp", request.getParameter("inputPassword"));
		}else 
		{
			//message d'erreur
			request.setAttribute("msgErreur_motDePasse", "Le mot de passe doit être saisi");
			existeErreur = true;
		}

		//si le mdp est bien saisi
		//on vérifie la confirmation qui doit être saisie
		if(request.getParameter("inputPasswordConfirmation") != null && !request.getParameter("inputPasswordConfirmation").isEmpty())
		{
			confirm = request.getParameter("inputPasswordConfirmation");
			request.setAttribute("inputConfirmMdp", request.getParameter("inputPasswordConfirmation"));
		}else 
		{
			//message d'erreur
			request.setAttribute("msgErreur_confirm", "La confirmation du mot de passe doit être saisie");
			existeErreur = true;
		}		

		if(!existeErreur)
		{
			//on verifie que les 2 saisies sont identiques
			if(mdp.equals(confirm))
				user.setMotDePasse(mdp);
			else
			{
				//message d'erreur
				request.setAttribute("msgErreur_confirm", "la confirmation est différente du mot de passe");
				existeErreur = true;
			}
		}
		//---------------------------------
		
		if(request.getParameter("inputNom") != null && !request.getParameter("inputNom").isEmpty())
		{
			user.setNom(request.getParameter("inputNom"));
			request.setAttribute("inputNom", request.getParameter("inputNom"));
		}else 
		{
			//message d'erreur
			request.setAttribute("msgErreur_nom", "Le nom doit être saisi");
			existeErreur = true;
		}
	
		if(request.getParameter("inputEmail") != null && !request.getParameter("inputEmail").isEmpty())
		{
			user.setEmail(request.getParameter("inputEmail"));
			request.setAttribute("inputEmail", request.getParameter("inputEmail"));
		}else 
		{
			//message d'erreur
			request.setAttribute("msgErreur_email", "L'email doit être saisi");
			existeErreur = true;
		}

		if(request.getParameter("inputRue") != null && !request.getParameter("inputRue").isEmpty())
		{
			user.setRue(request.getParameter("inputRue"));
			request.setAttribute("inputRue", request.getParameter("inputRue"));
		}else 
		{
			//message d'erreur
			request.setAttribute("msgErreur_rue", "La rue doit être saisie");
			existeErreur = true;
		}
		
		if(request.getParameter("inputVille") != null && !request.getParameter("inputVille").isEmpty())
		{
			user.setVille(request.getParameter("inputVille"));
			request.setAttribute("inputVille", request.getParameter("inputVille"));
		}else 
		{
			//message d'erreur
			request.setAttribute("msgErreur_ville", "La ville doit être saisie");
			existeErreur = true;
		}
		
		//gestion des valeurs qui peuvent être nulle
		if(request.getParameter("inputTelephone") != null && !request.getParameter("inputTelephone").isEmpty())
		{
			try{
				user.setTelephone(request.getParameter("inputTelephone"));
			}
			catch (NumberFormatException e) {
				//message d'erreur
				request.setAttribute("msgErreur_telephone", "Le telephone ne doit être composé que de chiffres !");
				existeErreur = true;
			}
			request.setAttribute("inputTelephone", request.getParameter("inputTelephone"));
		}else 
		{
			user.setTelephone(null);
		}
		
		return existeErreur;
	}

	public void preRemplirProfil(HttpServletRequest request) {
		Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute(NOM_ATTRIBUT_SESSION_UTILISATEUR);
		
		if(utilisateur!= null) {
			request.setAttribute("inputPseudo", utilisateur.getPseudo());
			request.setAttribute("inputPrenom", utilisateur.getPrenom());
			request.setAttribute("inputNom", utilisateur.getNom());
			request.setAttribute("inputCodePostal", String.valueOf(utilisateur.getCodePostal()));
			request.setAttribute("inputEmail", utilisateur.getEmail());
			request.setAttribute("inputRue", utilisateur.getRue());
			request.setAttribute("inputVille", utilisateur.getVille());
			if(utilisateur.getTelephone() != null)
				request.setAttribute("inputTelephone", String.valueOf(utilisateur.getTelephone()));
			else
				request.setAttribute("inputTelephone", "");
			request.setAttribute("inputMdp", utilisateur.getMotDePasse());
			request.setAttribute("inputConfirmMdp", utilisateur.getMotDePasse());
		}
	}
	
	public void supprimerProfilUtilisateur(int noUtilisateur) throws BLLException {
		try {
			this.daoUtilisateur.deleteUser(noUtilisateur);
		} catch (DALException ex) {
			throw new BLLException("BLL - Impossible de supprimer l'utilisateur", ex);
		}	
	}
}
