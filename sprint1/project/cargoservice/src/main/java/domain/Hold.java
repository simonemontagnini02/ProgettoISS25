package main.java.domain;

public class Hold implements IHold {

    private int length;
    private int width;
    private int totalWeight;
    private final int maxLoad;
    private Slot[] slots;

    // 🔹 Istanza unica della classe
    private static Hold instance = null;

    // 🔹 Costruttore privato
    private Hold() {
        this.length = 10;
        this.width = 10;
        this.totalWeight = 0;
        this.maxLoad = 500;
        slots = new Slot[4];
        for (int i = 0; i < 4; i++) {
            slots[i] = new Slot(i + 1, -1);
        }
    }

    // 🔹 Metodo pubblico per ottenere l’istanza
    public static synchronized Hold getInstance() {
        if (instance == null) {
            instance = new Hold();
        }
        return instance;
    }

    @Override
    public void addContainer(int slotId, int productId, int weight) {
        slots[slotId - 1].setProductId(productId);
        // aggiorno anche il peso totale
        totalWeight += weight;
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public int getTotalWeight() {
        return totalWeight;
    }

    public int getMaxLoad() {
        return maxLoad;
    }

    public Slot[] getSlots() {
        return slots;
    }
}