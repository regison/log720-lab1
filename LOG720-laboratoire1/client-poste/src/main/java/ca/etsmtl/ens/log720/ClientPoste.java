/**
 * 
 */
package ca.etsmtl.ens.log720;

import ca.etsmtl.ens.log720.terminal.*;

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
		
		clientposte = new ClientPoste();
		
		Menu mainMenu = buildMenus();
		term = new Terminal(mainMenu);
		term.launchTerminal();
	}
	
	/* ---- Buisness Logic ---- */
	
	
	
	
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
				//TODO Creer un nouveau dossier
				//TODO ajouter dossier dans la banque
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
				//TODO Creer une nouvelle infraction
				//TODO ajouter infraction dans la liste d'infraction
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
				System.out.println("Voici la liste des dossiers:");
				//TODO Afficher la liste des dossier
				System.out.println("--Fin de la liste --");
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
				System.out.println("Voici la liste des infractions:");
				//TODO Afficher la liste des infractions
				System.out.println("--Fin de la liste --");
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
