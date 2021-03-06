package ca.etsmtl.ens.log720.modulevoiture;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

/**
 * 
 */

/**
 * @author charly
 *
 */
public class ModuleVoiture {

	
	private static final String BANQUE_REACTIONS_REFERENCE_FILE_NAME = "BanqueReactions_reference.nsref";
	public static ModuleVoiture moduleVoiture;
	
	private org.omg.PortableServer.POA _poa;

	public org.omg.PortableServer.POA get_poa() {
		return _poa;
	}

	/**
	 * @param orb 
	 * @throws InvalidName 
	 * @throws AdapterInactive 
	 * 
	 */
	public ModuleVoiture(ORB orb) throws InvalidName, AdapterInactive {
		_poa  = org.omg.PortableServer.POAHelper
				.narrow(orb.resolve_initial_references("RootPOA"));

		_poa.the_POAManager().activate();
		
		try {			
			org.omg.CORBA.Object banqueReactions = _poa.servant_to_reference(new BanqueReactionsImpl());

			PrintWriter ps = new PrintWriter(
					new FileOutputStream(
							new File(
									BANQUE_REACTIONS_REFERENCE_FILE_NAME
									)
							)
					);
			ps.println(orb.object_to_string(banqueReactions));
			
			ps.close();

			
		} catch (ServantNotActive e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WrongPolicy e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init(args, null);
		try {
			moduleVoiture = new ModuleVoiture(orb);

			orb.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
