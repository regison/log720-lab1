package ca.etsmtl.ens.log720.serverposte;

import java.util.ArrayList;

import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;
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
public class ServerPoste {

	public static ServerPoste serverposte;
	private org.omg.PortableServer.POA poa;
	private BanqueDossiersImpl servantBanqueDossiers;
	private BanqueInfractionsImpl servantBanqueInfractions;
	
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

			servantBanqueDossiers = new BanqueDossiersImpl();
			servantBanqueInfractions = new BanqueInfractionsImpl();
			
			org.omg.CORBA.Object banqueDossiers  = poa.servant_to_reference(servantBanqueDossiers);
			org.omg.CORBA.Object banqueInfractions = poa.servant_to_reference(servantBanqueInfractions);
			
			NamingContextExt nc = NamingContextExtHelper.narrow(orb
					.resolve_initial_references("NameService"));
			
			NameComponent[] name = new NameComponent[] { new NameComponent(
					"BanqueDossiers", "service") };

			nc.rebind(name, banqueDossiers);
			
			name = new NameComponent[] { new NameComponent(
					"BanqueInfractions", "service") };

			nc.rebind(name, banqueInfractions);
						
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
	
	public org.omg.PortableServer.POA getPoa() {
		return poa;
	}
	
	public String exportBanqueDossierToCSV(){
		String csvObject = "";
		//print Header
		csvObject += "id,nom,prenom,noPermis,noPlaque,levelId,infractionsArray,reactionsArray" + "\n";
		for (DossierImpl doss : this.servantBanqueDossiers.getCollectionDossiers().dossiers()) {
			csvObject 
				+= doss.id() + "," 
					+ doss.nom() + "," 
					+ doss.prenom() + "," 
					+ doss.noPermis() + "," 
					+ doss.noPlaque() + "," 
					+ doss.niveau() + "," 
					+ doss.getListeInfraction() + "," 
					+ doss.getListeReaction() + "\n";
		}
		
		
		return csvObject;
		
	}

	public String exportBanqueInfractionToCSV(){
		String csvObject = "";
		//print Header
		csvObject += "id,description,niveau" + "\n";
		for (InfractionImpl infr : this.servantBanqueInfractions.get_collectionInfractions().infractions()) {
			csvObject 
				+= infr.id() + "," 
					+ infr.description() + "," 
					+ infr.niveau() + "\n";
		}
		
		
		return csvObject;
		
	}
	/**
	 * @param args
	 * //configure OAIAdrr in jacrob.propertie to allow acces from voiture client
	 */
	public static void main(String[] args) {
		
		org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init(args, null);
		try {
			serverposte = new ServerPoste(orb);

			//TODO load from file
			
			orb.run();
			
			serverposte.exportBanqueDossierToCSV();
			serverposte.exportBanqueInfractionToCSV();
			
			//TODO save to file;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
