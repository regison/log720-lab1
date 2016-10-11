/**
 * 
 */
package ca.etsmtl.ens.log720.serverposte.implementation;

import java.util.ArrayList;

import org.omg.PortableServer.POA;

import ca.etsmtl.ens.log720.lab1.CollectionDossierPOA;
import ca.etsmtl.ens.log720.lab1.Dossier;
import ca.etsmtl.ens.log720.lab1.DossierHelper;
import ca.etsmtl.ens.log720.serverposte.ServerPoste;

/**
 * @author charly
 *
 */
public class CollectionDossiersImpl extends CollectionDossierPOA {

	private ArrayList<DossierImpl> _listeDossiers;

	/**
	 * 
	 */
	public CollectionDossiersImpl() {
		this._listeDossiers = new ArrayList<DossierImpl>();
	}

	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.CollectionDossierOperations#getDossier(int)
	 */
	public Dossier getDossier(int index) {
		if(index > this.size())
			return null;
		
		try {
			// Trouver le cours correspondant au parametre "index"
			DossierImpl dossier = _listeDossiers.get(index);

			// Recuperer le POA cree dans le serveur
			org.omg.PortableServer.POA srvPOA = ServerPoste.getPoa();

			// Activer l'objet et retourne l'objet CORBA
			org.omg.CORBA.Object obj = srvPOA.servant_to_reference(dossier);

			// Retourner un dossier
			return DossierHelper.narrow(obj);
		} catch (Exception e) {
			System.out.println("Erreur retour de l'objet Etudiant : " + e);
			return null;
		}		
	}

	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.CollectionDossierOperations#size()
	 */
	public int size() {
		return this._listeDossiers.size();
	}
	
	protected ArrayList<DossierImpl> dossiers(){
		return this._listeDossiers;
	}

}
