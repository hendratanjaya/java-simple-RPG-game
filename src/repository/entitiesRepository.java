package repository;

import java.util.ArrayList;
import java.util.Random;

import attributes.*;
import fileManagement.manageFile;
import entities.*;
public class entitiesRepository {
	private manageFile file;
	public entitiesRepository() {
		file = new manageFile();
	}
	
	public void printPlayerItem(Player p) {
		
		
			System.out.println("========================================================================================================");
			System.out.println("|ID        |Name                          |Type           |Price     |Damage    |Max Use/Mana|Use Left |");
			System.out.println("========================================================================================================");
		if(p.getItemList().isEmpty()) {
			System.out.println("|                                          No Item Found                                               |");
			System.out.println("|======================================================================================================|");
			
		}else {
			for(Item x : p.getItemList()) {
				if(x instanceof Spell) {
					System.out.printf("|%-10s|%-30s|%-15s|%-10d|%-10.2f|%-13.2f|%-8s|\n", x.getId(),x.getName(),x.getType(),x.getPrice(),((Spell)x).getDamage(),((Spell)x).getMana(),"-");
				}else if(x instanceof Offensive) {
					System.out.printf("|%-10s|%-30s|%-15s|%-10d|%-10.2f|%-13d|%-8d|\n", x.getId(),x.getName(),x.getType(),x.getPrice(),((Offensive)x).getDamage(), ((Offensive)x).getMaxUse(),((Offensive)x).getUseLeft());
				}else {
					System.out.printf("|%-10s|%-30s|%-15s|%-10d|%-10.2f|%-13d|%-8d|\n", x.getId(),x.getName(),x.getType(),x.getPrice(),((Defensive)x).getDeflect(),((Defensive)x).getMaxUse(),((Defensive)x).getUseLeft());
				}
					
			}
			
			System.out.println("|======================================================================================================|");
		}
		
	}
	
	public void viewDefensiveItem(Player p) {
		
		
			System.out.println("=======================================================================================================");
			System.out.println("|ID        |Name                          |Type           |Price     |Deflect    |Max Use      |Use Left|");
			System.out.println("=======================================================================================================");
		for(Item x : p.getItemList()) {
			if(x instanceof Defensive) {
				System.out.printf("|%-10s|%-30s|%-15s|%-10d|%-10.2f|%-13d|%-8d|\n", x.getId(),x.getName(),x.getType(),x.getPrice(),((Defensive)x).getDeflect(),((Defensive)x).getMaxUse(),((Defensive)x).getUseLeft());
			}
		}
		
			System.out.println("|=====================================================================================================|");
	}
	
	public void viewAllOffensiveItem(Player p) {
		
		ArrayList<Item> userItem = p.getItemList();
		System.out.println("=======================================================================================================");
		System.out.println("|ID        |Name                          |Type           |Price     |Damage    |Max Use      |Use Left|");
		System.out.println("=======================================================================================================");
	for(Item x : userItem) {
		if(x instanceof Offensive) {
			System.out.printf("|%-10s|%-30s|%-15s|%-10d|%-10.2f|%-13d|%-8d|\n", x.getId(),x.getName(),x.getType(),x.getPrice(),((Offensive)x).getDamage(), ((Offensive)x).getMaxUse(),((Offensive)x).getUseLeft());
		}else if(x instanceof Spell) {
			System.out.printf("|%-10s|%-30s|%-15s|%-10d|%-10.2f|%-13.2f|%-8s|\n", x.getId(),x.getName(),x.getType(),x.getPrice(),((Spell)x).getDamage(),((Spell)x).getMana(),"-");
			
		}
	}
	
		System.out.println("|=====================================================================================================|");

	}
	
	public String getRandomMonster(int type) {
		Random rand = new Random();
		int idx;
		ArrayList<String> monster;
		if(type == 0) {
			monster = file.getStrMonster();
			idx = rand.nextInt(monster.size());
			return monster.get(idx);
		}else if(type == 1) {
			monster = file.getIntMonster();
			idx = rand.nextInt(monster.size());
			return monster.get(idx);
		}else {
			monster = file.getAgiMonster();
			idx = rand.nextInt(monster.size());
			return monster.get(idx);
		}
		
	}
	
	public int countItem(ArrayList<Item> list, int itemType) {
		
		int count = 0;
		if(itemType == 0) {
			for(Item x : list) {
				if(x instanceof Defensive) {
					count++;
				}
			}
		}else {
			for(Item x : list) {
				if(x instanceof Spell || x instanceof Offensive) {
					count++;
				}
			}
		}
		
		return count;
		
	}
	public ArrayList<Player> getAllPlayer (){
		return file.getPlayer();
	}
	public Player getPlayer(String email, String pass) {
		
		ArrayList<Player> player =  file.getPlayer();
		
		for(Player x : player) {
			if(x.getEmail().equals(email) && x.getPassword().equals(pass)) {
				return x;
			}
		}
		
		return null;
	}
	
	public void addNewPlayer(Player p) {
		file.getPlayer().add(p);
	}
	
	public void saveAllPlayerData() {
		file.insertIntoFile();
	}
}
