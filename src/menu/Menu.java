package menu;


import java.util.Scanner;
import entities.Player;
import gameplay.Run;
import repository.entitiesRepository;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
public class Menu {
	
	private Scanner sc = new Scanner(System.in);
	private entitiesRepository entData = new entitiesRepository();
	public Menu() {
		//ArrayList<Player> player = entData.getPlayerList();
		System.out.println("[W E L C O M E   T O   D O T W]\n\n");
		System.out.print("Enter To Continue..");sc.nextLine();
		System.out.println("\n\n");
		
		while(true) {
			logInMenu();
		}
	}
	
	public void logInMenu() {
		System.out.println("[LOGIN MENU]\n");
		System.out.println("1. Log in");
		System.out.println("2. Register");
		System.out.println("3. Exit");
		String in;
		
		do {
			System.out.print(">> ");
			in = sc.nextLine();
		}while(validateInput(in) == false);
		
		switch(in) {
		case "1": logIn();break;
		case "2": register();break;
		case "3": System.exit(0);
		}
	}
	
	private void logIn() {
		Player found = null;
		String email,pass;
		do {
			System.err.println();
			System.out.println("Input 'Exit' to quit from Login Menu\n");
			System.out.print("Input Email: ");
			email = sc.nextLine();
			if(email.equalsIgnoreCase("exit")) return;
			System.out.print("Input Password: ");
			pass = sc.nextLine();
			if(pass.equalsIgnoreCase("exit")) return;
			
			found = entData.getPlayer(email, pass);
			if(found == null) {
				System.out.println("Incorrect credential\n");
				System.out.print("Enter To Continue.."); sc.nextLine();
			}
		}while(found == null);
		System.out.println("Loggin in..\n");
		System.out.print("Enter To Continue..");sc.nextLine();
		System.out.println("\n");
		gamePage(found);
		
	}
	
	private void register() {
		String email, pass;
		do {
			System.out.println("\n[REGISTER MENU]\n");
			System.out.print("Input new Email: ");
			email = sc.nextLine();
			if(email.equalsIgnoreCase("exit")) return;
			System.out.print("Input new Password: ");
			pass = sc.nextLine();
			if(pass.equalsIgnoreCase("exit")) return;
			
			if(validateEmail(email) != 0) {
				errorMessage(validateEmail(email));
				System.out.println();
				System.out.print("Enter to continue..");sc.nextLine();
			}else if(validatePassword(pass) != true) {
				System.out.println("Password length must be between 8 and 30");
				System.out.println();
				System.out.print("Enter to continue..");sc.nextLine();
			}
			
			
		}while(validateEmail(email)!= 0 || validatePassword(pass) != true);
		System.out.println("\nRegister Success");
		Player newPlayer = new Player(email,pass);
		entData.addNewPlayer(newPlayer);
		entData.saveAllPlayerData();
		System.out.print("Enter To Continue..");
		sc.hasNextLine();
		System.out.println("\n");
	}
	
	private int validateEmail(String email) {
		
		//String regex = "^[A-Za-z0-9]+@[A-Za-z]+.[A-Za-z]+$";
		
		//Matcher match = Pattern.compile(regex).matcher(email);
		if(email.isBlank()) return 4;
		char[] mail = email.toCharArray();
		int dotPos = 0;
		int attPos = 0;
		int count = 0;
		if(mail[0] == '@' || mail[mail.length-1] == '@') return 1;
		for(int i = 0; i < mail.length;i++ ) {
			if(mail[i] == '.') dotPos = i;
			if(mail[i] == '@') {
				attPos = i;
				count++;
				if(count > 1) return 2;
			}
			if(mail[i] == '@' && mail[i+1] == '.') return 3;
		}
		if(count == 0) return 4;
		if(dotPos < attPos) return 5;
		if(mail[mail.length-1] == '.') return 6;
		
		
		
		
		return 0;
	}
	
	private void errorMessage(int code) {
		
		if(code == 1) {
			System.out.println("Email should not start or ended with '@'");
		}else if(code == 2) {
			System.out.println("There must be only one '@' in the Email");
		}else if(code ==3) {
			System.out.println("Email should contain domain name");
		}else if(code == 4) {
			System.out.println("There must be a '@' in the email");
		}else if(code == 5) {
			System.out.println("There must be a '.' after '@'");
		}else if(code == 6) {
			System.out.println("Email can't ended with '.'");
		}
	}
	
	private boolean validatePassword(String pass) {
		if(pass.isBlank()) 
			return false;
		
		if(pass.length() < 8 || pass.length() > 30)
			return false;
		
		return true;
		
	}
	
	private void gamePage(Player p) {
		System.out.println("[WELCOME TO THE GAME]\n");
		System.out.println("1. Start Game");
		System.out.println("2. Game Guide");
		System.out.println("3. Exit Game");
		String in = "";
		
		do {
			System.out.print(">> ");
			in = sc.nextLine();
		}while(validateInput(in) == false);
		
		switch(in) {
		case "1": new Run(p);break;
		case "2": gameGuide(); break;
		case "3": return;
		}
		
	}
	
	private boolean validateInput(String in) {
		if(in.equals("1") || in.equals("2") || in.equals("3")) {
			return true;
		}
		
		return false;
	}
	
	private void gameGuide() {
		System.out.println("\n[GAME GUIDE]\n");
		
		System.out.println("Hello this is Hendra writing, this game is inspired by the case maker favorite game of all time that is DOTA,");
		System.out.println("the game is really simple where you move inside the game using controls. In the game you can collect coins as you move.");
		System.out.println("You can also meet enemies while going through the grass in the game. If you killed an enemy you will be rewarded money");
		System.out.println("that you can use again to buy the item\n\n");
		
		System.out.println("Characater Informations: \n");
		System.out.println("Coins : 0");
		System.out.println("Grass : v | V");
		System.out.println("Character : X");
		System.out.println("Wall : #");
		
		System.out.println("Keybind Information: ");
		System.out.println("w        : Move up");
		System.out.println("a        : Move left");
		System.out.println("s        : Move down");
		System.out.println("d        : Move right");
		System.out.println("i        : Show all character item");
		System.out.println("z        : Shop menu");
		System.out.println("e        : Exit game and save your information");
		
		System.out.println("\nEnter To Continue..");
		sc.nextLine();
		System.out.println();
		
	}
	
	

}
