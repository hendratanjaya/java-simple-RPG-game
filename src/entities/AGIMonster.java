package entities;


import attributes.BonusAttack;


public class AGIMonster extends Monster implements BonusAttack{
	private int critical;
	public AGIMonster(String name,int damage, int health,int critical,String type) {
		
		super(name,damage,health,type);
		this.setCritical(critical);
	}
	public int getCritical() {
		return critical;
	}
	public void setCritical(int critical) {
		this.critical = critical;
	}
	
	@Override
	public void attacked(double damage) {
		// TODO Auto-generated method stu
		double health = this.getHealth() - damage > 0 ? this.getHealth() - damage : 0;
		this.setHealth(health);
		
	}
	@Override
	public double attacking() {
		double damage = enhanceAttack(this.getDamage(),this.getCritical());
		return damage;
	}
	@Override
	public double enhanceAttack(double damage, double bonus) {
		// TODO Auto-generated method stub
		return damage * bonus;
	}
	
	

}
