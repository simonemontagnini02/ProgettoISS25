package main.java.domain;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unibo.basicomm23.utils.CommUtils;

public class Slot {
	
	private int slotId;
	private int productId;
	private static final Logger logger = LoggerFactory.getLogger(Slot.class);
	
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
	
	private static int getJsonInt(String jsonStr, String key)  {
		try {
			JSONObject j = (JSONObject) new JSONParser().parse(jsonStr);
			long jsl = (long) j.get(key);
			return Long.valueOf(jsl).intValue();
		}catch(ParseException e) {
			return 0;
		}
	}
	
	public Slot(String jsonStr)   {
		this( getJsonInt(jsonStr, "slotId"), getJsonInt(jsonStr, "productId"));
		//CommUtils.outmagenta("Slot | creation json : " + jsonStr);
		logger.info( "Slot | created json:"+ this.toString() + " in tread:" + Thread.currentThread().getId());
	}
	
}
