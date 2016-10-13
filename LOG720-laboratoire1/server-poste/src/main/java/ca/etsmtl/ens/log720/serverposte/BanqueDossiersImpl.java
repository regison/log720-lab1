/**
 * 
 */
package ca.etsmtl.ens.log720.serverposte;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.time.ZonedDateTime;

import ca.etsmtl.ens.log720.lab1.BanqueDossiersPOA;
import ca.etsmtl.ens.log720.lab1.CollectionDossier;
import ca.etsmtl.ens.log720.lab1.CollectionDossierHelper;
import ca.etsmtl.ens.log720.lab1.Dossier;
import ca.etsmtl.ens.log720.lab1.DossierHelper;
import ca.etsmtl.ens.log720.lab1.InvalidIdException;
import ca.etsmtl.ens.log720.lab1.NoPermisExisteDejaException;

/**
 * @author charly
 * @modify by Regg
 *
 */
public class BanqueDossiersImpl extends BanqueDossiersPOA{

	
	private CollectionDossiersImpl collectionDossiers;
	private PrintWriter dossierOutPutFileStream, dossierInfractionsoutPutFileStream,dossierReactionoutPutFileStream;
	/**
	 * 
	 */
	public BanqueDossiersImpl(String dossierFilePathData,String dossier__InfractionsFilePathData,String dossier__ReactionsFilePathData) 
	{
		collectionDossiers =  new CollectionDossiersImpl();
		
		this.loadFromFile(dossierFilePathData,dossier__InfractionsFilePathData,dossier__ReactionsFilePathData);
	}

	private void loadFromFile(String dossierFilePathData,String dossier__InfractionsFilePathData,String dossier__ReactionsFilePathData) 
	{
		loadDossierFromFile(dossierFilePathData);
		loadDossier__InfractionFromFile(dossier__InfractionsFilePathData);
		loadDossier__ReactionFromFile(dossier__ReactionsFilePathData);
	}

	private void loadDossierFromFile(String dossierFilePathData) {
		try {
			File inputFile = new File(dossierFilePathData);
			FileInputStream inputFilestream = new FileInputStream(inputFile);
			InputStreamReader isr = new InputStreamReader(inputFilestream);
			BufferedReader br = new BufferedReader(isr);
			
			String line = null;
			while((line = br.readLine()) != null){
				String[] strSplitted = line.split(",");
				this.ajouterDossier(strSplitted[1], 
						  strSplitted[2],
						  strSplitted[3], 
						  strSplitted[4]);
				
			}
			
			@SuppressWarnings("unused")
			File backUpFile = new File(inputFile,inputFile.getName() + ZonedDateTime.now());
			
			br.close();
		} catch (FileNotFoundException e1) {
			
		} catch (IOException e) {

		} catch (NoPermisExisteDejaException e) {
		}
		
		
		try {
			dossierOutPutFileStream = new PrintWriter(
					new FileOutputStream(
							new File(
									dossierFilePathData
									)
							)
					);
		} catch (FileNotFoundException e) {
			
		}
		
	}

	private void loadDossier__InfractionFromFile(String dossier__InfractionFilePathData) {
		try {
			File inputFile = new File(dossier__InfractionFilePathData);
			FileInputStream inputFilestream = new FileInputStream(inputFile);
			InputStreamReader isr = new InputStreamReader(inputFilestream);
			BufferedReader br = new BufferedReader(isr);
			
			String line = null;
			while((line = br.readLine()) != null){
				String[] strSplitted = line.split(",");
				try{
					int idDossier = Integer.parseInt(strSplitted[0]);
					int idInfraction= Integer.parseInt(strSplitted[1]);
					this.ajouterInfractionAuDossier(idDossier, idInfraction);
				}
				catch(NumberFormatException mfex){
					
				} catch (InvalidIdException e) {
				}
				
			}
			
			@SuppressWarnings("unused")
			File backUpFile = new File(inputFile,inputFile.getName() + ZonedDateTime.now());
			
			br.close();
		} catch (FileNotFoundException e1) {
			
		} catch (IOException e) {
		}
		
		
		try {
			dossierInfractionsoutPutFileStream = new PrintWriter(
					new FileOutputStream(
							new File(
									dossier__InfractionFilePathData
									)
							)
					);
		} catch (FileNotFoundException e1) {
			
		}
		
	}

