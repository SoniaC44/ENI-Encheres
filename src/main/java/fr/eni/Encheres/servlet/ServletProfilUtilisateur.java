package fr.eni.Encheres.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.Encheres.bll.BLLException;
import fr.eni.Encheres.bll.UtilisateurManager;
import fr.eni.Encheres.bo.Utilisateur;

/**
 * Servlet implementation class ServletProfilUtilisateur
 */
public class ServletProfilUtilisateur extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String ERR_PROFIL = "errorProfil";
	private final String MSG_ERR_PROFIL = "l'identifiant de l'utilisateur est mal renseigné";
	private final String MSG_ERR_PROFIL_PARSE = "l'identifiant de l'utilisateur est incorrect";
	private final String MSG_ERR_PROFIL_ACCES_BDD_IMPOSSIBLE = "l'accès à la BDD impossible";
	private final String MSG_ERR_PROFIL_EXISTE_PAS = "l'utilisateur n'existe pas";


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer id_user = null;
		
		if(request.getParameter("id_user") != null && !request.getParameter("id_user").isEmpty())
		{
			try{
				id_user = Integer.parseInt(request.getParameter("id_user"));
				Utilisateur profileUser = null;
				UtilisateurManager um = new UtilisateurManager();
				try {
					profileUser  = um.afficherProfilUtilisateur(id_user);
					if(profileUser.getPseudo()==null){
						request.setAttribute(ERR_PROFIL, MSG_ERR_PROFIL_EXISTE_PAS);
					}else {
						request.setAttribute("inputPseudo", profileUser.getPseudo());
						request.setAttribute("inputNom", profileUser.getNom());
						request.setAttribute("inputPrenom", profileUser.getPrenom());
						request.setAttribute("inputEmail", profileUser.getEmail());
						request.setAttribute("inputTelephone", profileUser.getTelephone());
						request.setAttribute("inputRue", profileUser.getRue());
						request.setAttribute("inputCodePostal", profileUser.getCodePostal());
						request.setAttribute("inputVille", profileUser.getVille());
						request.setAttribute("id_user", id_user);
					}
				} catch (BLLException e) {
					e.printStackTrace();
					request.setAttribute(ERR_PROFIL, MSG_ERR_PROFIL_ACCES_BDD_IMPOSSIBLE);
				}
			} catch (Exception e) {
				request.setAttribute(ERR_PROFIL, MSG_ERR_PROFIL_PARSE);
			}	
		}else{
			request.setAttribute(ERR_PROFIL, MSG_ERR_PROFIL);
			//profil
		}
		RequestDispatcher rd = request.getServletContext().getNamedDispatcher("profileUserJSP");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}
}