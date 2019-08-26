package csc130.pente.team3;

import java.io.Serializable;

public class Stone implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Player owner;
	private int locationX;
	private int locationY;

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}

	public int getLocationX() {
		return locationX;
	}

	public void setLocationX(int locationX) {
		this.locationX = locationX;
	}

	public int getLocationY() {
		return locationY;
	}

	public void setLocationY(int locationY) {
		this.locationY = locationY;
	}
	
	
}
