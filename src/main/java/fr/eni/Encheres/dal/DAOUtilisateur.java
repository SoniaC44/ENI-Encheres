package fr.eni.Encheres.dal;

import fr.eni.Encheres.bo.Utilisateur;

public interface DAOUtilisateur {
	
	public boolean existeUserPseudo(String pseudo) throws DALException;
	
	public boolean existeUserEmail(String email) throws DALException;
	
	public Utilisateur getUser(String identifiant, String motDePasse) throws DALException;
	
	public void insertUser(Utilisateur utilisateur) throws DALException;

	public Utilisateur getProfilUser(int no_utilisateur) throws DALException;
	
	public void updateUser(Utilisateur utilisateur) throws DALException;

	public void deleteUser(int noUtilisateur) throws DALException;
		
}
