package ca.etsmtl.ens.log720.serverposte.implementation;

import ca.etsmtl.ens.log720.lab1.BanqueInfractionsPOA;
import ca.etsmtl.ens.log720.lab1.CollectionInfraction;
import ca.etsmtl.ens.log720.lab1.CollectionInfractionHelper;
import ca.etsmtl.ens.log720.lab1.Dossier;
import ca.etsmtl.ens.log720.lab1.Infraction;
import ca.etsmtl.ens.log720.lab1.InfractionHelper;
import ca.etsmtl.ens.log720.lab1.NiveauHorsBornesException;
import ca.etsmtl.ens.log720.serverposte.ServerPoste;

public class BanqueInfractionsImpl extends BanqueInfractionsPOA {

	private CollectionInfractionImpl _collectionInfractions;
	
	public BanqueInfractionsImpl() {
		this._collectionInfractions = new CollectionInfractionImpl();
	}

	public CollectionInfraction infractions() {
		try{
			// Recuperer le POA cree dans le serveur
			org.omg.PortableServer.POA srvPOA = ServerPoste.serverposte.getPoa();
	
			// Activer l'objet et retourne l'objet CORBA
			org.omg.CORBA.Object obj = srvPOA.servant_to_reference(_collectionInfractions);
			// Retourner une Collection de dossiers
			return CollectionInfractionHelper.narrow(obj);
			
		} catch (Exception e) {
			System.out.println("Erreur retour de l'objet CollectionReactions : "	+ e);
			return null;
		}
	}

	public CollectionInfraction trouverInfractionsParDossier(Dossier mydossier) {
		CollectionInfractionImpl collInfractions = new CollectionInfractionImpl();
		
		if(mydossier == null)
			return null;
		
		for(int infractionId: mydossier.getListeInfraction()){
			collInfractions.infractions().add(this._collectionInfractions.infractions().get(infractionId));
		}
		
		try{
			// Recuperer le POA cree dans le serveur
			org.omg.PortableServer.POA srvPOA = ServerPoste.serverposte.getPoa();
	
			// Activer l'objet et retourne l'objet CORBA
			org.omg.CORBA.Object obj = srvPOA.servant_to_reference(collInfractions);
			// Retourner une Collection de dossiers
			return CollectionInfractionHelper.narrow(obj);
			
		} catch (Exception e) {
			System.out.println("Erreur retour de l'objet Etudiant : " + e);
			return null;
		}	
	}

	public Infraction trouverInfractionParId(int idInfraction) {
		try{
			for(InfractionImpl infractionFound : _collectionInfractions.infractions()){
				if (infractionFound.id() == idInfraction){
					// Recuperer le POA cree dans le serveur
					org.omg.PortableServer.POA srvPOA = ServerPoste.serverposte.getPoa();
			
					// Activer l'objet et retourne l'objet CORBA
					org.omg.CORBA.Object obj = srvPOA.servant_to_reference(infractionFound);
					// Retourner une Collection de dossiers
					return InfractionHelper.narrow(obj);
				}			
			}

		} catch (Exception e) {
			System.out.println("Erreur retour de l'objet Etudiant : " + e);
			return null;
		}	
		
		return null;
	}

	public void ajouterInfraction(String description, int niveau) throws NiveauHorsBornesException {
		int newId = _collectionInfractions.size();
		InfractionImpl infraction = new InfractionImpl(newId, description, niveau);
		this._collectionInfractions.infractions().add(infraction);

	}

}
