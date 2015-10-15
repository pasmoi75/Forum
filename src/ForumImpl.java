
import java.util.*;
import java.io.*;
import java.rmi.*;
import java.rmi.server.*;

/**
 * classe repr�sentant l'objet servant du forum
 */
public class ForumImpl extends UnicastRemoteObject implements Forum {
	// TO DO

	public ForumImpl() throws RemoteException {
		super();
	}

	/**
	 * la structure de m�moristion des intervenants
	 */
	protected HashMap intervenants = new HashMap();
	/**
	 * l'identifiant unique d'intervenant
	 */
	protected Integer id = new Integer(0);

	/**
	 * Enregistre un intervenant dans la structure de m�moristion des
	 * intervenants. Cette m�thode est appel�e par le traitant de communication
	 * du programme client (IntervenantImpl)
	 * 
	 * @param intervenant
	 *            une reference distante vers l'intervenant
	 * @param nom
	 *            nom de l'intervenant
	 * @param prenom
	 *            prenom de l'intervenant
	 * @return un identifiant interne representant l'intervenant dans la
	 *         structure de m�moristion des intervenants
	 */
	public synchronized HashMap enter(Intervenant intervenant, String prenom, String nom) throws RemoteException {

		if (!this.intervenants.containsValue(intervenant)) {
			
			id ++;
			Intervenant nouveau = new IntervenantImpl(nom, prenom);
			this.intervenants.put(id, nouveau);
		}
		
		intervenant.setId(id);

		return intervenants;

	}

	/**
	 * De-enregistre un intervanant dans la structure de m�moristion des
	 * intervenants. Cette m�thode est appel�e par le traitant de communication
	 * du programme client (IntervenantImpl)
	 * 
	 * @param id
	 *            identification de l'intervenant retourne lors de l'appel � la
	 *            methode enter.
	 */
	public synchronized void leave(int id) throws RemoteException {

		if (!this.intervenants.containsKey(id))
			intervenants.remove(id);
		// A VOIR SI ON RENVOI l'OBJET

	}
	
	public HashMap getIntervenants() {
		return intervenants;
	}

}
