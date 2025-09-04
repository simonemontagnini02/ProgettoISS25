package main.java.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.java.domain.adapter.AdapterStorage;
import unibo.basicomm23.utils.CommUtils;

public class Hold implements IHold {

    private int length;
    private int width;
    private int totalWeight;
    private final int maxLoad;
    private Slot[] slots;
    private IStorage dataStore = AdapterStorage.setup();
    private  final Logger logger  = LoggerFactory.getLogger(Hold.class);

    private static Hold instance = null;

    private Hold() {
        this.length = 10;
        this.width = 10;
        this.totalWeight = 0;
        this.maxLoad = 500;
        slots = new Slot[4];
        for (int i = 0; i < 4; i++) {
        	String slotIdStr = dataStore.getItem(i);
        	if(slotIdStr == null) {
        		slots[i] = new Slot(i, -1);
        	} else {
        		slots[i] = new Slot(slotIdStr);
        	}
        }
    }

    public static synchronized Hold getInstance() {
        if (instance == null) {
            instance = new Hold();
        }
        return instance;
    }

    @Override
    public void addContainer(int slotId, int productId, int weight) {
        int res = createSlot(slots[slotId], productId);
        totalWeight += weight;
    }
    
    private int createSlot(Slot slot, int productId) {
    	CommUtils.outblue( "Hold | creatingSlot:"+ slot.toString() );
    	logger.info( "Hold | creatingSlot:"+ slot.toString() );
    	int slotId = slot.getSlotId();
    	int slotAnswer;
    	String slotIdStr = dataStore.getItem(slotId);
    	CommUtils.outgreen("Hold | createSlot " + slotId + " slotIdStr: " + slotIdStr);
    	
    	if ( slotIdStr == null ) { 
      		CommUtils.delay(4000);
			dataStore.createItem(slotId,slot.toString());
			slots[slotId].setProductId(productId);
			slotAnswer = slotId;
	   } else {  //ESISTE GIA' UNO SLOT CON LO STESSO SLOTID
		   CommUtils.outmagenta("Hold | WARNING - Duplicate key, Slot Id: " + slotId);
		   slots[slotId] = new Slot(slotIdStr);
		   slotAnswer = -1;
	   } 
	   logger.info( "Hold | createSlot:"+ slotAnswer );
	   return slotAnswer;
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