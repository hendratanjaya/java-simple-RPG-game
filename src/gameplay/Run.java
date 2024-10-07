package gameplay;
import entities.*;

import repository.entitiesRepository;
import repository.itemRepository;

import attributes.*;


import java.util.Random;
import java.util.Scanner;
public class Run {
	private Map myMap  = new Map();
	private String[] Map = myMap.makeMap();
	private Scanner sc = new Scanner(System.in);
	private itemRepository itemData = new itemRepository();
	private entitiesRepository entData = new entitiesRepository();
	private boolean exit = false;
	private boolean idle = false;
	//private Fight fight;
	public Run(Player p) {
		//ArrayList<Player> p = entData.getAllPlayer();
		
		
		run(p);
	}
	
	private void run(Player p) {
		String[] map = generateMap(p);
		printMap(map,p);
		while(exit == false) {
			
			inputMove(p);
			if(exit == true) return;
			if(idle == true) printMap(map,p);
			map = generateMap(p);
			checkObstacle(p,map);
			idle = false;
			
		}
		
		exit = false;
		
	}
	
	private String[] generateMap(Player p) {
		String[] displayMap = myMap.displayMap(Map, p);
		return displayMap;
	}
	private void printMap(String[] map, Player p) {
		for(String x : map) {
			System.out.println(x);
		}
		
		System.out.println();
		System.out.println("Player Information: ");
		System.out.printf("Health      : %.2f\n" ,(double) p.getHealth());
		System.out.printf("Money       : %d\n" , p.getMoney());
		System.out.printf("Mana        : %.2f\n" ,(double) p.getMana());
		System.out.printf("Base Damage : %.2f\n" ,(double) p.getDamage());
	}
	private void inputMove(Player p) {
		String in = "";
		do {
			System.out.print(">> ");
			in = sc.nextLine();
		}while(!inputValid(in));
		
		switch(in.toLowerCase()) {
		case "w" : p.move(0, -1, Map); break;
		case "s" : p.move(0, 1, Map); break;
		case "a" : p.move(-1, 0, Map); break;
		case "d" : p.move(1, 0, Map); break;
		case "i" : idle = true; openInventory(p); break;
		case "z" : idle = true; shop(p); break;
		case "e" : idle = true; saveAndExit(p); break;
		}
	}
	
	private void openInventory(Player p) {
		entitiesRepository entData = new entitiesRepository();
		entData.printPlayerItem(p);
		
		System.out.println("\nEnter To Continue..");
		sc.nextLine();
		System.out.println();
		
		
	}
	
	private void shop(Player p) {
		
		
		do {
			System.out.println("\n[WELCOME TO SHOP]\n");
			System.out.println("1. Buy Offensive Item");
			System.out.println("2. Buy Defensive Item");
			System.out.println("3. Buy Spell");
			System.out.println("4. Exit");
			String in = "";
			do {
				System.out.print(">> ");
				in = sc.nextLine();
			}while(validateInput(in)==false);
			
			switch(in) {
			case "1" : buyOffensiveItem(p);break;
			case "2" : buyDefensiveItem(p);break;
			case "3" : buySpellItem(p); break;
			case "4" : return;
			}
		}while(true);
		
	}
	
	private void buyOffensiveItem(Player p) {
		itemData.viewOffensiveItem();
		Item found;
		
		System.out.print("Input item's ID ['Exit to cancel']: ");
		String id = sc.nextLine();
		if(id.equalsIgnoreCase("exit")) return;
		found = itemData.getItemById(id, p.getItemList());
			
		if(found != null && found instanceof Offensive) {
			System.out.println("You already have this item and it is Offensive type, resetting item's use amount\n");
			((Offensive)found).setItemUsed(0);
			((Offensive)found).setUseLeft();
			System.out.print("Enter To Continue");
			sc.nextLine();
			System.out.println();
			
		}else {
			
			found = itemData.getItemById(id, itemData.getItem());
			if(found == null || !(found instanceof Offensive)) {
				System.out.println("Item Not found\n");
				
				System.out.print("Enter To Continue..");
				sc.nextLine();
				System.out.println();
				return;
			}
			Offensive temp = (Offensive)found;
			if(p.getMoney() < temp.getPrice()) {
				System.out.println("You dont have enough money\n");
				System.out.print("Enter To Continue..");
				sc.nextLine();
				System.out.println();
				return;
			}
			
			System.out.printf("%s Purchased\n\n",temp.getName());
			
			p.setMoney(p.getMoney()-temp.getPrice());
			p.getItemList().add(new Offensive(temp));
			System.out.print("Enter To Continue");
			sc.nextLine();
			System.out.println();
		}
		
		
	}
	
