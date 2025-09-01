package main.java.domain;

public class CargoServiceHelper {

    // Recupera sempre la stessa Hold singleton
    private static final Hold hold = Hold.getInstance();

    private CargoServiceHelper() {
        // Costruttore privato per impedire istanziazione
    }

    public static int handleLoadRequest(int productId, int weight) {
        // Controlla peso e slot liberi
        if (!checkWeight(weight)) {
            return -1; // richiesta rifiutata per superamento peso
        }

        int freeSlot = checkFreeSlots();
        if (freeSlot == -1) {
            return -2; // richiesta rifiutata per mancanza di slot
        }

        // Se tutto ok → aggiunge container
        hold.addContainer(freeSlot, productId, weight);
        return freeSlot; // ritorna slot assegnato
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
            if (slot.getProductId() == -1) { // -1 significa libero
                return slot.getSlotId();
            }
        }
        return -1; // nessuno slot libero
    }
}
