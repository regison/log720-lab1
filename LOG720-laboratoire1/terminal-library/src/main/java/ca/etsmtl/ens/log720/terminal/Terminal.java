/**
 * 
 */
package ca.etsmtl.ens.log720.terminal;

import java.util.InputMismatchException;
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
		while(!exit_term){
			
			int menuIndex = -1;
			try{
				menuIndex = inScanner.nextInt();
				if(menuIndex < currentMenu.getSubMenus().size()){
					m = currentMenu.getSubMenus().get(menuIndex);
					this.navigateTo(m);
				}
				else
				{
					System.out.println("This menu does not exist");
				}
			}catch(InputMismatchException imex){
				inScanner.nextLine();
				System.out.println("This menu does not exist");
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
