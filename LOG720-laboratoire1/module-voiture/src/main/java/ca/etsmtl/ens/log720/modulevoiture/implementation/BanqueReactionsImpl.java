/**
 * 
 */
package ca.etsmtl.ens.log720.modulevoiture.implementation;

import java.util.ArrayList;

import org.omg.PortableServer.POA;

import ca.etsmtl.ens.log720.lab1.BanqueReactionsPOA;
import ca.etsmtl.ens.log720.lab1.CollectionReaction;
import ca.etsmtl.ens.log720.lab1.CollectionReactionHelper;
import ca.etsmtl.ens.log720.lab1.Dossier;
import ca.etsmtl.ens.log720.lab1.Reaction;
import ca.etsmtl.ens.log720.modulevoiture.ModuleVoiture;


/**
 * @author charly
 *
 */
public class BanqueReactionsImpl extends BanqueReactionsPOA {

	private static int maxId = 0;
	
	private CollectionReactionsImpl _collectionReactions;
	/**
	 * 
	 */
	public BanqueReactionsImpl() {
		this._collectionReactions = new CollectionReactionsImpl();
	}

	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.BanqueReactionsOperations#reactions()
	 */
	public CollectionReaction reactions() {
		try {
			// Recuperer le POA cree dans le serveur
			POA rootpoa = ModuleVoiture.moduleVoiture.get_poa();

			// Activer l'objet et retourne l'objet CORBA
			org.omg.CORBA.Object obj = rootpoa
					.servant_to_reference(_collectionReactions);

			// Retourner une Collection d'etudiant
			return CollectionReactionHelper.narrow(obj);
			
		} catch (Exception e) {
			System.out.println("Erreur retour de l'objet CollectionReactions : "	+ e);
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.BanqueReactionsOperations#ajouterReaction(java.lang.String, int)
	 */
	public void ajouterReaction(String reaction, int gravite) {
		ReactionImpl reactionImpl = new ReactionImpl(reaction, gravite);
		this._collectionReactions.getReactions().add(reactionImpl);
		++maxId;
	}

	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.BanqueReactionsOperations#trouverReactionsParDossier(ca.etsmtl.ens.log720.lab1.Dossier)
	 */
	public CollectionReaction trouverReactionsParDossier(Dossier myDossier) {
		
//		for (int i = 0; i < this._collectionReactions.size(); ++i) {
//			Reaction r = this._collectionReactions.getReaction(i);
//			if(r. == idReaction)
//				return r;
//		}
		return null;
	}

	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.BanqueReactionsOperations#trouverReactionParId(int)
	 */
	public Reaction trouverReactionParId(int idReaction) {
		for (int i = 0; i < this._collectionReactions.size(); ++i) {
			Reaction r = this._collectionReactions.getReaction(i);
			if(r.id() == idReaction)
				return r;
		}
		return null;
	}
	
	public static int getMaxId() {
		return maxId ;
	}

}