	private void buyDefensiveItem(Player p) {
		itemData.viewDefensiveItem();
		Item found;
		
		System.out.print("Input item's ID ['Exit to cancel']: ");
		String id = sc.nextLine();
		if(id.equalsIgnoreCase("exit")) return;
		found = itemData.getItemById(id, p.getItemList());
			
		if(found != null && found instanceof Defensive) {
			System.out.println("You already have this item and it is Defensive type, resetting item's use amount\n");
			((Defensive)found).setItemUsed(0);
			((Defensive)found).setUseLeft();
			System.out.print("Enter To Continue");
			sc.nextLine();
			System.out.println();
			
		}else {
			
			found = itemData.getItemById(id, itemData.getItem());
			if(found == null || !(found instanceof Defensive)) {
				System.out.println("Item Not found\n");
				
				System.out.print("Enter To Continue..");
				sc.nextLine();
				System.out.println();
				return;
			}
			Defensive temp = (Defensive)found;
			if(p.getMoney() < temp.getPrice()) {
				System.out.println("You dont have enough money\n");
				System.out.print("Enter To Continue..");
				sc.nextLine();
				System.out.println();
				return;
			}
			System.out.printf("%s Purchased\n\n",temp.getName());
			
			p.setMoney(p.getMoney()-temp.getPrice());
			p.getItemList().add(new Defensive(temp));
			System.out.print("Enter To Continue");
			sc.nextLine();
			System.out.println();
		}
	}
	
	private void buySpellItem(Player p) {
		itemData.viewSpellItem();
		Item found;
		
		System.out.print("Input item's ID ['Exit to cancel']: ");
		String id = sc.nextLine();
		if(id.equalsIgnoreCase("exit")) return;
		found = itemData.getItemById(id, p.getItemList());
			
		if(found != null && found instanceof Spell) {
			System.out.println("You already have this item and it is Spell type\n");
			
			System.out.print("Enter To Continue");
			sc.nextLine();
			System.out.println();
			
		}else {
			
			found = itemData.getItemById(id, itemData.getItem());
			if(found == null || !(found instanceof Spell)) {
				System.out.println("Item Not found\n");
				
				System.out.print("Enter To Continue..");
				sc.nextLine();
				System.out.println();
				return;
			}
			Spell temp = (Spell)found;
			if(p.getMoney() < temp.getPrice()) {
				System.out.println("You dont have enough money\n");
				System.out.print("Enter To Continue..");
				sc.nextLine();
				System.out.println();
				return;
			}
			System.out.printf("%s Purchased\n\n",temp.getName());
			
			p.setMoney(p.getMoney()-temp.getPrice());
			p.getItemList().add(new Spell(temp));
			System.out.print("Enter To Continue");
			sc.nextLine();
			System.out.println();
		
		}
	}
	
	private void saveAndExit(Player p) {
		System.out.println("Saving Your Progress..");
		System.out.println("Enter To Continue..");
		sc.nextLine();
		System.out.println("\n\n");
		Player x = entData.getPlayer(p.getEmail(), p.getPassword());
		x.update(p);
		entData.saveAllPlayerData();
		exit = true;
	}
	
	private boolean inputValid(String in) {
		if(in.equalsIgnoreCase("w") ||in.equalsIgnoreCase("a") || 
		   in.equalsIgnoreCase("s") ||in.equalsIgnoreCase("d") ||
		   in.equalsIgnoreCase("i") ||in.equalsIgnoreCase("z") ||
		   in.equalsIgnoreCase("e")){
			return true;
		}else {
			return false;
		}
	}
	
	private boolean validateInput(String in) {
		if(in.equals("1") || in.equals("2") || in.equals("3") || in.equals("4")) {
			return true;
		}
		
		return false;
	}
	
	private void checkObstacle(Player p, String[] map) {
		if(exit == true || idle == true) return;
		int y = p.getYpos();
		int x = p.getXpos();
		
		char [] line = Map[y].toCharArray();
		if(line[x] == '0') {
			line[x] = ' ';
			int money = p.getMoney() + 50;
			p.setMoney(money);
			Map[y] = new String(line);
			printMap(map,p);
		}else if(line[x] == 'v' || line[x] == 'V') {
			printMap(map,p);
			Random rand = new Random();
			int chance = rand.nextInt(100);
			if(chance < 30) {
				
				new Fight(p);
				
				printMap(map,p);
			}
		}else printMap(map,p);
	}
}
