package gameplay;
import repository.entitiesRepository;
import repository.itemRepository;

import java.util.Random;
import java.util.Scanner;

import attributes.*;
import entities.*;
public class Fight {
	private Random rand = new Random();
	
	private Scanner sc = new Scanner(System.in);

	private entitiesRepository entData = new entitiesRepository();
	private itemRepository itemData = new itemRepository();
	
	public Fight(Player p) {
		int type = rand.nextInt(3);
		Monster m = initiateMonster(type);
		System.out.println("\n[FIGHT]");
		System.out.println("WELCOME TO THE FIGHT SCENE");
		
		System.out.println("You are fighting " + m.getName());
		
		getEnter();
		start(p,m,type);
	}
	public void start(Player p, Monster m,int type) {
		
		Object[] obj = new Object[2];
		
		int first = rand.nextInt(10) %2;
		obj[0] = first == 0? p : m;
		obj[1]  = first == 0? m : p;
		fightScenario(obj,type);
	}
	
	private Monster initiateMonster(int type) {
		int damage =0;
		int health = 0;
		String name = entData.getRandomMonster(type);
		Monster monster;
		if(type == 0) {
			damage = rand.nextInt(11) + 20; 
			health = rand.nextInt(11) + 200;
			int armor = rand.nextInt(21) + 20;
			
			monster = new STRMonster(name,damage,health,armor,"Strength" );
			return monster;
		}else if(type == 1) {
			
			damage = rand.nextInt(11) + 10;
			health = rand.nextInt(11) + 100;
			
			monster = new INTMonster(name,damage,health,"Intelligence");
			return monster;
			
		}else {
			
			damage = rand.nextInt(11) + 40;
			health = rand.nextInt(11) + 100;
			int critical = rand.nextInt(3) +1;
			monster = new AGIMonster(name,damage,health,critical, "Agility");
			return monster;
		}
		
		
	}
	
	private void fightScenario(Object[] obj, int type) {
		Monster m ;
		Player p;
		
		
			
		if(obj[0] instanceof Player) {
			p = (Player) obj[0];
			if(type == 0) 
				m = (STRMonster)obj[1];
				
			else if(type == 1) 
				m = (INTMonster)obj[1];
			else
				m = (AGIMonster)obj[1];
			
			while(p.getHealth() >0 && m.getHealth() >0) {
				playerAttack(m,p);
				if(m.getHealth() == 0) break;
				monsterAttack(m,p);
				if(p.getHealth() == 0) break;
			}
			
		}else {
			p = (Player) obj[1];
			
			if(type == 0) 
				m = (STRMonster)obj[0];
			else if(type == 1) 
				m = (INTMonster)obj[0];
			else
				m = (AGIMonster)obj[0];
			
			while(p.getHealth() >0 && m.getHealth() >0) {
				
				monsterAttack(m,p);
				if(p.getHealth() == 0) break;
				playerAttack(m,p);
				if(m.getHealth() == 0) break;
			}
			
		}
		
		if(p.getHealth() == 0) {
			System.out.println("\n[YOU DEAD]");
			System.out.println("You Dead, resetting character's information..");
			p.reset(p);
			System.out.println();
			getEnter();
		}else if(m.getHealth() == 0) {
			
			System.out.printf("%s has been killed\n",m.getName());
			System.out.println("You gain +100 money\n");
			p.setMoney(p.getMoney()+100);
			getEnter();
		}
		
	}
	private boolean cont = true;
	private void playerAttack(Monster m, Player p) {
		
		do {
			System.out.println("\n[PLAYER TURN]\n");
			printInformation(m,p);
			System.out.println();
			System.out.println("Your choice:\n");
			System.out.println("1. Pure Attack");
			System.out.println("2. Attack With Item");
			System.out.println("3. Store Mana");
			String in = "";
			do {
				System.out.print(">> ");
				in = sc.nextLine();
			}while(validateInput(in) == false);
			
			switch(in) {
			case "1" : pureAttack(p,m); break;
			case "2" : useItem(p,m); break;
			case "3" : storeMana(p); break;
			}
		}while(cont == true);
		
		cont = true;
		printInformation(m,p);
		getEnter();
		
	}
	
