/**
 * 
 */
package ca.etsmtl.ens.log720.modulevoiture.implementation;

import java.util.ArrayList;

import org.omg.PortableServer.POA;

import ca.etsmtl.ens.log720.lab1.CollectionReactionPOA;
import ca.etsmtl.ens.log720.lab1.Reaction;
import ca.etsmtl.ens.log720.lab1.ReactionHelper;
import ca.etsmtl.ens.log720.modulevoiture.ModuleVoiture;

/**
 * @author charly
 *
 */
public class CollectionReactionsImpl extends CollectionReactionPOA {

	private ArrayList<ReactionImpl> _listeReactions;
	
	/**
	 * 
	 */
	public CollectionReactionsImpl() {
		this._listeReactions = new ArrayList<ReactionImpl>();
	}

	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.CollectionReactionOperations#size()
	 */
	public int size() {
		return _listeReactions.size();
	}

	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.CollectionReactionOperations#getReaction(int)
	 */
	public Reaction getReaction(int index) {
		try {
			// Trouver le cours correspondant au parametre "index"
			ReactionImpl reaction = _listeReactions.get(index);

			// Recuperer le POA cree dans le serveur
			POA rootpoa = ModuleVoiture.moduleVoiture.get_poa();

			// Activer l'objet et retourne l'objet CORBA
			org.omg.CORBA.Object obj = rootpoa.servant_to_reference(reaction);

			// Retourner une Reaction
			return ReactionHelper.narrow(obj);
		} catch (Exception e) {
			System.out.println("Erreur retour de l'objet Reaction : " + e);
			return null;
		}
	}

	protected ArrayList<ReactionImpl> getReactions() {
		return this._listeReactions;
	}
}
