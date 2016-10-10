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
 * @modify by Regg
 *
 */
public class BanqueDossiersImpl extends BanqueDossiersPOA {

	private ColletionDossierImpl collectionDossiers;
	private org.omg.CORBA.ORB ord;
	/**
	 * 
	 */
	public BanqueDossiersImpl() {
		// TODO Auto-generated constructor stub
		orb = org.omg.CORBA.ORB.init(args, null);
		colletionsDossiers =  new CollectionDossierImpl();
	}

	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.BanqueDossiersOperations#dossiers()
	 */
	public CollectionDossier dossiers() {
		try{
			// Recuperer le POA cree dans le serveur
			ServeurPoste srvPOA  = new ServerPoste(orb);
			// Activer l'objet et retourne l'objet CORBA
			org.omg.CORBA.Object obj = srvPOA.servant_to_reference(_CollectionDossiers);
			// Retourner une Collection de dossiers
			return CollectionDossierHelper.narrow(obj);}
		catch(Exception e){
		
		}
	
	}

	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.BanqueDossiersOperations#trouverDossiersParPlaque(java.lang.String)
	 */
	public CollectionDossier trouverDossiersParPlaque(String plaque) {
		
		if(!plaque.isEmpty()){
			int pos = 0;
			for(DossierImpl dossierFound : collectionDossiers){
				if (dossierFound.getPlaqueId()().toLowerCase()
								.equals(plaque.toLowerCase())){
					return collectionDossiers.getDossier(pos);
				}
				pos +=1 ;				
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.BanqueDossiersOperations#trouverDossiersParNom(java.lang.String, java.lang.String)
	 */
	public CollectionDossier trouverDossiersParNom(String nom, String prenom) {
		
		if(!nom.isEmpty() || !prenom.isEmpty()){
			int pos = 0;
			for(DossierImpl dossierFound : collectionDossiers){
				if (dossierFound.getNom().toLowerCase()
								.equals(nom.toLowerCase()) ||
					dossierFound.getPrenom().toLowerCase()
								.equals(prenom.toLowerCase())){
					return collectionDossiers.getDossier(pos);
				}
				pos +=1 ;				
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.BanqueDossiersOperations#trouverDossierParPermis(java.lang.String)
	 */
	public Dossier trouverDossierParPermis(String noPermis) {

		if(!noPermis.isEmpty()){
			int pos = 0;
			for(DossierImpl dossierFound : collectionDossiers){
				if (dossierFound.getPermisId().toLowerCase()
								.equals(noPermis.toLowerCase())){
					return collectionDossiers.getDossier(pos);
				}
				pos +=1 ;				
			}
		}	
		return null;
	}

	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.BanqueDossiersOperations#trouverDossierParId(int)
	 */
	public Dossier trouverDossierParId(int idDossier) {
		// TODO Auto-generated method stub
		if(!noPermis.isEmpty()){
			int pos = 0;
			for(DossierImpl dossierFound : collectionDossiers){
				if (dossierFound.id() == idDossier)){
					return collectionDossiers.getDossier(pos);
				}
				pos +=1 ;				
			}
		}	
	}

	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.BanqueDossiersOperations#ajouterDossier(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public void ajouterDossier(String nom, String prenom, String noPermis, String noPlaque)
			throws NoPermisExisteDejaException {
		
		int idDossier = collectionDossiers.size();
		
		//Create a new file
		DossierImpl newDossier = new DossierImpl(idDossier, nom, prenom, noPlaque, noPermis);
		
		//Verify if file already exists if not add it
		if (!collectionDossiers.contains(newDossier))
			collectionDossiers.add(newDossier);
		else
			throws new Exception();

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