	private void pureAttack(Player p, Monster m) {
		
		System.out.println("\nAttacking " + m.getName());
		
		System.out.printf("%s got damage %.2f\n\n",m.getName(),p.getDamage());
//		if(m instanceof STRMonster) {
//			System.out.printf("But attack got deflected by %.2lf because of armor", ((STRMonster)m).getArmor());
//		}
		
		double damage = p.attacking();
		m.attacked(damage);
		cont = false;
	}
	
	private void useItem(Player p, Monster m) {
		
		int count = entData.countItem(p.getItemList(), 1);
		if(count == 0) {
			System.out.println("You dont have any item offensive currently\n\n");
			cont = true;
			getEnter();
			return;
		}
	
		String id;
		Item found;
		do {
			System.out.println("ITEM INFORMATION");
			entData.viewAllOffensiveItem(p);
			System.out.println("Choose Item");
			System.out.print("Input Item's ID: ");
			id = sc.nextLine();
			found = itemData.getItemById(id, p.getItemList());

		}while(found == null || found instanceof Defensive);
		if(found instanceof Spell ) {
			if(Double.compare(((Spell)found).getMana(), p.getMana()) == 1) {
				System.out.println("You dont have enough mana");
				cont = true;
				getEnter();
				return;
			}
		}
		p.setActiveItem(found);
		if(m instanceof STRMonster)
			((STRMonster)m).setActiveItem(true);
		double damage = p.attacking();
		m.attacked(damage);
		playerAttackMessage(damage,p,m);
		p.setActiveItem(null);
		cont = false;
			
				
	}
	
	private void storeMana(Player p) {
		System.out.println("\nStoring mana...");
		System.out.println("Added 10.00 mana");
		p.setMana(p.getMana() + 10);
		cont = false;
		getEnter();
	}
	
	private void monsterAttack(Monster m, Player p) {
		System.out.println("[MONSTER TURN]\n");
		
		System.out.println("Monster is going to attack");
		int defItem = entData.countItem(p.getItemList(), 0);
		
		if(defItem != 0) {
			System.out.println("Do you want to use your defensive item?");
			
			String valid;
			do {
				System.out.print("Yes | No (Case Insensitive): ");
				valid = sc.nextLine();
						
			}while(!valid.equalsIgnoreCase("yes") && !valid.equalsIgnoreCase("no"));
			
			
			if(valid.equalsIgnoreCase("yes")) {
				
				useDefensiveItem(p,m);
				
			}else {
				double damage = m.attacking();
				p.attacked(damage);
				monsterAttackMessage(damage,m,p);
				
			}
		}else {
			
			double damage = m.attacking();
			p.attacked(damage);
			monsterAttackMessage(damage,m,p);
			
		}
		
		printInformation(m,p);
		getEnter();
		
	}
	private void useDefensiveItem(Player p, Monster m) {
		
		entData.viewDefensiveItem(p);
		
		System.out.print("Input Item's ID : ");
		String id = sc.nextLine();
		Item found = itemData.getItemById(id, p.getItemList());
		double damage = m.attacking();
		
		if(found == null || !(found instanceof Defensive)) {
			
			p.setActiveItem(null);
			p.attacked(damage);
			monsterAttackMessage(damage, m,p);
			
		}else {
			Defensive item = (Defensive) found;
			if(item.getMaxUse() == item.getItemUsed()) {
				
				p.setActiveItem(null);
			}else
				p.setActiveItem(found);
			
			monsterAttackMessage(damage, m,p);
			p.attacked(m.attacking());
			
		}
		
		
	}
	
