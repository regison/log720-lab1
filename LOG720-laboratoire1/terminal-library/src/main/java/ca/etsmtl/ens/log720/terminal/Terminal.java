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
	
	/**
	 * 
	 */
	public Terminal(Menu mainMenu) {
		this.mainMenu = mainMenu;
		this.currentMenu = mainMenu;
	}
	
	public void launchTerminal(){
		Menu m;		
		this.navigateTo(mainMenu);
		Scanner sc = new Scanner(System.in);
		while(!exit_term){
			
			int menuIndex = -1;
			try{
				menuIndex = sc.nextInt();
				if(menuIndex < currentMenu.getSubMenus().size()){
					m = currentMenu.getSubMenus().get(menuIndex);
					this.navigateTo(m);
				}
				else
				{
					System.out.println("This menu does not exist");
				}
			}catch(InputMismatchException imex){
				sc.nextLine();
				System.out.println("This menu does not exist");

			}
			
			
		}
		sc.close();
	}

	public void exit() {
		this.exit_term = true;
	}
	
	public void navigateTo(Menu m){
		this.currentMenu = m;
		this.currentMenu.doAction();
	}
}
