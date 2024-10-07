package entities;


import attributes.DefenseMechanism;


public class STRMonster extends Monster implements DefenseMechanism{
	
	private double armor;
	private boolean activeItem;
	public STRMonster(String name,int damage, int health, int armor,String type) {
		// TODO Auto-generated constructor stub
		super(name,damage,health,type);
		this.setArmor(armor);
		this.setActiveItem(false);
	}
	public double getArmor() {
		return armor;
	}
	public void setArmor(double armor) {

		this.armor = armor;
	}
	
	public boolean getActiveItem() {
		return activeItem;
	}
	public void setActiveItem(boolean activeItem) {
		this.activeItem = activeItem;
	}
	
	@Override
	public void attacked(double damage) {
		// TODO Auto-generated method stub
		if(activeItem == true) {
			damage = deflectDamage(damage,this.getArmor());
			this.setActiveItem(false);
		}
			
		
		double health = this.getHealth()-damage >0? this.getHealth()-damage:0;
		this.setHealth(health);
	}
	
	@Override
	public double attacking() {
		
		return this.getDamage();
	}
	
	@Override
	public double deflectDamage(double damage, double defense) {
		damage = damage - defense> 0? damage-defense:0;
		return damage;
	}
	
	
	
	
	
}
