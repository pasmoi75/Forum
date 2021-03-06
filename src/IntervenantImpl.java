import java.awt.*;
import java.awt.event.*;
import java.lang.*;
import java.net.InetAddress;
import java.rmi.*;
import java.rmi.server.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;


/**
 * Cette classe d�fini le traitant de communication du programme client.
 * Elle est utilis�e par les classes connectListener,writeListener,whoListener,leaveListener 
 * du GUI pour effectuer les communications distante avec le forum.
*/
public class IntervenantImpl extends UnicastRemoteObject implements Intervenant   {
	
  private static IrcGui gui;
  
  /**
  * R�f�rence distante vers un forum.
  */
  private static Forum forum=null; //ref vers forum   
  
  /**
 * Identification du client dans le forum. Cet identifiant est retourn� 
 * lors de l'appel � la m�thode enter sur le forum distant.
 */
  private int id;
  
  public void setId(int id) {
	  this.id=id;
  }
  
  /**
 * nom de l'intervenant
 */
  private String nom;
  /**
 * prenom de l'intervenant
 */
  private String prenom;
  
  /**
 * constructeur de la classe IntervenantImpl. Le nom et le prenom de l'intervenant sont pass�s
 * en parametre du programme client (irc.java)
 * @param nom nom de l'intervenant
 * @param prenom prenom de l'intervenant
 */
  public  IntervenantImpl (String nom, String prenom) throws RemoteException {
  	super();
  	this.nom = nom;
  	this.prenom = prenom;
  }
  
 
    
  /**
 * Fixe une reference directe vers le gui (IrcGui). Cette reference est utilis�e 
 * par le traitant de communication pour imprimer des message de chat dans le gui 
 * via la methode print definie dans IrcGui.
 * @param gui le GUI
 */
  public void setGUI(IrcGui gui){
  	this.gui = gui;
  }
  
  /**
 * Execute la methode enter sur le forum. Cette methode est appel� par le traitant 
 * writeListener d�fini dans IrcGui. Cette m�thode doit utiliser un serveur de nom 
 * pour obtenir une r�f�rence distante vers le forum et ex�cuter la m�thode enter 
 * dessus.
 * @param forum_name nom du forum 
 */
  public void enter (String forum_name) throws Exception {
  	
	  String URL = "//" + InetAddress.getLocalHost().getHostName() + ":" + 8585 + "/mon_serveur";
	  Remote remote =  Naming.lookup(URL + "/" + forum_name);
	  if(remote instanceof ForumImpl) {
		  
		   this.forum = (ForumImpl) remote ;
		   forum.enter(this, this.prenom, this.nom);
		   Naming.rebind(URL, this.forum);
		   		  	
	  }
	  else throw new Exception("Server problem");
	  
	 
	 
  	
  }
	
  
  /**
 * Execute la methode say sur le forum. Cette methode est appel� par le traitant 
 * writeListener d�fini dans IrcGui. Cette m�thode doit utilise une r�f�rence distante 
 * vers le forum et ex�cuter la m�thode say dessus.
 * @param msg message � envoyer aux intervenants enregistrer dans le forum. 
 */
  public void say (String msg) throws Exception {
  	
  		// TO DO
	
  }
  
  /**
 * Cette methode est appel� par le forum pour imprimer un nouveau message de 
 * chat a l'intervenant. Cette impression est d�l�gu�e � la m�thode print d�finie 
 * dans IrcGui. 
 * @param msg nouveau message � imprimer dans le gui.
 */
  public void listen (String msg) throws RemoteException {
  	
	  gui.Print(msg);
  } 
  
 

  public void  addNewClient(Intervenant i) throws RemoteException{
  	// TO DO
  } 
 
  public void delNewClient(Intervenant i) throws RemoteException{
  	// TO DO
  } 

  /**
 * Execute la methode leave sur le forum. Cette methode est appel� par le traitant 
 * leaveListener d�fini dans IrcGui. Cette m�thode doit utilise une r�f�rence distante 
 * vers le forum et ex�cuter la m�thode leave dessus.
 */
  public void leave() throws Exception {
	  
	this.forum.leave(id);
	
  }
  
  /**
 * Execute la methode who sur le forum. Cette methode est appel� par le traitant 
 * whoListener d�fini dans IrcGui. Cette m�thode doit utilise une r�f�rence distante 
 * vers le forum et ex�cuter la methode who dessus.
 */
  public String who() throws Exception {
	 
	  HashMap inters = this.forum.getIntervenants();
	  String result = " ";
	  Set id = inters.keySet();
	  Iterator it = id.iterator();
	  while ( it.hasNext()) {
		  IntervenantImpl inter = (IntervenantImpl) inters.get(it.next());
		  result += inter.prenom + " " + inter.nom + ",";
	  }
	  return result;
  }
}