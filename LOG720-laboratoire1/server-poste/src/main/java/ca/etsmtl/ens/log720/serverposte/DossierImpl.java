/**
 * 
 */
package ca.etsmtl.ens.log720.serverposte;

import java.util.ArrayList;

import ca.etsmtl.ens.log720.lab1.DossierPOA;
import ca.etsmtl.ens.log720.lab1.Infraction;

/**
 * @author charly
 *
 */
public class DossierImpl extends DossierPOA{

	private int id;
	private String nom;
	private String prenom;
	private String noPermis;
	private String noPlaque;
	private int levelId;

	private ArrayList<Integer> infractionsArray ;
	private ArrayList<Integer> reactionsArray ;
	/**
	 * @param noPermis 
	 * @param noPlaque 
	 * @param prenom2 
	 * @param nom2 
	 * @param idDossier 
	 * 
	 */
	public DossierImpl(int idDossier, String nom, String prenom, String noPlaque, String noPermis) {
		this.id = idDossier;
		this.nom = nom;
		this.prenom = prenom;
		this.noPermis = noPermis;
		this.noPlaque = noPlaque;
		this.levelId = 0;
		infractionsArray = new ArrayList<Integer>();
		reactionsArray = new ArrayList<Integer>();
	}

	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.DossierOperations#id()
	 */
	public int id() {
		return id;
	}

	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.DossierOperations#nom()
	 */
	public String nom() {
		return nom;
	}

	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.DossierOperations#noPermis()
	 */
	public String noPermis() {
		return noPermis;
	}

	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.DossierOperations#noPlaque()
	 */
	public String noPlaque() {
		return noPlaque;
	}

	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.DossierOperations#prenom()
	 */
	public String prenom() {
		return prenom;
	}

	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.DossierOperations#niveau()
	 */
	public int niveau() {
		return levelId;
	}

	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.DossierOperations#getListeInfraction()
	 */
	public int[] getListeInfraction() {
		int[] infractionsId = new int[infractionsArray.size()];
		for (int i=0; i < infractionsArray.size(); i++)
	    {
			infractionsId[i] = infractionsArray.get(i).intValue();
	    }
		return infractionsId;
	}

	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.DossierOperations#getListeReaction()
	 */
	public int[] getListeReaction() {
		int[] reactionsId = new int[reactionsArray.size()];
		for (int i=0; i < reactionsArray.size(); i++)
	    {
			reactionsId[i] = reactionsArray.get(i).intValue();
	    }
		return reactionsId;
	}

	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.DossierOperations#ajouterReactionAListe(int)
	 */
	public void ajouterReactionAListe(int idReaction) {
		if (idReaction != 0 
				&& !reactionsArray.contains(idReaction)){
			this.reactionsArray.add(idReaction);
		}

	}

	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.DossierOperations#ajouterInfractionAListe(int)
	 */
	public void ajouterInfractionAListe(int idInfraction) {
		if (idInfraction !=0
				&& !infractionsArray.contains(idInfraction)){
			
			
			//TODO recuperer le niveau de l'infraction afin de l'associer au dossier
			Infraction i = ServerPoste.serverposte.servantBanqueInfractions.trouverInfractionParId(idInfraction);
			if(i != null){
				this.infractionsArray.add(idInfraction);
				this.levelId = i.niveau() > this.levelId ?  i.niveau() : this.levelId ;
			}
		}
	}

	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.DossierOperations#_toString()
	 */
	public String _toString() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public boolean equals(Object o) {
		
		if(o instanceof DossierImpl)
		 return false;
		
		DossierImpl dossiero = (DossierImpl) o;
		
		return this.noPermis == dossiero.noPermis;
	}

	public String toCSV() {
		return this.id() + "," 
				+ this.nom() + "," 
				+ this.prenom() + "," 
				+ this.noPermis() + "," 
				+ this.noPlaque();
	}

}
