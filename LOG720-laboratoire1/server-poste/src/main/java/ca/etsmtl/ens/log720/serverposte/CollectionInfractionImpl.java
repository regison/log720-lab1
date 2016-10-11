/**
 * 
 */
package ca.etsmtl.ens.log720.serverposte;

import java.io.Serializable;
import java.util.ArrayList;

import ca.etsmtl.ens.log720.lab1.CollectionInfractionPOA;
import ca.etsmtl.ens.log720.lab1.Infraction;
import ca.etsmtl.ens.log720.lab1.InfractionHelper;

/**
 * @author charly
 *
 */
public class CollectionInfractionImpl extends CollectionInfractionPOA implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5782210045508528963L;
	private ArrayList<InfractionImpl> _listeInfractions;

	/**
	 * 
	 */
	public CollectionInfractionImpl() {
		this._listeInfractions = new ArrayList<InfractionImpl>();
	}

	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.CollectionInfractionOperations#getInfraction(int)
	 */
	public Infraction getInfraction(int index) {
		if(index > this.size())
			return null;
		try{
			InfractionImpl infraction = this._listeInfractions.get(index);
			
			// Recuperer le POA cree dans le serveur
			org.omg.PortableServer.POA srvPOA = ServerPoste.serverposte.getPoa();
	
			// Activer l'objet et retourne l'objet CORBA
			org.omg.CORBA.Object obj = srvPOA.servant_to_reference(infraction);
			// Retourner une Collection de dossiers
			return InfractionHelper.narrow(obj);
			
		} catch (Exception e) {
			System.out.println("Erreur retour de l'objet Etudiant : " + e);
			return null;
		}	


	}

	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.CollectionInfractionOperations#size()
	 */
	public int size() {
		return this._listeInfractions.size();
	}

	public ArrayList<InfractionImpl> infractions() {
		return this._listeInfractions;
	}

}
