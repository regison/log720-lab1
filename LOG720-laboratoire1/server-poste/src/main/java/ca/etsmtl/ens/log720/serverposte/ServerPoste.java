package ca.etsmtl.ens.log720.serverposte;

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
	protected BanqueDossiersImpl servantBanqueDossiers;
	protected BanqueInfractionsImpl servantBanqueInfractions;
	
	/**
	 * @param orb 
	 * @throws InvalidName 
	 * @throws AdapterInactive 
	 * 
	 */
	public ServerPoste(ORB orb,String dossierFilePathData,String dossier__InfractionsFilePathData,String dossier__ReactionsFilePathData,String infractionFilePathData) throws InvalidName, AdapterInactive {
		poa = org.omg.PortableServer.POAHelper
				.narrow(orb.resolve_initial_references("RootPOA"));

		poa.the_POAManager().activate();
		
		try {

			servantBanqueDossiers = new BanqueDossiersImpl(dossierFilePathData,dossier__InfractionsFilePathData,dossier__ReactionsFilePathData);
			servantBanqueInfractions = new BanqueInfractionsImpl(infractionFilePathData);
			
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
	
	public Boolean verifierUnicitePermis(Integer permis){		
		return servantBanqueDossiers.dossiers().getDossier(permis) != null;
	}
	
	/**
	 * @param args
	 * //configure OAIAdrr in jacrob.propertie to allow acces from voiture client
	 */
	public static void main(String[] args) {
		
		org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init(args, null);
		try {
			
			String dossierFilePathData = "dossierFilePathData.csv" ;
			String dossier__InfractionsFilePathData = "dossierFilePathData.csv" ;
			String dossier__ReactionsFilePathData = "dossierFilePathData.csv" ;
			String infractionFilePathData = "dossierFilePathData.csv" ;
				
			serverposte = new ServerPoste(orb, dossierFilePathData, dossier__InfractionsFilePathData,dossier__ReactionsFilePathData,infractionFilePathData);
			
			orb.run();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
