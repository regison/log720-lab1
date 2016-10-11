package ca.etsmtl.ens.log720.serverposte;

import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

import ca.etsmtl.ens.log720.serverposte.implementation.BanqueDossiersImpl;
import ca.etsmtl.ens.log720.serverposte.implementation.BanqueInfractionsImpl;

/**
 * 
 */

/**
 * @author charly
 *
 */
public class ServerPoste {

	private static ServerPoste serverposte;
	private static org.omg.PortableServer.POA poa;
	
	/**
	 * @param orb 
	 * @throws InvalidName 
	 * @throws AdapterInactive 
	 * 
	 */
	public ServerPoste(ORB orb) throws InvalidName, AdapterInactive {
		poa = org.omg.PortableServer.POAHelper
				.narrow(orb.resolve_initial_references("RootPOA"));

		poa.the_POAManager().activate();
		
		try {
			org.omg.CORBA.Object banqueInfractions = poa.servant_to_reference(new BanqueInfractionsImpl());
			org.omg.CORBA.Object banqueDossiers = poa.servant_to_reference(new BanqueDossiersImpl());
			
			NamingContextExt nc = NamingContextExtHelper.narrow(orb
					.resolve_initial_references("NameService"));
			
			nc.rebind(nc.to_name("BanqueInfractions"), banqueInfractions);
			nc.rebind(nc.to_name("BanqueDossiers"), banqueDossiers);
			
		} catch (ServantNotActive e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WrongPolicy e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CannotProceed e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (org.omg.CosNaming.NamingContextPackage.InvalidName e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public static org.omg.PortableServer.POA getPoa() {
		return poa;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init(args, null);
		try {
			serverposte = new ServerPoste(orb);

			orb.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
