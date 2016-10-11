/**
 * 
 */
package ca.etsmtl.ens.log720.serverposte.implementation;

import ca.etsmtl.ens.log720.lab1.BanqueDossiersPOA;
import ca.etsmtl.ens.log720.lab1.CollectionDossier;
import ca.etsmtl.ens.log720.lab1.CollectionDossierHelper;
import ca.etsmtl.ens.log720.lab1.Dossier;
import ca.etsmtl.ens.log720.lab1.DossierHelper;
import ca.etsmtl.ens.log720.lab1.InvalidIdException;
import ca.etsmtl.ens.log720.lab1.NoPermisExisteDejaException;
import ca.etsmtl.ens.log720.serverposte.ServerPoste;

/**
 * @author charly
 * @modify by Regg
 *
 */
public class BanqueDossiersImpl extends BanqueDossiersPOA {

	private CollectionDossiersImpl collectionDossiers;
	/**
	 * 
	 */
	public BanqueDossiersImpl() {
		collectionDossiers =  new CollectionDossiersImpl();
	}

	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.BanqueDossiersOperations#dossiers()
	 */
	public CollectionDossier dossiers() {
		try{
			// Recuperer le POA cree dans le serveur
			org.omg.PortableServer.POA srvPOA = ServerPoste.getPoa();
	
			// Activer l'objet et retourne l'objet CORBA
			org.omg.CORBA.Object obj = srvPOA.servant_to_reference(collectionDossiers);
			// Retourner une Collection de dossiers
			return CollectionDossierHelper.narrow(obj);
			
		} catch (Exception e) {
			System.out.println("Erreur retour de l'objet CollectionReactions : "	+ e);
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.BanqueDossiersOperations#trouverDossiersParPlaque(java.lang.String)
	 */
	public CollectionDossier trouverDossiersParPlaque(String plaque) {
		CollectionDossiersImpl collDossier = new CollectionDossiersImpl();
		
		if(plaque.isEmpty())
			return null;
		
		for(DossierImpl dossierFound : collectionDossiers.dossiers()){
			if (dossierFound.noPlaque().toLowerCase()
							.equals(plaque.toLowerCase())){
				collDossier.dossiers().add(dossierFound);
			}			
		}
		
		try{
			// Recuperer le POA cree dans le serveur
			org.omg.PortableServer.POA srvPOA = ServerPoste.getPoa();
	
			// Activer l'objet et retourne l'objet CORBA
			org.omg.CORBA.Object obj = srvPOA.servant_to_reference(collDossier);
			// Retourner une Collection de dossiers
			return CollectionDossierHelper.narrow(obj);
			
		} catch (Exception e) {
			System.out.println("Erreur retour de l'objet Etudiant : " + e);
			return null;
		}	
	}

	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.BanqueDossiersOperations#trouverDossiersParNom(java.lang.String, java.lang.String)
	 */
	public CollectionDossier trouverDossiersParNom(String nom, String prenom) {
		CollectionDossiersImpl collDossier = new CollectionDossiersImpl();
		
		if(!nom.isEmpty() || !prenom.isEmpty()){
			for(DossierImpl dossierFound : collectionDossiers.dossiers()){
				if (dossierFound.nom().toLowerCase().equals(nom.toLowerCase()) 
						&& dossierFound.prenom().toLowerCase().equals(prenom.toLowerCase())){
					collDossier.dossiers().add(dossierFound);
				}			
			}
			
			try{
				// Recuperer le POA cree dans le serveur
				org.omg.PortableServer.POA srvPOA = ServerPoste.getPoa();
		
				// Activer l'objet et retourne l'objet CORBA
				org.omg.CORBA.Object obj = srvPOA.servant_to_reference(collDossier);
				// Retourner une Collection de dossiers
				return CollectionDossierHelper.narrow(obj);
				
			} catch (Exception e) {
				System.out.println("Erreur retour de l'objet Etudiant : " + e);
				return null;
			}	
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.BanqueDossiersOperations#trouverDossierParPermis(java.lang.String)
	 */
	public Dossier trouverDossierParPermis(String noPermis) {
		try{
			if(!noPermis.isEmpty()){
				for(DossierImpl dossierFound : collectionDossiers.dossiers()){
					if (dossierFound.noPermis().toLowerCase()
									.equals(noPermis.toLowerCase())){
						// Recuperer le POA cree dans le serveur
						org.omg.PortableServer.POA srvPOA = ServerPoste.getPoa();
				
						// Activer l'objet et retourne l'objet CORBA
						org.omg.CORBA.Object obj = srvPOA.servant_to_reference(dossierFound);
						// Retourner une Collection de dossiers
						return DossierHelper.narrow(obj);
					}
				
				}
			}
		} catch (Exception e) {
			System.out.println("Erreur retour de l'objet Dossier par permis : " + e);
			return null;
		}	
		
		return null;
	}

	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.BanqueDossiersOperations#trouverDossierParId(int)
	 */
	public Dossier trouverDossierParId(int idDossier) {
		try{
			for(DossierImpl dossierFound : collectionDossiers.dossiers()){
				if (dossierFound.id() == idDossier){
					// Recuperer le POA cree dans le serveur
					org.omg.PortableServer.POA srvPOA = ServerPoste.getPoa();
			
					// Activer l'objet et retourne l'objet CORBA
					org.omg.CORBA.Object obj = srvPOA.servant_to_reference(dossierFound);
					// Retourner une Collection de dossiers
					return DossierHelper.narrow(obj);
				}			
			}

		} catch (Exception e) {
			System.out.println("Erreur retour de l'objet Dossier par Id : " + e);
			return null;
		}	
		
		return null;
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
		if (!collectionDossiers.dossiers().contains(newDossier))
			collectionDossiers.dossiers().add(newDossier);
		else
			throw new NoPermisExisteDejaException();

	}

	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.BanqueDossiersOperations#ajouterInfractionAuDossier(int, int)
	 */
	public void ajouterInfractionAuDossier(int idDossier, int idInfraction) throws InvalidIdException {
		
			if (idDossier != 0 &&  idInfraction != 0){
				Dossier dossier = trouverDossierParId(idDossier);
				
				if (dossier != null)
					dossier.ajouterInfractionAListe(idInfraction);				
			}
			else
				throw new InvalidIdException();
	}

	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.BanqueDossiersOperations#ajouterReactionAuDossier(int, int)
	 */
	public void ajouterReactionAuDossier(int idDossier, int idReaction) throws InvalidIdException {

		if (idDossier != 0 &&  idReaction != 0){
			Dossier dossier = trouverDossierParId(idDossier);
			
			if (dossier != null)
				dossier.ajouterReactionAListe(idReaction);				
		}
		else
			throw new InvalidIdException();

	}

}
