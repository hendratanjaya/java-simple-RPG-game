package entities;

import java.util.ArrayList;
import attributes.*;
public class Player implements AttackMechanism,DefenseMechanism, BonusAttack{
	
	private String email;
	private String password;
	private double damage;
	private double mana;
	private double health;
	private int money;
	private int xpos;
	private int ypos;
	private ArrayList<Item> itemList = new ArrayList<>();
	private Item activeItem;
	public Player(String email, String password) {
		
		this.setEmail(email);
		this.setPassword(password);
		this.setDamage(30);
		this.setMana(30);
		this.setHealth(300);
		this.setMoney(100);
		this.setXpos(333/2);
		this.setYpos(313/2);
		this.setItemList(new ArrayList<Item>());
		this.setActiveItem(null);
		
	}
	public Player(String email, String password, int money,double health,double mana,ArrayList<Item> list) {
		
		this.setEmail(email);
		this.setPassword(password);
		this.setHealth(health);
		this.setMoney(money);
		this.setMana(mana);
		
		this.setDamage(30);
		this.setXpos(333/2);
		this.setYpos(313/2);
		this.setItemList(list);
		this.setActiveItem(null);
	}
	
	
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public double getDamage() {
		return damage;
	}
	public void setDamage(double damage) {
		this.damage = damage;
	}
	public double getMana() {
		return mana;
	}
	public void setMana(double mana) {
		this.mana = mana;
	}
	public double getHealth() {
		return health;
	}
	public void setHealth(double d) {
		this.health = d;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public int getXpos() {
		return xpos;
	}
	public void setXpos(int xpos) {
		this.xpos = xpos;
	}
	public int getYpos() {
		return ypos;
	}
	public void setYpos(int ypos) {
		this.ypos = ypos;
	}
	public ArrayList<Item> getItemList() {
		return itemList;
	}
	public void setItemList(ArrayList<Item> itemList) {
		this.itemList = itemList;
	}
	public Item getActiveItem() {
		return activeItem;
	}
	public void setActiveItem(Item activeItem) {
		this.activeItem = activeItem;
	}
	
	public void reset(Player x) {
		this.setEmail(x.getEmail());
		this.setPassword(x.getPassword());
		this.setDamage(x.getDamage());
		this.setMana(30);
		this.setHealth(300);
		this.setMoney(100);
		this.setXpos(x.getXpos());
		this.setYpos(x.getYpos());
		this.setItemList(new ArrayList<>());
		this.setActiveItem(null);
	}
	
	public void update(Player x) {
		this.setEmail(x.getEmail());
		this.setPassword(x.getPassword());
		this.setDamage(x.getDamage());
		this.setMana(x.getMana());
		this.setHealth(x.getHealth());
		this.setMoney(x.getMoney());
		this.setItemList(x.getItemList());
		this.setActiveItem(null);
	}
	
	
	public void move(int deltX, int deltY, String[] map) {
		
		
		int xpos = this.getXpos();
		int ypos = this.getYpos();
		
		if(xpos + deltX< 17) deltX = 0; 
		else if(xpos + deltX > 316) deltX = 0;
		
		if(ypos + deltY < 7) deltY = 0;
		else if(ypos + deltY >306) deltY = 0;
		
		
		char[] temp = map[deltY + ypos].toCharArray();
		
		if(temp[deltX + xpos] == '#') {
			deltX = 0;
			deltY = 0;
		}else {
			xpos += deltX;
			ypos += deltY;
		}
		this.setXpos(xpos);
		this.setYpos(ypos);
	}
	
	@Override
	public void attacked(double damage) {
		
		if(this.getActiveItem() != null) {
			Defensive item = ((Defensive)this.getActiveItem());
			double deflected = deflectDamage(damage, item.getDeflect());
			damage = deflected > 0 ? deflected: 0;
			
			item.setItemUsed(item.getItemUsed() + 1);
			item.setUseLeft();
			if(item.getUseLeft() == 0) {
				this.getItemList().remove(this.getItemList().indexOf(item));
			}
		}
		
		double health = this.getHealth()-damage > 0? this.getHealth()-damage: 0;
		this.setHealth(health);
		
		this.setActiveItem(null);
		
		
	}
	@Override
	public double attacking() {
		double damage = 0;;
		if(this.getActiveItem() != null) {
			if(this.getActiveItem() instanceof Offensive) {
				Offensive item = ((Offensive)this.getActiveItem());
				item.setItemUsed(item.getItemUsed()+1);
				item.setUseLeft();
				damage = enhanceAttack(this.getDamage(), item.getDamage());
				if(item.getUseLeft() == 0) {
					this.getItemList().remove(this.getItemList().indexOf(item));
				}
			}else if(this.getActiveItem() instanceof Spell){
				
				Spell item = ((Spell)this.getActiveItem());
				this.setMana(this.getMana()-item.getMana());
				damage = enhanceAttack(this.getDamage() ,item.getDamage());
				
			}
			
		}else {
			damage = this.getDamage();
		}
		return damage;
	}
	@Override
	public double enhanceAttack(double damage, double bonus) {
		
		return damage + bonus;
	}
	@Override
	public double deflectDamage(double damage, double defense) {
		double deflected = damage - defense;
		
		return deflected;
	}
	
	


	

}
