import java.rmi.*;
import java.util.HashMap;

public interface Forum extends Remote {
	/**
 * Enregistre un intervanant dans la structure de m�moristion des intervenants. Cette m�thode est
 * appel�e par le traitant de communication du programme client (IntervenantImpl) 
 * @param intervenant une reference distante vers l'intervenant
 * @param nom nom de l'intervenant
 * @param prenom prenom de l'intervenant
 * @return un identifiant interne representant l'intervenant 
 * dans la structure de m�moristion des intervenants
 */
  public  HashMap enter (Intervenant intervenant, String prenom, String nom) throws RemoteException;
  
   /**
 * De-enregistre un intervanant dans la structure de m�moristion des intervenants. Cette m�thode est
 * appel�e par le traitant de communication du programme client (IntervenantImpl) 
 * @param id identification de l'intervenant retourne lors de l'appel � la methode enter.
 */
  public  void leave(int id) throws RemoteException;
  
  public HashMap getIntervenants();
 
}



    
  