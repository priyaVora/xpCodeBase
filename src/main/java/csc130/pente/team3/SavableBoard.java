package csc130.pente.team3;

import java.io.Serializable;

public class SavableBoard implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Stone[][] locations;
	private Player player1;
	private Player player2;
	private int turnNumber;
	
	public SavableBoard(Stone[][] locations, Player player1, Player player2, int turnNumber) {
		super();
		this.locations = locations;
		this.player1 = player1;
		this.player2 = player2;
		this.setTurnNumber(turnNumber);
	}

	public Stone[][] getLocations() {
		return locations;
	}

	public void setLocations(Stone[][] locations) {
		this.locations = locations;
	}

	public Player getPlayer1() {
		return player1;
	}

	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getTurnNumber() {
		return turnNumber;
	}

	public void setTurnNumber(int turnNumber) {
		this.turnNumber = turnNumber;
	}

	
	
}
