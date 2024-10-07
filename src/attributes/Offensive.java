package attributes;

public class Offensive extends Item{
	private double damage;
	private int maxUse;
	private int itemUsed;
	private int useLeft;
	
	public Offensive(String id, String name, String type,int price, double damage, int maxUse, int itemUsed) {
		super(id,name,type,price);
		this.setDamage(damage);
		this.setMaxUse(maxUse);
		this.setItemUsed(itemUsed);
		this.setUseLeft();
	}
	
	public Offensive(Offensive x) {
		super(x.getId(),x.getName(),x.getType(),x.getPrice());
		this.setDamage(x.getDamage());
		this.setMaxUse(x.getMaxUse());
		this.setItemUsed(x.getItemUsed());
		this.setUseLeft();
	}
	public double getDamage() {
		return damage;
	}
	public void setDamage(double damage) {
		this.damage = damage;
	}
	public int getMaxUse() {
		return maxUse;
	}
	public void setMaxUse(int maxUse) {
		this.maxUse = maxUse;
	}
	public int getUseLeft() {
		return useLeft;
	}
	public void setUseLeft() {
		this.useLeft = this.maxUse - this.itemUsed;
	}
	public int getItemUsed() {
		return itemUsed;
	}
	public void setItemUsed(int itemUsed) {
		this.itemUsed = itemUsed;
	}

}
