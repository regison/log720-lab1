/**
 * 
 */
package ca.etsmtl.ens.log720.serverposte.implementation;

import java.util.ArrayList;

import ca.etsmtl.ens.log720.lab1.DossierPOA;

/**
 * @author charly
 *
 */
public class DossierImpl extends DossierPOA {

	private int id;
	private String nom;
	private String prenom;
	private String permisId;
	private String plaqueId;
	private int levelId;

	private ArrayList<Integer> infractionsArray ;
	private ArrayList<Integer> reactionsArray ;
	/**
	 * 
	 */
	public DossierImpl() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.DossierOperations#id()
	 */
	public int id() {
		// TODO Auto-generated method stub
		return id;
	}

	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.DossierOperations#nom()
	 */
	public String nom() {
		// TODO Auto-generated method stub
		return nom;
	}

	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.DossierOperations#noPermis()
	 */
	public String noPermis() {
		// TODO Auto-generated method stub
		return permisId;
	}

	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.DossierOperations#noPlaque()
	 */
	public String noPlaque() {
		// TODO Auto-generated method stub
		return plaqueId;
	}

	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.DossierOperations#prenom()
	 */
	public String prenom() {
		// TODO Auto-generated method stub
		return prenom;
	}

	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.DossierOperations#niveau()
	 */
	public int niveau() {
		// TODO Auto-generated method stub
		return levelId;
	}

	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.DossierOperations#getListeInfraction()
	 */
	public int[] getListeInfraction() {
		// TODO Auto-generated method stub
		return infractionsArray.toArray();
	}

	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.DossierOperations#getListeReaction()
	 */
	public int[] getListeReaction() {
		// TODO Auto-generated method stub
		return reactionsArray.toArray();
	}

	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.DossierOperations#ajouterReactionAListe(int)
	 */
	public void ajouterReactionAListe(int idReaction) {
		if (idReaction != 0){
			this.reactionsArray.add(idReaction);
		}

	}

	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.DossierOperations#ajouterInfractionAListe(int)
	 */
	public void ajouterInfractionAListe(int idInfraction) {
		if (idInfraction !=0){
			this.infractionsArray.add(idInfraction);
		}
	}

	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.DossierOperations#_toString()
	 */
	public String _toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
