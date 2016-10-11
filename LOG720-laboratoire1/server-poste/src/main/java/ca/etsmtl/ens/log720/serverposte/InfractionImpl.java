/**
 * 
 */
package ca.etsmtl.ens.log720.serverposte;

import java.io.Serializable;

import ca.etsmtl.ens.log720.lab1.InfractionPOA;

/**
 * @author charly
 *
 */
public class InfractionImpl extends InfractionPOA {

	private String description;
	private int niveau;
	private int id;

	/**
	 * @param niveau 
	 * @param description 
	 * 
	 */
	public InfractionImpl(int id, String description, int niveau) {
		this.id = id;
		this.description = description;
		this.niveau = niveau;
	}

	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.InfractionOperations#id()
	 */
	public int id() {
		return id;
	}

	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.InfractionOperations#description()
	 */
	public String description() {
		return description;
	}

	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.InfractionOperations#niveau()
	 */
	public int niveau() {
		return niveau;
	}

	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.InfractionOperations#_toString()
	 */
	public String _toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
