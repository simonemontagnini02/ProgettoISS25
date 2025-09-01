package main.java.domain;

public class Slot {
	
	private int slotId;
	private int productId;
	
	public Slot(int slotId, int productId) {
		super();
		this.slotId = slotId;
		this.productId = productId;
	}

	public int getProductId() {
		return productId;
	}
	
	public int getSlotId() {
		return slotId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}
	
}
