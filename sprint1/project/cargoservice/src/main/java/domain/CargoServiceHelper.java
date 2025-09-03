package main.java.domain;

public class CargoServiceHelper {
	private static CargoServiceHelper csh;
    private final Hold hold;
    
    public static CargoServiceHelper getSingleton() {
		if( csh == null )   csh = new CargoServiceHelper();
		return csh;
	}

    public CargoServiceHelper() {
        this.hold = Hold.getInstance();
    }

    public int handleLoadRequest(int productId, int weight) {
        if (!checkWeight(weight)) {
            return -1;
        }

        int freeSlot = checkFreeSlots();
        if (freeSlot == -1) {
            return -2;
        }

        hold.addContainer(freeSlot, productId, weight);
        return freeSlot;
    }

    public boolean checkWeight(int weight) {
        return hold.getTotalWeight() + weight <= hold.getMaxLoad();
    }

    public int checkFreeSlots() {
        Slot[] slots = hold.getSlots();
        for (Slot slot : slots) {
            if (slot.getProductId() == -1) {
                return slot.getSlotId();
            }
        }
        return -1;
    }
}
