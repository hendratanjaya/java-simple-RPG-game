package attributes;

public class Defensive extends Item{
	private double deflect;
	private int maxUse;
	private int itemUsed;
	private int useLeft;
	public Defensive(String id, String name, String type, int price, double deflect, int maxUse, int itemUsed) {
		super(id, name, type, price);
		this.setDeflect(deflect);
		this.setMaxUse(maxUse);
		this.setItemUsed(itemUsed);
		this.useLeft = maxUse - itemUsed;
	}
	
	public Defensive(Defensive x) {
		super(x.getId(),x.getName(),x.getType(),x.getPrice());
		this.setDeflect(x.getDeflect());
		this.setMaxUse(x.getMaxUse());
		this.setItemUsed(x.getItemUsed());
		this.setUseLeft();
	}
	public double getDeflect() {
		return deflect;
	}
	public void setDeflect(double deflect) {
		this.deflect = deflect;
	}
	public int getMaxUse() {
		return maxUse;
	}
	public void setMaxUse(int maxUse) {
		this.maxUse = maxUse;
	}
	public int getItemUsed() {
		return itemUsed;
	}
	public void setItemUsed(int itemUsed) {
		this.itemUsed = itemUsed;
	}
	public int getUseLeft() {
		return useLeft;
	}
	public void setUseLeft() {
		this.useLeft = this.maxUse - this.itemUsed;
	}
	
	
	
	
	

}
