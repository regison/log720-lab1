 /**
 * 
 */
package ca.etsmtl.ens.log720;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import ca.etsmtl.ens.log720.lab1.BanqueDossiers;
import ca.etsmtl.ens.log720.lab1.BanqueDossiersHelper;
import ca.etsmtl.ens.log720.lab1.BanqueInfractions;
import ca.etsmtl.ens.log720.lab1.BanqueInfractionsHelper;
import ca.etsmtl.ens.log720.lab1.BanqueReactions;
import ca.etsmtl.ens.log720.lab1.BanqueReactionsHelper;
import ca.etsmtl.ens.log720.lab1.CollectionDossier;
import ca.etsmtl.ens.log720.lab1.CollectionInfraction;
import ca.etsmtl.ens.log720.lab1.CollectionReaction;
import ca.etsmtl.ens.log720.lab1.Dossier;
import ca.etsmtl.ens.log720.lab1.Infraction;
import ca.etsmtl.ens.log720.lab1.InvalidIdException;
import ca.etsmtl.ens.log720.lab1.Reaction;
import ca.etsmtl.ens.log720.terminal.Menu;
import ca.etsmtl.ens.log720.terminal.Terminal;


/**
 * @author charly
 *
 */
public class ClientVoiture {

	private static final String BANQUE_REACTIONS_REFERENCE_FILE_NAME = "BanqueReactions_reference.nsref";
	
	private static Terminal term;
	private static ClientVoiture clientVoiture;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
	