	private void monsterAttackMessage(double bonus, Monster m, Player p) {
		System.out.println();
		if(m instanceof INTMonster) {
			System.out.printf("%s is attacking with base damage of %.2f\n", m.getName(), m.getDamage());
			System.out.printf("%s is an Intelligence Monster, using skill gave bonus damage %.2f\n\n",m.getName(),bonus-m.getDamage());
			if(p.getActiveItem() != null) {
				System.out.printf("%s is equipped\n", p.getActiveItem().getName());
				System.out.printf("%s was used. There is %d times left to use this item\n\n",p.getActiveItem().getName(),((Defensive)p.getActiveItem()).getUseLeft()-1);
				System.out.printf("Receive Damage %.2f, but deflected %.2f using %s\n\n" ,bonus,((Defensive)p.getActiveItem()).getDeflect(),p.getActiveItem().getName());
			}else {
				System.out.printf("Receive Damage %.2f\n\n", bonus);
			}
			
		}else if(m instanceof STRMonster) {
			System.out.printf("%s is attacking with base damage of %.2f\n", m.getName(), m.getDamage());
			if(p.getActiveItem() != null) {
				System.out.printf("%s is equipped\n", p.getActiveItem().getName());
				System.out.printf("%s was used. There is %d times left to use this item\n\n",p.getActiveItem().getName(),((Defensive)p.getActiveItem()).getUseLeft()-1);
				System.out.printf("Receive Damage %.2f, but deflected %.2f using %s\n\n" ,m.getDamage(),((Defensive)p.getActiveItem()).getDeflect(),p.getActiveItem().getName());
			}else {
				System.out.printf("Receive Damage %.2f\n\n",m.getDamage());
			}
		}else if(m instanceof AGIMonster) {
			System.out.printf("%s is attacking with a critical damage of %.2f\n", m.getName(), bonus);
			if(p.getActiveItem() != null) {
				System.out.printf("%s is equipped\n", p.getActiveItem().getName());
				System.out.printf("%s was used. There is %d times left to use this item\n\n",p.getActiveItem().getName(),((Defensive)p.getActiveItem()).getUseLeft()-1);
				System.out.printf("Receive Damage %.2f, but deflected %.2f using %s\n\n" ,bonus,((Defensive)p.getActiveItem()).getDeflect(),p.getActiveItem().getName());
			}else {
				System.out.printf("Receive Damage %.2f\n\n", bonus);
			}
		}
		
		getEnter();
		
	}
	
	private void playerAttackMessage(double damage, Player p, Monster m) {
		System.out.println();
		if(p.getActiveItem() != null) {
			
			System.out.printf("Attacking %s with %s\n", m.getName(),p.getActiveItem().getName());
			if(p.getActiveItem() instanceof Offensive)
				System.out.printf("%s was used. There is %d times left to use this item\n\n", p.getActiveItem().getName(), ((Offensive)p.getActiveItem()).getUseLeft());
			else if(p.getActiveItem() instanceof Spell) 
				System.out.printf("Used %.2f of mana\n\n", ((Spell)p.getActiveItem()).getMana());
			
			System.out.printf("%s attacked with %.2f of damage\n", m.getName(),damage);
			
			if(m instanceof STRMonster) 
				System.out.printf("But attack got deflected by %.2f because of armor\n",((STRMonster)m).getArmor());
			
			System.out.println("Updated Monster Information");
			System.out.println("MONSTER INFORMATION: ");
			System.out.println("Name      : " + m.getName());
			System.out.printf ("Health    : %.2f\n" , m.getHealth());
			System.out.printf ("Damage    : %.2f\n" , m.getDamage());
			if(m instanceof STRMonster) {
				System.out.printf("Armor     : %.2f\n\n",((STRMonster)m).getArmor());
			}
			
			
		}else {
			System.out.printf("Attacking %s\n", m.getName());
			System.out.printf("%s got damage %.2f\n\n", m.getName(),damage);
		}
			
		getEnter();
	}
	
	private void printInformation(Monster m,Player p) {
		
		System.out.println("[CURRENT INFORMATION]");
		
		System.out.println("MONSTER INFORMATION");
		System.out.printf("Name        : %s\n" , m.getName());
		System.out.printf("Health      : %.2f\n" , m.getHealth());
		System.out.printf("Damage      : %.2f\n" , m.getDamage());
		if(m instanceof STRMonster) {
			System.out.printf("Armor       : %.2f\n" , ((STRMonster)m).getArmor());
			
		}
		System.out.println();
		
		System.out.println("PLAYER INFORMATION: " + p.getEmail());
		System.out.printf("Health      : %.2f\n" , p.getHealth());
		System.out.printf("Money       : %d\n" , p.getMoney());
		System.out.printf("Mana        : %.2f\n" , p.getMana());
		System.out.printf("Base Damage : %.2f\n" , p.getDamage());
		
		if(!p.getItemList().isEmpty()) {
			entData.printPlayerItem(p);
		}
	}
	
	private boolean validateInput(String in) {
		if(in.equals("1") || in.equals("2") || in.equals("3")) {
			return true;
		}
		
		return false;
	}
	private void getEnter() {
		System.out.print("\nEnter to continue..");
		
		
		sc.nextLine();
		System.out.println();
		
	}
	
	

}
