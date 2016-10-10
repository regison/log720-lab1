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
public class ClientPoste {

	private static Terminal term;
	private static ClientPoste clientposte;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			clientposte = new ClientPoste(args);
			Menu mainMenu = buildMenus();
			term = new Terminal(mainMenu);
			term.launchTerminal();
		} catch (InvalidName e) {
			System.out.println(e);
			e.printStackTrace();
		}
		
		
	}
	
	
	/* ---- Buisness Logic ---- */
	private org.omg.CORBA.ORB orb;
	private NamingContextExt nc;
	
	public ClientPoste(String[] args) throws InvalidName{
		this.orb = org.omg.CORBA.ORB.init(args, null);
		// get hold of the naming service
		this.nc = NamingContextExtHelper.narrow(orb
				.resolve_initial_references("NameService"));
	}
	
	protected void ajouterDossier(String nom, String prenom, String numPlaque, String numPermis) throws NotFound, CannotProceed, org.omg.CosNaming.NamingContextPackage.InvalidName, NoPermisExisteDejaException {
		
		NameComponent[] name = new NameComponent[] { new NameComponent(
				"BanqueDossiers", "service") };

		// resolve name to get a reference to our server
		BanqueDossiers banqueDossier = BanqueDossiersHelper
				.narrow(nc.resolve(name));

		// Ajout d'un dossier
		banqueDossier.ajouterDossier(nom, prenom, numPlaque, numPermis);
		
	}
	
	protected void ajouterInfraction(String description, int niveauGravite) throws NotFound, CannotProceed, org.omg.CosNaming.NamingContextPackage.InvalidName, NiveauHorsBornesException {
				
		NameComponent[] name = new NameComponent[] { new NameComponent(
				"BanqueInfractions", "service") };

		// resolve name to get a reference to our server
		BanqueInfractions banqueInfraction = BanqueInfractionsHelper
				.narrow(nc.resolve(name));

		// Ajout d'un dossier
		banqueInfraction.ajouterInfraction(description,niveauGravite);
	}
	
	protected CollectionDossier dossiers() throws NotFound, CannotProceed, org.omg.CosNaming.NamingContextPackage.InvalidName {
		NameComponent[] name = new NameComponent[] { new NameComponent(
				"BanqueInfractions", "service") };

		// resolve name to get a reference to our server
		BanqueDossiers banqueDossiers = BanqueDossiersHelper
				.narrow(nc.resolve(name));

		// Ajout d'un dossier
		return banqueDossiers.dossiers();
		
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
		
		//build Menu Ajouter dossier dans la banque de dossier
		subMenu = buildMenuAjouterDossier();
		mainMenu.AddSubMenu(subMenu);
		
		//build Menu Ajouter Infraction dans la banque d'infractions
		subMenu = buildMenuAjouterInfraction();
		mainMenu.AddSubMenu(subMenu);
		
		//build Menu Lister dossier
		subMenu = buildMenuListerDossiers();
		mainMenu.AddSubMenu(subMenu);
		
		//build Menu lister infractions
		subMenu = buildMenuAjouterInfractions();
		mainMenu.AddSubMenu(subMenu);
		
		return mainMenu;
	}

	private static Menu buildMenuAjouterDossier() {
		Menu m = new Menu("Ajouter un dossier a la banque de dossier");
		m.AddSubMenu(buildGoBackMenu());
		m.setAction(new Menu.ActionDelegate() {
			public void doAction(Menu m) {
				clearConsole();
				System.out.println("Vous avez choisi d'ajouter un dossier dans la Banque de dossier.");
				
				String tmp, nom, prenom, numPlaque, numPermis;
				Scanner sc = new Scanner(System.in);
				System.out.println("Nom ?");
				while((tmp = sc.nextLine()) == "")
					System.out.println("Entrée invalide");
				nom = tmp;
				
				System.out.println("Prénom ?");
				while((tmp = sc.nextLine()) == "")
					System.out.println("Entrée invalide");
				prenom = tmp;
				
				System.out.println("Numéro de plaque ?");
				while((tmp = sc.nextLine()) == "")
					System.out.println("Entrée invalide");
				numPlaque = tmp;
				
				System.out.println("Numéro de permis ?");
				while((tmp = sc.nextLine()) == "")
					System.out.println("Entrée invalide");
				numPermis = tmp;
				
				sc.close();
				
				try {
					clientposte.ajouterDossier(nom, prenom, numPlaque, numPermis);
				} catch (NotFound e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (CannotProceed e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (org.omg.CosNaming.NamingContextPackage.InvalidName e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoPermisExisteDejaException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				System.out.println("Votre dossier à ete ajouté");
				System.out.println(m.subMenutoString());
			}
			});
		
		return m;
	}

	private static Menu buildMenuAjouterInfractions() {
		Menu m = new Menu("Ajouter un dossier a la banque de dossier");
		m.AddSubMenu(buildGoBackMenu());
		m.setAction(new Menu.ActionDelegate() {
			public void doAction(Menu m) {
				clearConsole();
				System.out.println("Vous avez choisi d'ajouter une infraction dans la Banque d'infractions.");
				
				String tmp, description;
				int tmpInt = 0, niveauGravite;
				boolean invalide = true;
				Scanner sc = new Scanner(System.in);
				System.out.println("Description ?");
				while((tmp = sc.nextLine()) == "")
					System.out.println("Entrée invalide");
				description = tmp;
				
				System.out.println("Prénom ?");
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
				
				sc.close();
				
				try {
					clientposte.ajouterInfraction(description, niveauGravite);
				} catch (NotFound e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (CannotProceed e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (org.omg.CosNaming.NamingContextPackage.InvalidName e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NiveauHorsBornesException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Votre infraction à ete ajouté");
				System.out.println(m.subMenutoString());
			}
			});
		
		return m;
	}

	private static Menu buildMenuListerDossiers() {
		Menu m = new Menu("Lister les dossiers de la banque de dossier");
		m.AddSubMenu(buildGoBackMenu());
		m.setAction(new Menu.ActionDelegate() {
			public void doAction(Menu m) {
				clearConsole();
				
				try {
					CollectionDossier dossiers = clientposte.dossiers();
					if(dossiers.size() > 0)
					{
						System.out.println("Voici la liste des dossiers:");
						for (int i = 0; i < dossiers.size(); ++i) {
							Dossier d = dossiers.getDossier(i);
							System.out.println("### " + d.id() + "###");
							System.out.println(clientposte.toString(d));
						}
						System.out.println("--Fin de la liste --");
					}else{
						System.out.println("Aucun dossiers n'existe dans la Banque de dossier");
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

	private static Menu buildMenuAjouterInfraction() {
		Menu m = new Menu("Lister les onfractions de la banque d'infractions");
		m.AddSubMenu(buildGoBackMenu());
		m.setAction(new Menu.ActionDelegate() {
			public void doAction(Menu m) {
				clearConsole();

				try {
					CollectionInfraction infractions = clientposte.infractions();
					if(infractions.size() > 0)
					{
						System.out.println("Voici la liste des dossiers:");
						for (int i = 0; i < infractions.size(); ++i) {
							Infraction infra = infractions.getInfraction(i);
							System.out.println("### " + infra.id() + "###");
							System.out.println(clientposte.toString(infra));
						}
						System.out.println("--Fin de la liste --");
					}else{
						System.out.println("Aucun dossiers n'existe dans la Banque de dossier");
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