		try {
			clientVoiture = new ClientVoiture(args);
			Menu mainMenu = buildMenus();
			term = new Terminal(mainMenu);
			term.launchTerminal();
			
		} catch (InvalidName e) {
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
	
	
	/* ---- Business Logic ---- */
	private org.omg.CORBA.ORB orb;
	private NamingContextExt nc;
	private Dossier dossierCourant;

	private BanqueDossiers banqueDossier;
	private BanqueInfractions banqueInfractions;
	private BanqueReactions banqueReactions;

	public ClientVoiture(String[] args) throws InvalidName, NotFound, CannotProceed, org.omg.CosNaming.NamingContextPackage.InvalidName{
		this.orb = org.omg.CORBA.ORB.init(args, null);
		// get hold of the naming service
		this.nc = NamingContextExtHelper.narrow(orb
				.resolve_initial_references("NameService"));
		
		NameComponent[] name = new NameComponent[] { new NameComponent(
				"BanqueDossiers", "service") };

		// resolve name to get a reference to our server
		banqueDossier = BanqueDossiersHelper.narrow(nc.resolve(name));
		
		name = new NameComponent[] { new NameComponent(
				"BanqueInfractions", "service") };

		// resolve name to get a reference to our server
		banqueInfractions = BanqueInfractionsHelper.narrow(nc.resolve(name));
		
		
		try {
			FileInputStream fis = new FileInputStream(BANQUE_REACTIONS_REFERENCE_FILE_NAME);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			String ior = br.readLine();
			banqueReactions = BanqueReactionsHelper.narrow(orb.string_to_object(ior));
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	protected CollectionDossier trouverDossierParNom(String nom, String prenom) {
		
		return banqueDossier.trouverDossiersParNom(nom, prenom);
		
	}
	
	protected CollectionDossier trouverDossierParNumPlaque(String numPlaque){
	
		return banqueDossier.trouverDossiersParPlaque(numPlaque);
	}
	
	protected Dossier trouverDossierParNumPermis(String numPermis) {

		return banqueDossier.trouverDossierParPermis(numPermis);
	}
	
	protected Dossier selectionnerDossier(String numPermis){
		Dossier d = banqueDossier.trouverDossierParPermis(numPermis);
		this.dossierCourant = d;
		return d;
	}


	protected Dossier getDossierSelectionne() {
		return this.dossierCourant;
	}
	
	protected CollectionReaction reactions()  {

		return banqueReactions.reactions();
	}
	
	protected void ajouterReaction(String description, int niveauGravite){

		banqueReactions.ajouterReaction(description, niveauGravite);
	}
	
	protected void ajouterReactionAuDossierSelectionne(int idReaction) throws InvalidIdException {
		this.banqueDossier.ajouterReactionAuDossier(this.dossierCourant.id(), idReaction);	
	}
	
	protected void ajouterInfractionAuDossierSelectionne(int idInfraction) throws InvalidIdException {
		this.banqueDossier.ajouterInfractionAuDossier(this.dossierCourant.id(), idInfraction);		
	}
	
	protected CollectionInfraction infractions(){
		
		return banqueInfractions.infractions();
	}
	
	protected String toString(Dossier d) {
		String dString = "";
		dString += "Nom: \"" + d.nom() + "\"\n";
		dString += "Prenom: \"" + d.prenom() + "\"\n";
		dString += "Numero Permis: \"" + d.noPermis() + "\"\n";
		dString += "Numero Plaque: \"" + d.noPlaque() + "\"\n";
		dString += "Niveau Severite: \"" + d.niveau() + "\"";
		int[] dossierReactions = d.getListeReaction();
		if (dossierReactions.length > 0){
			dString += "\n\n- Liste des reactions: \n";
			for(int idReaction : dossierReactions){
				Reaction r  = banqueReactions.trouverReactionParId(idReaction);
				dString += "\t"+ toString(r);				
			}
		}
		int[] dossierInfraction = d.getListeInfraction();
		if (dossierInfraction.length > 0){
			dString += "\n\n- Liste des infractions:";
			for(int idInfraction : dossierInfraction){
				Infraction i  = banqueInfractions.trouverInfractionParId(idInfraction);
				dString += "\t"+  "- Description: \"" + i.description() + "\" / Niveau Severite: \"" + i.niveau() + "\"";				
			}
		}
		
		return dString;
	}
	
	protected String toString(Infraction i) {
		String iString = "";
		
		iString += "Description: \"" + i.description() + "\"\n";
		iString += "Niveau Severite: \"" + i.niveau() + "\"";
		
		return iString;
	}
	
	protected String toString(Reaction r) {
		String rString = "";
		
		rString += r.id() + " -> description: " + r.description() + " / Gravite: " + r.niveau();
		
		return rString;
	}
	
	
	/* -------- Menus -------- */
	
	
	private static Menu buildMenus() {
		Menu mainMenu = new Menu("Menu Principal");
		mainMenu.setAction(new Menu.ActionDelegate() {
			public void doAction(Menu m) {
				clearConsole();
				
				System.out.println(m);
				System.out.println("Que voulez-vous faire ?");
				System.out.println(m.subMenutoString());
			}
		});
		
		//exit Menu
		Menu subMenu = new Menu("Exit");
		mainMenu.AddSubMenu(subMenu);
		subMenu.setAction(new Menu.ActionDelegate() {
			public void doAction(Menu m) {
				System.out.println("Merci d'avoir utilis� notre application");
				term.exit();
			}
		});
		
		//build Menu Rechercher dossier
		subMenu = buildMenuRechercherDossier();
		mainMenu.AddSubMenu(subMenu);
		
		
		subMenu = buildMenuVisualiserDossierSelectionne();
		mainMenu.AddSubMenu(subMenu);
		
		//build Menu Lister reactions possibles
		subMenu = buildMenuListerReaction();
		mainMenu.AddSubMenu(subMenu);
		
		//build Menu Ajouter Reaction au dossier Selectionner
		subMenu = buildMenuAjouterReaction();
		mainMenu.AddSubMenu(subMenu);
		
		//build Menu Ajouter Reaction au dossier Selectionner
		subMenu = buildMenuAjouterReactionAuDossierSelectionne();
		mainMenu.AddSubMenu(subMenu);
		
		//build Menu Lister Infractions
		subMenu = buildMenuListerInfractions();
		mainMenu.AddSubMenu(subMenu);
		
		//build Menu Ajouter infraction au dossier Selectionner
		subMenu = buildMenuAjouterInfractionAuDossierSelectionne();
		mainMenu.AddSubMenu(subMenu);
		
		return mainMenu;
	}

	private static Menu buildMenuRechercherDossier() {
		Menu m = new Menu("Rechercher un dossier");
		m.AddSubMenu(buildGoBackMenu());
		m.AddSubMenu(buildMenuRechercherDossierParNomEtPrenom());
		m.AddSubMenu(buildMenuRechercherDossierParNumPlaque());
		m.AddSubMenu(buildMenuRechercherDossierParnumPermis());
		
		m.setAction(new Menu.ActionDelegate() {
			public void doAction(Menu m) {
				clearConsole();
				System.out.println(m);
				System.out.println("Selectionnez un critere de recherche:");
				System.out.println(m.subMenutoString());
			}
			});
		
		return m;
	}
	
	/*** SubMenu Rechercher Dossier ***/
	private static Menu buildMenuRechercherDossierParNomEtPrenom() {
		Menu m = new Menu("Rechercher un dossier par le nom et le prenom");
		m.AddSubMenu(buildGoBackMenu());
		m.setAction(new Menu.ActionDelegate() {
			public void doAction(Menu m) {
				clearConsole();
				String tmp, nom, prenom;
				Scanner sc = Terminal.getInScanner();
				System.out.println("Nom ?");
				while((tmp = sc.nextLine()) == "\n")
					System.out.println("Entr�e invalide");
				nom = tmp;
				
				System.out.println("Prenom ?");
				while((tmp = sc.nextLine()) == "\n")
					System.out.println("Entr�e invalide");
				prenom = tmp;
				
				CollectionDossier dossiers;
				dossiers = clientVoiture.trouverDossierParNom(nom, prenom);
				if(dossiers.size() > 0)
				{
					m.AddSubMenu(buildMenuSelectionnerDossier());
					
					System.out.println("Voici les resultats de la recherche");
					for (int i = 0; i < dossiers.size(); ++i) {
						Dossier d = dossiers.getDossier(i);
						System.out.println("### " + d.id() + " ###");
						System.out.println(clientVoiture.toString(d)+ "\n");
					}
					System.out.println("-- Fin de la liste --");
				}else{
					m.RemoveSubMenu(buildMenuSelectionnerDossier());
					System.out.println("Aucun dossier trouve dans la Banque de dossier");
				}

				System.out.println(m.subMenutoString());
			}
			});
		
		return m;
	}

	private static Menu buildMenuRechercherDossierParNumPlaque() {
		Menu m = new Menu("Rechercher un dossier par le numero de plaque");
		m.AddSubMenu(buildGoBackMenu());
		m.setAction(new Menu.ActionDelegate() {
			public void doAction(Menu m) {
				clearConsole();
				String tmp, numPlaque;
				Scanner sc = Terminal.getInScanner();
				System.out.println("Numero de plaque ?");
				while((tmp = sc.nextLine()) == "\n")
					System.out.println("Entr�e invalide");
				numPlaque = tmp;
							
				CollectionDossier dossiers;
				dossiers = clientVoiture.trouverDossierParNumPlaque(numPlaque);
				if(dossiers.size() > 0)
				{

					m.AddSubMenu(buildMenuSelectionnerDossier());
					
					System.out.println("Voici les resultats de la recherche");
					for (int i = 0; i < dossiers.size(); ++i) {
						Dossier d = dossiers.getDossier(i);
						System.out.println("### " + d.id() + " ###");
						System.out.println(clientVoiture.toString(d)+"\n");
					}
					System.out.println("-- Fin de la liste --");
				}else{
					m.RemoveSubMenu(buildMenuSelectionnerDossier());
					System.out.println("Aucun dossier trouve dans la Banque de dossier");
				}

				System.out.println(m.subMenutoString());
			}
			});
		return m;
	}

	private static Menu buildMenuRechercherDossierParnumPermis() {
		Menu m = new Menu("Rechercher un dossier par le numero de permis");
		m.AddSubMenu(buildGoBackMenu());
		m.setAction(new Menu.ActionDelegate() {
			public void doAction(Menu m) {
				clearConsole();
				String tmp, numPermis;
				Scanner sc = Terminal.getInScanner();
				System.out.println("Numero de permis ?");
				while((tmp = sc.nextLine()) == "\n")
					System.out.println("Entr�e invalide");
				numPermis = tmp;

				Dossier d;
				d = clientVoiture.trouverDossierParNumPermis(numPermis);
				if(d != null)
				{
					m.AddSubMenu(buildMenuSelectionnerDossier());
					System.out.println("Voici le dossier trouve");
					System.out.println("### " + d.id() + " ###");
					System.out.println(clientVoiture.toString(d));
				}else
				{
					m.RemoveSubMenu(buildMenuSelectionnerDossier());
					System.out.println("Aucun dossier trouve dans la Banque de dossier");
				}
				System.out.println(m.subMenutoString());
			}
			});
		return m;
	}

	/*** Fin SubMenu Rechercher Dossier ***/
	
	private static Menu buildMenuSelectionnerDossier() {
		Menu m = new Menu("Selectionner un dossier");
		Menu goBack = buildGoBackMenu();
		goBack.setAction(new Menu.ActionDelegate() {
			
			public void doAction(Menu m) {
				term.navigateTo(m.getParentMenu().getParentMenu().getParentMenu());
			}
		});
		m.AddSubMenu(goBack);
		m.setAction(new Menu.ActionDelegate() {
			public void doAction(Menu m) {
				String tmp, numPermis;
				Scanner sc = Terminal.getInScanner();
				System.out.println("Numero de permis ?");
				while((tmp = sc.nextLine()) == "\n")
					System.out.println("Entr�e invalide");
				numPermis = tmp;		
				
				Dossier d;
				d = clientVoiture.selectionnerDossier(numPermis);
				if(d != null){
					System.out.println("### " + d.id() + " ###");
					System.out.println(clientVoiture.toString(d)+"\n");
				}
				else{
					System.out.println("Le dossier est inexistant.");
				}
				
				System.out.println(m.subMenutoString());
				
			}
			});
		
		return m;
	}
	
	private static Menu buildMenuVisualiserDossierSelectionne() {
		Menu m = new Menu("Visualiser le dossier selectionne");
		m.AddSubMenu(buildGoBackMenu());
		m.setAction(new Menu.ActionDelegate() {
			public void doAction(Menu m) {
				Dossier d;
				d = clientVoiture.getDossierSelectionne();
				if(d != null){
					System.out.println("### " + d.id() + " ###");
					System.out.println(clientVoiture.toString(d)+"\n");
				}else{
					System.out.println("Aucun dossier selectionne");
				}
				System.out.println(m.subMenutoString());
			}
			});
		
		return m;
	}

	private static Menu buildMenuListerReaction() {
		Menu m = new Menu("Lister les reactions possibles");
		m.AddSubMenu(buildGoBackMenu());
		m.setAction(new Menu.ActionDelegate() {
			public void doAction(Menu m) {
				CollectionReaction reactions;
				reactions = clientVoiture.reactions();
				if(reactions.size() > 0)
				{
					System.out.println("Voici la liste des reactions:");
					for (int i = 0; i < reactions.size(); ++i) {
						Reaction r = reactions.getReaction(i);
						System.out.println("### " + r.id() + "###");
						System.out.println(clientVoiture.toString(r));
					}
					System.out.println("-- Fin de la liste --");
				}else{
					System.out.println("Aucune reactions n'existe dans la Banque de reactions");
				}

				System.out.println(m.subMenutoString());
			}
			});
		
		return m;
	}

	private static Menu buildMenuAjouterReaction() {
		Menu m = new Menu("Ajouter Reaction");
		m.AddSubMenu(buildGoBackMenu());
		m.setAction(new Menu.ActionDelegate() {
			public void doAction(Menu m) {
				clearConsole();

				System.out.println("Vous avez choisi d'ajouter une reaction dans la Banque de reaction.");
				
				String tmp, description;
				int tmpInt = 0, niveauGravite;
				boolean invalide = true;
				
				Scanner sc = Terminal.getInScanner();
				System.out.println("Description ?");
				while((tmp = sc.nextLine()) == "\n")
					System.out.println("Entr�e invalide");
				description = tmp;
				
				System.out.println("Niveau de Gravite ?");
				while(invalide)
				{
					if((tmp = sc.nextLine()) != "\n" )
					{
						try
						{
							tmpInt = Integer.parseInt(tmp);
							invalide = false;
						}
						catch(NumberFormatException nfEx){
							invalide = true;
						}
					}
					else
						System.out.println("Entr�e invalide");
				}
				niveauGravite = tmpInt;

				clientVoiture.ajouterReaction(description, niveauGravite);
				System.out.println("Votre reaction � ete ajout�");
				
				System.out.println(m.subMenutoString());
			}
			});
		
		return m;
	}
	
	private static Menu buildMenuAjouterReactionAuDossierSelectionne() {
		Menu m = new Menu("Ajouter une reaction au dossier selectionne");
		m.AddSubMenu(buildGoBackMenu());		
		m.setAction(new Menu.ActionDelegate() {

			public void doAction(Menu m) {
				int tryCount = 0;
				String tmp;
				int tmpInt = 0, idReaction;
				boolean invalide = true;
				
				Scanner sc = Terminal.getInScanner();
				System.out.println("Identifiant de la reaction ?");
				while(invalide && tryCount < 3)
				{
					if((tmp = sc.nextLine()) != "\n" )
					{
						try
						{
							tmpInt = Integer.parseInt(tmp);
							invalide = false;
						}
						catch(NumberFormatException nfEx){
							System.out.println("Entr�e invalide");
							invalide = true;
						}
					}
					else{
						System.out.println("Entr�e invalide");
						tryCount++;
					}
				}
				
				idReaction = tmpInt;
				Dossier d;
				d = clientVoiture.getDossierSelectionne();
				if(d != null){
					try {
						clientVoiture.ajouterReactionAuDossierSelectionne(idReaction);
						System.out.println("Votre reaction � ete ajout� au dossier selectione");
					} catch (InvalidIdException e) {
						System.out.println("Impossible d'ajouter la reaction");
					}

					
				}else{
					System.out.println("Aucun dossier selectionne");
				}
				System.out.println(m.subMenutoString());
			}
			});
		
		return m;
	}
	
	private static Menu buildMenuListerInfractions() {
		Menu m = new Menu("Lister les infractions possibles");
		m.AddSubMenu(buildGoBackMenu());
		m.setAction(new Menu.ActionDelegate() {
			public void doAction(Menu m) {
				clearConsole();

				CollectionInfraction infractions = clientVoiture.infractions();
				if(infractions.size() > 0)
				{
					System.out.println("Voici la liste des infractions:");
					for (int i = 0; i < infractions.size(); ++i) {
						Infraction infra = infractions.getInfraction(i);
						System.out.println("### " + infra.id() + "###");
						System.out.println(clientVoiture.toString(infra));
					}
					System.out.println("-- Fin de la liste --");
				}else{
					System.out.println("Aucune infraction n'existe dans la Banque d'infractions");
				}

				System.out.println(m.subMenutoString());
			}
			});
		
		return m;
	}
		
	private static Menu buildMenuAjouterInfractionAuDossierSelectionne() {
		Menu m = new Menu("Ajouter une infraction au dossier selectionne");
		m.AddSubMenu(buildGoBackMenu());
		m.setAction(new Menu.ActionDelegate() {

			public void doAction(Menu m) {
				int tryCount = 0;
				String tmp;
				int tmpInt = 0, idInfraction;
				boolean invalide = true;
				
				Scanner sc = Terminal.getInScanner();
				System.out.println("Identifiant de l'infraction ?");
				while(invalide && tryCount  < 3)
				{
					if((tmp = sc.nextLine()) != "\n" )
					{
						try
						{
							tmpInt = Integer.parseInt(tmp);
							invalide = false;
						}
						catch(NumberFormatException nfEx){
							System.out.println("Entr�e invalide");
							invalide = true;
						}
					}
					else{
						System.out.println("Entr�e invalide");
						tryCount++;
					}
				}
				
				
				idInfraction = tmpInt;
				Dossier d;
				d = clientVoiture.getDossierSelectionne();
				if(d != null){
					try {
						clientVoiture.ajouterInfractionAuDossierSelectionne(idInfraction);
						System.out.println("Votre infraction � ete ajout� au doossier selectionne");
					} catch (InvalidIdException e) {
						System.out.println("Impossible d'ajoute l'infraction");
					}
					
					
				}else{
					System.out.println("Aucun dossier selectionne");
				}
				
				
				System.out.println(m.subMenutoString());
				
			}
			});
		
		return m;
	}
	
	private static Menu buildGoBackMenu() {
		Menu goBackMenu = new Menu("Aller au menu pr�c�dent");
		goBackMenu.setAction(new Menu.ActionDelegate() {
			public void doAction(Menu m) {
				clearConsole();
				term.navigateTo(m.getParentMenu().getParentMenu());				
			}
		});
		return goBackMenu;
	}
	
	public final static void clearConsole()
	{
	    try
	    {
	        final String os = System.getProperty("os.name");
	        if (os.contains("Windows"))
	            Runtime.getRuntime().exec("cls");
	        else
	        	System.out.print("\033[H\033[2J");
	    }
	    catch (final Exception e)
	    {
	    	for(int i=0; i<30; ++i)
	    		System.out.println("");
	    }
	}
	
}
