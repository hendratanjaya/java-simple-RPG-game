package entities;

import attributes.AttackMechanism;

public abstract class Monster implements AttackMechanism{
	
	private String name;
	private double damage;
	private double health;
	private String type;
	public Monster(String name,int damage, int health,String type){
		this.setName(name);
		this.setDamage(damage);
		this.setHealth(health);
		this.setType(type);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getDamage() {
		return damage;
	}
	public void setDamage(int damage) {
		this.damage = damage;
	}
	public double getHealth() {
		return health;
	}
	public void setHealth(double health) {
		this.health = health;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	
	
}
