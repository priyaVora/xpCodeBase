package csc130.pente.team3;

import java.io.Serializable;

public class Player implements Serializable{
	private static final long serialVersionUID = 1L;
	private String name;
	private int capturedStonesValue;
	private boolean isFirstPlayer;

	public Player(String name, boolean isFirstPLayer) {
		setName(name);
		setCapturedStonesValue(0);
		setFirstPlayer(isFirstPLayer);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCapturedStonesValue() {
		return capturedStonesValue;
	}

	public void setCapturedStonesValue(int capturedStonesValue) {
		this.capturedStonesValue = capturedStonesValue;
	}

	public boolean isFirstPlayer() {
		return isFirstPlayer;
	}

	public void setFirstPlayer(boolean isFirstPlayer) {
		this.isFirstPlayer = isFirstPlayer;
	}

	public void addCapturedStones(int i) {
		this.setCapturedStonesValue(this.getCapturedStonesValue() + i);
		
	} 
	
}
