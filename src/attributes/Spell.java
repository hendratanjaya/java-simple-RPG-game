package attributes;

public class Spell extends Item {
	private double damage;
	private double mana;
	public Spell(String id,String name, String type, int price, double damage,double mana) {
		super(id,name,type,price);
		this.setDamage(damage);
		this.setMana(mana);
	}
	
	public Spell(Spell x) {
		super(x.getId(),x.getName(),x.getType(),x.getPrice());
		this.setDamage(x.getDamage());
		this.setMana(x.getMana());
		
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
	
	
}
