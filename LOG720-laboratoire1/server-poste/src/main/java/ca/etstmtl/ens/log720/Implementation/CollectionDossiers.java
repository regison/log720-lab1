package ca.etstmtl.ens.log720.Implementation;

import java.util.ArrayList;

public class CollectionDossiers extends CollectionDossiersPOA{

	private ArrayList<DossierImpl> listOfDossiers;
	
	public CollectionDossiers(){
		listOfDossiers =  new ArrayList<DossierImpl>()
	}
	
	public Dossier getDossier(int idDossier){
		DossierImpl dImpl = listOfDossiers.get(idDossier);
		
		if (dImpl != null){
			POA root = ServerPoste.getPOa()
		}
	}
	
	public int size(){
		return listOfDossiers.size();
	}
}
