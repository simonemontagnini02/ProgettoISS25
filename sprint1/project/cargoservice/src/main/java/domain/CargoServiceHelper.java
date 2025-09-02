package main.java.domain;

public class CargoServiceHelper {

    private static final Hold hold = Hold.getInstance();

    private CargoServiceHelper() {
    }

    public static int handleLoadRequest(int productId, int weight) {
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

    public static boolean checkWeight(int weight) {
        if(hold.getTotalWeight() + weight <= hold.getMaxLoad())
        	return true;
        else
        	return false;
    }

    public static int checkFreeSlots() {
        Slot[] slots = hold.getSlots();
        for (Slot slot : slots) {
            if (slot.getProductId() == -1) {
                return slot.getSlotId();
            }
        }
        return -1;
    }
}
