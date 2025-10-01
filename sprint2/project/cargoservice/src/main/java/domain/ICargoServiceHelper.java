package main.java.domain;

public interface ICargoServiceHelper {
	
	public int handleLoadRequest(int productId);
	public boolean checkWeight();
	public int checkFreeSlots();
	
}
