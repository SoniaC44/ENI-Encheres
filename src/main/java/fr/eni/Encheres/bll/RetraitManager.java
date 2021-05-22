package fr.eni.Encheres.bll;

import javax.servlet.http.HttpServletRequest;

import fr.eni.Encheres.bo.Retrait;

public class RetraitManager {

	public boolean verifierSaisiesRetrait(HttpServletRequest request, Retrait lieuRetrait) {
		boolean existeErreur = false;
		
		//Retrait unLieuDeRetrait
		if(request.getParameter("inputRue") != null && !request.getParameter("inputRue").trim().isEmpty())
		{
			lieuRetrait.setRue(request.getParameter("inputRue").trim());
			request.setAttribute("inputRue", request.getParameter("inputRue").trim());
		}
		else 
		{
			//message d'erreur
			request.setAttribute("msgErreur_Rue", "La rue doit être saisie");
			existeErreur = true;
		}
		
		if(request.getParameter("inputCodePostal") != null && !request.getParameter("inputCodePostal").trim().isEmpty())
		{
			int codePostal = 0;
			try{
				if(request.getParameter("inputCodePostal").trim().matches("[0-9]{5}"))
				{
					codePostal = Integer.parseInt(request.getParameter("inputCodePostal").trim());
				}else {
					//message d'erreur
					request.setAttribute("msgErreur_codePostal", "Le code postal doit être composé de 5 chiffres uniquement");
					existeErreur = true;
				}				
				lieuRetrait.setCodePostal(codePostal);
			}catch (Exception e) {
				//message d'erreur
				request.setAttribute("msgErreur_codePostal", "Le code postal ne doit comporter que des chiffres");
				existeErreur = true;
			}
			request.setAttribute("inputCodePostal", request.getParameter("inputCodePostal").trim());
			
		}
		else 
		{
			//message d'erreur
			request.setAttribute("msgErreur_codePostal", "Le code postal doit être saisi");
			existeErreur = true;
		}
		
		if(request.getParameter("inputVille") != null && !request.getParameter("inputVille").trim().isEmpty())
		{
			lieuRetrait.setVille(request.getParameter("inputVille").trim());
			request.setAttribute("inputVille", request.getParameter("inputVille").trim());
		}
		else 
		{
			//message d'erreur
			request.setAttribute("msgErreur_ville", "La ville doit être saisie");
			existeErreur = true;
		}
		return existeErreur;
	}

}
