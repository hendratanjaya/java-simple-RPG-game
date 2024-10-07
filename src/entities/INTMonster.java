package entities;

import java.util.Random;


import attributes.BonusAttack;

public class INTMonster extends Monster implements BonusAttack{
	private Random rand = new Random();
	public INTMonster(String name, int damage,int health, String type) {
		// TODO Auto-generated constructor stub
		super(name,damage,health,type);
	}

	@Override
	public double enhanceAttack(double damage, double bonus) {
		bonus = rand.nextInt(20) + 30;
		return bonus + damage;
	}

	@Override
	public void attacked(double damage) {
		double health = this.getHealth() - damage > 0 ? this.getHealth() - damage : 0;
		this.setHealth(health);
		
	}

	@Override
	public double attacking() {
		double damage = enhanceAttack(this.getDamage(),0);
		return damage;
	}

	

}
