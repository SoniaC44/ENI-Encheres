package fr.eni.Encheres.servlet;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import fr.eni.Encheres.bll.ArticleVenduManager;
import fr.eni.Encheres.bll.BLLException;

/**
 * Application Lifecycle Listener implementation class ListenerDemarrageSession
 *
 */
public class ListenerDemarrageSession implements HttpSessionListener {


	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent se)  { 
    	ArticleVenduManager avm = new ArticleVenduManager();
    	try {
			avm.mettreAJourArticlesEtCredits();
		} catch (BLLException e) {
			e.printStackTrace();
		}
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent se)  { 
         // TODO Auto-generated method stub
    }
	
}