	private void loadDossier__ReactionFromFile(String dossier__ReactionFilePathData) {
		try {
			File inputFile = new File(dossier__ReactionFilePathData);
			FileInputStream inputFilestream = new FileInputStream(inputFile);
			InputStreamReader isr = new InputStreamReader(inputFilestream);
			BufferedReader br = new BufferedReader(isr);
			
			String line = null;
			while((line = br.readLine()) != null){
				String[] strSplitted = line.split(",");
				
				try{
					int idDossier = Integer.parseInt(strSplitted[0]);
					int idReaction= Integer.parseInt(strSplitted[1]);
					this.ajouterReactionAuDossier(idDossier, idReaction);
				}
				catch(NumberFormatException mfex){
					
				} catch (InvalidIdException e) {
				}
			}
			
			@SuppressWarnings("unused")
			File backUpFile = new File(inputFile,inputFile.getName() + ZonedDateTime.now());
			
			br.close();
		} catch (FileNotFoundException e1) {
			
		} catch (IOException e) {

		}
		
		
		try {
			dossierReactionoutPutFileStream = new PrintWriter(
					new FileOutputStream(
							new File(
									dossier__ReactionFilePathData
									)
							)
					);
		} catch (FileNotFoundException e) {
			
		}
		
	}


	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.BanqueDossiersOperations#dossiers()
	 */
	public CollectionDossier dossiers() {
		try{
			// Recuperer le POA cree dans le serveur
			org.omg.PortableServer.POA srvPOA = ServerPoste.serverposte.getPoa();
	
			// Activer l'objet et retourne l'objet CORBA
			org.omg.CORBA.Object obj = srvPOA.servant_to_reference(collectionDossiers);
			// Retourner une Collection de dossiers
			return CollectionDossierHelper.narrow(obj);
			
		} catch (Exception e) {
			System.out.println("Erreur retour de l'objet CollectionReactions : "	+ e);
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.BanqueDossiersOperations#trouverDossiersParPlaque(java.lang.String)
	 */
	public CollectionDossier trouverDossiersParPlaque(String plaque) {
		CollectionDossiersImpl collDossier = new CollectionDossiersImpl();
		
		if(plaque.isEmpty())
			return null;
		
		for(DossierImpl dossierFound : collectionDossiers.dossiers()){
			if (dossierFound.noPlaque().toLowerCase()
							.equals(plaque.toLowerCase())){
				collDossier.dossiers().add(dossierFound);
			}			
		}
		
		try{
			// Recuperer le POA cree dans le serveur
			org.omg.PortableServer.POA srvPOA = ServerPoste.serverposte.getPoa();
	
			// Activer l'objet et retourne l'objet CORBA
			org.omg.CORBA.Object obj = srvPOA.servant_to_reference(collDossier);
			// Retourner une Collection de dossiers
			return CollectionDossierHelper.narrow(obj);
			
		} catch (Exception e) {
			System.out.println("Erreur retour de l'objet Etudiant : " + e);
			return null;
		}	
	}

	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.BanqueDossiersOperations#trouverDossiersParNom(java.lang.String, java.lang.String)
	 */
	public CollectionDossier trouverDossiersParNom(String nom, String prenom) {
		CollectionDossiersImpl collDossier = new CollectionDossiersImpl();
		
		if(!nom.isEmpty() || !prenom.isEmpty()){
			for(DossierImpl dossierFound : collectionDossiers.dossiers()){
				if (dossierFound.nom().toLowerCase().equals(nom.toLowerCase()) 
						&& dossierFound.prenom().toLowerCase().equals(prenom.toLowerCase())){
					collDossier.dossiers().add(dossierFound);
				}			
			}
			
			try{
				// Recuperer le POA cree dans le serveur
				org.omg.PortableServer.POA srvPOA = ServerPoste.serverposte.getPoa();
		
				// Activer l'objet et retourne l'objet CORBA
				org.omg.CORBA.Object obj = srvPOA.servant_to_reference(collDossier);
				// Retourner une Collection de dossiers
				return CollectionDossierHelper.narrow(obj);
				
			} catch (Exception e) {
				System.out.println("Erreur retour de l'objet Etudiant : " + e);
				return null;
			}	
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.BanqueDossiersOperations#trouverDossierParPermis(java.lang.String)
	 */
	public Dossier trouverDossierParPermis(String noPermis) {
		try{
			if(!noPermis.isEmpty()){
				for(DossierImpl dossierFound : collectionDossiers.dossiers()){
					if (dossierFound.noPermis().toLowerCase()
									.equals(noPermis.toLowerCase())){
						// Recuperer le POA cree dans le serveur
						org.omg.PortableServer.POA srvPOA = ServerPoste.serverposte.getPoa();
				
						// Activer l'objet et retourne l'objet CORBA
						org.omg.CORBA.Object obj = srvPOA.servant_to_reference(dossierFound);
						// Retourner une Collection de dossiers
						return DossierHelper.narrow(obj);
					}
				
				}
			}
		} catch (Exception e) {
			System.out.println("Erreur retour de l'objet Dossier par permis : " + e);
			return null;
		}	
		
		return null;
	}

	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.BanqueDossiersOperations#trouverDossierParId(int)
	 */
	public Dossier trouverDossierParId(int idDossier) {
		try{
			for(DossierImpl dossierFound : collectionDossiers.dossiers()){
				if (dossierFound.id() == idDossier){
					// Recuperer le POA cree dans le serveur
					org.omg.PortableServer.POA srvPOA = ServerPoste.serverposte.getPoa();
			
					// Activer l'objet et retourne l'objet CORBA
					org.omg.CORBA.Object obj = srvPOA.servant_to_reference(dossierFound);
					// Retourner une Collection de dossiers
					return DossierHelper.narrow(obj);
				}			
			}

		} catch (Exception e) {
			System.out.println("Erreur retour de l'objet Dossier par Id : " + e);
			return null;
		}	
		
		return null;
	}

	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.BanqueDossiersOperations#ajouterDossier(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public void ajouterDossier(String nom, String prenom, String noPermis, String noPlaque)
			throws NoPermisExisteDejaException {
		
		int idDossier = collectionDossiers.size();
		
		
		
		//Create a new dossier
		DossierImpl newDossier = new DossierImpl(idDossier, nom, prenom, noPlaque, noPermis);
		
		//Verify if file already exists if not add it
		if (!collectionDossiers.dossiers().contains(newDossier)){
			collectionDossiers.dossiers().add(newDossier);
			if(this.dossierOutPutFileStream != null)
				this.dossierOutPutFileStream.println(newDossier);
		}
		else
			throw new NoPermisExisteDejaException();

	}

	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.BanqueDossiersOperations#ajouterInfractionAuDossier(int, int)
	 */
	public void ajouterInfractionAuDossier(int idDossier, int idInfraction) throws InvalidIdException {
		
			if (idDossier != 0 &&  idInfraction != 0){
				Dossier dossier = trouverDossierParId(idDossier);
				
				if (dossier != null){
					dossier.ajouterInfractionAListe(idInfraction);
					if(this.dossierInfractionsoutPutFileStream != null)
						this.dossierInfractionsoutPutFileStream.println("" + idDossier + "," + idInfraction + "\n");
				}
			}
			else
				throw new InvalidIdException();
	}

	/* (non-Javadoc)
	 * @see ca.etsmtl.ens.log720.lab1.BanqueDossiersOperations#ajouterReactionAuDossier(int, int)
	 */
	public void ajouterReactionAuDossier(int idDossier, int idReaction) throws InvalidIdException {

		if (idDossier != 0 &&  idReaction != 0){
			Dossier dossier = trouverDossierParId(idDossier);
			
			if (dossier != null){
				dossier.ajouterReactionAListe(idReaction);	
				if(this.dossierReactionoutPutFileStream != null)
					this.dossierReactionoutPutFileStream.println("" + idDossier + "," + idReaction + "\n");
			}
		}
		else
			throw new InvalidIdException();

	}

	public String toCSV() {
		String csvObject = "";
		//print Header
		csvObject += "id,nom,prenom,noPermis,noPlaque,levelId,infractionsArray,reactionsArray" + "\n";
		for (DossierImpl doss : this.collectionDossiers.dossiers()) {
			csvObject 
				+= doss.toCSV() + "\n";
		}
		
		return csvObject;
	}

}
