package fr.eni.Encheres.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.Encheres.bll.BLLException;
import fr.eni.Encheres.bll.UtilisateurManager;
import fr.eni.Encheres.bo.Utilisateur;

/**
 * Servlet implementation class ServletConnexion
 */
public class ServletConnexion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String VALUE_NOM_INPUT_ID_PARAM = "inputIdentifiant";
	private final String VALUE_NOM_INPUT_PSWD_PARAM = "inputPassword";
	private final String CHAR_ENCOD = "UTF-8";
	private final String NOM_CONNEXIONJSP = "connexionJSP";
	private final String NOM_ATTRIBUT_SESSION_UTILISATEUR = "utilisateurSession";
	private final String ERR_LOGIN = "errorLogin";
	private final String MSG_ERR_LOGIN = "il n'y a pas de compte associé à cet identifiant et ce mot de passe";
	private final String MSG_ERR_LOGIN_BDD = "Une erreur s'est produite lors de la récupération du compte.";
	private final String ERR_INPUT_ID = "errorInputId";
	private final String MSG_ERR_INPUT_ID = "Veuillez saisir un identifiant.";
	private final String ERR_INPUT_PSWD = "errorInputPswd";
	private final String MSG_ERR_INPUT_PSWD = "Veuillez saisir un mot de passe.";
	private final String VALUE_NOM_INPUT_ID_ATTR = "attributIdentifiant";
	private final String SERVLET_ACCUEIL = "/Accueil";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletConnexion() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getServletContext().getNamedDispatcher(NOM_CONNEXIONJSP);
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding(CHAR_ENCOD);
		
		boolean ValuesOK = true;
		String valueMDP = "";
		String valueId = "";
		/* Récupération de la session depuis la requête */
        HttpSession session = request.getSession();
        Utilisateur user = null;
		
		if(request.getParameter(VALUE_NOM_INPUT_ID_PARAM) != null && !request.getParameter(VALUE_NOM_INPUT_ID_PARAM).isEmpty())
		{
			valueId = request.getParameter(VALUE_NOM_INPUT_ID_PARAM);
			request.setAttribute(VALUE_NOM_INPUT_ID_ATTR, valueId);
		}else {
			request.setAttribute(ERR_INPUT_ID,MSG_ERR_INPUT_ID);
			ValuesOK = false;
		}
		
		if(request.getParameter(VALUE_NOM_INPUT_PSWD_PARAM) != null && !request.getParameter(VALUE_NOM_INPUT_PSWD_PARAM).isEmpty())
		{
			valueMDP = request.getParameter(VALUE_NOM_INPUT_PSWD_PARAM);
		}
		else {
			request.setAttribute(ERR_INPUT_PSWD,MSG_ERR_INPUT_PSWD);
			ValuesOK = false;
		}
		
		if(ValuesOK)
		{	        
			try 
			{
				//recuperation user en BDD
				UtilisateurManager um = new UtilisateurManager();
				user = um.selectionnerUtilisateur(valueId, valueMDP);
				
				if(user == null)
					request.setAttribute(ERR_LOGIN,MSG_ERR_LOGIN);
				
			} catch (BLLException e) {
				e.printStackTrace();
				request.setAttribute(ERR_LOGIN,MSG_ERR_LOGIN_BDD);
			}
		}
	
		//on enregistre dans la session l'user si on l'a trouvé en bdd, null sinon.
		session.setAttribute(NOM_ATTRIBUT_SESSION_UTILISATEUR,user);
		
		if(user == null)
		{	
			doGet(request, response);
		}else
		{
			response.sendRedirect(request.getServletContext().getContextPath() + SERVLET_ACCUEIL);
		}
	}
}
