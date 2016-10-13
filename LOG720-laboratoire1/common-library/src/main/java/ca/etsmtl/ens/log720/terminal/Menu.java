package ca.etsmtl.ens.log720.terminal;


import java.util.ArrayList;
import java.util.Iterator;

/**
 * 
 */

/**
 * @author charly
 *
 */
public class Menu implements Iterable<Menu>{

	public interface ActionDelegate{
		public void doAction(Menu m);	
	}
	
	private String name;
	private Menu parentMenu;

	private ActionDelegate action;
	private ArrayList<Menu> subMenus;
	/**
	 * 
	 */
	public Menu(String name) {
		this.name = name;
		this.subMenus = new ArrayList<Menu>();
	}
	public String getName() {
		return name;
	}
	public ArrayList<Menu> getSubMenus() {
		return subMenus;
	}
	
	public Iterator<Menu> iterator() {
		return getSubMenus().iterator();
	}

	public void AddSubMenu(Menu menu){
		if(!this.subMenus.contains(menu))
			this.subMenus.add(menu);	
		menu.parentMenu = this;
	}
	
	public Menu getParentMenu() {
		return parentMenu;
	}
	
	@Override
	public String toString() {
		return name;
	}
	public String subMenutoString() {
		String output = "";
		for(int i = 0; i < subMenus.size(); ++i){
			output += "" + i + ". " + subMenus.get(i) + "\n";
		}
		
		return output;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(ActionDelegate action) {
		this.action = action;
	}
	
	public void doAction(){
		if(this.action != null)
		{
			this.action.doAction(this);
		}
	}
	public void RemoveSubMenu(Menu subMenu) {
		if(this.subMenus.contains(subMenu))	
		{
			this.subMenus.remove(subMenu);
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Menu))
			return false;
		
		Menu oMenu = (Menu)obj;
		
		return this.name.equalsIgnoreCase(oMenu.name);
	}
	
}
