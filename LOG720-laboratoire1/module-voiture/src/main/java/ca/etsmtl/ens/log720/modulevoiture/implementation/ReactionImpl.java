/**
 * 
 */
package ca.etsmtl.ens.log720.modulevoiture.implementation;

import ca.etsmtl.ens.log720.lab1.ReactionPOA;

/**
 * @author charly
 *
 */
public class ReactionImpl extends ReactionPOA {

	private int gravite;
	private int id;
	private String description;
	
	/**
	 * @param gravite 
	 * @param reaction 
	 * 
	 */
	public ReactionImpl(String reaction, int gravite) {
		this.id = BanqueReactionsImpl.getMaxId();
		this.description = reaction;
		this.gravite = gravite;
	}

	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.ReactionOperations#id()
	 */
	public int id() {
		return this.id;
	}

	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.ReactionOperations#description()
	 */
	public String description() {
		return this.description;
	}

	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.ReactionOperations#niveau()
	 */
	public int niveau() {
		return this.gravite;
	}

	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.ReactionOperations#_toString()
	 */
	public String _toString() {
		return "" + this.id() + " -> description: " + this.description() + " / Gravite: " + this.gravite;
	}

}
