package fileManagement;
import java.util.ArrayList;


import entities.*;
import attributes.*;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
public class manageFile {
	
	private ArrayList<Item> itemList = new ArrayList<>();
	private ArrayList<String> strList = new ArrayList<>();
	private ArrayList<String> intList = new ArrayList<>();
	private ArrayList<String> agiList = new ArrayList<>();
	private ArrayList<Player> playerList = new ArrayList<>();
	
	private BufferedReader rd;
	public manageFile() {
		addItemFromFile();
		addMonsterFromFile();
		addUserFromFile();
	}
	
	public ArrayList<String> getStrMonster(){
		return this.strList;
	}
	public ArrayList<String> getIntMonster(){
		return this.intList;
	}
	public ArrayList<String> getAgiMonster(){
		return this.agiList;
	}
	
	public ArrayList<Item> getItem(){
		return this.itemList;
	}
	
	public ArrayList<Player> getPlayer(){
		return this.playerList;
	}
	
	private void addItemFromFile() {
		
		try {
			rd = new BufferedReader(new FileReader("file\\item.txt"));
			String line;
			Item items;
			while((line = rd.readLine()) != null) {
				String[] parts = line.split("#");
				String id = parts[0];
				String name = parts[1];
				String type = parts[2];
				int price = Integer.parseInt(parts[3]);
				
				if(type.equals("spell")) {
					int damage = Integer.parseInt(parts[4]);
					int mana = Integer.parseInt(parts[5]);
					items = new Spell(id,name,type,price,damage,mana);
					itemList.add(items);
				}else if(type.equals("offensive")) {
					int damage = Integer.parseInt(parts[4]);
					int max_use = Integer.parseInt(parts[5]);
					items = new Offensive(id,name,type,price,damage,max_use,0);
					itemList.add(items);
					
				}else if(type.equals("defensive")) {
					int deflect = Integer.parseInt(parts[4]);
					int max_use = Integer.parseInt(parts[5]);
					items = new Defensive(id,name,type,price,deflect,max_use,0);
					itemList.add(items);
				}
				
			}
			
			rd.close();
			
		} catch (FileNotFoundException e) {
			
			System.out.println("File Not Found");
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}
	
	private void addMonsterFromFile() {
		
		try {
			rd = new BufferedReader(new FileReader("file\\hero.txt"));
			
			String line;
			int counter = 0;
			while((line = rd.readLine()) != null) {
				String[] parts = line.split("#");
				if(counter == 0) {
					for(String x : parts) {
						strList.add(x);
					}
				}else if(counter == 1) {
					for(String x : parts) {
						intList.add(x);
					}
				}else {
					for(String x : parts) {
						agiList.add(x);
					}
				}
				
				counter++;
				
			}
			
			rd.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("File Not Found");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void addUserFromFile() {
		try {
			rd = new BufferedReader(new FileReader("file\\credential.txt"));
			
			String line;
			Player p;
			while((line = rd.readLine()) != null) {
				String[] parts = line.split("#");
				ArrayList<Item> userItem = new ArrayList<>();
				if(parts.length == 6) {
					
					userItem = getItemFromUserData(parts[5]);
				}
				
				p = new Player(parts[0],parts[1],Integer.parseInt(parts[2]),Double.parseDouble(parts[3]),Double.parseDouble(parts[4]),userItem);
				playerList.add(p);
			}
			
			rd.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("File Not Found");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private Item getItemFromList (String id) {
		
		for(Item x : itemList) {
			if(x.getId().equals(id)) 
				return x;
		}
		return null;
	}
	private ArrayList<Item> getItemFromUserData(String parts){
		
		String[] items = parts.split("-");
		ArrayList<Item> userItem = new ArrayList<>();
		for(String x : items) {
			String []detail = x.split("@");
			Item item = getItemFromList(detail[0]);
			int usage = Integer.parseInt(detail[1]);
			if(item instanceof Defensive) {
				Defensive tmp = (Defensive)item;
				userItem.add(new Defensive(tmp.getId(),tmp.getName(),tmp.getType(),tmp.getPrice(),tmp.getDeflect(),tmp.getMaxUse(),usage));
			}
			else if(item instanceof Offensive) {
				Offensive tmp = (Offensive)item;
				userItem.add(new Offensive(tmp.getId(),tmp.getName(),tmp.getType(),tmp.getPrice(),tmp.getDamage(),tmp.getMaxUse(),usage));
			}
			else if(item instanceof Spell) {
				Spell tmp = (Spell)item;
				userItem.add(new Spell(tmp.getId(),tmp.getName(),tmp.getType(),tmp.getPrice(),tmp.getDamage(),tmp.getMana()));
			}
		}
		return userItem;
	}
	
	public void insertIntoFile() {
		try {
			BufferedWriter rt = new BufferedWriter(new FileWriter("file\\credential.txt"));
			rt.close();
			ArrayList<String> player =new ArrayList<>();
			for(Player x : this.playerList) {
				
				String detail = String.format("%s#%s#%d#%.2f#%.2f#",x.getEmail(),x.getPassword(),x.getMoney(),x.getHealth(),x.getMana());
				
				if(x.getItemList().isEmpty()) {
					detail = detail+"\n";
					player.add(detail);
					
				}else {
					String items = "";
					for(Item i : x.getItemList()) {
						items = items+i.getId()+"@";
						if(i instanceof Spell) {
							items += '0';
						}else if(i instanceof Offensive) {
							items += String.format("%d",((Offensive)i).getItemUsed());
						}else if(i instanceof Defensive) {
							items += String.format("%d",((Defensive)i).getItemUsed());
						}
						if(i.getId().equals(x.getItemList().get(x.getItemList().size()-1).getId())) {
							items += "\n";
						}else {
							items += '-';
						}
					}
					detail += items;
					player.add(detail);
				}
				
			}
			
			rt = new BufferedWriter(new FileWriter("file\\credential.txt",true));
			for(String  x: player) {
				rt.append(x);
				
			}
			rt.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	
}
