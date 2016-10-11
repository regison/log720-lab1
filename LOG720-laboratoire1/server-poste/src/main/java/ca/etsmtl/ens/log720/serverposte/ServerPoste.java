package ca.etsmtl.ens.log720.serverposte;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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

import ca.etsmtl.ens.log720.lab1.BanqueReactionsHelper;
import ca.etsmtl.ens.log720.lab1.InvalidIdException;
import ca.etsmtl.ens.log720.lab1.NiveauHorsBornesException;
import ca.etsmtl.ens.log720.lab1.NoPermisExisteDejaException;

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
	
	public ServerPoste(ORB orb, String csvBanqueDossierFileName, String csvBanqueInfractionFileName) throws InvalidName, AdapterInactive {
		this(orb);
		
		try{
			try {
				FileInputStream fis = new FileInputStream(csvBanqueInfractionFileName);
				InputStreamReader isr = new InputStreamReader(fis);
				BufferedReader br = new BufferedReader(isr);
				String str = br.readLine();
				if(str != null){
					while((str = br.readLine()) != null)
					{
						String[] strSplitted = str.split(",");
						try {
							this.servantBanqueInfractions.ajouterInfraction(strSplitted[0], Integer.parseInt(strSplitted[1]));
						} catch (NumberFormatException e) {
							// ignore infraction
						} catch (NiveauHorsBornesException e) {
							// ignore infraction
						}
					}
				}
				br.close();
			
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			try {
				FileInputStream fis = new FileInputStream(csvBanqueDossierFileName);
				InputStreamReader isr = new InputStreamReader(fis);
				BufferedReader br = new BufferedReader(isr);
				String str = br.readLine();
				if(str != null){
					while((str = br.readLine()) != null)
					{
						try{
							String[] strSplitted = str.split(",");
							int dossierId = Integer.parseInt(strSplitted[0]);
							this.servantBanqueDossiers.ajouterDossier(strSplitted[1], strSplitted[2],strSplitted[3], strSplitted[4]);
							
							//TODO add infractions id to dossier from strSplitted[5]
							//this.servantBanqueDossiers.ajouterInfractionAuDossier(dossierId, 0);
							//TODO add reactions id to dossier from strSplitted[6]
							//this.servantBanqueDossiers.ajouterReactionAuDossier(dossierId, 0);
							
						} catch (NoPermisExisteDejaException e) {
							// ignore dossier
						}/* catch (InvalidIdException e) {
							// ignore dossier
						}*/
						
					}
				}
				br.close();
			
			} catch (FileNotFoundException e) {
				// ignore import
			}
		}catch(IOException ioe){
			
		}
	}

	public org.omg.PortableServer.POA getPoa() {
		return poa;
	}
	
	public void exportBanqueDossierToCSV(String csvBanqueDossiersFileName){
		
		String csvToWrite = servantBanqueDossiers.toCSV();
		File file = new File(csvBanqueDossiersFileName);
		if(file.exists()){
			// clear file to recreate it
			file.delete();	
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try {
			PrintWriter ps = new PrintWriter(
					new FileOutputStream(file)
					);
			ps.println(csvToWrite);
			
			ps.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void exportBanqueInfractionToCSV(String csvBanqueInfractionsFileName){
		String csvToWrite = servantBanqueInfractions.toCSV();
		File file = new File(csvBanqueInfractionsFileName);
		if(file.exists()){
			file.delete();	
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			PrintWriter ps = new PrintWriter(
					new FileOutputStream(file)
					);
			ps.println(csvToWrite);
			
			ps.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @param args
	 * //configure OAIAdrr in jacrob.propertie to allow acces from voiture client
	 */
	public static void main(String[] args) {
		
		org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init(args, null);
		try {
			String csvBanqueDossiersFileName = "BanqueDossier.csv", csvBanqueInfractionsFileName = "BanqueInfraction.csv";
			
			serverposte = new ServerPoste(orb, csvBanqueDossiersFileName, csvBanqueInfractionsFileName);
			
			orb.run();
			
			serverposte.exportBanqueDossierToCSV(csvBanqueDossiersFileName);
			serverposte.exportBanqueInfractionToCSV(csvBanqueInfractionsFileName);
			
			//TODO save to file;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
