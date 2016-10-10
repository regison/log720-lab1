/**
 * 
 */
package ca.etsmtl.ens.log720;

import java.util.Scanner;

import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import ca.etsmtl.ens.log720.terminal.*;
import ca.etsmtl.ens.log720.lab1.*;


/**
 * @author charly
 *
 */
public class ClientVoiture {

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
			System.out.println(e);
			e.printStackTrace();
		}
		
		
	}
	
	
	/* ---- Business Logic ---- */
	private org.omg.CORBA.ORB orb;
	private NamingContextExt nc;
	private Dossier dossierCourant;
	
	public ClientVoiture(String[] args) throws InvalidName{
		this.orb = org.omg.CORBA.ORB.init(args, null);
		// get hold of the naming service
		this.nc = NamingContextExtHelper.narrow(orb
				.resolve_initial_references("NameService"));
	}
	
	protected CollectionDossier trouverDossierParNom(String nom, String prenom) throws NotFound, CannotProceed, org.omg.CosNaming.NamingContextPackage.InvalidName {
		NameComponent[] name = new NameComponent[] { new NameComponent(
				"BanqueDossiers", "service") };

		// resolve name to get a reference to our server
		BanqueDossiers banqueDossier = BanqueDossiersHelper
				.narrow(nc.resolve(name));

		return banqueDossier.trouverDossiersParNom(nom, prenom);
		
	}
	
	protected CollectionDossier trouverDossierParNumPlaque(String numPlaque) throws NotFound, CannotProceed, org.omg.CosNaming.NamingContextPackage.InvalidName {
		NameComponent[] name = new NameComponent[] { new NameComponent(
				"BanqueDossiers", "service") };

		// resolve name to get a reference to our server
		BanqueDossiers banqueDossier = BanqueDossiersHelper
				.narrow(nc.resolve(name));

		return banqueDossier.trouverDossiersParPlaque(numPlaque);
	}
	
	protected Dossier trouverDossierParNumPermis(String numPermis) throws NotFound, CannotProceed, org.omg.CosNaming.NamingContextPackage.InvalidName {
		NameComponent[] name = new NameComponent[] { new NameComponent(
				"BanqueDossiers", "service") };

		// resolve name to get a reference to our server
		BanqueDossiers banqueDossier = BanqueDossiersHelper
				.narrow(nc.resolve(name));

		return banqueDossier.trouverDossierParPermis(numPermis);
	}
	
	protected Dossier selectionnerDossier(String numPermis) throws NotFound, CannotProceed, org.omg.CosNaming.NamingContextPackage.InvalidName {
		NameComponent[] name = new NameComponent[] { new NameComponent(
				"BanqueDossiers", "service") };

		// resolve name to get a reference to our server
		BanqueDossiers banqueDossier = BanqueDossiersHelper
				.narrow(nc.resolve(name));


		Dossier d = banqueDossier.trouverDossierParPermis(numPermis);
		this.dossierCourant = d;
		return d;
	}

	protected CollectionReaction reactions() throws NotFound, CannotProceed, org.omg.CosNaming.NamingContextPackage.InvalidName {
		NameComponent[] name = new NameComponent[] { new NameComponent(
				"BanqueReactions", "service") };

		// resolve name to get a reference to our server
		BanqueReactions banqueReaction = BanqueReactionsHelper
				.narrow(nc.resolve(name));

		return banqueReaction.reactions();
	}
	
	protected void ajouterReaction(String description, int niveauGravite) throws NotFound, CannotProceed, org.omg.CosNaming.NamingContextPackage.InvalidName {
		NameComponent[] name = new NameComponent[] { new NameComponent(
				"BanqueReactions", "service") };

		// resolve name to get a reference to our server
		BanqueReactions banqueReaction = BanqueReactionsHelper
				.narrow(nc.resolve(name));

		banqueReaction.ajouterReaction(description, niveauGravite);
	}
	
	protected void ajouterReactionAuDossierSelectionne(int idReaction) {
		this.dossierCourant.ajouterReactionAListe(idReaction);
	}
	
	protected void ajouterInfractionAuDossierSelectionne(int idInfraction) {
		this.dossierCourant.ajouterInfractionAListe(idInfraction);		
	}
	
	protected CollectionInfraction infractions() throws NotFound, CannotProceed, org.omg.CosNaming.NamingContextPackage.InvalidName {
		NameComponent[] name = new NameComponent[] { new NameComponent(
				"BanqueInfractions", "service") };

		// resolve name to get a reference to our server
		BanqueInfractions banqueInfractions = BanqueInfractionsHelper
				.narrow(nc.resolve(name));

		// Ajout d'un dossier
		return banqueInfractions.infractions();
	}
	
	protected String toString(Dossier d) {
		String dString = "";
		
		dString += "Nom: \"" + d.nom() + "\"\n";
		dString += "Prenom: \"" + d.prenom() + "\"\n";
		dString += "Numero Permis: \"" + d.noPermis() + "\"\n";
		dString += "Numero Plaque: \"" + d.noPlaque() + "\"\n";
		dString += "Niveau Severite: \"" + d.niveau() + "\"";
		
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
		
		rString += "Description: \"" + r.description() + "\"\n";
		rString += "Niveau Severite: \"" + r.niveau() + "\"";
		
		return rString;
	}
	
	
	/* -------- Menus -------- */
	
	
	private static Menu buildMenus() {
		Menu mainMenu = new Menu("Menu Principal");
		mainMenu.setAction(new Menu.ActionDelegate() {
			public void doAction(Menu m) {
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
				System.out.println("Merci d'avoir utilisé notre application");
				term.exit();
			}
		});
		
		//build Menu Rechercher dossier
		subMenu = buildMenuRechercherDossier();
		mainMenu.AddSubMenu(subMenu);
		
		//build Menu Selectionner Dossier
		subMenu = buildMenuSelectionnerDossier();
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
				
				String tmp, nom, prenom;
				Scanner sc = new Scanner(System.in);
				System.out.println("Nom ?");
				while((tmp = sc.nextLine()) == "")
					System.out.println("Entrée invalide");
				nom = tmp;
				
				System.out.println("Prenom ?");
				while((tmp = sc.nextLine()) == "")
					System.out.println("Entrée invalide");
				prenom = tmp;
				
				CollectionDossier dossiers;
				try {
					dossiers = clientVoiture.trouverDossierParNom(nom, prenom);
					if(dossiers.size() > 0)
					{
						System.out.println("Voici les resultats de la recherche");
						for (int i = 0; i < dossiers.size(); ++i) {
							Dossier d = dossiers.getDossier(i);
							System.out.println("### " + d.id() + "###");
							System.out.println(clientVoiture.toString(d));
						}
						System.out.println("-- Fin de la liste --");
					}else{
						System.out.println("Aucun dossier trouve dans la Banque d'infractions");
					}
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
				
				sc.close();
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
				String tmp, numPlaque;
				Scanner sc = new Scanner(System.in);
				System.out.println("Numero de plaque ?");
				while((tmp = sc.nextLine()) == "")
					System.out.println("Entrée invalide");
				numPlaque = tmp;
							
				CollectionDossier dossiers;
				try {
					dossiers = clientVoiture.trouverDossierParNumPlaque(numPlaque);
					if(dossiers.size() > 0)
					{
						System.out.println("Voici les resultats de la recherche");
						for (int i = 0; i < dossiers.size(); ++i) {
							Dossier d = dossiers.getDossier(i);
							System.out.println("### " + d.id() + "###");
							System.out.println(clientVoiture.toString(d));
						}
						System.out.println("-- Fin de la liste --");
					}else{
						System.out.println("Aucun dossier trouve dans la Banque d'infractions");
					}
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

				
				sc.close();
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
				String tmp, numPermis;
				Scanner sc = new Scanner(System.in);
				System.out.println("Numero de permis ?");
				while((tmp = sc.nextLine()) == "")
					System.out.println("Entrée invalide");
				numPermis = tmp;
					
				
				Dossier d;
				try {
					d = clientVoiture.trouverDossierParNumPermis(numPermis);
					System.out.println("Voici le dossier trouve");
					System.out.println("### " + d.id() + "###");
					System.out.println(clientVoiture.toString(d));
					
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
				
				sc.close();
				System.out.println(m.subMenutoString());
			}
			});
		return m;
	}

	/*** Fin SubMenu Rechercher Dossier ***/
	
	private static Menu buildMenuSelectionnerDossier() {
		Menu m = new Menu("Selectionner un dossier");
		m.AddSubMenu(buildGoBackMenu());
		m.setAction(new Menu.ActionDelegate() {
			public void doAction(Menu m) {
				clearConsole();
				String tmp, numPermis;
				Scanner sc = new Scanner(System.in);
				System.out.println("Numero de permis ?");
				while((tmp = sc.nextLine()) == "")
					System.out.println("Entrée invalide");
				numPermis = tmp;		
				
				Dossier d;
				try {
					d = clientVoiture.selectionnerDossier(numPermis);
					System.out.println("### " + d.id() + "###");
					System.out.println(clientVoiture.toString(d));
					
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
				
				sc.close();
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
				clearConsole();
				CollectionReaction reactions;
				try {
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
				
				Scanner sc = new Scanner(System.in);
				System.out.println("Description ?");
				while((tmp = sc.nextLine()) == "")
					System.out.println("Entrée invalide");
				description = tmp;
				
				System.out.println("Niveau de Gravite ?");
				while(invalide)
				{
					if((tmp = sc.nextLine()) != "" )
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
						System.out.println("Entrée invalide");
				}
				niveauGravite = tmpInt;
				
				try {
					clientVoiture.ajouterReaction(description, niveauGravite);
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
				
				sc.close();
				
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
				//clearConsole();
				
				String tmp;
				int tmpInt = 0, idReaction;
				boolean invalide = true;
				
				Scanner sc = new Scanner(System.in);
				System.out.println("Identifiant de la reaction ?");
				while(invalide)
				{
					if((tmp = sc.nextLine()) != "" )
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
						System.out.println("Entrée invalide");
				}
				
				idReaction = tmpInt;
				
				clientVoiture.ajouterReactionAuDossierSelectionne(idReaction);
				
				sc.close();
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

				try {
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
//clearConsole();
				
				String tmp;
				int tmpInt = 0, idInfraction;
				boolean invalide = true;
				
				Scanner sc = new Scanner(System.in);
				System.out.println("Identifiant de la reaction ?");
				while(invalide)
				{
					if((tmp = sc.nextLine()) != "" )
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
						System.out.println("Entrée invalide");
				}
				
				idInfraction = tmpInt;
				
				clientVoiture.ajouterInfractionAuDossierSelectionne(idInfraction);
				
				sc.close();
				
			}
			});
		
		return m;
	}
	
	private static Menu buildGoBackMenu() {
		Menu goBackMenu = new Menu("Aller au menu précédent");
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
	            Runtime.getRuntime().exec("clear");
	    }
	    catch (final Exception e)
	    {
	    	for(int i=0; i<30; ++i)
	    		System.out.println("");
	    }
	}
	
}
