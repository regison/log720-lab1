package ca.etsmtl.ens.log720.serverposte;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Date;

import ca.etsmtl.ens.log720.lab1.BanqueInfractionsPOA;
import ca.etsmtl.ens.log720.lab1.CollectionInfraction;
import ca.etsmtl.ens.log720.lab1.CollectionInfractionHelper;
import ca.etsmtl.ens.log720.lab1.Dossier;
import ca.etsmtl.ens.log720.lab1.Infraction;
import ca.etsmtl.ens.log720.lab1.InfractionHelper;
import ca.etsmtl.ens.log720.lab1.NiveauHorsBornesException;

public class BanqueInfractionsImpl extends BanqueInfractionsPOA{

	private CollectionInfractionImpl _collectionInfractions;
	private PrintWriter infractionsoutPutFileStream;
	

	public BanqueInfractionsImpl(String infractionFilePathData) {
		this._collectionInfractions = new CollectionInfractionImpl();
		this.loadFromFile(infractionFilePathData);
	}

	private void loadFromFile(String infractionFilePathData) 
	{
		loadInfractionFromFile(infractionFilePathData);
		
	}
	
	private void loadInfractionFromFile(String infractionsFilePathData) {
		try {
			File inputFile = new File(infractionsFilePathData);
			FileInputStream inputFilestream = new FileInputStream(inputFile);
			InputStreamReader isr = new InputStreamReader(inputFilestream);
			BufferedReader br = new BufferedReader(isr);
			
			String line = null;
			while((line = br.readLine()) != null){
				String[] strSplitted = line.split(",");
				try{
					this.ajouterInfraction(strSplitted[1], Integer.parseInt(strSplitted[2]));
					
				} catch(NumberFormatException nfex){
				} catch (NiveauHorsBornesException e) {
				}
			}
			br.close();
			
			File backUpFile = new File(inputFile.getName() + "_" + (new Date()));
			Files.copy(inputFilestream,backUpFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			
		} catch (FileNotFoundException e1) {
			
		} catch (IOException e) {}
		
		try {
			infractionsoutPutFileStream = new PrintWriter(
					new FileOutputStream(
							new File(
									infractionsFilePathData
									)
							)
					);
		} catch (FileNotFoundException e) {
			
		}
	}

	public CollectionInfraction infractions() {
		try{
			// Recuperer le POA cree dans le serveur
			org.omg.PortableServer.POA srvPOA = ServerPoste.serverposte.getPoa();
	
			// Activer l'objet et retourne l'objet CORBA
			org.omg.CORBA.Object obj = srvPOA.servant_to_reference(_collectionInfractions);
			// Retourner une Collection de dossiers
			return CollectionInfractionHelper.narrow(obj);
			
		} catch (Exception e) {
			System.out.println("Erreur retour de l'objet CollectionReactions : "	+ e);
			return null;
		}
	}

	public CollectionInfraction trouverInfractionsParDossier(Dossier mydossier) {
		CollectionInfractionImpl collInfractions = new CollectionInfractionImpl();
		
		if(mydossier == null)
			return null;
		
		for(int infractionId: mydossier.getListeInfraction()){
			collInfractions.infractions().add(this._collectionInfractions.infractions().get(infractionId));
		}
		
		try{
			// Recuperer le POA cree dans le serveur
			org.omg.PortableServer.POA srvPOA = ServerPoste.serverposte.getPoa();
	
			// Activer l'objet et retourne l'objet CORBA
			org.omg.CORBA.Object obj = srvPOA.servant_to_reference(collInfractions);
			// Retourner une Collection de dossiers
			return CollectionInfractionHelper.narrow(obj);
			
		} catch (Exception e) {
			System.out.println("Erreur retour de l'objet Etudiant : " + e);
			return null;
		}	
	}

	public Infraction trouverInfractionParId(int idInfraction) {
		try{
			for(InfractionImpl infractionFound : _collectionInfractions.infractions()){
				if (infractionFound.id() == idInfraction){
					// Recuperer le POA cree dans le serveur
					org.omg.PortableServer.POA srvPOA = ServerPoste.serverposte.getPoa();
			
					// Activer l'objet et retourne l'objet CORBA
					org.omg.CORBA.Object obj = srvPOA.servant_to_reference(infractionFound);
					// Retourner une Collection de dossiers
					return InfractionHelper.narrow(obj);
				}			
			}

		} catch (Exception e) {
			System.out.println("Erreur retour de l'objet Etudiant : " + e);
			return null;
		}	
		
		return null;
	}

	public void ajouterInfraction(String description, int niveau) throws NiveauHorsBornesException {
		int newId = _collectionInfractions.size();
		InfractionImpl infraction = new InfractionImpl(newId, description, niveau);
		this._collectionInfractions.infractions().add(infraction);
		if(this.infractionsoutPutFileStream != null){
			this.infractionsoutPutFileStream.println(infraction.toCSV());
			this.infractionsoutPutFileStream.flush();
		}

	}

}
