package repository;
import java.util.ArrayList;


import attributes.*;

import fileManagement.*;

public class itemRepository {
	private manageFile data;
	
	
	public itemRepository() {
		
		data = new manageFile();
	}
	
	
	public ArrayList<Item> getItem(){
		return data.getItem();
	}
	
	public void viewDefensiveItem() {
		
			ArrayList<Item> items = data.getItem();
			System.out.println("=======================================================================================================");
			System.out.println("|ID        |Name                          |Type           |Price     |Deflect    |Max Use      |Use Left|");
			System.out.println("=======================================================================================================");
			for(Item x : items) {
			if(x instanceof Defensive) {
				System.out.printf("|%-10s|%-30s|%-15s|%-10d|%-10.2f|%-13d|%-8d|\n", x.getId(),x.getName(),x.getType(),x.getPrice(),((Defensive)x).getDeflect(),((Defensive)x).getMaxUse(),((Defensive)x).getUseLeft());
			}
		}
		
			System.out.println("|=====================================================================================================|");
	}
	
	public void viewOffensiveItem() {
		
		ArrayList<Item> items = data.getItem();
		System.out.println("=======================================================================================================");
		System.out.println("|ID        |Name                          |Type           |Price     |Damage    |Max Use      |Use Left|");
		System.out.println("=======================================================================================================");
		for(Item x : items) {
			if(x instanceof Offensive) {
				System.out.printf("|%-10s|%-30s|%-15s|%-10d|%-10.2f|%-13d|%-8d|\n", x.getId(),x.getName(),x.getType(),x.getPrice(),((Offensive)x).getDamage(), ((Offensive)x).getMaxUse(),((Offensive)x).getUseLeft());
			}
		}
	
		System.out.println("|=====================================================================================================|");

	}
	
	public void viewSpellItem() {
		ArrayList<Item> items = data.getItem();
		System.out.println("===============================================================================================");
		System.out.println("|ID        |Name                          |Type           |Price     |Damage    |Mana         |");
		System.out.println("===============================================================================================");
		for(Item x : items) {
			if(x instanceof Spell) {
				System.out.printf("|%-10s|%-30s|%-15s|%-10d|%-10.2f|%-13.2f|\n", x.getId(),x.getName(),x.getType(),x.getPrice(),((Spell)x).getDamage(),((Spell)x).getMana());
			}
		}
		System.out.println("===============================================================================================");
	
	}
	
	public Item getItemById(String id, ArrayList<Item> itemList) {
		for(Item x : itemList) {
			if(x.getId().equals(id)) {
				return x;
			}
		}
		
		return null;
	}
}
//=======================================================================================================
//|ID        |Name                          |Type           |Price     |Damage    |Max Use/Mana|Use Left|
//=======================================================================================================
//|WRHBND    |Wraith Band                   |Offensive      |5         |3.00      |9         |9         |
//|SCRC      |Sacred Relic                  |Defensive      |18        |15.00     |4         |4         |
//|WCTHBLD   |Witch Blade                   |Spells         |7         |5.00      |2.00      |-         |
//=======================================================================================================



	