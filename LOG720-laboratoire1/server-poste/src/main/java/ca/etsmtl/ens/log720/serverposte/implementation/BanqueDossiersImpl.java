/**
 * 
 */
package ca.etsmtl.ens.log720.serverposte.implementation;

import ca.etsmtl.ens.log720.lab1.BanqueDossiersPOA;
import ca.etsmtl.ens.log720.lab1.CollectionDossier;
import ca.etsmtl.ens.log720.lab1.Dossier;
import ca.etsmtl.ens.log720.lab1.InvalidIdException;
import ca.etsmtl.ens.log720.lab1.NoPermisExisteDejaException;

/**
 * @author charly
 *
 */
public class BanqueDossiersImpl extends BanqueDossiersPOA {

	/**
	 * 
	 */
	public BanqueDossiersImpl() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.BanqueDossiersOperations#dossiers()
	 */
	public CollectionDossier dossiers() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.BanqueDossiersOperations#trouverDossiersParPlaque(java.lang.String)
	 */
	public CollectionDossier trouverDossiersParPlaque(String plaque) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.BanqueDossiersOperations#trouverDossiersParNom(java.lang.String, java.lang.String)
	 */
	public CollectionDossier trouverDossiersParNom(String nom, String prenom) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.BanqueDossiersOperations#trouverDossierParPermis(java.lang.String)
	 */
	public Dossier trouverDossierParPermis(String noPermis) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.BanqueDossiersOperations#trouverDossierParId(int)
	 */
	public Dossier trouverDossierParId(int idDossier) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.BanqueDossiersOperations#ajouterDossier(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public void ajouterDossier(String nom, String prenom, String noPermis, String noPlaque)
			throws NoPermisExisteDejaException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.BanqueDossiersOperations#ajouterInfractionAuDossier(int, int)
	 */
	public void ajouterInfractionAuDossier(int idDossier, int idInfraction) throws InvalidIdException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.BanqueDossiersOperations#ajouterReactionAuDossier(int, int)
	 */
	public void ajouterReactionAuDossier(int idDossier, int idReaction) throws InvalidIdException {
		// TODO Auto-generated method stub

	}

}
