/**
 * 
 */
package ca.etsmtl.ens.log720.terminal;

import java.util.Scanner;

/**
 * @author charly
 *
 */
public class Terminal {
	private Menu mainMenu;
	private Menu currentMenu;
	private boolean exit_term = false;
	private static Scanner inScanner;
	

	/**
	 * 
	 */
	public Terminal(Menu mainMenu) {
		this.mainMenu = mainMenu;
		this.currentMenu = mainMenu;
		inScanner = new Scanner(System.in);
	}
	
	public void launchTerminal(){
		Menu m;		
		this.navigateTo(mainMenu);
		
		String tmp;
		boolean invalide;
		int menuIndex = -1;
		while(!exit_term){
			tmp = "";
			invalide = true;
			menuIndex = -1;
			
			while(invalide)
			{
				if((tmp = inScanner.nextLine()) != "\n" )
				{
					try
					{
						menuIndex = Integer.parseInt(tmp);
						invalide = false;
						if(menuIndex < currentMenu.getSubMenus().size()){
							m = currentMenu.getSubMenus().get(menuIndex);
							this.navigateTo(m);
						}
						else
						{
							System.out.println("This menu does not exist");
						}
						
					}
					catch(NumberFormatException nfEx){
						invalide = true;
						System.out.println("This menu does not exist");
					}
				}
				else
					System.out.println("Entrée invalide");
			}		
			
		}
		
	}

	public void exit() {
		this.exit_term = true;
		inScanner.close();
	}

	public void navigateTo(Menu m){
		this.currentMenu = m;
		this.currentMenu.doAction();
	}
	
	public static Scanner getInScanner() {
		return inScanner;
	}
}
